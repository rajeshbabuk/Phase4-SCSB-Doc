package org.recap.util;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.jpa.DeaccessionItemChangeLog;
import org.recap.model.reports.ReportsRequest;
import org.recap.model.reports.ReportsResponse;
import org.recap.model.search.DeaccessionItemResultsRow;
import org.recap.model.solr.Item;
import org.recap.repository.jpa.DeaccesionItemChangeLogDetailsRepository;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rajeshbabuk on 13/1/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({SolrTemplate.class, SolrClient.class})
public class ReportsServiceUtilUT extends BaseTestCaseUT4 {

    @InjectMocks
    ReportsServiceUtil reportsServiceUtil;

    @Mock
    private DateUtil dateUtil;

    @Mock
    private SolrQueryBuilder solrQueryBuilder;

    @Mock
    private CommonUtil commonUtil;

    @Mock
    private DeaccesionItemChangeLogDetailsRepository deaccesionItemChangeLogDetailsRepository;

    @Before
    public void setup()throws Exception{
        MockitoAnnotations.initMocks(this);
        Mockito.when(dateUtil.getFromDateAccession(Mockito.any())).thenCallRealMethod();
        Mockito.when(dateUtil.getToDateAccession(Mockito.any())).thenCallRealMethod();
    }

    @Test
    public void populateAccessionDeaccessionItemCounts() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String requestedFromDate = simpleDateFormat.format(new Date());
        String requestedToDate = simpleDateFormat.format(new Date());

        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setAccessionDeaccessionFromDate(requestedFromDate);
        reportsRequest.setAccessionDeaccessionToDate(requestedToDate);
        reportsRequest.setOwningInstitutions(Arrays.asList("CUL", "PUL", "NYPL"));
        reportsRequest.setCollectionGroupDesignations(Arrays.asList("Private", "Open", "Shared"));

        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        ReflectionTestUtils.setField(reportsServiceUtil,"solrTemplate",mocksolrTemplate1);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        QueryResponse queryResponse=Mockito.mock(QueryResponse.class);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        GroupResponse groupResponse=Mockito.mock(GroupResponse.class);
        Mockito.when(queryResponse.getGroupResponse()).thenReturn(groupResponse);
        List<GroupCommand> values=new ArrayList<>();
        GroupCommand groupCommand=new GroupCommand(ScsbCommonConstants.IS_DELETED_ITEM,1);
        SolrDocumentList solrDocumentList=new SolrDocumentList();
        SolrDocument solrDocument=new SolrDocument();
        solrDocument.setField(ScsbCommonConstants.IS_DELETED_ITEM,true);
        solrDocumentList.add(solrDocument);
        Group group=new Group(ScsbCommonConstants.IS_DELETED_ITEM,solrDocumentList);
        groupCommand.add(group);
        values.add(groupCommand);
        Mockito.when(groupResponse.getValues()).thenReturn(values);
        Map<String, FieldStatsInfo> getFieldStatsInfo=new HashMap<>();
        FieldStatsInfo fieldStatsInfo=Mockito.mock(FieldStatsInfo.class);;
        getFieldStatsInfo.put(ScsbCommonConstants.BARCODE,fieldStatsInfo);
        Mockito.when(queryResponse.getFieldStatsInfo()).thenReturn(getFieldStatsInfo);
        SolrQuery query=new SolrQuery("testquery");
        Mockito.when(solrQueryBuilder.buildSolrQueryForAccessionReports(Mockito.any(),Mockito.anyString(),Mockito.anyBoolean(),Mockito.anyString())).thenReturn(query);
        Mockito.when(solrQueryBuilder.buildSolrQueryForDeaccessionReports(Mockito.any(),Mockito.anyString(),Mockito.anyBoolean(),Mockito.anyString())).thenReturn(query);
        ReportsResponse reportsResponse = reportsServiceUtil.populateAccessionDeaccessionItemCounts(reportsRequest);
        assertNotNull(reportsResponse);
    }

    @Test
    public void populateCGDItemCounts() throws Exception {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setOwningInstitutions(Arrays.asList("CUL", "PUL", "NYPL"));
        reportsRequest.setCollectionGroupDesignations(Arrays.asList("Private", "Open", "Shared"));
        SolrQuery query=new SolrQuery("testquery");
        Mockito.when(solrQueryBuilder.buildSolrQueryForCGDReports(Mockito.anyString(),Mockito.anyString())).thenReturn(query);
        Mockito.when(solrQueryBuilder.buildSolrQueryForDeaccesionReportInformation(Mockito.any(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(query);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        ReflectionTestUtils.setField(reportsServiceUtil,"solrTemplate",mocksolrTemplate1);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        QueryResponse queryResponse=Mockito.mock(QueryResponse.class);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        GroupResponse groupResponse=Mockito.mock(GroupResponse.class);
        Mockito.when(queryResponse.getGroupResponse()).thenReturn(groupResponse);
        List<GroupCommand> values=new ArrayList<>();
        GroupCommand groupCommand=new GroupCommand(ScsbCommonConstants.IS_DELETED_ITEM,1);
        SolrDocumentList solrDocumentList=new SolrDocumentList();
        SolrDocument solrDocument=new SolrDocument();
        solrDocument.setField(ScsbCommonConstants.IS_DELETED_ITEM,true);
        solrDocumentList.add(solrDocument);
        Group group=new Group(ScsbCommonConstants.IS_DELETED_ITEM,solrDocumentList);
        groupCommand.add(group);
        values.add(groupCommand);
        Mockito.when(groupResponse.getValues()).thenReturn(values);
        Map<String, FieldStatsInfo> getFieldStatsInfo=new HashMap<>();
        FieldStatsInfo fieldStatsInfo=Mockito.mock(FieldStatsInfo.class);;
        getFieldStatsInfo.put(ScsbCommonConstants.BARCODE,fieldStatsInfo);
        Mockito.when(queryResponse.getFieldStatsInfo()).thenReturn(getFieldStatsInfo);
        ReportsResponse reportsResponse = reportsServiceUtil.populateCgdItemCounts(reportsRequest);
        assertNotNull(reportsResponse);
    }

    @Test
    public void populateIncompleteRecordsReport() throws Exception {
        Boolean[] booleans={true,false};
        for (Boolean b: booleans) {
            ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setIncompletePageSize(1);
        reportsRequest.setIncompletePageNumber(1);
        reportsRequest.setExport(b);
        reportsRequest.setOwningInstitutions(Arrays.asList("CUL", "PUL", "NYPL"));
        reportsRequest.setCollectionGroupDesignations(Arrays.asList("Private", "Open", "Shared"));
        SolrQuery query = new SolrQuery("testquery");
        Mockito.when(solrQueryBuilder.buildSolrQueryForIncompleteReports(reportsRequest.getIncompleteRequestingInstitution())).thenReturn(query);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        ReflectionTestUtils.setField(reportsServiceUtil, "solrTemplate", mocksolrTemplate1);
        SolrClient solrClient = PowerMockito.mock(SolrClient.class);
        QueryResponse queryResponse = Mockito.mock(QueryResponse.class);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        Mockito.when(solrClient.query(query, SolrRequest.METHOD.POST)).thenReturn(queryResponse);
        GroupResponse groupResponse = Mockito.mock(GroupResponse.class);
        Mockito.when(queryResponse.getGroupResponse()).thenReturn(groupResponse);
        List<GroupCommand> values = new ArrayList<>();
        GroupCommand groupCommand = new GroupCommand(ScsbCommonConstants.IS_DELETED_ITEM, 1);
        SolrDocumentList solrDocumentList = new SolrDocumentList();
        SolrDocument solrDocument = new SolrDocument();
        solrDocument.setField(ScsbCommonConstants.IS_DELETED_ITEM, true);
        solrDocument.setField(ScsbCommonConstants.BIB_ID, 1);
        solrDocumentList.add(solrDocument);
        Group group = new Group(ScsbCommonConstants.IS_DELETED_ITEM, solrDocumentList);
        groupCommand.add(group);
        values.add(groupCommand);
        Mockito.when(groupResponse.getValues()).thenReturn(values);
        Map<String, FieldStatsInfo> getFieldStatsInfo = new HashMap<>();
        FieldStatsInfo fieldStatsInfo = Mockito.mock(FieldStatsInfo.class);
        getFieldStatsInfo.put(ScsbCommonConstants.BARCODE, fieldStatsInfo);
        Mockito.when(queryResponse.getFieldStatsInfo()).thenReturn(getFieldStatsInfo);
            Item item = getItem();
            item.setItemCreatedDate(new Date());
        Mockito.when(commonUtil.getItem(Mockito.any())).thenReturn(item);
        Mockito.when(solrQueryBuilder.buildSolrQueryToGetBibDetails(Mockito.anyList(),Mockito.anyInt())).thenReturn(query);
        Mockito.when(queryResponse.getResults()).thenReturn(solrDocumentList);
        ReportsResponse reportsResponse = reportsServiceUtil.populateIncompleteRecordsReport(reportsRequest);
        assertNotNull(reportsResponse);
        }
    }

    @Test
    public void populateDeaccessionResults() throws Exception {
        ReportsResponse reportsResponse1 = new ReportsResponse();
        List<DeaccessionItemResultsRow> deaccessionItemResultsRowList = new ArrayList<>();
        deaccessionItemResultsRowList.add(new DeaccessionItemResultsRow());
        reportsResponse1.setDeaccessionItemResultsRows(deaccessionItemResultsRowList);
        SolrQuery query=new SolrQuery("testquery");
        Mockito.when(solrQueryBuilder.buildSolrQueryForDeaccesionReportInformation(Mockito.any(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(query);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        ReflectionTestUtils.setField(reportsServiceUtil,"solrTemplate",mocksolrTemplate1);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        QueryResponse queryResponse=Mockito.mock(QueryResponse.class);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        GroupResponse groupResponse=Mockito.mock(GroupResponse.class);
        Mockito.when(queryResponse.getGroupResponse()).thenReturn(groupResponse);
        Mockito.when(groupResponse.getValues()).thenReturn(getGroupCommands());
        Map<String, FieldStatsInfo> getFieldStatsInfo=new HashMap<>();
        FieldStatsInfo fieldStatsInfo=Mockito.mock(FieldStatsInfo.class);
        getFieldStatsInfo.put(ScsbCommonConstants.BARCODE,fieldStatsInfo);
        Mockito.when(queryResponse.getFieldStatsInfo()).thenReturn(getFieldStatsInfo);
        Mockito.when(commonUtil.getItem(Mockito.any())).thenReturn(getItem());
        Mockito.when(queryResponse.getResults()).thenReturn(getSolrDocuments());
        Mockito.when(deaccesionItemChangeLogDetailsRepository.findByRecordIdAndOperationTypeAndOrderByUpdatedDateDesc(Mockito.anyInt(),Mockito.anyString())).thenReturn(getDeaccessionItemChangeLogs());
        ReportsResponse reportsResponse = reportsServiceUtil.populateDeaccessionResults(getReportsRequest());
        assertNotNull(reportsResponse);
        assertNotNull(reportsResponse.getDeaccessionItemResultsRows());
        assertTrue(reportsResponse.getDeaccessionItemResultsRows().size() > 0);
        List<DeaccessionItemResultsRow> deaccessionItemResultsRows = reportsResponse.getDeaccessionItemResultsRows();
        assertNotNull(deaccessionItemResultsRows);
        assertTrue(deaccessionItemResultsRows.size() > 0);
    }

    private List<DeaccessionItemChangeLog> getDeaccessionItemChangeLogs() {
        List<DeaccessionItemChangeLog> itemChangeLogEntityList = new ArrayList<>();
        DeaccessionItemChangeLog deaccessionItemChangeLog = new DeaccessionItemChangeLog();
        deaccessionItemChangeLog.setRecordId(1);
        itemChangeLogEntityList.add(deaccessionItemChangeLog);
        return itemChangeLogEntityList;
    }

    private List<GroupCommand> getGroupCommands() {
        List<GroupCommand> values = new ArrayList<>();
        GroupCommand groupCommand = new GroupCommand(ScsbCommonConstants.IS_DELETED_ITEM, 1);
        Group group = new Group(ScsbCommonConstants.IS_DELETED_ITEM, getSolrDocuments());
        groupCommand.add(group);
        values.add(groupCommand);
        return values;
    }

    private SolrDocumentList getSolrDocuments() {
        SolrDocumentList solrDocumentList = new SolrDocumentList();
        SolrDocument solrDocument = new SolrDocument();
        solrDocument.setField(ScsbCommonConstants.IS_DELETED_ITEM, true);
        solrDocument.setField(ScsbCommonConstants.BIB_ID, 1);
        solrDocument.setField(ScsbConstants.TITLE_DISPLAY, "test");
        solrDocumentList.add(solrDocument);
        return solrDocumentList;
    }

    @Test
    public void testPopulateDeaccessionResultsForPageCount() throws Exception {
        ReportsResponse reportsResponse1 = new ReportsResponse();
        List<DeaccessionItemResultsRow> deaccessionItemResultsRowList = new ArrayList<>();
        deaccessionItemResultsRowList.add(new DeaccessionItemResultsRow());
        reportsResponse1.setDeaccessionItemResultsRows(deaccessionItemResultsRowList);
        SolrQuery query=new SolrQuery("testquery");
        Mockito.when(solrQueryBuilder.buildSolrQueryForDeaccesionReportInformation(Mockito.any(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(query);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        ReflectionTestUtils.setField(reportsServiceUtil,"solrTemplate",mocksolrTemplate1);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        QueryResponse queryResponse=Mockito.mock(QueryResponse.class);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        GroupResponse groupResponse=Mockito.mock(GroupResponse.class);
        Mockito.when(queryResponse.getGroupResponse()).thenReturn(groupResponse);
        Mockito.when(groupResponse.getValues()).thenReturn(getGroupCommands());
        Map<String, FieldStatsInfo> getFieldStatsInfo=new HashMap<>();
        FieldStatsInfo fieldStatsInfo=Mockito.mock(FieldStatsInfo.class);
        getFieldStatsInfo.put(ScsbCommonConstants.BARCODE,fieldStatsInfo);
        Mockito.when(queryResponse.getFieldStatsInfo()).thenReturn(getFieldStatsInfo);
        Mockito.when(getFieldStatsInfo.get(ScsbCommonConstants.BARCODE).getCountDistinct()).thenReturn(10l);
        Mockito.when(commonUtil.getItem(Mockito.any())).thenReturn(getItem());
        Mockito.when(queryResponse.getResults()).thenReturn(getSolrDocuments());
        Mockito.when(deaccesionItemChangeLogDetailsRepository.findByRecordIdAndOperationTypeAndOrderByUpdatedDateDesc(Mockito.anyInt(),Mockito.anyString())).thenReturn(getDeaccessionItemChangeLogs());
        ReportsResponse reportsResponse = reportsServiceUtil.populateDeaccessionResults(getReportsRequest());
        assertNotNull(reportsResponse);
        assertNotNull(reportsResponse.getDeaccessionItemResultsRows());
        assertTrue(reportsResponse.getDeaccessionItemResultsRows().size() > 0);
        List<DeaccessionItemResultsRow> deaccessionItemResultsRows = reportsResponse.getDeaccessionItemResultsRows();
        assertNotNull(deaccessionItemResultsRows);
        assertTrue(deaccessionItemResultsRows.size() > 0);
    }

    private ReportsRequest getReportsRequest() {
        ReportsRequest reportsRequest = new ReportsRequest();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        reportsRequest.setAccessionDeaccessionFromDate(simpleDateFormat.format(new Date()));
        reportsRequest.setAccessionDeaccessionToDate(simpleDateFormat.format(new Date()));
        reportsRequest.setDeaccessionOwningInstitution("PUL");
        return reportsRequest;
    }

    private Item getItem() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemBibIdList(Arrays.asList(1, 2, 3));
        item.setItemLastUpdatedDate(new Date());
        return item;
    }

}

package org.recap.model.solr;

import org.junit.jupiter.api.Test;
import org.recap.BaseTestCaseUT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 8/6/17.
 */
public class BibItemUT extends BaseTestCaseUT {

    @Test
    public void testBibItem() {
        BibItem bibItem = new BibItem();
        bibItem.setId("1");
        bibItem.setBibId(1);
        bibItem.setTitle("Title1");
        bibItem.setTitleDisplay("test");
        bibItem.setTitleSubFieldA("Title");
        bibItem.setAuthorDisplay("Author1");
        bibItem.setAuthorSearch(Arrays.asList("test"));
        bibItem.setBarcode("BC234");
        bibItem.setDocType("Bib");
        bibItem.setImprint("sample imprint");
        List<String> isbnList = new ArrayList<>();
        isbnList.add("978-3-16-148410-0");
        List<String> OclcNumber = new ArrayList<>();
        OclcNumber.add("978-3-16-148410-0");
        List<String> issnList = new ArrayList<>();
        issnList.add("978-3-16-148410-0");
        bibItem.setIsbn(isbnList);
        bibItem.setIssn(issnList);
        bibItem.setLccn("sample lccn");
        bibItem.setPublicationPlace("Texas");
        bibItem.setPublisher("McGraw Hill");
        bibItem.setPublicationDate("1998");
        bibItem.setSubject("Physics");
        bibItem.setNotes("Notes");
        bibItem.setOwningInstitution("PUL");
        bibItem.setOwningInstitutionBibId("1");
        bibItem.setRoot("_root_");
        bibItem.setOclcNumber(OclcNumber);
        bibItem.setMaterialType("test");
        bibItem.setLeaderMaterialType("test");
        bibItem.setOwningInstHoldingsIdList(Arrays.asList(1));
        bibItem.setTitleSort("test");
        bibItem.setBibCreatedBy("Guest");
        bibItem.setBibCreatedDate(new Date());
        bibItem.setBibLastUpdatedBy("Guest");
        bibItem.setBibLastUpdatedDate(new Date());
        List<Holdings> holdingsList  = new ArrayList<>();
        bibItem.setHoldingsList(holdingsList);
        bibItem.setHoldingsList(null);
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setItemId(1);
        item.setBarcode("BC234");
        item.setCallNumberSearch("123");
        item.setVolumePartYear("V1");
        item.setCustomerCode("NA");
        item.setAvailability("Available");
        items.add(item);
        bibItem.setItems(items);
        bibItem.setItems(null);
        bibItem.addItem(item);
        List<Holdings> holdings = new ArrayList<>();
        Holdings holding= new Holdings();
        holding.setId("1");
        holdings.add(holding);
        bibItem.addHoldings(holding);
        bibItem.setDeletedBib(true);


        assertNotNull(bibItem.getId());
        assertNotNull(bibItem.getBibId());
        assertNotNull(bibItem.getDocType());
        assertNotNull(bibItem.getBarcode());
        assertNotNull(bibItem.getTitle());
        assertNotNull(bibItem.getTitleDisplay());
        assertNotNull(bibItem.getTitleSubFieldA());
        assertNotNull(bibItem.getAuthorDisplay());
        assertNotNull(bibItem.getAuthorSearch());
        assertNotNull(bibItem.getOwningInstitution());
        assertNotNull(bibItem.getPublisher());
        assertNotNull(bibItem.getPublicationPlace());
        assertNotNull(bibItem.getPublicationDate());
        assertNotNull(bibItem.getSubject());
        assertNotNull(bibItem.getIsbn());
        assertNotNull(bibItem.getIssn());
        assertNotNull(bibItem.getOclcNumber());
        assertNotNull(bibItem.getMaterialType());
        assertNotNull(bibItem.getNotes());
        assertNotNull(bibItem.getLccn());
        assertNotNull(bibItem.getImprint());
        assertNotNull(bibItem.getHoldingsList());
        assertNotNull(bibItem.getHoldingsList());
        assertNotNull(bibItem.getOwningInstHoldingsIdList());
        assertNotNull(bibItem.getItems());
        assertNotNull(bibItem.getOwningInstitutionBibId());
        assertNotNull(bibItem.getLeaderMaterialType());
        assertNotNull(bibItem.getTitleSort());
        assertNotNull(bibItem.getBibCreatedBy());
        assertNotNull(bibItem.getBibCreatedDate());
        assertNotNull(bibItem.getBibLastUpdatedBy());
        assertNotNull(bibItem.getBibLastUpdatedDate());
        assertNotNull(bibItem.isDeletedBib());
        assertNotNull(bibItem.getRoot());


    }

}
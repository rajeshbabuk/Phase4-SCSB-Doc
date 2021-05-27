package org.recap.model.search;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by premkb on 2/8/16.
 */
public class SearchRecordsRequestUT {

    @Test
    public void testSearchRecordsRequest()throws Exception{
        SearchRecordsRequest searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.reset();
        searchRecordsRequest.resetPageNumber();
        setSearchRecordsRequest(searchRecordsRequest);
        assertNotNull(searchRecordsRequest);
        SearchRecordsRequest searchRecordsRequestNull = new SearchRecordsRequest();
        searchRecordsRequestNull.setSearchResultRows(null);
        assertNotNull(searchRecordsRequestNull.getSearchResultRows());
        assertEquals("Title1",searchRecordsRequest.getSearchResultRows().get(0).getTitle());
        assertEquals("Available",searchRecordsRequest.getAvailability().get(0));
        assertEquals("245",searchRecordsRequest.getFieldName());
        assertEquals("Stay Hungry",searchRecordsRequest.getFieldValue());
        assertEquals("NYPL",searchRecordsRequest.getOwningInstitutions().get(0));
        assertEquals("Shared",searchRecordsRequest.getCollectionGroupDesignations().get(0));
        assertEquals("Monograph",searchRecordsRequest.getMaterialTypes().get(0));
        assertEquals(Integer.valueOf(1),searchRecordsRequest.getTotalPageCount());
        assertEquals("1",searchRecordsRequest.getTotalBibRecordsCount());
        assertEquals("1",searchRecordsRequest.getTotalItemRecordsCount());
        assertEquals(Integer.valueOf(1),searchRecordsRequest.getIndex());
        assertTrue(searchRecordsRequest.isSelectAll());
        assertTrue(searchRecordsRequest.isSelectAllFacets());
        assertTrue(searchRecordsRequest.isShowResults());

    }

    private void setSearchRecordsRequest(SearchRecordsRequest searchRecordsRequest){
        searchRecordsRequest.setShowResults(true);
        List<SearchResultRow> searchResultRows = new ArrayList<>();
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setTitle("Title1");
        searchResultRow.setBibId(1);
        searchResultRows.add(searchResultRow);
        searchRecordsRequest.setSearchResultRows(searchResultRows);
        List<String> availability = new ArrayList<>();
        availability.add("Available");
        searchRecordsRequest.setAvailability(availability);
        searchRecordsRequest.setFieldName("245");
        searchRecordsRequest.setFieldValue("Stay Hungry");
        List<String> owningInstitutions = new ArrayList<>();
        owningInstitutions.add("NYPL");
        searchRecordsRequest.setOwningInstitutions(owningInstitutions);
        List<String> collectionGroupDesignations = new ArrayList<>();
        collectionGroupDesignations.add("Shared");
        searchRecordsRequest.setCollectionGroupDesignations(collectionGroupDesignations);
        List<String> materialTypes = new ArrayList<>();
        materialTypes.add("Monograph");
        searchRecordsRequest.setMaterialTypes(materialTypes);
        searchRecordsRequest.setTotalPageCount(1);
        searchRecordsRequest.setTotalBibRecordsCount("1");
        searchRecordsRequest.setTotalItemRecordsCount("1");
        searchRecordsRequest.setSelectAll(false);
        searchRecordsRequest.setIndex(1);
        searchRecordsRequest.setSelectAll(true);
        searchRecordsRequest.setShowResults(true);
        searchRecordsRequest.setSelectAllFacets(true);
    }
}

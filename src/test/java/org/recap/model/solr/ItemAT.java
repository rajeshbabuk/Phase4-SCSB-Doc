
package org.recap.model.solr;

import org.junit.jupiter.api.Test;

import org.recap.BaseTestCaseUT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemAT extends BaseTestCaseUT {

    @Test
    public void indexItem() throws Exception {

        List<Integer> itemBibIdList = new ArrayList<>();
        List<Integer> holdingsIdList = new ArrayList<>();
        itemBibIdList.add(101);
        itemBibIdList.add(102);
        holdingsIdList.add(201);
        holdingsIdList.add(202);

        Item item = new Item();
        item.setId("1");
        item.setOwningInstitutionItemId("1");
        item.setBarcode("1");
        item.setItemId(301);
        item.setDocType("Item");
        item.setAvailability("Available");
        item.setCallNumberSearch("F864");
        item.setCustomerCode("PA");
        item.setCollectionGroupDesignation("Shared");
        item.setUseRestriction("Use Restriction");
        item.setVolumePartYear("1970");
        item.setHoldingsIdList(holdingsIdList);
        item.setItemBibIdList(itemBibIdList);
        item.setRoot("_root_");
        item.setCopyNumber("String");
        item.setCallNumberDisplay("String");
        item.setOwningInstitution("1");
        item.setAvailabilityDisplay("1");
        item.setUseRestrictionDisplay("1");
        item.setCopyNumber("1");
        item.setItemCreatedBy("test" );
        item.setItemCreatedDate(new Date());
        item.setItemLastUpdatedDate(new Date() );
        item.setItemLastUpdatedBy("test" );
        item.setTitleSort("test" );
        item.setItemCatalogingStatus("test" );
        item.setCgdChangeLog("test" );
        item.setDeletedItem(true);
        assertNotNull(item.getId());
        assertNotNull(item.getOwningInstitutionItemId());
        assertNotNull(item.getCallNumberDisplay());
        assertNotNull(item.getOwningInstitution());
        assertNotNull(item.getAvailabilityDisplay());
        assertNotNull(item.getUseRestrictionDisplay());
        assertNotNull(item.getRoot());
        assertNotNull(item.getCopyNumber());
        assertNotNull(item.getItemCreatedBy());
        assertNotNull(item.getItemCreatedDate());
        assertNotNull(item.getItemLastUpdatedDate());
        assertNotNull(item.getItemLastUpdatedBy());
        assertNotNull(item.isDeletedItem());
        assertNotNull(item.getTitleSort());
        assertNotNull(item.getItemCatalogingStatus());
        assertNotNull(item.getCgdChangeLog());


        assertNotNull(item.getBarcode());
        assertNotNull(item.getItemId());
        assertNotNull(item.getDocType());
        assertNotNull(item.getAvailability());
        assertNotNull(item.getCallNumberSearch());
        assertNotNull(item.getCustomerCode());
        assertNotNull(item.getCollectionGroupDesignation());
        assertNotNull(item.getUseRestriction());
        assertNotNull(item.getVolumePartYear());
        assertNotNull(item.getHoldingsIdList());
        assertNotNull(item.getItemBibIdList());
    }
}


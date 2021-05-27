package org.recap.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.util.UpdateCgdUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by rajeshbabuk on 3/1/17.
 */
public class UpdateCgdRestControllerUT extends BaseTestCaseUT {


    @InjectMocks
    UpdateCgdRestController UpdateCgdRestController;

    @Mock
    UpdateCgdUtil updateCgdUtil;

    String itemBarcode = "1";
    String owningInstitution = "PUL";
    String oldCollectionGroupDesignation = "Private";
    String newCollectionGroupDesignation = "Open";
    String cgdChangeNotes = "Notes";
    String username = "guest";

    @Test
    public void updateCgdForItem() throws Exception {
        Mockito.when(updateCgdUtil.updateCGDForItem(itemBarcode, owningInstitution, oldCollectionGroupDesignation, newCollectionGroupDesignation, cgdChangeNotes,username)).thenReturn(ScsbCommonConstants.SUCCESS);
        String statusMessage= UpdateCgdRestController.updateCgdForItem(itemBarcode,owningInstitution,oldCollectionGroupDesignation,newCollectionGroupDesignation,cgdChangeNotes,username);
        assertEquals(ScsbCommonConstants.SUCCESS,statusMessage);
    }

    @Test
    public void updateCgdForItemException() throws Exception {
        Mockito.when(updateCgdUtil.updateCGDForItem(itemBarcode, owningInstitution, oldCollectionGroupDesignation, newCollectionGroupDesignation, cgdChangeNotes,username)).thenThrow(NullPointerException.class);
        String statusMessage= UpdateCgdRestController.updateCgdForItem(itemBarcode,owningInstitution,oldCollectionGroupDesignation,newCollectionGroupDesignation,cgdChangeNotes,username);
        assertNull(statusMessage);
    }
}

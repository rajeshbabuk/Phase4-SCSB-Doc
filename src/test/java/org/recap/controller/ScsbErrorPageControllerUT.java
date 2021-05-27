package org.recap.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.recap.BaseTestCaseUT;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by hemalathas on 23/2/17.
 */
public class ScsbErrorPageControllerUT extends BaseTestCaseUT {

    @InjectMocks
    ScsbErrorPageController scsbErrorPageController;

    @Test
    public void testErrorPage(){
        String response = scsbErrorPageController.recapErrorPage();
        String path = scsbErrorPageController.getErrorPath();
        assertNotNull(response);
        assertEquals("error",response);
        assertNotNull(path);
        assertEquals("/error",path);
    }

}
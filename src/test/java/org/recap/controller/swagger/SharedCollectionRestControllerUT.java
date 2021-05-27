package org.recap.controller.swagger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.BaseTestCaseUT;
import org.recap.controller.SharedCollectionRestController;
import org.recap.model.BibItemAvailabityStatusRequest;
import org.recap.model.ItemAvailabityStatusRequest;
import org.recap.service.ItemAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by chenchulakshmig on 14/10/16.
 */
public class SharedCollectionRestControllerUT extends BaseTestCaseUT {

    @InjectMocks
    private SharedCollectionRestController sharedCollectionRestController;

    @Mock
    ItemAvailabilityService itemAvailabilityService;

    @BeforeEach
    public void setup()throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itemAvailabilityStatus() throws Exception {
        ItemAvailabityStatusRequest itemAvailabityStatusRequest = new ItemAvailabityStatusRequest();
        String barcode = null;
        List<String>  barcodeList = new ArrayList<>();
        barcodeList.add(barcode);
        itemAvailabityStatusRequest.setBarcodes(barcodeList);
        ResponseEntity responseEntity = sharedCollectionRestController.itemAvailabilityStatus(itemAvailabityStatusRequest);
        assertNotNull(responseEntity);
        assertEquals( HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    public void itemAvailabilityStatusException() throws Exception {
        ItemAvailabityStatusRequest itemAvailabityStatusRequest = new ItemAvailabityStatusRequest();
        String barcode = null;
        List<String>  barcodeList = new ArrayList<>();
        barcodeList.add(barcode);
        itemAvailabityStatusRequest.setBarcodes(barcodeList);
        Mockito.when(itemAvailabilityService.getItemStatusByBarcodeAndIsDeletedFalseList(Mockito.anyList())).thenThrow(NullPointerException.class);
        ResponseEntity responseEntity = sharedCollectionRestController.itemAvailabilityStatus(itemAvailabityStatusRequest);
        assertNotNull(responseEntity);
        assertEquals( HttpStatus.SERVICE_UNAVAILABLE,responseEntity.getStatusCode());
    }

    @Test
    public void testBibAvailabilityStatus() throws Exception {
        BibItemAvailabityStatusRequest bibItemAvailabityStatusRequest = new BibItemAvailabityStatusRequest();
        bibItemAvailabityStatusRequest.setBibliographicId("93540");
        bibItemAvailabityStatusRequest.setInstitutionId("PUL");
        ResponseEntity responseEntity = sharedCollectionRestController.bibAvailabilityStatus(bibItemAvailabityStatusRequest);
        assertNotNull(responseEntity);
    }
}
package org.recap.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.repository.jpa.ItemDetailsRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 3/7/17.
 */
public class ItemControllerUT extends BaseTestCaseUT {

    @InjectMocks
    ItemController mockedItemController;

    @Mock
    ItemDetailsRepository mockedItemDetailsRepository;

    @Test
    public void testItemController(){
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setItemEntities(Arrays.asList(new ItemEntity()));
        bibliographicEntity.setHoldingsEntities(Arrays.asList(new HoldingsEntity()));
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setBarcode("3325656544854");
        itemEntity.setHoldingsEntities(Arrays.asList(new HoldingsEntity()));
        itemEntity.setBibliographicEntities(Arrays.asList(bibliographicEntity));
        Mockito.when(mockedItemDetailsRepository.findByBarcodeIn(Mockito.any())).thenReturn(Arrays.asList(itemEntity));
        List<ItemEntity> itemEntityList = mockedItemController.findByBarcodeIn("[3325656544854,334545888458]");
        assertNotNull(itemEntityList);
        assertEquals("3325656544854",itemEntityList.get(0).getBarcode());
   }

}
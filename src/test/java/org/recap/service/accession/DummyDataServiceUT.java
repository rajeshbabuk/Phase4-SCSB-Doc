package org.recap.service.accession;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.CollectionGroupEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.ItemStatusEntity;
import org.recap.model.jpa.OwningInstitutionIDSequence;
import org.recap.repository.jpa.CollectionGroupDetailsRepository;
import org.recap.repository.jpa.ItemStatusDetailsRepository;
import org.recap.repository.jpa.OwningInstitutionIDSequenceRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by premkb on 28/4/17.
 */
public class DummyDataServiceUT extends BaseTestCaseUT {

    @InjectMocks
    private DummyDataService dummyDataService;

    @Mock
    OwningInstitutionIDSequenceRepository owningInstitutionIDSequenceRepository;

    @Mock
    CollectionGroupDetailsRepository collectionGroupDetailsRepository;

    @Mock
    ItemStatusDetailsRepository itemStatusDetailsRepository;

    @Mock
    AccessionDAO accessionDAO;

    @Test
    public void createDummyDataAsIncomplete(){
        Mockito.when(owningInstitutionIDSequenceRepository.saveAndFlush(Mockito.any())).thenReturn(new OwningInstitutionIDSequence());
        List<CollectionGroupEntity> collectionGroupEntityList=new ArrayList<>();
        collectionGroupEntityList.add(getCollectionGroupEntity());
        Mockito.when(collectionGroupDetailsRepository.findAll()).thenReturn(collectionGroupEntityList);
        List<ItemStatusEntity> itemStatusEntities=new ArrayList<>();
        ItemStatusEntity itemStatusEntity=new ItemStatusEntity();
        itemStatusEntity.setStatusCode("RecentlyReturned");
        itemStatusEntity.setStatusDescription("RecentlyReturned");
        itemStatusEntities.add(itemStatusEntity);
        Mockito.when(itemStatusDetailsRepository.findAll()).thenReturn(itemStatusEntities);
        Mockito.when(accessionDAO.saveBibRecord(Mockito.any())).thenReturn(getBibliographicEntity());

        BibliographicEntity bibliographicEntity = dummyDataService.createDummyDataAsIncomplete(1,"3245678232","PA");
        assertNotNull(bibliographicEntity);
        assertEquals(ScsbCommonConstants.INCOMPLETE_STATUS,bibliographicEntity.getCatalogingStatus());
        assertEquals(ScsbConstants.DUMMY_CALL_NUMBER_TYPE,bibliographicEntity.getItemEntities().get(0).getCallNumberType());
        assertEquals(ScsbCommonConstants.DUMMYCALLNUMBER,bibliographicEntity.getItemEntities().get(0).getCallNumber());
        assertEquals(ScsbCommonConstants.INCOMPLETE_STATUS,bibliographicEntity.getItemEntities().get(0).getCatalogingStatus());
    }

    @Test
    public void createDummyDataAsIncompleteException(){
        Mockito.when(owningInstitutionIDSequenceRepository.saveAndFlush(Mockito.any())).thenReturn(new OwningInstitutionIDSequence());
        List<CollectionGroupEntity> collectionGroupEntityList=new ArrayList<>();
        collectionGroupEntityList.add(getCollectionGroupEntity());
        Mockito.when(collectionGroupDetailsRepository.findAll()).thenThrow(NullPointerException.class);
        List<ItemStatusEntity> itemStatusEntities=new ArrayList<>();
        ItemStatusEntity itemStatusEntity=new ItemStatusEntity();
        itemStatusEntity.setStatusCode("RecentlyReturned");
        itemStatusEntity.setStatusDescription("RecentlyReturned");
        itemStatusEntities.add(itemStatusEntity);
        Mockito.when(itemStatusDetailsRepository.findAll()).thenThrow(NullPointerException.class);
        Mockito.when(accessionDAO.saveBibRecord(Mockito.any())).thenReturn(getBibliographicEntity());
        BibliographicEntity bibliographicEntity = dummyDataService.createDummyDataAsIncomplete(1,"3245678232","PA");
        assertNotNull(bibliographicEntity);
    }

    @Test
    public void createDummyDataAsException(){
        Mockito.when(owningInstitutionIDSequenceRepository.saveAndFlush(Mockito.any())).thenThrow(NullPointerException.class);
        Mockito.when(accessionDAO.saveBibRecord(Mockito.any())).thenReturn(getBibliographicEntity());
        BibliographicEntity bibliographicEntity = dummyDataService.createDummyDataAsIncomplete(1,"3245678232","PA");
        assertNotNull(bibliographicEntity);
    }

    private BibliographicEntity getBibliographicEntity() {
        BibliographicEntity bibliographicEntity=new BibliographicEntity();
        bibliographicEntity.setCatalogingStatus(ScsbCommonConstants.INCOMPLETE_STATUS);
        ItemEntity itemEntity=new ItemEntity();
        itemEntity.setCallNumberType(ScsbConstants.DUMMY_CALL_NUMBER_TYPE);
        itemEntity.setCallNumber(ScsbCommonConstants.DUMMYCALLNUMBER);
        itemEntity.setCatalogingStatus(ScsbCommonConstants.INCOMPLETE_STATUS);
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));
        return bibliographicEntity;
    }

    private CollectionGroupEntity getCollectionGroupEntity() {
        CollectionGroupEntity collectionGroupEntity=new CollectionGroupEntity();
        collectionGroupEntity.setCollectionGroupDescription("Private");
        collectionGroupEntity.setId(3);
        collectionGroupEntity.setCollectionGroupCode("Private");
        return collectionGroupEntity;
    }
}

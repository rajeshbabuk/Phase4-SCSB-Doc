package org.recap.util;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.recap.BaseTestCaseUT;
import org.recap.TestUtil;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.solr.Holdings;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNull;

public class HoldingsJSONUtilUT extends BaseTestCaseUT {

    @InjectMocks
    HoldingsJSONUtil holdingsJSONUtil;

    @Mock
    private ProducerTemplate producerTemplate;

    @Test
    public void generateHoldingsForIndex(){
        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setId(1);
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setCreatedBy("tst");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setLastUpdatedBy("tst");
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(1567));
        holdingsEntity.setInstitutionEntity(TestUtil.getInstitutionEntity(1,"PUL","Princeton"));
        holdingsJSONUtil.getProducerTemplate();
        Holdings holdings=holdingsJSONUtil.generateHoldingsForIndex(holdingsEntity);
        assertNull(holdings);
    }

}

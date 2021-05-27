package org.recap.model.jpa;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HoldingsEntityUT {

    @Test
    public void holdingsEntity()throws Exception{
        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setCreatedBy("tst");
        holdingsEntity.setLastUpdatedBy("tst");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(1));
        holdingsEntity.equals(new HoldingsEntity());
        holdingsEntity.equals(null);
        holdingsEntity.hashCode();
        assertNotNull(holdingsEntity);
    }
}

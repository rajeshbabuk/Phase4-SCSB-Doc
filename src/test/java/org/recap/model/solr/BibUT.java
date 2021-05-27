package org.recap.model.solr;

import org.junit.jupiter.api.Test;
import org.recap.BaseTestCaseUT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 8/6/17.
 */
public class BibUT extends BaseTestCaseUT {

    @Test
    public void testBib(){
        List<String> issnList = new ArrayList<>();
        List<String>isbnList = new ArrayList<>();
        List<String> oclcNumberList = new ArrayList<>();
        List<Integer> holdingsIdList = new ArrayList<>();
        List<Integer> itemIdList = new ArrayList<>();
        issnList.add("0394469756");
        isbnList.add("0394469755");
        oclcNumberList.add("00133182");
        oclcNumberList.add("00440790");
        holdingsIdList.add(201);
        holdingsIdList.add(202);
        itemIdList.add(301);
        itemIdList.add(302);
        Bib bib = new Bib();
        bib.setId("1");
        bib.setContentType("XML");
        bib.setBibId(101);
        bib.setOwningInstitutionBibId("CU0021524");
        bib.setDocType("Bib");
        bib.setTitle("Middleware for SCSB");
        bib.setBarcode("1");
        bib.setTitle("Test Bib 1");
        bib.setAuthorDisplay("Hoepli, Nancy L");
        bib.setAuthorSearch(Arrays.asList("Hoepli, Nancy L", "Ibn Jubayr"));
        bib.setPublisher("McClelland & Stewart, limited");
        bib.setImprint("Toronto, McClelland & Stewart, limited [c1926]");
        bib.setIssn(issnList);
        bib.setIsbn(isbnList);
        bib.setOclcNumber(oclcNumberList);
        bib.setPublicationDate("1960");
        bib.setMaterialType("Material Type 1");
        bib.setNotes("Bibliographical footnotes 1");
        bib.setOwningInstitution("PUL");
        bib.setSubject("Arab countries Politics and government.");
        bib.setPublicationPlace("Paris");
        bib.setLccn("71448228");
        bib.setHoldingsIdList(holdingsIdList);
        bib.setBibItemIdList(itemIdList);
        bib.setTitleDisplay("test");
        bib.setTitleStartsWith("test");
        bib.setTitleSubFieldA("test");
        bib.setTitleSort("test");
        bib.setLeaderMaterialType("test");
        bib.setBibCreatedBy("Guest");
        bib.setBibCreatedDate(new Date());
        bib.setBibLastUpdatedBy("Guest");
        bib.setBibLastUpdatedDate(new Date());
        bib.setBibHoldingLastUpdatedDate(new Date());
        bib.setBibItemLastUpdatedDate(new Date());
        bib.setDeletedBib(false);
        bib.setBibCatalogingStatus("Incomplete");
        bib.setOwningInstHoldingsIdList(Arrays.asList(1));
        Bib bib1=new Bib();
        bib1.hashCode();
        bib.hashCode();
        bib.equals(bib);
        bib1.equals(bib);
        bib.equals(false);
        bib.equals(bib1);
        assertNotNull(bib.getId());
        assertNotNull(bib.getBibId());
        assertNotNull(bib.getDocType());
        assertNotNull(bib.getBarcode());
        assertNotNull(bib.getTitle());
        assertNotNull(bib.getTitleDisplay());
        assertNotNull(bib.getTitleStartsWith());
        assertNotNull(bib.getTitleSubFieldA());
        assertNotNull(bib.getAuthorDisplay());
        assertNotNull(bib.getAuthorSearch());
        assertNotNull(bib.getOwningInstitution());
        assertNotNull(bib.getPublisher());
        assertNotNull(bib.getPublicationPlace());
        assertNotNull(bib.getPublicationDate());
        assertNotNull(bib.getSubject());
        assertNotNull(bib.getIsbn());
        assertNotNull(bib.getIssn());
        assertNotNull(bib.getOclcNumber());
        assertNotNull(bib.getMaterialType());
        assertNotNull(bib.getNotes());
        assertNotNull(bib.getLccn());
        assertNotNull(bib.getImprint());
        assertNotNull(bib.getHoldingsIdList());
        assertNotNull(bib.getOwningInstHoldingsIdList());
        assertNotNull(bib.getBibItemIdList());
        assertNotNull(bib.getOwningInstitutionBibId());
        assertNotNull(bib.getContentType());
        assertNotNull(bib.getLeaderMaterialType());
        assertNotNull(bib.getTitleSort());
        assertNotNull(bib.getBibCreatedBy());
        assertNotNull(bib.getBibCreatedDate());
        assertNotNull(bib.getBibLastUpdatedBy());
        assertNotNull(bib.getBibLastUpdatedDate());
        assertNotNull(bib.getBibHoldingLastUpdatedDate());
        assertNotNull(bib.getBibItemLastUpdatedDate());
        assertNotNull(bib.isDeletedBib());
        assertNotNull(bib.getBibCatalogingStatus());
    }

    @Test
    public void testBib1(){
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(2,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"HTML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","test","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","789","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","test","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","test","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","test","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","test",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",10,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"test","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","test","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","test","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","test","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","test","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","test","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","test","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","test","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","test","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","test","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","test","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","test","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","test","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","test",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",7,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,8,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"test","leaderMaterialType","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","test","bibCatalogingStatus"));
        getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","bibCatalogingStatus").equals(getBib(1,"XML","docType","123456","title","titleDispaly","titleSort","titleStartsWith",9,"Hoepli, Nancy L","authorSearch","owningInstitution","publisher","publicationPlace","publicationDate","subject","isbn","issn","oclcNumber","materialType","notes","iccn","imprint",4,5,"owningInstitutionBibId","leaderMaterialType","test"));
    }

    private Bib getBib(int bibId,String contentType,String docType,String barcode,String title,String titleDispaly,String titleSort,String titleStartsWith,int titleSubFieldA,String authorDisplay,String authorSearch,String owningInstitution,String publisher,String publicationPlace,String publicationDate,String subject,String isbn,
                       String issn,String oclcNumber,String materialType,String notes,String lccn,String imprint,int holdingsIdList,int bibItemIdList,String owningInstitutionBibId,String leaderMaterialType,String bibCatalogingStatus) {
        Bib bib = new Bib();
        bib.setBibId(bibId);
        bib.setContentType(contentType);
        bib.setDocType(docType);
        bib.setBarcode(barcode);
        bib.setTitle(title);
        bib.setTitleDisplay(titleDispaly);
        bib.setTitleSort(titleSort);
        bib.setTitleStartsWith(titleStartsWith);
        bib.setOwningInstHoldingsIdList(Arrays.asList(titleSubFieldA));
        bib.setAuthorDisplay(authorDisplay);
        bib.setAuthorSearch(Collections.singletonList(authorSearch));
        bib.setOwningInstitution(owningInstitution);
        bib.setPublisher(publisher);
        bib.setPublicationPlace(publicationPlace);
        bib.setPublicationDate(publicationDate);
        bib.setSubject(subject);
        bib.setIsbn(Collections.singletonList(isbn));
        bib.setIssn(Collections.singletonList(issn));
        bib.setOclcNumber(Collections.singletonList(oclcNumber));
        bib.setMaterialType(materialType);
        bib.setNotes(notes);
        bib.setLccn(lccn);
        bib.setImprint(imprint);
        bib.setHoldingsIdList(Arrays.asList(holdingsIdList));
        bib.setBibItemIdList(Arrays.asList(bibItemIdList));
        bib.setOwningInstitutionBibId(owningInstitutionBibId);
        bib.setLeaderMaterialType(leaderMaterialType);
        bib.setBibCatalogingStatus(bibCatalogingStatus);
        return bib;
    }

}
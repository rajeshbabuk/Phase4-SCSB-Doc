package org.recap.camel.processor;

import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.MatchingBibEntity;
import org.recap.model.jpa.MatchingMatchPointsEntity;
import org.recap.model.jpa.ReportEntity;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.repository.jpa.MatchingBibDetailsRepository;
import org.recap.repository.jpa.MatchingMatchPointsDetailsRepository;
import org.recap.repository.jpa.ReportDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by angelind on 27/10/16.
 */
@Component
public class MatchingAlgorithmProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MatchingAlgorithmProcessor.class);

    @Autowired
    private MatchingMatchPointsDetailsRepository matchingMatchPointsDetailsRepository;

    @Autowired
    private MatchingBibDetailsRepository matchingBibDetailsRepository;

    @Autowired
    private ReportDetailRepository reportDetailRepository;

    @Autowired
    private ItemDetailsRepository itemDetailsRepository;

    /**
     * This method is used to save matching match-point in the database.
     *
     * @param matchingMatchPointsEntities the matching match points entities
     */
    @Transactional
    public void saveMatchingMatchPointEntity(List<MatchingMatchPointsEntity> matchingMatchPointsEntities){
        logger.info("Listening and Saving the match point entries");
        matchingMatchPointsDetailsRepository.saveAll(matchingMatchPointsEntities);
        matchingMatchPointsDetailsRepository.flush();
    }

    /**
     * This method is used to save matching bibs in the database.
     *
     * @param matchingBibEntities the matching bib entities
     */
    //@Transactional
    public void saveMatchingBibEntity(List<MatchingBibEntity> matchingBibEntities){
        try {
            logger.info("Listening and Saving the matching bib entries");
            matchingBibDetailsRepository.saveAll(matchingBibEntities);
            //matchingBibDetailsRepository.flush();
        } catch (Exception ex) {
            logger.info("Exception : {0}",ex);
            for(MatchingBibEntity matchingBibEntity : matchingBibEntities) {
                try {
                    matchingBibDetailsRepository.save(matchingBibEntity);
                    //matchingBibDetailsRepository.flush();
                } catch (Exception e) {
                    logger.info("Exception for single Entity : " , e);
                    logger.info("ISBN : {}" , matchingBibEntity.getIsbn());
                }
            }
        }
    }

    /**
     * This method is used to save matching report in the database.
     *
     * @param reportEntityList the report entity list
     */
    @Transactional
    public void saveMatchingReportEntity(List<ReportEntity> reportEntityList) {
        reportDetailRepository.saveAll(reportEntityList);
        reportDetailRepository.flush();
    }

    /**
     * This method is used to update item in the database.
     *
     * @param itemEntities the item entities
     */
    @Transactional
    public void updateItemEntity(List<ItemEntity> itemEntities) {
        itemDetailsRepository.saveAll(itemEntities);
        itemDetailsRepository.flush();
    }

    /**
     * Update matching bib entity.
     *
     * @param matchingBibMap the matching bib map
     */
    @Transactional
    public void updateMatchingBibEntity(Map matchingBibMap) {
        String status = (String) matchingBibMap.get(ScsbCommonConstants.STATUS);
        List<Integer> matchingBibIds = (List<Integer>) matchingBibMap.get(ScsbConstants.MATCHING_BIB_IDS);
        try {
            matchingBibDetailsRepository.updateStatusBasedOnBibs(status, matchingBibIds);
            matchingBibDetailsRepository.flush();
        } catch (Exception e) {
            logger.info("Exception while updating matching Bib entity status : " , e);
        }
    }
}

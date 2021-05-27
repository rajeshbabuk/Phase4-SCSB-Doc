package org.recap.matchingalgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.BaseTestCaseUT;
import org.recap.controller.MatchingAlgorithmController;
import org.recap.matchingalgorithm.service.MatchingAlgorithmHelperService;
import org.recap.matchingalgorithm.service.MatchingAlgorithmUpdateCGDService;
import org.recap.repository.jpa.MatchingBibDetailsRepository;
import org.recap.repository.jpa.MatchingMatchPointsDetailsRepository;
import org.recap.util.MatchingAlgorithmUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by angelind on 27/10/16.
 */
public class MatchingAlgorithmUT extends BaseTestCaseUT {

    private static final Logger logger = LoggerFactory.getLogger(MatchingAlgorithmUT.class);

    @Mock
    MatchingMatchPointsDetailsRepository matchingMatchPointsDetailsRepository;

    @Mock
    MatchingBibDetailsRepository matchingBibDetailsRepository;

    @Mock
    MatchingAlgorithmHelperService matchingAlgorithmHelperService;

    @Mock
    MatchingAlgorithmUtil matchingAlgorithmUtil;

    @Mock
    MatchingAlgorithmController matchingAlgorithmController;

    @Mock
    MatchingAlgorithmUpdateCGDService matchingAlgorithmUpdateCGDService;

    private Integer batchSize = 1000;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void populateTempMatchingPointsEntity() throws Exception {
        Mockito.when(matchingAlgorithmHelperService.findMatchingAndPopulateMatchPointsEntities()).thenReturn(Long.valueOf(7));
        long count = matchingAlgorithmHelperService.findMatchingAndPopulateMatchPointsEntities();
        Mockito.when(matchingMatchPointsDetailsRepository.count()).thenReturn(Long.valueOf(7));
        long savedCount = matchingMatchPointsDetailsRepository.count();
        assertTrue(savedCount>0);
    }

    @Test
    public void populateTempMatchingBibsEntity() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Mockito.when(matchingAlgorithmHelperService.populateMatchingBibEntities()).thenReturn(Long.valueOf(1));
        long count = matchingAlgorithmHelperService.populateMatchingBibEntities();

        stopWatch.stop();
        logger.info("Total Time taken : " + stopWatch.getTotalTimeSeconds());
        Mockito.when(matchingBibDetailsRepository.count()).thenReturn(Long.valueOf(1));
        long savedBibsCount = matchingBibDetailsRepository.count();
        assertTrue(savedBibsCount>0);
    }

    @Test
    public void testDiacriticTitles() {
        String title = "--A bude hůř : román o třech-dílech /-";
        String normalizedTitle = Normalizer.normalize(title, Normalizer.Form.NFD);
        normalizedTitle = normalizedTitle.replaceAll("[^\\p{ASCII}]", "");
        normalizedTitle = normalizedTitle.replaceAll("\\p{M}", "");
        assertNotNull(title);
    }

    @Test
    public void testGetTitleToMatch() {
        String title = "--A bude hůř : román o třech-dílech /-";
        String titleToMatch = matchingAlgorithmUtil.getTitleToMatch(title);
        assertNotNull(title);
    }

    @Test
    public void runWholeMatchingAlgorithm() throws Exception {
        Date matchingAlgoDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String matchingAlgoDateString = sdf.format(matchingAlgoDate);
        Mockito.when(matchingAlgorithmController.matchingAlgorithmFull(matchingAlgoDateString)).thenReturn("Status  : Done");
        String status = matchingAlgorithmController.matchingAlgorithmFull(matchingAlgoDateString);
        assertNotNull(status);
        assertTrue(status.contains("Done"));
    }

    @Test
    public void updateCGDForMatchingAlgorithm() throws Exception {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        matchingAlgorithmUpdateCGDService.updateCGDProcessForMonographs(batchSize);
        assertNotNull(batchSize);
        stopWatch.stop();
        logger.info("Total Time taken to update CGD is : " + stopWatch.getTotalTimeSeconds());
    }

}

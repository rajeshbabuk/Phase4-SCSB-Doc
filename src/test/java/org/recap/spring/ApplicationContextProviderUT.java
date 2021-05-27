package org.recap.spring;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.recap.BaseTestCaseUT;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 12/7/17.
 */
public class ApplicationContextProviderUT extends BaseTestCaseUT {

    @InjectMocks
    ApplicationContextProvider applicationContextProvider;

    @Mock
    private ApplicationContext context;

    @Test
    public void testApplicationContextProvider(){
        applicationContextProvider.getInstance();
        applicationContextProvider.setApplicationContext(context);
        ApplicationContext context = applicationContextProvider.getApplicationContext();
        assertNotNull(context);
    }

}
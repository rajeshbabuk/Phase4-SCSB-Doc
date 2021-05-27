package org.recap.config;



import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by hemalathas on 25/1/17.
 */
public class SwaggerInterceptorUT extends BaseTestCaseUT {

    @InjectMocks
    SwaggerInterceptor swaggerInterceptor;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    PrintWriter value;

    @Test
    public void testPreHandle() throws Exception {
        Mockito.when(httpServletResponse.getWriter()).thenReturn(value);
        httpServletRequest.setAttribute("api_key","scsb");
        swaggerInterceptor.afterCompletion(httpServletRequest,httpServletResponse,new Object(),new Exception());
        swaggerInterceptor.postHandle(httpServletRequest,httpServletResponse,new Object(),new ModelAndView());
        boolean continueExport = swaggerInterceptor.preHandle(httpServletRequest,httpServletResponse,new Object());
        assertTrue(!continueExport);
    }

}
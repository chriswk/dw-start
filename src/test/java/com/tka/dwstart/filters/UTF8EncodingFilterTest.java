package com.tka.dwstart.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UTF8EncodingFilterTest {

    private final FilterChain filterChain = mock(FilterChain.class);
    private final ServletRequest request = mock(ServletRequest.class);
    private final ServletResponse response = mock(ServletResponse.class);

    @Test
    public void should_set_utf8_encoding_in_response() throws IOException, ServletException {
        final UTF8EncodingFilter filter = new UTF8EncodingFilter();
        filter.doFilter(request, response, filterChain);
        verify(response).setCharacterEncoding("UTF-8");
    }

}

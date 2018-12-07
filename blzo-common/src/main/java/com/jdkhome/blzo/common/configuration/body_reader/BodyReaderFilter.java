package com.jdkhome.blzo.common.configuration.body_reader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(filterName="bodyReaderFilter",urlPatterns="/*")
public class BodyReaderFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper=null;
        if(request instanceof HttpServletRequest) {
            requestWrapper=new BodyReaderHttpServletRequestWrapper((HttpServletRequest)request);
        }
        if(requestWrapper==null) {
            chain.doFilter(request, response);
        }else {
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {
        // do nothing

    }

}
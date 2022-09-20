package com.ltp.gradesubmission.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GlobalExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String errMsg = null;
        Integer errStatus = null;
        try {
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            errStatus = HttpServletResponse.SC_FORBIDDEN;
            errMsg = e.getMessage();
        } catch (EntityNotFoundException e404) {
            errStatus = HttpServletResponse.SC_NOT_FOUND;
            errMsg = e404.getMessage();
        } catch (RuntimeException e400) {
            errStatus = HttpServletResponse.SC_BAD_REQUEST;
            errMsg = e400.getMessage();
        } catch (Exception e500) {
            errStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            // errMsg = e500.getMessage();
        }
        if (errStatus != null) {
            response.setStatus(errStatus);
            response.getWriter().write("BAD REQUEST! " + (errMsg == null || errMsg.isBlank() || errMsg.equals("null") ? "" : errMsg));
            response.getWriter().flush();
        }
    }
}

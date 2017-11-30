/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import businesslogic.UserService;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 721292
 */
public class nAdminFiler implements Filter {
    
    private FilterConfig filterConfig = null;
    
    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
  
        // ensure user is authenticated
        HttpSession session = ((HttpServletRequest)request).getSession();
        if (session.getAttribute("username") != null) {
            // yes, go onwards to the servlet or next filter
            String username = (String) session.getAttribute("username");
            UserService us = new UserService();
            
            try {
                if(us.get(username).getRole().getRoleID() == 1)
                    chain.doFilter(request, response);
            } catch (Exception ex) {
                ((HttpServletResponse)response).sendRedirect("login");
            }
        } else {
            // get out of here!
            ((HttpServletResponse)response).sendRedirect("home");
        }
        
       // this code executes after the servlet
       // ...
            
    }

    @Override
    public void destroy() {        
    }
    
}
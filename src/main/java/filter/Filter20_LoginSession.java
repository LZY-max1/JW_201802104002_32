package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "Filter 20",urlPatterns = {"/*"})
public class Filter20_LoginSession implements Filter {
    public void destroy() {
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException{
        System.out.println("Filter20_LoginSession begin!");
        HttpServletRequest request1 = (HttpServletRequest)request;
        HttpServletResponse response1 = (HttpServletResponse)response;
        HttpSession session = request1.getSession(false);
        String path = request1.getRequestURI();
        if(path.contains("/login.ctl") || path.contains("/logout.ctl")){
            chain.doFilter(request,response);
            System.out.println("Filter20_LoginSession ends!");
        }else  if(session != null && session.getAttribute("currentUser") != null){
            chain.doFilter(request,response);
            System.out.println("Filter20_LoginSession ends!");
        }else{
            response1.getWriter().println("您没有登录，请登录");
        }
    }
}

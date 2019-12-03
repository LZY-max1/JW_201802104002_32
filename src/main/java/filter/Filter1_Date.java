package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

@WebFilter(filterName = "Filter 1", urlPatterns = {"/*"})
public class Filter1_Date implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 1 - date begins");
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        String path=request.getRequestURI();
        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        System.out.println("当前日期时间：" + df.format(date));
        // Calendar cal = Calendar.getInstance();
        //String time= cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH) + 1)+"月"+cal.get(Calendar.DATE)+"日"+cal.get(Calendar.HOUR_OF_DAY)+": "+cal.get(Calendar.MINUTE);
       System.out.println(path+" @ "+df.format(date));
       //放行，执行其它过滤器，如过滤器已经执行完毕，执行原请求
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter 1 - date ends");
    }
}




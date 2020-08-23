package controller.filter;

import dao.UserAccountDAO;
import model.UserAccount;
import utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//@WebFilter(filterName = "cookieFilter", urlPatterns = { "/*" })
public class CookieFilter implements Filter {

    private static final String COOKIES_ARE_CHECKED_ATTRIBUTE_KEY = "COOKIE_CHECKED";
    private static final String COOKIES_ARE_CHECKED_ATTRIBUTE_VALUE = "CHECKED";

    public CookieFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        UserAccount userInSession = SessionUtils.getLoginedUser(session);
        if (userInSession != null) {
            session.setAttribute(COOKIES_ARE_CHECKED_ATTRIBUTE_KEY, COOKIES_ARE_CHECKED_ATTRIBUTE_VALUE);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // Connection создан в JDBCFilter.
        Connection conn = SessionUtils.getStoredConnection(servletRequest);
        // Триггер для проверки Cookie.
        String checked = (String) session.getAttribute(COOKIES_ARE_CHECKED_ATTRIBUTE_KEY);
        if (checked == null && conn != null) {
            String userName = SessionUtils.getUserNameInCookie(request);
            if(userName != null) {
                try {
                    UserAccountDAO dao = new UserAccountDAO(conn);
                    UserAccount user = dao.get(userName);
                    SessionUtils.storeLoginedUser(session, user);
                } catch (SQLException e) {
                    e.printStackTrace();
//                throw new ServletException("Cookies registration error", e);
                }
                // Отметить проверенные Cookie.
                session.setAttribute(COOKIES_ARE_CHECKED_ATTRIBUTE_KEY, COOKIES_ARE_CHECKED_ATTRIBUTE_VALUE);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }
}

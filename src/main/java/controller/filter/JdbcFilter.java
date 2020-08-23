package controller.filter;

import utils.ConnectionUtils;
import utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

public class JdbcFilter implements Filter {

    public JdbcFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /*
   метод проверяет запрос на необходимость работы с сервлетом, а не c css, decorator.html и тд
    */
    private boolean needJDBC(HttpServletRequest request) {
        System.out.println("JDBC Filter");
        //
        // Servlet Url-pattern: /spath/*
        //
        // => /spath
        String servletPath = request.getServletPath();
        // => /abc/mnp
        String pathInfo = request.getPathInfo();

        String urlPattern = servletPath;

        if (pathInfo != null) {
            // => /spath/*
            urlPattern = servletPath + "/*";
        }

        // Key: servletName.
        // Value: ServletRegistration
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();

        // Коллекционировать все Servlet в вашем WebApp.
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // Открыть  connection (соединение) только для request со специальной ссылкой.
        // (Например ссылка к controller.servlet, jsp, ..)
        // Избегать открытия Connection для обычных запросов.
        // (Например image, css, javascript,... )
        if (this.needJDBC(request)) {
            System.out.println("Open Connection for: " + request.getServletPath());
            Connection conn = null;
            try {
                // Создать объект Connection подключенный к database.
                conn = ConnectionUtils.getConnection();
                // Настроить автоматический commit false, чтобы активно контролировать.
                conn.setAutoCommit(false);

                // Сохранить объект Connection в attribute в request.
                SessionUtils.storeConnection(servletRequest, conn);

                // Разрешить request продвигаться далее.
                // (Далее к следующему Filter или к цели).
                chain.doFilter(servletRequest, servletResponse);

                // Вызвать метод commit() чтобы завершить транзакцию с DB.
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionUtils.rollbackQuietly(conn);
//                throw new ServletException();
            } finally {
                ConnectionUtils.closeQuietly(conn);
            }
        }
        // Для обычных request (image,css,decorator.html,..)
        // не нужно открывать connection.
        else {
            // Разрешить request продвигаться далее.
            // (Далее к следующему Filter tiếp или к цели).
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

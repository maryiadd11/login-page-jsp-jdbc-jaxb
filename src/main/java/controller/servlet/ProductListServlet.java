package controller.servlet;

import dao.ProductDAO;
import decorator.ProductRowDecorator;
import model.Product;
import utils.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ProductListServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = SessionUtils.getStoredConnection(request);
        String errorString = null;
        List<Product> list = null;
        try {
            ProductDAO dao = new ProductDAO(conn);
            list = dao.listAll();
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
//            throw new ServletException("Can't list products");
        }
        // Сохранить информацию в request attribute перед тем как forward к views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("productList", list);
        String productListHtml = list.stream()
                .map(ProductRowDecorator::createProductHtmlRow)
                .collect(Collectors.joining("\n"));
        request.setAttribute("productListHtml", productListHtml);
        // Forward к /WEB-INF/views/productListView.jsp
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/productListView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}

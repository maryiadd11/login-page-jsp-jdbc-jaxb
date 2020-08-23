package controller.servlet;

import dao.ProductDAO;
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

public class EditProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public EditProductServlet() {
        super();
    }
    // Отобразить страницу редактирования продукта.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = SessionUtils.getStoredConnection(request);
        String code = request.getParameter("code");
        Product product = null;
        String errorString = null;
        try {
            ProductDAO dao = new ProductDAO(conn);
            product = dao.get(code);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        // Ошибки не имеются.
        // Продукт не существует для редактирования (edit).
        // Redirect sang trang danh sách sản phẩm.
        if (errorString != null && product == null) {
            response.sendRedirect(request.getServletPath() + "/productList");
            return;
        }
        // Сохранить информацию в request attribute перед тем как forward к views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("product", product);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
        dispatcher.forward(request, response);
    }

    // После того, как пользователь отредактировал информацию продукта и нажал на Submit.
    // Данный метод будет выполнен.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = SessionUtils.getStoredConnection(request);
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        float price = 0;
        String errorString = null;
        try {
            price = Float.parseFloat(priceStr);
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        Product product = new Product(code, name, price);
        try {
            ProductDAO dao = new ProductDAO(conn);
            dao.update(product);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString += e.getMessage();
        }
        // Сохранить информацию в request attribute перед тем как forward к views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("product", product);
        // Если имеется ошибка, forward к странице edit.
        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
            dispatcher.forward(request, response);
        }
        // Если все хорошо.
        // Redirect к странице со списком продуктов.
        else {
            response.sendRedirect(request.getContextPath() + "/productList");
        }
    }
}

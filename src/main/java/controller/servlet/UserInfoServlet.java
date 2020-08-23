package controller.servlet;

import model.UserAccount;
import utils.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserInfoServlet extends HttpServlet {

    //TODO
    private static final long serialVersionUID = 1L;

    public UserInfoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Проверить, вошел ли пользователь в систему (login) или нет.
        UserAccount loginedUser = SessionUtils.getLoginedUser(session);
        // Если еще не вошел в систему (login).
        if (loginedUser == null) {
            // Redirect (Перенаправить) к странице login.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // Сохранить информацию в request attribute перед тем как forward (перенаправить).
        request.setAttribute("user", loginedUser);
        // Если пользователь уже вошел в систему (login), то forward (перенаправить) к странице
        // /WEB-INF/views/userInfoView.jsp
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userInfoView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}

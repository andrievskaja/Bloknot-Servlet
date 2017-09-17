package com.andrievskaya.servlet;

import com.andrievskaja.service.model.view.UserView;
import com.andrievskaja.dao.UserDao;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Людмила
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        Map<String, String> messages = new HashMap<String, String>();

        if (username == null || username.isEmpty()) {
            messages.put("email", "Please enter username");
        }

        if (password == null || password.isEmpty()) {
            messages.put("password", "Please enter password");
        }

        if (messages.isEmpty()) {
            UserView user = userDao.save(username, password);

            if (user != null && user.getId() != null) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/portal/user/");
                return;
            } else {
                messages.put("login", "Unknown login, please try again");
            }
        }

        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

}

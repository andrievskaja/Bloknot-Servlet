/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrievskaya.servlet;

import com.andrievskaja.dao.RecordDao;
import com.andrievskaja.dao.UserDao;
import com.andrievskaja.service.model.view.RecordForm;
import com.andrievskaja.service.model.view.RecordView;
import com.andrievskaja.service.model.view.UserView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.map.ObjectMapper;


/**
 *
 * @author Людмила
 */
@WebServlet(urlPatterns = {"/delete", "/add", "/edit","/getRecord"})
public class RecordServlet extends HttpServlet {

    private RecordDao recordDao = new RecordDao();
//    private UserServiceImpl userService = new UserServiceImpl();
//    private WebApplicationContext springContext;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

//    @Override
//    public void init(final ServletConfig config) throws ServletException {
//        super.init(config);
//        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
//        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
//        beanFactory.autowireBean(this);
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserView user = (UserView) session.getAttribute("user");
        List<RecordView> list = recordDao.getAll(user.getId());
        request.setAttribute("records", list);
        request.getRequestDispatcher("/WEB-INF/jsp/portal/bloknot.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserView user = (UserView) session.getAttribute("user");
        Long id = null;
        if (request.getParameter("id") != null) {
            id = Long.parseLong(request.getParameter("id"));
        }
        RecordForm form = new RecordForm();
        form.setId(id);
        form.setUserId(user.getId());
        PrintWriter out = response.getWriter();
        switch (request.getRequestURI()) {
            case "/delete":
                out.print(delete(id, user.getId()));
                break;
            case "/add":
                form.setText(request.getParameter("text"));
                String json = OBJECT_MAPPER.writeValueAsString(add(form));
                out.print(json);
                break;
            case "/edit":
                edit(id, user.getId(), request.getParameter("text"));
                break;
        }
    }

    public String delete(Long id, Long userId) {
        return recordDao.delete(id, userId);
    }

    public String edit(Long id, Long userId, String text) {
        return recordDao.edit(id, text, userId);
    }

    public RecordView add(RecordForm form) {
        return recordDao.save(form);
    }
}

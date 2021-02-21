package com.rmv.university;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/test/*")
public class MainServlet extends HttpServlet {

    private final DBManager dbManager = new DBManager();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log("Method init =)");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Method doGet" + System.currentTimeMillis() +"\n");
        String sql = "INSERT INTO testTable (id) VALUES (2)";
        try {
            PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        resp.getWriter().write("Method service\n");
    }

    @Override
    public void destroy() {
        super.destroy();
        log("Method destroy =)");
    }
}

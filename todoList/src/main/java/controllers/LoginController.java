package controllers;

import dao.LoginDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Login;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private LoginDao loginDao;

    @Override
    public void init() {
        loginDao = new LoginDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        authenticate(req,resp);
    }

    private void authenticate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validate inputs
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Username and password are required");
            req.getRequestDispatcher("login/login.jsp").forward(req, resp);
            return;
        }

        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);

        try{
            if(loginDao.validate(login)){
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                resp.sendRedirect(req.getContextPath() + "/list");
            } else {
                req.setAttribute("error", "Invalid username or password");
                RequestDispatcher rd = req.getRequestDispatcher("login/login.jsp");
                rd.forward(req, resp);
            }
        } catch (SQLException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

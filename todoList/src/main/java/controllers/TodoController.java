package controllers;

import dao.TodoDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Todo;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/")
public class TodoController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private TodoDao todoDao;

    public void init(){
        todoDao = new TodoDao();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        try{
            switch (action){
                case "/new" -> {
                    showNewForm(req, resp);

                }
                case "/insert" -> {
                    System.out.println("inserting data in todo table");
                    insertTodo(req, resp);
                }
                case  "/delete" -> {
                    System.out.println("deleting todo");
                    deleteTodo(req, resp);
                }
                case "/edit" -> showEditForm(req,resp);
                case "/update" -> updateTodo(req,resp);
                case "/list" -> listTodo(req,resp);
                case "/logout" -> {
                    req.getSession().invalidate();
                    resp.sendRedirect("login/login.jsp");
                }
                default -> {
                    RequestDispatcher rd = req.getRequestDispatcher("login/login.jsp");
                    rd.forward(req,resp);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void listTodo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Todo> listTodo = todoDao.selectAllTodos();
        req.setAttribute("listTodo", listTodo);
        RequestDispatcher rd = req.getRequestDispatcher("todo/todo-list.jsp");
        rd.forward(req,resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("todo/todo-form.jsp"); // Ensure this path is correct
        rd.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Todo existingTodo = todoDao.selectTodo(id);
        RequestDispatcher rd = req.getRequestDispatcher("todo/todo-form.jsp");
        req.setAttribute("todo",existingTodo);
        rd.forward(req,resp);
    }

    private void insertTodo(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {

        try {
            // Get form parameters
            String title = req.getParameter("title");
            String username = (String) req.getSession().getAttribute("username");
            String description = req.getParameter("description");
            LocalDate targetDate = LocalDate.parse(req.getParameter("targetDate"));
            boolean isDone = Boolean.parseBoolean(req.getParameter("isDone"));

            // Debug output
            System.out.println("Inserting todo:");
            System.out.println("Title: " + title);
            System.out.println("Username: " + username);
            System.out.println("Description: " + description);
            System.out.println("Target Date: " + targetDate);
            System.out.println("Status: " + isDone);

            Todo newTodo = new Todo(title, username, description, targetDate, isDone);
            todoDao.insertTodo(newTodo);

            resp.sendRedirect(req.getContextPath() + "/list");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error creating todo: " + e.getMessage());
            req.getRequestDispatcher("/todo/todo-form.jsp").forward(req, resp);
        }
    }

    private void updateTodo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
//        String username = req.getParameter("username");
        String username = (String) req.getSession().getAttribute("username");
        String description = req.getParameter("description");
        LocalDate targetDate = LocalDate.parse(req.getParameter("targetDate"));
        boolean isDone = Boolean.parseBoolean(req.getParameter("isDone"));

        Todo updatetodo = new Todo(id, title, username, description, targetDate,isDone);

        todoDao.updateTodo(updatetodo);

        resp.sendRedirect("list");

    }

    private void deleteTodo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        todoDao.deleteTodo(id);
        resp.sendRedirect("list");
    }
}

package dao;

import database.DBConnection;
import model.Todo;
import utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDao {

    private static final String todo_table = DBConnection.getProps("TODOLIST_TABLE");
    private static final String insert_todo_in_db = "INSERT INTO " + todo_table + " (description, is_done, target_date, username, title) VALUES (?,?,?,?,?);";

    private static final String select_todo_by_id = "SELECT id,title,username,description,target_date,is_done FROM "+ todo_table+" WHERE id =?";
    private static final String select_all_todos = "SELECT * FROM " + todo_table + ";";
    private static final String delete_todo_by_id = "DELETE FROM " + todo_table + " WHERE id = ?";
    private static final String update_todo = "UPDATE " + todo_table + " SET title = ?, username = ?, description = ?, target_date = ?, is_done = ? WHERE id = ?;";

    public void insertTodo(Todo todo) throws SQLException{
        System.out.println("inserting todo in database");

        try(Connection connection = DBConnection.getConnection()){
            PreparedStatement ps = connection.prepareStatement(insert_todo_in_db);
            ps.setString(1,todo.getDescription());
            ps.setBoolean(2,todo.getStatus());
            ps.setDate(3, Utils.getSQLDate(todo.getTargetDate()));
            ps.setString(4,todo.getUsername());
            ps.setString(5,todo.getTitle());

            System.out.println("Executing: " + ps);

            System.out.println(ps);

            int rowAffected = ps.executeUpdate();

            if(rowAffected > 0){
                System.out.println("todo is successfully entered into database");
            } else {
                System.out.println("failed to insert todo in db");
            }
        }
    }

    public Todo selectTodo(long todoId) throws SQLException {
        Todo todo = null;
        try(Connection connection = DBConnection.getConnection()){
            PreparedStatement ps = connection.prepareStatement(select_todo_by_id);
            ps.setLong(1,todoId);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String description = rs.getString("description");
                LocalDate targetDate = rs.getDate("target_date").toLocalDate();
                boolean isDone = rs.getBoolean("is_done");

                todo = new Todo(id,title,username,description,targetDate,isDone);
            }

            return todo;
        }
    }

    public List<Todo> selectAllTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection()){
            PreparedStatement ps = connection.prepareStatement(select_all_todos);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String description = rs.getString("description");
                LocalDate targetDate = rs.getDate("target_date").toLocalDate();
                boolean isDone = rs.getBoolean("is_done");

                todos.add(new Todo(id,title,username,description,targetDate,isDone));
            }
        }

        return todos;

    }

    public boolean deleteTodo(int id) throws SQLException {
        boolean rowDeleted;
        try(Connection connection = DBConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(delete_todo_by_id);
            ps.setLong(1,id);
            rowDeleted = ps.executeUpdate() > 0;
            System.out.println("todo deleted successfully. row deleted : " + rowDeleted);
        }

        return rowDeleted;
    }

    public boolean updateTodo(Todo todo) throws SQLException{
        boolean rowUpdated;
        try(Connection connection = DBConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(update_todo);
            ps.setString(1,todo.getTitle());
            ps.setString(2,todo.getUsername());
            ps.setString(3, todo.getDescription());
            ps.setDate(4,Utils.getSQLDate(todo.getTargetDate()));
            ps.setBoolean(5, todo.getStatus());
            ps.setLong(6, todo.getId()); // creating a new todo
            rowUpdated = ps.executeUpdate() > 0;

            System.out.println("todo updated successfully.");
        }

        return rowUpdated;
    }


}

package com.hippotech.dao;



import com.hippotech.dto.TaskDTO;

import java.sql.*;
import java.util.ArrayList;

public class TaskDAO implements DAO<TaskDTO> {

    private static final String DELETE = "DELETE FROM task WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM task ORDER BY id";
    private static final String FIND_BY_ID = "SELECT * FROM task WHERE id=?";
//    private static final String FIND_BY_NAME = "SELECT * FROM task WHERE name=?";
    private static final String INSERT = "INSERT INTO task(id, projectName, title, name, startDate, deadline, finishDate," +
            "expectTime, finishTime, processed) VALUES(?, ?, ?, ?, ? ,?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE task SET projectName=?, title=?, name=?, startDate=?, deadline=?, " +
            "finishDate=?, expectTime=?, finishTime=?, processed=? WHERE id=?";

    private Connection connection;
    private PreparedStatement preparedStatement;

    public TaskDAO() {
        connection = getConnection();
    }

    @Override
    public ArrayList<TaskDTO> getAll() {
        ArrayList<TaskDTO> tasks = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet RS = preparedStatement.executeQuery();
            while (RS.next()) {
                TaskDTO task = new TaskDTO(RS.getString("id"), RS.getString("projectName"), RS.getString("title"),
                        RS.getString("name"), RS.getString("startDate"), RS.getString("deadline"),
                        RS.getString("finishDate"), RS.getInt("expectTime"), RS.getInt("finishTime"),
                        RS.getInt("processed"));
                tasks.add(task);
            }
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            close(preparedStatement);
        }
        return tasks;
    }

    @Override
    public TaskDTO get(String id) {
        try {
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                TaskDTO task = new TaskDTO();
                task.setId(rs.getString("id"));
                task.setPrName(rs.getString("projectName"));
                task.setTitle(rs.getString("title"));
                task.setName(rs.getString("name"));
                task.setStartDate(rs.getString("startDate"));
                task.setDeadline(rs.getString("deadline"));
                task.setFinishDate(rs.getString("finishDate"));
                task.setExpectedTime(rs.getInt("expectTime"));
                task.setFinishTime(rs.getInt("finishTime"));
                task.setProcessed(rs.getInt("processed"));
                return task;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(preparedStatement);
        }

    }

    @Override
    public void add(TaskDTO task) {
        try {
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, task.getId());
            preparedStatement.setString(2, task.getPrName());
            preparedStatement.setString(3, task.getTitle());
            preparedStatement.setString(4, task.getName());
            preparedStatement.setString(5, task.getStartDate());
            preparedStatement.setString(6, task.getDeadline());
            preparedStatement.setString(7, task.getFinishDate());
            preparedStatement.setInt(8, task.getExpectedTime());
            preparedStatement.setInt(9, task.getFinishTime());
            preparedStatement.setInt(10, task.getProcessed());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public void update(TaskDTO task) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(10, task.getId());
            preparedStatement.setString(1, task.getPrName());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setString(3, task.getName());
            preparedStatement.setString(4, task.getStartDate());
            preparedStatement.setString(5, task.getDeadline());
            preparedStatement.setString(6, task.getFinishDate());
            preparedStatement.setInt(7, task.getExpectedTime());
            preparedStatement.setInt(8, task.getFinishTime());
            preparedStatement.setInt(9, task.getProcessed());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public void delete(TaskDTO task) {

        try {
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1, task.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public ArrayList<String> getAllName() {
        return null;
    }

    private Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(DB_URL, ID, PASS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

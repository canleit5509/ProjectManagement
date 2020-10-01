package com.hippotech.dao;


import com.hippotech.dto.ProjectNameDTO;

import java.sql.*;
import java.util.ArrayList;

public class ProjectNameDAO implements DAO<ProjectNameDTO> {
    Connection connection;

    private static final String DELETE = "DELETE FROM projectname WHERE projectName=?";
    private static final String FIND_ALL = "SELECT * FROM projectname ORDER BY projectName";
    private static final String FIND_ALL_DONE = "SELECT * FROM projectname WHERE done=? ORDER BY projectName";
    private static final String FIND_BY_ID = "SELECT * FROM projectName WHERE projectName=?";
    private static final String INSERT = "INSERT INTO projectname(projectName, projectColor, done) VALUES(?, ?, ?)";
    private static final String UPDATE1 = "SET FOREIGN_KEY_CHECKS=OFF";
    private static final String UPDATE2 = "UPDATE projectname SET projectName=?, projectColor=?, done=? WHERE projectName=?";
    private static final String UPDATE3 = "UPDATE task SET projectName=? where projectName=?";
    private static final String UPDATE4 = "SET FOREIGN_KEY_CHECKS=ON";
            //    private static final String UPDATE = "UPDATE projectname SET projectName=?, projectColor=?, done=? WHERE projectName=?";
    PreparedStatement preparedStatement;
    ArrayList<ProjectNameDTO> projectNames;
    public ProjectNameDAO() {
        connection = getConnection();
        projectNames = new ArrayList<>();
    }

    @Override
    public ArrayList<ProjectNameDTO> getAll() {
        projectNames.clear();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL);
            System.out.println(preparedStatement.toString());
            ResultSet RS = preparedStatement.executeQuery();
            while (RS.next()) {
                ProjectNameDTO projectName = new ProjectNameDTO(RS.getString("projectName"), RS.getString("projectColor"), RS.getInt("done"));
                projectNames.add(projectName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(preparedStatement);
        }
        return projectNames;
    }

    public ArrayList<ProjectNameDTO> getAllDone(int check) {
        projectNames.clear();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL_DONE);
            preparedStatement.setInt(1, check);
            ResultSet RS = preparedStatement.executeQuery();
            while (RS.next()) {
                ProjectNameDTO projectName = new ProjectNameDTO(RS.getString("projectName"), RS.getString("projectColor"), RS.getInt("done"));
                projectNames.add(projectName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(preparedStatement);
        }
        return projectNames;
    }

    @Override
    public ProjectNameDTO get(String id) {
        try {
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setString(1, id);
            ResultSet RS = preparedStatement.executeQuery();
            ProjectNameDTO projectName = null;
            while (RS.next()) {
                projectName = new ProjectNameDTO(RS.getString("projectName"), RS.getString("projectColor"), RS.getInt("done"));
            }
            return projectName;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(ProjectNameDTO project) {
        try {
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setString(2, project.getProjectColor());
            preparedStatement.setInt(3, project.getDone());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public void update(ProjectNameDTO projectName) {

    }


    public void update(ProjectNameDTO project, String oldName) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE1);
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement(UPDATE2);
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setString(2, project.getProjectColor());
            preparedStatement.setInt(3, project.getDone());
            preparedStatement.setString(4, oldName);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(UPDATE3);
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setString(2, oldName);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(UPDATE4);
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public void delete(ProjectNameDTO projectName) {
        try {
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1, projectName.getProjectName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public ArrayList<String> getAllName() {
        ArrayList<String> projectNames = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet RS = preparedStatement.executeQuery();
            while (RS.next()) {
                String s = RS.getString("projectName");
                projectNames.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(preparedStatement);
        }
        return projectNames;
    }

    public ArrayList<String> getAllProjectNameDoing() {

        ArrayList<String> projectNames = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL_DONE);
            preparedStatement.setInt(1, 0);
            ResultSet RS = preparedStatement.executeQuery();
            while (RS.next()) {
                String s = RS.getString("projectName");
                projectNames.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(preparedStatement);
        }
        return projectNames;
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

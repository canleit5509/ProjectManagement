package com.hippotech.dao;

import java.util.ArrayList;

public interface DAO<T> {
    String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost:3306/projectmanager";
    String ID = "project";
    String PASS = "";

    ArrayList<T> getAll();

    T get(String id);

    void add(T t);

    void update(T t);

    void delete(T t);

    ArrayList<String> getAllName();
}

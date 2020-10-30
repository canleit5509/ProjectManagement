package com.hippotech.service;



import com.hippotech.dao.TaskDAO;
import com.hippotech.dto.TaskDTO;
import com.hippotech.model.Task;

import java.util.ArrayList;

public class TaskService {
    private final TaskDAO dao;

    public TaskService() {
        dao = new TaskDAO();
    }

    public Task convertToTask(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setPrName(dto.getPrName());
        task.setTitle(dto.getTitle());
        task.setName(dto.getName());
        task.setStartDate(dto.getStartDate());
        task.setDeadline(dto.getDeadline());
        task.setFinishDate(dto.getFinishDate());
        task.setExpectedTime(dto.getExpectedTime());
        task.setFinishTime(dto.getFinishTime());
        task.setProcessed(dto.getProcessed());
        return task;
    }

    public TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setPrName(task.getPrName());
        dto.setTitle(task.getTitle());
        dto.setName(task.getName());
        dto.setStartDate(task.getStartDate());
        dto.setDeadline(task.getDeadline());
        dto.setFinishDate(task.getFinishDate());
        dto.setExpectedTime(task.getExpectedTime());
        dto.setFinishTime(task.getFinishTime());
        dto.setProcessed(task.getProcessed());
        return dto;
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<TaskDTO> taskDTOs = dao.getAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (TaskDTO task :
                taskDTOs) {
            tasks.add(convertToTask(task));
        }
        return tasks;
    }

    public ArrayList<Task> getAllTaskBy(int column) {
        ArrayList<TaskDTO> taskDTOs = dao.getAllBy(column);
        ArrayList<Task> tasks = new ArrayList<>();
        for (TaskDTO task :
                taskDTOs) {
            tasks.add(convertToTask(task));
            System.out.println(task.getName());
        }
        return tasks;
    }

    public Task getTask(String id) {
        return convertToTask(dao.get(id));
    }

    public void addTask(Task task) {
        TaskDTO dto = convertToDTO(task);
        dao.add(dto);
    }

    public void updateTask(Task task) {
        TaskDTO dto = convertToDTO(task);
        dao.update(dto);
    }
    public void deleteTask(Task task){
        dao.delete(convertToDTO(task));
    }
}

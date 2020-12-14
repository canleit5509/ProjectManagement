package com.hippotech.service;


import com.hippotech.dao.ProjectNameDAO;
import com.hippotech.dto.ProjectNameDTO;
import com.hippotech.model.ProjectName;

import java.util.ArrayList;

public class ProjectNameService {
    private final ProjectNameDAO dao;

    public ProjectNameService() {
        dao = new ProjectNameDAO();
    }

    public ProjectName convertToProjectName(ProjectNameDTO dto) {
        ProjectName projectName = new ProjectName();
        projectName.setProjectName(dto.getProjectName());
        projectName.setProjectColor(dto.getProjectColor());
        projectName.setDone(dto.getDone());
        return projectName;
    }

    public ProjectNameDTO convertToDTO(ProjectName projectName) {
        ProjectNameDTO dto = new ProjectNameDTO();
        dto.setProjectName(projectName.getProjectName());
        dto.setProjectColor(projectName.getProjectColor());
        dto.setDone(projectName.getDone());
        return dto;
    }

    public ProjectName getProjectName(String projectName) {
        ProjectNameDTO dto = dao.get(projectName);
        return convertToProjectName(dto);
    }

    public void addProjectName(ProjectName name) {
        ProjectNameDTO projectName = convertToDTO(name);
        dao.add(projectName);
    }

    public ArrayList<ProjectName> getAllProject() {
        ArrayList<ProjectNameDTO> projectNameDTOs = dao.getAll();
        ArrayList<ProjectName> projectNames = new ArrayList<>();
        for (ProjectNameDTO dto :
                projectNameDTOs) {
            projectNames.add(convertToProjectName(dto));
        }
        return projectNames;
    }

    public void updateProject(ProjectName name, String oldName) {
        ProjectNameDTO projectName = convertToDTO(name);
        dao.update(projectName, oldName);
    }

    public void deleteProject(ProjectName projectName) {
        ProjectNameDTO dto = convertToDTO(projectName);
        dao.delete(dto);
    }

    public ArrayList<ProjectName> getAllDoneProject(int check) {
        ArrayList<ProjectNameDTO> DTOs = dao.getAllDone(check);
        ArrayList<ProjectName> projectNames = new ArrayList<>();
        for (ProjectNameDTO dto :
                DTOs) {
            projectNames.add(convertToProjectName(dto));
        }
        return projectNames;
    }

    public ArrayList<String> getAllProjectName() {
        return dao.getAllName();
    }

    public ArrayList<String> getAllDoingProjectName() {
        return dao.getAllProjectNameDoing();
    }
}

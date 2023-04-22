/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author giova
 */
public class ProjectController {

    public void save(Project project) {
        String sql = "INSERT INTO projects (name, description, createdAt, updatedAt) value(?,?,?,?) ";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar projeto" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void update(Project project) {
        String slq = "UPDATE projects SET name=?, name=?, description=?, createdAt = ?, updatedAt=? WHERE id = ? ";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(slq);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tesk" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeById(int idProject) {

        String sql = "DELETE FROM projects WHERE id = ?";
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1, idProject);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar project");
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }

    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        List<Project> projects = new ArrayList<Project>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultset = statement.executeQuery();

            while (resultset.next()) {
                Project p = new Project();
                p.setId(resultset.getInt("id"));
                p.setName(resultset.getString("name"));
                p.setDescription(resultset.getString("description"));
                p.setCreatedAt(resultset.getDate("createdAt"));
                p.setUpdatedAt(resultset.getDate("updatedAt"));

                projects.add(p);

            }

        } catch (SQLException e) {
            throw new RuntimeException("erro ao pegar todos os metodos");
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultset);
        }

        return projects;
    }
}

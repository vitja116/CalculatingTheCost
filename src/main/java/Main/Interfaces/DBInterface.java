package Main.Interfaces;

import Main.Entities.TasksEntity;
import Main.Entities.TaskOperationsEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DBInterface {

    Connection connection() throws SQLException;

    void initializeDB() throws SQLException;

    /**
     * All - all operations
     * Opened - only opened operations
     */
    List<TasksEntity> getTasks(String param) throws SQLException;

    List<TasksEntity> getTasksBetweenTime(String startDate, String endDate) throws SQLException;

    List<TaskOperationsEntity> getTaskOperations() throws SQLException;

    void insertNewTaskOperation(String description) throws SQLException;

    void insertNewTaskOperation(String operationID, String info, String planedCount, String price) throws SQLException;

    void closeTaskOperations(String taskID, String factCount) throws SQLException;
}

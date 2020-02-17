package Main.Interfaces;

import Main.Entities.OperationsEntity;
import Main.Entities.TaskEntity;

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
    List<OperationsEntity> getOperations(String param) throws SQLException;

    List<OperationsEntity> getOperationBetweenTime(String startDate, String endDate) throws SQLException;

    List<TaskEntity> getTask() throws SQLException;

    void insertNewOperation(String description) throws SQLException;

    void insertNewTask(String operationID, String info, String planedCount, String price) throws SQLException;

    void closeTask(String taskID, String factCount) throws SQLException;
}

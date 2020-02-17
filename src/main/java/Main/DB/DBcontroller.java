package Main.DB;

import Main.Entities.TasksEntity;
import Main.Entities.TaskOperationsEntity;
import Main.Interfaces.DBInterface;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBcontroller implements DBInterface {

    public Connection connection() throws SQLException {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:hsqldb:mem:some", "SA", "");
    }

    public void initializeDB() throws SQLException {
        Connection con = connection();
        con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS tasks (id INTEGER IDENTITY NOT NULL , description VARCHAR(255), cost float,  status VARCHAR(10), end_date DATE, PRIMARY KEY (id));");
        con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS tasks_operations(id INTEGER IDENTITY NOT NULL , task_id INT, info VARCHAR(255), planed_count smallint, fact_count smallint, price float, cost float, is_completed boolean, PRIMARY KEY (id), FOREIGN KEY (task_id) REFERENCES tasks(id));");
    }

    // All or Opened
    public List<TasksEntity> getTasks(String param) throws SQLException {
        Connection con = connection();
        List<TasksEntity> tasks = new ArrayList<>();
        ResultSet result;
        switch (param) {
            case "All":
                result = con.createStatement().executeQuery("SELECT * FROM tasks;");
                break;
            case "Opened":
                result = con.createStatement().executeQuery("SELECT * FROM tasks WHERE status = 'inProgress' OR status = 'Project';");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + param);
        }

        if (result != null) {
            while (result.next()) {
                if (result.getString("end_date") != null) {
                    tasks.add(new TasksEntity(result.getInt("id"),
                            result.getString("description"),
                            result.getFloat("cost"),
                            result.getString("status"),
                            LocalDate.parse(result.getString("end_date"))));
                } else {
                    tasks.add(new TasksEntity(result.getInt("id"),
                            result.getString("description"),
                            result.getFloat("cost"),
                            result.getString("status"),
                            null));
                }

            }
        }
        return tasks;
    }

    public List<TasksEntity> getTasksBetweenTime(String startDate, String endDate) throws SQLException {
        Connection con = connection();
        List<TasksEntity> tasks = new ArrayList<>();
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM tasks WHERE end_date BETWEEN ? AND ?;");
        pstmt.setString(1, startDate);
        pstmt.setString(2, endDate);
        ResultSet result = pstmt.executeQuery();

        if (result != null) {
            while (result.next()) {
                tasks.add(new TasksEntity(result.getInt("id"),
                        result.getString("description"),
                        result.getFloat("cost"),
                        result.getString("status"),
                        LocalDate.parse(result.getString("end_date"))));
            }
        }
        return tasks;
    }

    public List<TaskOperationsEntity> getTaskOperations() throws SQLException {
        Connection con = connection();
        List<TaskOperationsEntity> task = new ArrayList<>();
        ResultSet result = con.createStatement().executeQuery("SELECT * FROM tasks_operations;");
        if (result != null) {
            while (result.next()) {
                task.add(new TaskOperationsEntity(result.getInt("id"),
                        result.getInt("task_id"),
                        result.getString("info"),
                        result.getInt("planed_count"),
                        result.getInt("fact_count"),
                        result.getFloat("price"),
                        result.getFloat("cost"),
                        result.getBoolean("is_completed")));
            }
        }
        return task;
    }

    public void insertNewTaskOperation(String description) throws SQLException {
        Connection con = connection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO tasks (description,status,cost) VALUES (?,'Project', 0);");
        pstmt.setString(1, description);
        pstmt.executeUpdate();
    }

    public void insertNewTaskOperation(String operationID, String info, String planedCount, String price) throws SQLException {
        Connection con = connection();
        int cost = Integer.parseInt(price) * Integer.parseInt(planedCount);

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO tasks_operations (task_id, info, planed_count, price,cost, is_completed) VALUES (?,?,?,?,?,FALSE);");
        pstmt.setString(1, String.valueOf(operationID));
        pstmt.setString(2, info);
        pstmt.setString(3, planedCount);
        pstmt.setString(4, price);
        pstmt.setString(5, String.valueOf(cost));
        pstmt.executeUpdate();
        updateTasksTables(Integer.parseInt(operationID));
    }

    public void closeTaskOperations(String taskOprID, String factCount) throws SQLException {
        Connection con = connection();

        PreparedStatement pstmtGet = con.prepareStatement("SELECT * FROM tasks_operations WHERE id = ?");
        pstmtGet.setString(1, String.valueOf(taskOprID));
        ResultSet result = pstmtGet.executeQuery();

        float taskPrice = 0;
        int oprID = 0;
        while (result.next()) {
            taskPrice = result.getFloat("price");
            oprID = result.getInt("task_id");
        }
        double price = taskPrice * Float.parseFloat(factCount);

        PreparedStatement pstmtUpdate = con.prepareStatement("UPDATE tasks_operations SET fact_count = ?, cost = ?, is_completed = true WHERE id = ?");
        pstmtUpdate.setString(1, factCount);
        pstmtUpdate.setString(2, String.valueOf(price));
        pstmtUpdate.setString(3, String.valueOf(taskOprID));
        pstmtUpdate.executeUpdate();

        updateTasksTables(oprID);
    }

    public void updateTasksTables(int taskID) throws SQLException {
        Connection con = connection();
        List<TaskOperationsEntity> tasks_operations = getOperationByTaskID(taskID);
        String timeNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String standartDate = "1900-01-01";

        boolean isCompleted = true;
        float cost = 0;

        PreparedStatement pstmtTasksUpdate = con.prepareStatement("UPDATE tasks SET cost = ?,status = ?,end_date = ? WHERE id = ?;");
        pstmtTasksUpdate.setString(4, String.valueOf(taskID));

        if (tasks_operations.size() == 1) {
            if (tasks_operations.get(0).isCompleted) {
                pstmtTasksUpdate.setString(1, String.valueOf(tasks_operations.get(0).cost));
                pstmtTasksUpdate.setString(2, "Completed");
                pstmtTasksUpdate.setString(3, timeNow);
            } else {
                pstmtTasksUpdate.setString(1, String.valueOf(tasks_operations.get(0).cost));
                pstmtTasksUpdate.setString(2, "inProgress");
                pstmtTasksUpdate.setString(3, standartDate);
            }
        } else {
            for (TaskOperationsEntity value : tasks_operations) {
                cost += value.cost;
                if (!value.isCompleted)
                    isCompleted = false;

            }

            if (isCompleted) {
                pstmtTasksUpdate.setString(1, String.valueOf(cost));
                pstmtTasksUpdate.setString(2, "Completed");
                pstmtTasksUpdate.setString(3, timeNow);
            } else {
                pstmtTasksUpdate.setString(1, String.valueOf(cost));
                pstmtTasksUpdate.setString(2, "inProgress");
                pstmtTasksUpdate.setString(3, standartDate);
            }

        }
        pstmtTasksUpdate.executeUpdate();
    }

    public List<TaskOperationsEntity> getOperationByTaskID(int taskOperationID) throws SQLException {
        Connection con = connection();
        List<TaskOperationsEntity> task = new ArrayList<>();
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM tasks_operations WHERE task_id = ?;");
        pstmt.setString(1, String.valueOf(taskOperationID));
        ResultSet result = pstmt.executeQuery();
        if (result != null) {
            while (result.next()) {
                task.add(new TaskOperationsEntity(result.getInt("id"),
                        result.getInt("task_id"),
                        result.getString("info"),
                        result.getInt("planed_count"),
                        result.getInt("fact_count"),
                        result.getFloat("price"),
                        result.getFloat("cost"),
                        result.getBoolean("is_completed")));
            }
        }
        return task;
    }
}

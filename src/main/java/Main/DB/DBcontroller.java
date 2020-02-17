package Main.DB;

import Main.Entities.OperationsEntity;
import Main.Entities.TaskEntity;
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
        Connection c;
        return c = DriverManager.getConnection("jdbc:hsqldb:mem:some", "SA", "");
    }

    public void initializeDB() throws SQLException {
        Connection con = connection();
        con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS operations (id INTEGER IDENTITY NOT NULL , description VARCHAR(255), cost float,  status VARCHAR(10), end_date DATE, PRIMARY KEY (id));");
        con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS tasks(id INTEGER IDENTITY NOT NULL , operation_id INT, info VARCHAR(255), planed_count smallint, fact_count smallint, price float, cost float, is_completed boolean, PRIMARY KEY (id), FOREIGN KEY (operation_id) REFERENCES operations(id));");
    }

    // All or Opened
    public List<OperationsEntity> getOperations(String param) throws SQLException {
        Connection con = connection();
        List<OperationsEntity> operations = new ArrayList<>();
        ResultSet result;
        switch (param) {
            case "All":
                result = con.createStatement().executeQuery("SELECT * FROM operations;");
                break;
            case "Opened":
                result = con.createStatement().executeQuery("SELECT * FROM operations WHERE status = 'inProgress' OR status = 'Project';");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + param);
        }

        if (result != null) {
            while (result.next()) {
                if (result.getString("end_date") != null) {
                    operations.add(new OperationsEntity(result.getInt("id"),
                            result.getString("description"),
                            result.getFloat("cost"),
                            result.getString("status"),
                            LocalDate.parse(result.getString("end_date"))));
                } else {
                    operations.add(new OperationsEntity(result.getInt("id"),
                            result.getString("description"),
                            result.getFloat("cost"),
                            result.getString("status"),
                            null));
                }

            }
        }
        return operations;
    }

    public List<OperationsEntity> getOperationBetweenTime(String startDate, String endDate) throws SQLException {
        Connection con = connection();
        List<OperationsEntity> operations = new ArrayList<>();
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM operations WHERE end_date BETWEEN ? AND ?;");
        pstmt.setString(1, startDate);
        pstmt.setString(2, endDate);
        ResultSet result = pstmt.executeQuery();

        if (result != null) {
            while (result.next()) {
                operations.add(new OperationsEntity(result.getInt("id"),
                        result.getString("description"),
                        result.getFloat("cost"),
                        result.getString("status"),
                        LocalDate.parse(result.getString("end_date"))));
            }
        }
        return operations;
    }

    public List<TaskEntity> getTask() throws SQLException {
        Connection con = connection();
        List<TaskEntity> task = new ArrayList<>();
        ResultSet result = con.createStatement().executeQuery("SELECT * FROM tasks;");
        if (result != null) {
            while (result.next()) {
                task.add(new TaskEntity(result.getInt("id"),
                        result.getInt("operation_id"),
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

    public void insertNewOperation(String description) throws SQLException {
        Connection con = connection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO operations (description,status,cost) VALUES (?,'Project', 0);");
        pstmt.setString(1, description);
        pstmt.executeUpdate();
    }

    public void insertNewTask(String operationID, String info, String planedCount, String price) throws SQLException {
        Connection con = connection();
        int cost = Integer.parseInt(price) * Integer.parseInt(planedCount);

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO tasks (operation_id, info, planed_count, price,cost, is_completed) VALUES (?,?,?,?,?,FALSE);");
        pstmt.setString(1, String.valueOf(operationID));
        pstmt.setString(2, info);
        pstmt.setString(3, planedCount);
        pstmt.setString(4, price);
        pstmt.setString(5, String.valueOf(cost));
        pstmt.executeUpdate();
        updateOperationTables(Integer.parseInt(operationID));
    }

    public void closeTask(String taskID, String factCount) throws SQLException {
        Connection con = connection();

        PreparedStatement pstmtGet = con.prepareStatement("SELECT * FROM tasks WHERE id = ?");
        pstmtGet.setString(1, String.valueOf(taskID));
        ResultSet result = pstmtGet.executeQuery();

        float taskPrice = 0;
        int oprID = 0;
        while (result.next()) {
            taskPrice = result.getFloat("price");
            oprID = result.getInt("operation_id");
        }
        double price = taskPrice * Float.parseFloat(factCount);

        PreparedStatement pstmtUpdate = con.prepareStatement("UPDATE tasks SET fact_count = ?, cost = ?, is_completed = true WHERE id = ?");
        pstmtUpdate.setString(1, String.valueOf(factCount));
        pstmtUpdate.setString(2, String.valueOf(price));
        pstmtUpdate.setString(3, String.valueOf(taskID));
        pstmtUpdate.executeUpdate();

        updateOperationTables(oprID);
    }

    public void updateOperationTables(int operationID) throws SQLException {
        Connection con = connection();
        List<TaskEntity> tasks = getTaskByOperationID(operationID);
        String timeNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String standartDate = "1900-01-01";

        boolean isCompleted = true;
        float cost = 0;

        PreparedStatement pstmtOperationsUpdate = con.prepareStatement("UPDATE operations SET cost = ?,status = ?,end_date = ? WHERE id = ?;");
        pstmtOperationsUpdate.setString(4, String.valueOf(operationID));

        if (tasks.size() == 1) {
            if (tasks.get(0).isCompleted) {
                pstmtOperationsUpdate.setString(1, String.valueOf(tasks.get(0).cost));
                pstmtOperationsUpdate.setString(2, "Completed");
                pstmtOperationsUpdate.setString(3, timeNow);
            } else {
                pstmtOperationsUpdate.setString(1, String.valueOf(tasks.get(0).cost));
                pstmtOperationsUpdate.setString(2, "inProgress");
                pstmtOperationsUpdate.setString(3, standartDate);
            }
        } else {
            for (TaskEntity value : tasks) {
                cost += value.cost;
                if (!value.isCompleted)
                    isCompleted = false;

            }

            if (isCompleted) {
                pstmtOperationsUpdate.setString(1, String.valueOf(cost));
                pstmtOperationsUpdate.setString(2, "Completed");
                pstmtOperationsUpdate.setString(3, timeNow);
            } else {
                pstmtOperationsUpdate.setString(1, String.valueOf(cost));
                pstmtOperationsUpdate.setString(2, "inProgress");
                pstmtOperationsUpdate.setString(3, standartDate);
            }

        }
        pstmtOperationsUpdate.executeUpdate();
    }

    public List<TaskEntity> getTaskByOperationID(int operationID) throws SQLException {
        Connection con = connection();
        List<TaskEntity> task = new ArrayList<>();
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM tasks WHERE operation_id = ?;");
        pstmt.setString(1, String.valueOf(operationID));
        ResultSet result = pstmt.executeQuery();
        if (result != null) {
            while (result.next()) {
                task.add(new TaskEntity(result.getInt("id"),
                        result.getInt("operation_id"),
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

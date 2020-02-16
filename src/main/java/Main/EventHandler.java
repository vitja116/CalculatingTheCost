package Main;

import Main.Entitys.OperationsEntity;
import Main.Entitys.TaskEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventHandler {

    public DBcontroller dbcontroller = new DBcontroller();

    // All or Opened
    public List<OperationsEntity> getOperations(String param) {
        List<OperationsEntity> opr = new ArrayList<>();
        try {
            opr = dbcontroller.getOperations(param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return opr;
    }

    public List<OperationsEntity> getOperationBetweenTime(String startDate, String endDate) {
        List<OperationsEntity> opr = new ArrayList<>();
        List<OperationsEntity> sortedOpr = new ArrayList<>();
        try {
            opr = dbcontroller.getOperationBetweenTime(startDate, endDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(opr.size() != 0){
            for (OperationsEntity value: opr) {
                if(value.status.equals("Completed")){
                    sortedOpr.add(value);
                }
            }
        }
        opr = sortedOpr;
        return opr;
    }


    public List<TaskEntity> getTasks(int operationID) {
        List<TaskEntity> tasks = new ArrayList<>();
        try {
            tasks = dbcontroller.getTask(operationID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public void createNewOperation(String desc) {
        try {
            dbcontroller.insertNewOperation(desc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewTask(int operationID, String info, int planedCount, int price){
        try {
            dbcontroller.insertNewTask(operationID, info, planedCount, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCostBetweenTime(String startDate, String endDate){
        int cost = 0;
        List<OperationsEntity> opr = getOperationBetweenTime(startDate, endDate);
        for (OperationsEntity value: opr) {
            cost+=value.cost;
        }
        return cost;
    }

    public void closeTask(int taskID, int factCount){
        try {
            dbcontroller.closeTask(taskID, factCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

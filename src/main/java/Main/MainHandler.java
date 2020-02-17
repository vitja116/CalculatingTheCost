package Main;

import Main.Entities.TasksEntity;
import Main.Interfaces.DBInterface;
import Main.Interfaces.GuiInterface;

import java.sql.SQLException;
import java.util.List;

public class MainHandler {

    public DataCheck dataCheck = new DataCheck();

    public DBInterface dbInterface;
    public GuiInterface guiInterface;

    public MainHandler(DBInterface dbInterface, GuiInterface guiInterface) {
        this.dbInterface = dbInterface;
        this.guiInterface = guiInterface;
    }

    public void initializeDB() {
        try {
            dbInterface.initializeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadMainScreen();
    }

    public void loadMainScreen() {
        guiInterface.mainScreen();
        try {
            eventHandler(guiInterface.getEventNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 0 Error
     * 1 Show all tasks and Tasks
     * 2 Create new task
     * 3 Show opened tasks
     * 4 Show closed tasks between time
     * 5 Create new operation to task
     * 6 Close operation
     * 7 Cost of all tasks between time
     */
    public void eventHandler(int eventNumber) throws SQLException {

        switch (eventNumber) {
            case 0:
                break;
            case 1:
                guiInterface.outputOperationsAndTasks(dbInterface.getTasks("All"), dbInterface.getTaskOperations());
                break;
            case 2:
                String desck = guiInterface.getNewTaskData();
                if (dataCheck.checkDescription(desck)) {
                    dbInterface.insertNewTaskOperation(desck);
                    guiInterface.outputNewTaskStatus("Success");
                } else
                    guiInterface.outputNewTaskStatus("Error invalid description");
                break;
            case 3:
                guiInterface.outputOperationsAndTasks(dbInterface.getTasks("Opened"), dbInterface.getTaskOperations());
                break;
            case 4:
                List<String> date = guiInterface.getTasksBetweenTimeData();
                if (dataCheck.checkStartDateAndEndDate(date)) {
                    guiInterface.outputForTasksBetweenTime(dbInterface.getTasksBetweenTime(date.get(0), date.get(1)));
                } else {
                    guiInterface.errorLogForTasksBetweenTime();
                }
                break;
            case 5:
                List<String> operationInfo = guiInterface.getNewOperationInfo();
                if (dataCheck.checkTaskInfo(operationInfo)) {
                    dbInterface.insertNewTaskOperation(operationInfo.get(0), operationInfo.get(1), operationInfo.get(2), operationInfo.get(3));
                    guiInterface.outputForNewOperation("Success");
                } else {
                    guiInterface.outputForNewOperation("Error invalid data");
                }
                break;
            case 6:
                List<String> closeOperationInfo = guiInterface.getCloseOperationInfo();
                if (dataCheck.checkCloseTaskInfo(closeOperationInfo)) {
                    dbInterface.closeTaskOperations(closeOperationInfo.get(0), closeOperationInfo.get(1));
                    guiInterface.outputForCloseOperation("Success");
                } else {
                    guiInterface.outputForCloseOperation("Error invalid data");
                }
                break;
            case 7:
                List<String> dateCost = guiInterface.getCostBetweenTimeInfo();
                if (dataCheck.checkStartDateAndEndDate(dateCost)) {
                    int cost = 0;
                    List<TasksEntity> opr = dbInterface.getTasksBetweenTime(dateCost.get(0), dateCost.get(1));
                    for (TasksEntity value : opr) {
                        cost += value.cost;
                    }
                    guiInterface.outputCostBetweenTime(cost);
                } else {
                    guiInterface.errorLogForCostBetweenTime("Invalid data");
                }
                break;
        }
        loadMainScreen();
    }

}

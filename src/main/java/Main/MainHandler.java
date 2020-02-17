package Main;

import Main.Entities.OperationsEntity;
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
     * 1 Show all operations and Tasks
     * 2 Create new operations
     * 3 Show opened operation
     * 4 Show closed operation between time
     * 5 Create new task to operation
     * 6 Close task
     * 7 Cost of all operation between time
     */
    public void eventHandler(int eventNumber) throws SQLException {

        switch (eventNumber) {
            case 0:
                break;
            case 1:
                guiInterface.outputOperationsAndTasks(dbInterface.getOperations("All"), dbInterface.getTask());
                break;
            case 2:
                String desck = guiInterface.getNewOperationData();
                if (dataCheck.checkDescription(desck)) {
                    dbInterface.insertNewOperation(desck);
                    guiInterface.outputNewOperationStatus("Success");
                } else
                    guiInterface.outputNewOperationStatus("Error invalid description");
                break;
            case 3:
                guiInterface.outputOperationsAndTasks(dbInterface.getOperations("Opened"), dbInterface.getTask());
                break;
            case 4:
                List<String> date = guiInterface.getOperationBetweenTimeData();
                if (dataCheck.checkStartDateAndEndDate(date)) {
                    guiInterface.outputForOperationBetweenTime(dbInterface.getOperationBetweenTime(date.get(0), date.get(1)));
                } else {
                    guiInterface.errorLogForOperationBetweenTime();
                }
                break;
            case 5:
                List<String> taskInfo = guiInterface.getNewTaskInfo();
                if (dataCheck.checkTaskInfo(taskInfo)) {
                    dbInterface.insertNewTask(taskInfo.get(0), taskInfo.get(1), taskInfo.get(2), taskInfo.get(3));
                    guiInterface.outputForNewTask("Success");
                } else {
                    guiInterface.outputForNewTask("Error invalid data");
                }
                break;
            case 6:
                List<String> closeTaskInfo = guiInterface.getCloseTaskInfo();
                if (dataCheck.checkCloseTaskInfo(closeTaskInfo)) {
                    dbInterface.closeTask(closeTaskInfo.get(0), closeTaskInfo.get(1));
                    guiInterface.outputForCloseTask("Success");
                } else {
                    guiInterface.outputForCloseTask("Error invalid data");
                }
                break;
            case 7:
                List<String> dateCost = guiInterface.getCostBetweenTimeInfo();
                if (dataCheck.checkStartDateAndEndDate(dateCost)) {
                    int cost = 0;
                    List<OperationsEntity> opr = dbInterface.getOperationBetweenTime(dateCost.get(0), dateCost.get(1));
                    for (OperationsEntity value : opr) {
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

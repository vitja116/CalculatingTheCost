package Main.Interfaces;

import Main.Entities.OperationsEntity;
import Main.Entities.TaskEntity;

import java.util.List;

public interface GuiInterface {

    void mainScreen();

    int getEventNumber();

    void outputOperationsAndTasks(List<OperationsEntity> opr, List<TaskEntity> task);

    String getNewOperationData();

    void outputNewOperationStatus(String status);

    List<String> getOperationBetweenTimeData();

    void errorLogForOperationBetweenTime();

    void outputForOperationBetweenTime(List<OperationsEntity> opr);

    List<String> getNewTaskInfo();

    void outputForNewTask(String status);

    List<String> getCloseTaskInfo();

    void outputForCloseTask(String status);

    List<String> getCostBetweenTimeInfo();

    void errorLogForCostBetweenTime(String error);

    void outputCostBetweenTime(int cost);

}

package Main.Interfaces;

import Main.Entities.TasksEntity;
import Main.Entities.TaskOperationsEntity;

import java.util.List;

public interface GuiInterface {

    void mainScreen();

    int getEventNumber();

    void outputOperationsAndTasks(List<TasksEntity> tasks, List<TaskOperationsEntity> opr);

    String getNewTaskData();

    void outputNewTaskStatus(String status);

    List<String> getTasksBetweenTimeData();

    void errorLogForTasksBetweenTime();

    void outputForTasksBetweenTime(List<TasksEntity> tasks);

    List<String> getNewOperationInfo();

    void outputForNewOperation(String status);

    List<String> getCloseOperationInfo();

    void outputForCloseOperation(String status);

    List<String> getCostBetweenTimeInfo();

    void errorLogForCostBetweenTime(String error);

    void outputCostBetweenTime(int cost);

}

package Main.Console;

import Main.Entities.OperationsEntity;
import Main.Entities.TaskEntity;
import Main.Interfaces.GuiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleHandler implements GuiInterface {

    public void mainScreen() {
        System.out.println("You are now on main screen" +
                "\n" + "1 Show all operations" +
                "\n" + "2 Create new operations" +
                "\n" + "3 Show opened operation" +
                "\n" + "4 Show closed operation between time" +
                "\n" + "5 Create new task to operation" +
                "\n" + "6 Close task" +
                "\n" + "7 Cost of all operation between time");
    }

    public int getEventNumber() {
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        assert input != null;
        switch (input) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            default:
                System.out.println("Something wrong");
                return 0;
        }
    }

    public void outputOperationsAndTasks(List<OperationsEntity> opr, List<TaskEntity> task) {
        if (opr.size() == 0) {
            System.out.println("You don't have selected operations.");
        } else {
            for (OperationsEntity value : opr) {
                System.out.println("-----Operation-----");
                switch (value.status) {
                    case "Project":
                        System.out.println("Operation number " + value.id + " Project" + "\n" + "Description:" + "\n" + value.description);
                        break;
                    case "inProgress":
                        System.out.println("Operation number " + value.id + " In Progress" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost);
                        System.out.println("---- Tasks ----");
                        taskOutputByID(task, value.id);
                        break;
                    case "Completed":
                        System.out.println("Operation number " + value.id + " Completed" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost + "\n" + "Competed date:" + "\n" + value.endDate);
                        System.out.println("---- Tasks ----");
                        taskOutputByID(task, value.id);
                        break;
                }

                System.out.println("--------------------");
            }
        }
    }

    public String getNewOperationData() {
        System.out.println("Set description to operation:");

        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        if (input != null) {
            return input;
        }
        System.out.println("Incorrect input");
        return null;
    }

    public void outputNewOperationStatus(String status) {
        System.out.println(status);
    }

    public void taskOutputByID(List<TaskEntity> task, int oprID) {
        for (TaskEntity value : task) {
            if (value.operationID == oprID) {
                if (value.isCompleted) {
                    System.out.println("Task number " + value.id + " Completed" + "\n" + "Info:" + "\n" + value.info + "\n" + "Planed count:" + "\n" + value.planedCount + "\n" + "Fact count:" + "\n" + value.factCount + "\n" + "Price:" + "\n" + value.price + "\n" + "Cost:" + "\n" + value.cost);
                } else {
                    System.out.println("Task number " + value.id + " In Progress" + "\n" + "Info:" + "\n" + value.info + "\n" + "Planed count:" + "\n" + value.planedCount + "\n" + "Price:" + "\n" + value.price + "\n" + "Cost:" + "\n" + value.cost);
                }
                System.out.println("---------------");
            }
        }
    }

    public List<String> getOperationBetweenTimeData() {
        List<String> time = new ArrayList<>();
        System.out.println("Input start time(Format yyyy-mm-dd):");

        Scanner startDateReader = new Scanner(System.in);
        String startDate = startDateReader.nextLine();

        System.out.println("Input end time(Format yyyy-mm-dd):");

        Scanner endDateReader = new Scanner(System.in);
        String endDate = endDateReader.nextLine();

        time.add(endDate);
        time.add(startDate);

        return time;
    }

    public void errorLogForOperationBetweenTime() {
        System.out.println("Invalid format");
    }

    public void outputForOperationBetweenTime(List<OperationsEntity> opr) {
        for (OperationsEntity value : opr) {
            System.out.println("-----Operation-----");
            switch (value.status) {
                case "Project":
                    System.out.println("Operation number " + value.id + " Project" + "\n" + "Description:" + "\n" + value.description);
                    break;
                case "inProgress":
                    System.out.println("Operation number " + value.id + " In Progress" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost);
                    break;
                case "Completed":
                    System.out.println("Operation number " + value.id + " Completed" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost + "\n" + "Competed date:" + "\n" + value.endDate);
                    break;
            }

            System.out.println("--------------------");
        }
    }

    public List<String> getNewTaskInfo() {
        List<String> infoList = new ArrayList<>();
        System.out.println("Insert number of Operation:");

        Scanner operationIDReader = new Scanner(System.in);
        String operationID = operationIDReader.nextLine();
        infoList.add(operationID);

        System.out.println("Insert info:");

        Scanner infoReader = new Scanner(System.in);
        String info = infoReader.nextLine();
        infoList.add(info);

        System.out.println("Insert planed count:");

        Scanner countReader = new Scanner(System.in);
        String count = countReader.nextLine();
        infoList.add(count);

        System.out.println("Insert price:");

        Scanner priceReader = new Scanner(System.in);
        String price = priceReader.nextLine();
        infoList.add(price);

        return infoList;

    }

    public void outputForNewTask(String status) {
        System.out.println(status);
    }

    public List<String> getCloseTaskInfo() {
        List<String> taskInfo = new ArrayList<>();
        System.out.println("Insert task id:");

        Scanner taskIDReader = new Scanner(System.in);
        String taskID = taskIDReader.nextLine();
        taskInfo.add(taskID);

        System.out.println("Insert fact. count:");

        Scanner factCountReader = new Scanner(System.in);
        String factCount = factCountReader.nextLine();
        taskInfo.add(factCount);

        return taskInfo;
    }

    public void outputForCloseTask(String status) {
        System.out.println(status);
    }

    public List<String> getCostBetweenTimeInfo() {
        List<String> date = new ArrayList<>();
        System.out.println("Input start time(Format yyyy-mm-dd):");

        Scanner startDateReader = new Scanner(System.in);
        String startDate = startDateReader.nextLine();
        date.add(startDate);

        System.out.println("Input end time(Format yyyy-mm-dd):");

        Scanner endDateReader = new Scanner(System.in);
        String endDate = endDateReader.nextLine();
        date.add(endDate);

        return date;
    }

    public void errorLogForCostBetweenTime(String error) {
        System.out.println(error);
    }

    public void outputCostBetweenTime(int cost) {
        System.out.println("Cost equals " + cost + " Euro.");
    }

}

package Main.Console;

import Main.Entities.TasksEntity;
import Main.Entities.TaskOperationsEntity;
import Main.Interfaces.GuiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleHandler implements GuiInterface {

    public void mainScreen() {
        System.out.println("You are now on main screen" +
                "\n" + "1 Show all tasks" +
                "\n" + "2 Create new task" +
                "\n" + "3 Show opened tasks" +
                "\n" + "4 Show closed tasks between time" +
                "\n" + "5 Create new operation to task" +
                "\n" + "6 Close operation" +
                "\n" + "7 Cost of all tasks between time");
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

    public void outputOperationsAndTasks(List<TasksEntity> tasks, List<TaskOperationsEntity> opr) {
        if (tasks.size() == 0) {
            System.out.println("You don't have selected tasks.");
        } else {
            for (TasksEntity value : tasks) {
                System.out.println("-----Tasks-----");
                switch (value.status) {
                    case "Project":
                        System.out.println("Task number " + value.id + " Project" + "\n" + "Description:" + "\n" + value.description);
                        break;
                    case "inProgress":
                        System.out.println("Task number " + value.id + " In Progress" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost);
                        System.out.println("---- Operations ----");
                        operationOutputByID(opr, value.id);
                        break;
                    case "Completed":
                        System.out.println("Task number " + value.id + " Completed" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost + "\n" + "Competed date:" + "\n" + value.endDate);
                        System.out.println("---- Operations ----");
                        operationOutputByID(opr, value.id);
                        break;
                }

                System.out.println("--------------------");
            }
        }
    }

    public String getNewTaskData() {
        System.out.println("Set description to task:");

        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        if (input != null) {
            return input;
        }
        System.out.println("Incorrect input");
        return null;
    }

    public void outputNewTaskStatus(String status) {
        System.out.println(status);
    }

    public void operationOutputByID(List<TaskOperationsEntity> opr, int taskID) {
        for (TaskOperationsEntity value : opr) {
            if (value.operationID == taskID) {
                if (value.isCompleted) {
                    System.out.println("Task number " + value.id + " Completed" + "\n" + "Info:" + "\n" + value.info + "\n" + "Planed count:" + "\n" + value.planedCount + "\n" + "Fact count:" + "\n" + value.factCount + "\n" + "Price:" + "\n" + value.price + "\n" + "Cost:" + "\n" + value.cost);
                } else {
                    System.out.println("Task number " + value.id + " In Progress" + "\n" + "Info:" + "\n" + value.info + "\n" + "Planed count:" + "\n" + value.planedCount + "\n" + "Price:" + "\n" + value.price + "\n" + "Cost:" + "\n" + value.cost);
                }
                System.out.println("---------------");
            }
        }
    }

    public List<String> getTasksBetweenTimeData() {
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

    public void errorLogForTasksBetweenTime() {
        System.out.println("Invalid format");
    }

    public void outputForTasksBetweenTime(List<TasksEntity> tasks) {
        for (TasksEntity value : tasks) {
            System.out.println("-----Tasks-----");
            switch (value.status) {
                case "Project":
                    System.out.println("Task number " + value.id + " Project" + "\n" + "Description:" + "\n" + value.description);
                    break;
                case "inProgress":
                    System.out.println("Task number " + value.id + " In Progress" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost);
                    break;
                case "Completed":
                    System.out.println("Task number " + value.id + " Completed" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost + "\n" + "Competed date:" + "\n" + value.endDate);
                    break;
            }

            System.out.println("--------------------");
        }
    }

    public List<String> getNewOperationInfo() {
        List<String> infoList = new ArrayList<>();
        System.out.println("Insert number of Task:");

        Scanner taskIDReader = new Scanner(System.in);
        String taskID = taskIDReader.nextLine();
        infoList.add(taskID);

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

    public void outputForNewOperation(String status) {
        System.out.println(status);
    }

    public List<String> getCloseOperationInfo() {
        List<String> taskInfo = new ArrayList<>();
        System.out.println("Insert operation id:");

        Scanner operationIDReader = new Scanner(System.in);
        String operationID = operationIDReader.nextLine();
        taskInfo.add(operationID);

        System.out.println("Insert fact. count:");

        Scanner factCountReader = new Scanner(System.in);
        String factCount = factCountReader.nextLine();
        taskInfo.add(factCount);

        return taskInfo;
    }

    public void outputForCloseOperation(String status) {
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

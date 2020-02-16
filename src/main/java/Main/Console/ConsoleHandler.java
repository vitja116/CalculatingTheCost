package Main.Console;

import Main.Entitys.OperationsEntity;
import Main.Entitys.TaskEntity;
import Main.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class ConsoleHandler {

    public EventHandler eventHandler = new EventHandler();

    public void mainScreen() {
        System.out.println("You are now on main screen" +
                "\n" + "1 Show all operations" +
                "\n" + "2 Create new operations" +
                "\n" + "3 Show opened operation" +
                "\n" + "4 Show closed operation between time" +
                "\n" + "5 Create new task to operation" +
                "\n" + "6 Close task" +
                "\n" + "7 Cost of all operation between time");
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        assert input != null;
        switch (input) {
            case "1":
                getAllOperations("All");
                break;
            case "2":
                createNewOperation();
                break;
            case "3":
                getAllOperations("Opened");
                break;
            case "4":
                getAllOperations("Time");
                break;
            case "5":
                createNewTask();
                break;
            case "6":
                closeTask();
                break;
            case "7":
                getCostBetweenTime();
            default:
                System.out.println("Something wrong");
                mainScreen();
                break;
        }
        mainScreen();
    }

    // All Opened Time
    public void getAllOperations(String param) {
        List<OperationsEntity> opr;
        if (param.equals("Time")) {
            opr = getOperationBetweenTime();
        } else {
            opr = eventHandler.getOperations(param);
        }

        if (opr.size() == 0) {
            System.out.println("You don't have selected operations.");
        } else {
            for (OperationsEntity value : opr) {
                List<TaskEntity> task = eventHandler.getTasks(value.id);
                System.out.println("-----Operations-----");
                switch (value.status) {
                    case "Project":
                        System.out.println("Operation number " + value.id + " Project" + "\n" + "Description:" + "\n" + value.description);
                        break;
                    case "inProgress":
                        System.out.println("Operation number " + value.id + " In Progress" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost);
                        System.out.println("---- Tasks ----");
                        taskOutput(task);
                        break;
                    case "Completed":
                        System.out.println("Operation number " + value.id + " Completed" + "\n" + "Description:" + "\n" + value.description + "\n" + "Cost:" + "\n" + value.cost + "\n" + "Competed date:" + "\n" + value.endDate);
                        System.out.println("---- Tasks ----");
                        taskOutput(task);
                        break;
                }

                System.out.println("--------------------");
            }
        }
    }

    public void taskOutput(List<TaskEntity> task) {
        for (TaskEntity value : task) {
            if (value.isCompleted) {
                System.out.println("Task number " + value.id + " Completed" + "\n" + "Info:" + "\n" + value.info + "\n" + "Planed count:" + "\n" + value.planedCount + "\n" + "Fact count:" + "\n" + value.factCount + "\n" + "Price:" + "\n" + value.price + "\n" + "Cost:" + "\n" + value.cost);
            } else {
                System.out.println("Task number " + value.id + " In Progress" + "\n" + "Info:" + "\n" + value.info + "\n" + "Planed count:" + "\n" + value.planedCount + "\n" + "Price:" + "\n" + value.price + "\n" + "Cost:" + "\n" + value.cost);
            }
            System.out.println("---------------");
        }
    }

    public List<OperationsEntity> getOperationBetweenTime() {
        System.out.println("Input start time(Format yyyy-mm-dd):");

        Scanner startDateReader = new Scanner(System.in);
        String startDate = startDateReader.nextLine();

        System.out.println("Input end time(Format yyyy-mm-dd):");

        Scanner endDateReader = new Scanner(System.in);
        String endDate = startDateReader.nextLine();

        return eventHandler.getOperationBetweenTime(startDate, endDate);

    }

    public void getCostBetweenTime() {
        System.out.println("Input start time(Format 01-01-19):");

        Scanner startDateReader = new Scanner(System.in);
        String startDate = startDateReader.nextLine();

        System.out.println("Input end time(Format 01-01-19):");
        BufferedReader endDateReader = new BufferedReader(new InputStreamReader(System.in));
        String endDate = null;
        try {
            endDate = endDateReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Cost equals " + eventHandler.getCostBetweenTime(startDate, endDate) + " Euro.");
    }

    public void createNewOperation() {
        System.out.println("Set description to operation:");

        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        if (input != null) {
            eventHandler.createNewOperation(input);
            System.out.println("Created successfully");
        }
    }

    public void createNewTask() {
        System.out.println("Insert number of Operation:");

        Scanner operationIDReader = new Scanner(System.in);
        int operationID = Integer.parseInt(operationIDReader.nextLine());

        System.out.println("Insert info:");

        Scanner infoReader = new Scanner(System.in);
        String info = infoReader.nextLine();

        System.out.println("Insert planed count:");

        Scanner countReader = new Scanner(System.in);
        int count = Integer.parseInt(countReader.nextLine());

        System.out.println("Insert price:");

        Scanner priceReader = new Scanner(System.in);
        int price = Integer.parseInt(priceReader.nextLine());

        if (info != null) {
            eventHandler.createNewTask(operationID, info, count, price);
            System.out.println("Success");
        } else {
            System.out.println("Error! you didn't insert some values");
        }

    }

    public void closeTask() {
        System.out.println("Insert task id:");

        Scanner taskIDReader = new Scanner(System.in);
        int taskID = Integer.parseInt(taskIDReader.nextLine());

        System.out.println("Insert fact. count:");

        Scanner factCountReader = new Scanner(System.in);
        int factCount = Integer.parseInt(factCountReader.nextLine());

        eventHandler.closeTask(taskID, factCount);
        System.out.println("Success");
    }

}

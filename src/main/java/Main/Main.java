package Main;

import Main.Console.ConsoleHandler;
import Main.Swing.PanelController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException, IOException {
        DBcontroller dbcontroller = new DBcontroller();
        PanelController panelController = new PanelController();
        ConsoleHandler consoleHandler = new ConsoleHandler();

        dbcontroller.initializeDB();

        System.out.println("Select GUI type" + "\n" + "1 Console" + "\n" + "2 Swing (in progress)" + "\n" + "3 Web (in progress)");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        switch (input) {
            case "1":
                consoleHandler.mainScreen();
                break;
            case "2":
                //panelController.mainPanelOutput(); TODO in progress
                break;
            case "3":
                //TODO add web
                break;
            default:
                System.out.println("Error");
                break;
        }


    }
}

package Main;

import Main.Console.ConsoleHandler;
import Main.DB.DBcontroller;
import Main.Interfaces.DBInterface;
import Main.Interfaces.GuiInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        DBInterface dbInterface = null;
        GuiInterface guiInterface = null;

        System.out.println("Select GUI type" + "\n" + "1 Console" + "\n" + "2 Swing (in progress)" + "\n" + "3 Web (in progress)");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        switch (input) {
            case "1":
                dbInterface = new DBcontroller();
                guiInterface = new ConsoleHandler();
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

        new MainHandler(dbInterface, guiInterface).initializeDB();

    }
}

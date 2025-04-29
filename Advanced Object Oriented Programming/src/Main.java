import model.Model;
import cli.CLI;
import controller.Controller;
import view.View;

import javax.swing.*;
import java.util.Scanner;

/**
 * Main entry point for the Battleships game.
 * Allows the user to choose between CLI and GUI modes.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Battleships!");
        System.out.println("Select mode:");
        System.out.println("1 - CLI (Command Line)");
        System.out.println("2 - GUI (Graphical)");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            // Launch CLI version
            Model model = new Model();
            CLI cli = new CLI(model);
            cli.run();
        } else if (choice.equals("2")) {
            // Launch GUI version
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Model model = new Model();
                    View view = new View(model, null); // Create view first
                    Controller controller = new Controller(model, view); // Then controller
                    view.setController(controller); // Link controller to view
                }
            });
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
    }
}

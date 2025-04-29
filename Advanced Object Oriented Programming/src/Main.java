import model.Model;
import cli.CLI;
import controller.Controller;
import view.View;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Battleships!");
        System.out.println("Select mode:");
        System.out.println("1 - CLI (Command Line)");
        System.out.println("2 - GUI (Graphical)");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            // 启动 CLI 版本
            Model model = new Model();
            CLI cli = new CLI(model);
            cli.run();
        } else if (choice.equals("2")) {
            // 启动 GUI 版本
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Model model = new Model();
                    View view = new View(model, null); // 先建 View
                    Controller controller = new Controller(model, view); // 再建 Controller
                    view.setController(controller); // 反向绑定
                }
            });
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
    }
}

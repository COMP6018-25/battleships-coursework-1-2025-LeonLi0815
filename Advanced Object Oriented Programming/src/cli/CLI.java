package cli;

import model.*;
import java.util.Scanner;

/**
 * Command-line interface for the Battleships game.
 */
public class CLI {
    private Model model;
    private Scanner scanner;

    /**
     * Constructor for CLI.
     * @param model the game model
     */
    public CLI(Model model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Runs the CLI version of the game.
     */
    public void run() {
        System.out.println("=== Welcome to Battleships CLI ===");
        displayGrid();

        while (!model.allShipsSunk()) {
            System.out.print("Enter coordinate (e.g., A5): ");
            String input = getUserInput();

            try {
                AttackResult result = model.attack(input);
                if (result.isHit()) {
                    System.out.println("Hit!");
                    if (result.isSunk()) {
                        System.out.println("You sunk a ship!");
                    }
                } else {
                    System.out.println("Miss!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Try again.");
            }

            displayGrid();
        }

        System.out.println("Congratulations! You sank all the ships!");
        System.out.println("Total tries: " + model.getTries());
    }

    /**
     * Displays the current grid.
     */
    public void displayGrid() {
        Board board = model.getBoard();
        System.out.println("   1 2 3 4 5 6 7 8 9 10");

        for (int row = 0; row < board.getSize(); row++) {
            char rowLabel = (char) ('A' + row);
            System.out.print(rowLabel + "  ");
            for (int col = 0; col < board.getSize(); col++) {
                CellStatus status = board.getCellStatus(row, col);
                switch (status) {
                    case HIT:
                        System.out.print("H ");
                        break;
                    case MISS:
                        System.out.print("M ");
                        break;
                    default:
                        System.out.print(". ");
                        break;
                }
            }
            System.out.println();
        }
    }

    /**
     * Gets user input.
     */
    public String getUserInput() {
        return scanner.nextLine().toUpperCase().trim();
    }
}

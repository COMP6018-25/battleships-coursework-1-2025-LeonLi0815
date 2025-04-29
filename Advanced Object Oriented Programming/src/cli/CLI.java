package cli;

import model.*;

import java.util.Scanner;

public class CLI {
    private Model model;
    private Scanner scanner;

    public CLI(Model model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("=== Welcome to Battleships CLI ===");
        displayGrid();

        while (!model.allShipsSunk()) {
            System.out.print("Enter coordinate (e.g., A5): ");
            String input = getUserInput();

            try {
                boolean hit = model.attack(input);
                if (hit) {
                    System.out.println("Hit!");
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

    public String getUserInput() {
        return scanner.nextLine().toUpperCase().trim();
    }
}

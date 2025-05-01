package controller;

import model.*;
import view.View;

/**
 * Controller for the Battleships GUI version.
 * Handles user actions and updates the Model and View accordingly.
 */
public class Controller {
    private Model model;
    private View view;

    /**
     * Constructor for Controller.
     * @param model the game model
     * @param view the game view
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Processes a user's guess based on clicked row and column.
     */
    public void processGuess(int row, int col) {
        String coordinate = convertToCoordinate(row, col);
        try {
            AttackResult result = model.attack(coordinate);
            if (result.isHit()) {
                view.showMessage("Hit!");
                if (result.isSunk()) {
                    view.showMessage("You sunk a ship!");
                }
            } else {
                view.showMessage("Miss!");
            }

            if (model.allShipsSunk()) {
                view.showMessage("Congratulations! You sank all ships in " + model.getTries() + " tries!");
                view.disableBoard();
            }
        } catch (IllegalArgumentException e) {
            view.showMessage("Invalid coordinate!");
        }
    }

    /**
     * Converts row and column into Battleships coordinate format (e.g., A5).
     */
    private String convertToCoordinate(int row, int col) {
        char rowChar = (char) ('A' + row);
        return rowChar + String.valueOf(col + 1);
    }

    public void loadFile(String filename) {
        boolean success = model.loadConfiguration(filename);
        if (!success) {
            view.showMessage("Failed to load configuration file.");
        } else {
            view.showMessage("Configuration loaded successfully.");
        }
    }

    public void updateView() {
        view.refresh();
    }
}

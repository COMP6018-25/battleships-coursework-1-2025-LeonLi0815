package controller;

import model.*;
import view.View;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

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

package model;

import java.util.*;

/**
 * Main game model handling the game state.
 */
public class Model extends Observable {
    private Board board;
    private List<Ship> ships;
    private int tries;

    /**
     * Constructor for Model.
     */
    public Model() {
        board = new Board();
        ships = new ArrayList<>();
        tries = 0;
        randomlyPlaceShips();
    }

    private void randomlyPlaceShips() {
        int[] shipLengths = {5, 4, 3, 2, 2};
        Random random = new Random();

        for (int length : shipLengths) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(board.getSize());
                int col = random.nextInt(board.getSize());
                boolean horizontal = random.nextBoolean();

                Ship ship = new Ship();
                if (board.placeShip(ship, row, col, length, horizontal)) {
                    ships.add(ship);
                    placed = true;
                }
            }
        }
    }

    /**
     * Handles an attack based on input coordinate.
     */
    public AttackResult attack(String coordinate) {
        int row = coordinate.charAt(0) - 'A';
        int col = Integer.parseInt(coordinate.substring(1)) - 1;

        if (!isValidCoordinate(row, col)) {
            throw new IllegalArgumentException("Invalid coordinate: " + coordinate);
        }

        tries++;
        Ship shipHit = board.attack(row, col);

        boolean hit = (shipHit != null);
        boolean sunk = false;

        if (hit) {
            shipHit.registerHit(new Position(row, col));
            if (shipHit.isSunk()) {
                sunk = true;
            }
        }

        setChanged();
        notifyObservers();
        return new AttackResult(hit, sunk);
    }

    private boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize();
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public int getTries() {
        return tries;
    }

    public Board getBoard() {
        return board;
    }

    public boolean loadConfiguration(String filename) {
        return false; // TODO
    }
}

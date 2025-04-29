package model;

import java.util.*;

public class Model extends Observable {
    private Board board;
    private List<Ship> ships;
    private int tries;

    private int totalHits; // 🔥 新增字段：统计所有命中的格子数

    public Model() {
        board = new Board();
        ships = new ArrayList<>();
        tries = 0;
        totalHits = 0;
        randomlyPlaceShips();
    }

    public AttackResult attack(String coordinate) {
        int row = coordinate.charAt(0) - 'A';
        int col = Integer.parseInt(coordinate.substring(1)) - 1;

        if (!isValidCoordinate(row, col)) {
            throw new IllegalArgumentException("Invalid coordinate: " + coordinate);
        }

        tries++;
        boolean hit = board.attack(row, col);

        boolean sunk = false;
        if (hit) {
            totalHits++;
            sunk = updateShipHit();
        }

        setChanged();
        notifyObservers();
        return new AttackResult(hit, sunk);
    }

    private boolean updateShipHit() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                ship.hit();
                if (ship.isSunk()) {
                    return true; // 🔥 有船被击沉
                }
                break;
            }
        }
        return false;
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
        // TODO
        return false;
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

                if (board.placeShip(row, col, length, horizontal)) {
                    ships.add(new Ship(length));
                    placed = true;
                }
            }
        }
    }

}

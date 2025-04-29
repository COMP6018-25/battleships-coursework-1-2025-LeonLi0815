package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents the game board.
 */
public class Board {
    private final int SIZE = 10;
    private CellStatus[][] grid;
    private Map<Position, Ship> shipPositions; // Track which ship occupies each cell

    /**
     * Constructor for Board.
     */
    public Board() {
        grid = new CellStatus[SIZE][SIZE];
        shipPositions = new HashMap<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = CellStatus.EMPTY;
            }
        }
    }

    /**
     * Places a ship on the board.
     */
    public boolean placeShip(Ship ship, int row, int col, int length, boolean horizontal) {
        if (!canPlaceShip(row, col, length, horizontal)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            int r = row;
            int c = col;
            if (horizontal) {
                c += i;
            } else {
                r += i;
            }
            grid[r][c] = CellStatus.SHIP;
            Position pos = new Position(r, c);
            ship.addPosition(pos);
            shipPositions.put(pos, ship);
        }
        return true;
    }

    private boolean canPlaceShip(int row, int col, int length, boolean horizontal) {
        if (horizontal) {
            if (col + length > SIZE) return false;
            for (int i = 0; i < length; i++) {
                if (grid[row][col + i] != CellStatus.EMPTY) return false;
            }
        } else {
            if (row + length > SIZE) return false;
            for (int i = 0; i < length; i++) {
                if (grid[row + i][col] != CellStatus.EMPTY) return false;
            }
        }
        return true;
    }

    /**
     * Attacks a position on the board.
     * @return the Ship hit, or null if missed
     */
    public Ship attack(int row, int col) {
        Position pos = new Position(row, col);

        if (grid[row][col] == CellStatus.SHIP) {
            grid[row][col] = CellStatus.HIT;
            return shipPositions.get(pos);
        } else if (grid[row][col] == CellStatus.EMPTY) {
            grid[row][col] = CellStatus.MISS;
        }
        return null;
    }

    /**
     * Gets the status of a cell.
     */
    public CellStatus getCellStatus(int row, int col) {
        return grid[row][col];
    }

    /**
     * Gets board size.
     */
    public int getSize() {
        return SIZE;
    }
}

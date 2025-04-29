package model;

import java.util.Random;

public class Board {
    private final int SIZE = 10;
    private CellStatus[][] grid;

    public Board() {
        grid = new CellStatus[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = CellStatus.EMPTY;
            }
        }
    }

    public boolean placeShip(int row, int col, int length, boolean horizontal) {
        if (!canPlaceShip(row, col, length, horizontal)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (horizontal) {
                grid[row][col + i] = CellStatus.SHIP;
            } else {
                grid[row + i][col] = CellStatus.SHIP;
            }
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

    public boolean attack(int row, int col) {
        if (grid[row][col] == CellStatus.SHIP) {
            grid[row][col] = CellStatus.HIT;
            return true;
        } else if (grid[row][col] == CellStatus.EMPTY) {
            grid[row][col] = CellStatus.MISS;
        }
        return false;
    }

    public CellStatus getCellStatus(int row, int col) {
        return grid[row][col];
    }

    public int getSize() {
        return SIZE;
    }
}

package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ship occupying specific positions on the board.
 */
public class Ship {
    private List<Position> positions;
    private List<Position> hits;

    /**
     * Constructor for Ship.
     */
    public Ship() {
        positions = new ArrayList<>();
        hits = new ArrayList<>();
    }

    /**
     * Adds a position occupied by this ship.
     */
    public void addPosition(Position position) {
        positions.add(position);
    }

    /**
     * Registers a hit on this ship if the position matches.
     */
    public void registerHit(Position position) {
        if (positions.contains(position) && !hits.contains(position)) {
            hits.add(position);
        }
    }

    /**
     * Checks if the ship is fully sunk.
     */
    public boolean isSunk() {
        return hits.size() == positions.size();
    }
}

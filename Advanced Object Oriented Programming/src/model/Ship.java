package model;

public class Ship {
    private final int length;
    private int hits;

    public Ship(int length) {
        assert length > 0 : "Ship length must be positive";
        this.length = length;
        this.hits = 0;
    }

    public void hit() {
        if (hits < length) {
            hits++;
        }
    }

    public boolean isSunk() {
        return hits >= length;
    }

    public int getLength() {
        return length;
    }

    public int getHits() {
        return hits;
    }
}

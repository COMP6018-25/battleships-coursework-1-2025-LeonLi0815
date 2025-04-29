package model;

public class AttackResult {
    private final boolean hit;
    private final boolean sunk;

    public AttackResult(boolean hit, boolean sunk) {
        this.hit = hit;
        this.sunk = sunk;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isSunk() {
        return sunk;
    }
}

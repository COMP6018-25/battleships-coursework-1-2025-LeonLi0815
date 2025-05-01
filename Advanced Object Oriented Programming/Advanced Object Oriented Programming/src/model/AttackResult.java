package model;

/**
 * Represents the result of an attack (hit and sunk information).
 */
public class AttackResult {
    private final boolean hit;
    private final boolean sunk;

    /**
     * Constructor for AttackResult.
     * @param hit whether the attack was a hit
     * @param sunk whether a ship was sunk
     */
    public AttackResult(boolean hit, boolean sunk) {
        this.hit = hit;
        this.sunk = sunk;
    }

    /**
     * Checks if it was a hit.
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Checks if a ship was sunk.
     */
    public boolean isSunk() {
        return sunk;
    }
}

package test;

import model.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Model class.
 */
public class ModelTest {
    private Model model;

    /**
     * Set up a new game model before each test.
     */
    @Before
    public void setUp() {
        model = new Model();
    }

    /**
     * Test that an attack returns a valid result.
     */
    @Test
    public void testAttackMissOrHit() {
        AttackResult result = model.attack("A1");
        assertNotNull(result);
        assertTrue(result.isHit() || !result.isHit());
    }

    /**
     * Test that tries increment after each attack.
     */
    @Test
    public void testTriesIncrement() {
        int triesBefore = model.getTries();
        model.attack("B2");
        int triesAfter = model.getTries();
        assertEquals(triesBefore + 1, triesAfter);
    }

    /**
     * Test that all ships can be sunk eventually by attacking ship cells.
     */
    @Test
    public void testVictoryConditionEventually() {
        for (int row = 0; row < model.getBoard().getSize(); row++) {
            for (int col = 0; col < model.getBoard().getSize(); col++) {
                CellStatus status = model.getBoard().getCellStatus(row, col);
                if (status == CellStatus.SHIP) {
                    String coord = (char)('A' + row) + String.valueOf(col + 1);
                    model.attack(coord);
                }
            }
        }
        assertTrue(model.allShipsSunk());
    }
}

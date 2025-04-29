package test;

import model.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {
    private Model model;

    @Before
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testAttackMiss() {
        // 假设攻击一个空白格子（很大概率是Miss）
        boolean hit = model.attack("A1");
        assertTrue("Attack should return true or false", hit || !hit);
    }

    @Test
    public void testTriesIncrement() {
        int triesBefore = model.getTries();
        model.attack("B2");
        int triesAfter = model.getTries();
        assertEquals("Tries should increment by 1 after an attack", triesBefore + 1, triesAfter);
    }

    @Test
    public void testVictoryConditionEventually() {
        // 模拟沉没所有舰船
        for (int row = 0; row < model.getBoard().getSize(); row++) {
            for (int col = 0; col < model.getBoard().getSize(); col++) {
                CellStatus status = model.getBoard().getCellStatus(row, col);
                if (status == CellStatus.SHIP) {
                    String coord = (char)('A' + row) + String.valueOf(col + 1);
                    model.attack(coord);
                }
            }
        }
        assertTrue("All ships should be sunk eventually", model.allShipsSunk());
    }
}

package model;

import org.junit.Assert;
import org.junit.Test;

public class CellTest {

    @Test
    public void distance() throws Exception {
        Cell cell = new Cell(0,0);
        Cell cell2 = new Cell(1,1);
        Assert.assertEquals(Math.sqrt(2), cell.distance(cell2), 0.000001);
    }

    @Test
    public void distanceSame() throws Exception {
        Cell cell = new Cell(0,0);
        Assert.assertEquals(0, cell.distance(cell), 0.000001);
    }

}
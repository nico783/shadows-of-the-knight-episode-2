package model;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.sqrt;
import static org.junit.Assert.*;

public class CandidatesTest {

    @Test
    public void testRestrictColder() {
        Candidates candidates = new Candidates(10, 10);

        Cell origin = new Cell(7, 2);
        Cell target = new Cell(2, 2);
        Distance state = Distance.COLDER;

        candidates.restrict(origin, target, state);

        assertFalse(candidates.contains(new Cell(4, 2)));
        assertFalse(candidates.contains(new Cell(3, 2)));
        assertFalse(candidates.contains(new Cell(3, 1)));
        assertFalse(candidates.contains(new Cell(3, 3)));
        assertFalse(candidates.contains(new Cell(0, 0)));
        assertFalse(candidates.contains(new Cell(0, 9)));
        assertFalse(candidates.contains(new Cell(4, 0)));
        assertFalse(candidates.contains(new Cell(4, 9)));

        assertTrue(candidates.contains(new Cell(5, 0)));
        assertTrue(candidates.contains(new Cell(5, 1)));
        assertTrue(candidates.contains(new Cell(5, 2)));
        assertTrue(candidates.contains(new Cell(5, 3)));
        assertTrue(candidates.contains(new Cell(5, 9)));
        assertTrue(candidates.contains(new Cell(9, 0)));
        assertTrue(candidates.contains(new Cell(9, 9)));
    }

    @Test
    public void testRestrictWarmer() {
        Candidates candidates = new Candidates(10, 10);

        Cell origin = new Cell(7, 2);
        Cell target = new Cell(2, 2);
        Distance state = Distance.WARMER;

        candidates.restrict(origin, target, state);

        assertFalse(candidates.contains(new Cell(5, 2)));
        assertFalse(candidates.contains(new Cell(5, 1)));
        assertFalse(candidates.contains(new Cell(5, 3)));

        assertTrue(candidates.contains(new Cell(4, 2)));
        assertTrue(candidates.contains(new Cell(4, 1)));
        assertTrue(candidates.contains(new Cell(4, 3)));
    }

    @Test
    public void testRestrictSame() {
        Candidates candidates = new Candidates(10, 10);

        Cell origin = new Cell(2, 2);
        Cell target = new Cell(4, 4);
        Distance state = Distance.SAME;

        candidates.restrict(origin, target, state);

        // diagonale de (0,6) à (6,0)
        assertEquals(7, candidates.size());
        assertTrue(candidates.contains(new Cell(6, 0)));
        assertTrue(candidates.contains(new Cell(0, 6)));
    }

    @Test
    public void testSimple2x2Warmer() {
        Candidates candidates = new Candidates(2, 2);
        Cell origin = new Cell(0, 0);
        Cell target = new Cell(0, 1);
        Distance state = Distance.WARMER;
        candidates.restrict(origin, target, state);
        assertEquals(1, candidates.size());
        assertFalse(candidates.contains(new Cell(0, 0)));

    }

    @Test
    public void testSimple2x2Game() {
        Candidates candidates = new Candidates(2, 2);
        Cell origin = new Cell(0, 0);
        Cell target = new Cell(1, 0);
        Distance state = Distance.COLDER;
        candidates.restrict(origin, target, state);
        assertEquals(1, candidates.size());
        assertFalse(candidates.contains(new Cell(1, 0)));
        assertFalse(candidates.contains(new Cell(1, 1)));

        origin = new Cell(1, 0);
        target = new Cell(0, 1);
        state = Distance.WARMER;
        candidates.restrict(origin, target, state);
        assertEquals(0, candidates.size());
    }

    @Test
    public void testSimple2x2LastMove() {

        Set<Cell> cells = new HashSet<>();
        cells.add(new Cell(1, 0));
        cells.add(new Cell(0, 1));
        cells.add(new Cell(1, 1));

        Candidates candidates = new Candidates(cells);

        Cell origin = new Cell(0, 1);
        Cell target = new Cell(1, 0);
        Distance state = Distance.SAME;
        candidates.restrict(origin, target, state);

        assertEquals(1, candidates.size());
    }

    @Test(timeout = 150)
    public void testLeftPerf() {
        Candidates candidates = new Candidates(1000, 1000);
        long debut = System.currentTimeMillis();
        int left = candidates.getLeft();
        System.out.println(System.currentTimeMillis() - debut);
    }

    @Test(timeout = 150)
    public void testConstructorPerf() {
        long debut = System.currentTimeMillis();
        Candidates candidates = new Candidates(1000, 1000);
        System.out.println(System.currentTimeMillis() - debut);
    }

}
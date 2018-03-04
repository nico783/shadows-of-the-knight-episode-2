package model;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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
        Cell target = new Cell(4, 2);
        Distance state = Distance.SAME;

        candidates.restrict(origin, target, state);

        // diagonale de (0,6) à (6,0)
        assertEquals(10, candidates.size());
        assertTrue(candidates.contains(new Cell(3, 0)));
        assertTrue(candidates.contains(new Cell(3, 9)));
    }

    @Test
    public void testSimple2x2Warmer() {
        Candidates candidates = new Candidates(2, 2);
        Cell origin = new Cell(0, 0);
        Cell target = new Cell(1, 0);
        Distance state = Distance.WARMER;
        candidates.restrict(origin, target, state);
        assertEquals(2, candidates.size());
        assertFalse(candidates.contains(new Cell(0, 0)));
        assertFalse(candidates.contains(new Cell(0, 1)));
    }

    @Test
    public void testSimple2x2Game() {
        Candidates candidates = new Candidates(2, 2);
        Cell origin = new Cell(0, 0);
        Cell target = new Cell(1, 0);
        Distance state = Distance.COLDER;
        candidates.restrict(origin, target, state);
        assertEquals(2, candidates.size());
        assertFalse(candidates.contains(new Cell(1, 0)));
        assertFalse(candidates.contains(new Cell(1, 1)));
    }

    @Test
    public void testSimple2x2LastMove() {

        Set<Cell> cells = new HashSet<>();
        cells.add(new Cell(1, 0));
        cells.add(new Cell(0, 1));
        cells.add(new Cell(1, 1));

        Candidates candidates = new Candidates(1,1);

        Cell origin = new Cell(0, 1);
        Cell target = new Cell(1, 0);
        Distance state = Distance.SAME;
        candidates.restrict(origin, target, state);

        assertEquals(1, candidates.size());
    }

    @Ignore
    @Test(timeout = 150)
    public void testLeftPerf() {
        Candidates candidates = new Candidates(1000, 1000);
        long debut = System.currentTimeMillis();
        int left = candidates.getLeft();
        System.out.println(System.currentTimeMillis() - debut);
    }

    @Ignore
    @Test(timeout = 150)
    public void testConstructorPerf() {
        long debut = System.currentTimeMillis();
        Candidates candidates = new Candidates(1000, 1000);
        System.out.println(System.currentTimeMillis() - debut);
    }


    @Test
    public void testGetRight(){
        Candidates candidates = new Candidates(3,1);
        assertEquals(3, candidates.getRight());
    }

    @Test
    public void testGetLeft(){
        Candidates candidates = new Candidates(3,1);
        assertEquals(1, candidates.getLeft());
    }

    @Test
    public void testGetTop(){
        Candidates candidates = new Candidates(3,3);
        assertEquals(1, candidates.getTop());
    }

    @Test
    public void testGetBack(){
        Candidates candidates = new Candidates(3,3);
        assertEquals(3, candidates.getBack());
    }

    @Test
    public void testGetHorizontalTarget(){
        Candidates candidates = new Candidates(10, 10);
        Cell origin = new Cell(3,0);
        double middle = 6;
        Cell result = candidates.getHorizontalTarget(origin, middle);
        assertEquals(new Cell(6,0), result);
    }

    @Test
    public void testGetHorizontalTargetImpaire(){
        Candidates candidates = new Candidates(10, 10);
        Cell origin = new Cell(3,0);
        double middle = 5.5;
        Cell result = candidates.getHorizontalTarget(origin, middle);
        assertEquals(new Cell(5,0), result);
    }

    @Test
    public void testGetHorizontalTargetSameOrigin(){
        Candidates candidates = new Candidates(10, 10);
        Cell origin = new Cell(3,0);
        double middle = 3.5;
        Cell result = candidates.getHorizontalTarget(origin, middle);
        assertEquals(new Cell(4,0), result);
    }

    @Test
    public void testGetHorizontalTargetSameOrigin2(){
        Candidates candidates = new Candidates(10, 10);
        Cell origin = new Cell(3,0);
        double middle = 3;
        Cell result = candidates.getHorizontalTarget(origin, middle);
        assertEquals(new Cell(4,0), result);
    }


    @Test
    public void testGetVerticalTarget(){
        Candidates candidates = new Candidates(10, 10);
        Cell origin = new Cell(0,3);
        double middle = 6;
        Cell result = candidates.getVerticalTarget(origin, middle);
        assertEquals(new Cell(0,6), result);
    }

    @Test
    public void testGetVerticalTargetImpaire(){
        Candidates candidates = new Candidates(10, 10);
        Cell origin = new Cell(0,3);
        double middle = 5.5;
        Cell result = candidates.getVerticalTarget(origin, middle);
        assertEquals(new Cell(0,5), result);
    }

    @Test
    public void testGetVerticalTargetSameOrigin(){
        Candidates candidates = new Candidates(10, 10);
        Cell origin = new Cell(0,3);
        double middle = 3.5;
        Cell result = candidates.getVerticalTarget(origin, middle);
        assertEquals(new Cell(0,4), result);
    }

    @Test
    public void testGetVerticalTargetSameOrigin2(){
        Candidates candidates = new Candidates(10, 10);
        Cell origin = new Cell(0,3);
        double middle = 3;
        Cell result = candidates.getVerticalTarget(origin, middle);
        assertEquals(new Cell(0,4), result);
    }

    @Test
    public void testLimit(){
        Candidates candidates = new Candidates(1, 1);
        assertEquals(candidates.getRight(), candidates.getLeft());
        assertEquals(candidates.getBack(), candidates.getTop());
    }


}
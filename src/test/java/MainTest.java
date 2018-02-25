import model.Candidates;
import model.Cell;
import model.Distance;
import org.junit.Test;

public class MainTest {

    @Test
    public void testPerf() {
        Candidates candidates = new Candidates(1000, 1000);

        Cell origin = new Cell(7,2);
        Cell target = new Cell(2,2);
        Distance state = Distance.COLDER;


        candidates.restrict(origin, target, state);
        origin = new Cell(target);
        long debut = System.currentTimeMillis();
        int left = candidates.getLeft();
        System.out.println(System.currentTimeMillis()-debut);
        int right = candidates.getRight();
        if (left != right) {
            double middle = (double) (left + right) / 2;
            target = candidates.getHorizontalTarget(origin, middle);
        } else {
            double middle = (double) (candidates.getTop() + candidates.getBack()) / 2;
            target = candidates.getVerticalTarget(origin, middle);
        }


    }

}
import model.Candidates;
import model.Cell;
import model.Distance;

import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // width of the building.
        int H = in.nextInt(); // height of the building.
        int N = in.nextInt(); // maximum number of turns before game over.
        int X0 = in.nextInt();
        int Y0 = in.nextInt();

        Candidates candidates = new Candidates(W, H);
        Cell origin = new Cell(X0, Y0);
        Cell target = new Cell(X0, Y0);

        // game loop
        while (true) {
            String bombDir = in.next(); // Current distance to the bomb compared to previous distance (COLDER, WARMER, SAME or UNKNOWN)
            Distance state = Distance.valueOf(bombDir);

            candidates.restrict(origin, target, state);

            origin = new Cell(target);

            int left = candidates.getLeft();
            int right = candidates.getRight();
            if (left != right) {
                double middle = (double) (left + right) / 2;
                target = candidates.getHorizontalTarget(origin, middle);
            } else {
                double middle = (double) (candidates.getTop() + candidates.getBack()) / 2;
                target = candidates.getVerticalTarget(origin, middle);
            }

            System.out.println(target.getAbscisse() + " " + target.getOrdonnee());
        }
    }


}

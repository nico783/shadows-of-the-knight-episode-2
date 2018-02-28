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
        Cell old;
        Cell origin = new Cell(X0, Y0);
        Cell target = new Cell(X0, Y0);

        // game loop
        while (true) {
            String bombDir = in.next(); // Current distance to the bomb compared to previous distance (COLDER, WARMER, SAME or UNKNOWN)
            Distance state = Distance.valueOf(bombDir);

            old = new Cell(origin);

            int left = candidates.getLeft();
            int right = candidates.getRight();

            if (left != right) {
                candidates.restrictAbs(old, target, state);
                origin = new Cell(target);
                if(origin.distance(new Cell(candidates.getRight(), old.getOrdonnee()))>origin.distance(new Cell(candidates.getLeft(), old.getOrdonnee()))){
                    target = new Cell(candidates.getRight(), old.getOrdonnee());
                } else {
                    target = new Cell(candidates.getLeft(), old.getOrdonnee());
                }

            } else {
                candidates.restrictOrd(new Cell(left, old.getOrdonnee()), target, state);
                origin = new Cell(target);
                if(origin.distance(new Cell(left, candidates.getBack()))>origin.distance(new Cell(left, candidates.getTop()))){
                    target = new Cell(left, candidates.getBack());
                } else {
                    target = new Cell(left, candidates.getTop());
                }
            }

            System.out.println(target.getAbscisse() + " " + target.getOrdonnee());
        }
    }


}

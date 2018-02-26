package model;

import java.util.ArrayList;
import java.util.List;

public class Candidates {

    private List<Integer> rows;

    private List<Integer> cols;

    public Candidates(int w, int h) {
        rows = new ArrayList<>();
        cols = new ArrayList<>();

        for(int i = 0; i <= w-1; i++){
            cols.add(i);
        }

        for(int j = 0; j <= h-1; j++){
            rows.add(j);
        }
    }

    /**
     * Permet de restreindre l'ensemble des candidats possibles en fonction de l'origine,
     * de la cellule d'arrivée, et de l'"état de state"
     *
     * @param origin cellule de départ
     * @param target cellule d'arrivée
     * @param state  état
     */
    public void restrictAbs(Cell origin, Cell target, Distance state) {
        Point middlePoint = new Point(((double) target.getAbscisse() + (double) origin.getAbscisse()) / 2,
                ((double) target.getOrdonnee() + (double) origin.getOrdonnee()) / 2);

        Vecteur originDirection = new Vecteur(middlePoint, origin);
        Vecteur targetDirection = new Vecteur(middlePoint, target);

        List<Integer> toRemove = new ArrayList<>();

        int left = getLeft();
        int right = getRight();
        if (state == Distance.COLDER) {
            for (int i = left; i <= right; i++) {
                if (new Vecteur(middlePoint, new Cell(i, 0)).scalarProduct(targetDirection) >= 0) {
                    toRemove.add(i);
                }
            }
        } else if (state == Distance.WARMER) {
            for (int i = left; i <= right; i++) {
                if (new Vecteur(middlePoint, new Cell(i, 0)).scalarProduct(originDirection) >= 0) {
                    toRemove.add(i);
                }
            }
        } else if (state == Distance.SAME) {
            for (int i = left; i <= right; i++) {
                if (new Vecteur(middlePoint, new Cell(i, 0)).scalarProduct(originDirection) != 0) {
                    toRemove.add(i);
                }
            }
        } else if (state == Distance.UNKNOWN) {
            // Pas de restriction possible
        } else {
            throw new IllegalStateException("Impossible state");
        }
        cols.removeAll(toRemove);
    }

    public void restrictOrd(Cell origin, Cell target, Distance state) {
        Point middlePoint = new Point(((double) target.getAbscisse() + (double) origin.getAbscisse()) / 2,
                ((double) target.getOrdonnee() + (double) origin.getOrdonnee()) / 2);

        Vecteur originDirection = new Vecteur(middlePoint, origin);
        Vecteur targetDirection = new Vecteur(middlePoint, target);
        List<Integer> toRemove = new ArrayList<>();

        if (state == Distance.COLDER) {
            for (int i = getTop(); i <= getBack(); i++) {
                if (new Vecteur(middlePoint, new Cell(0, i)).scalarProduct(targetDirection) >= 0) {
                    toRemove.add(i);
                }
            }

        } else if (state == Distance.WARMER) {
            for (int i = getTop(); i <= getBack(); i++) {
                if (new Vecteur(middlePoint, new Cell(0, i)).scalarProduct(originDirection) >= 0) {
                    toRemove.add(i);
                }
            }
        } else if (state == Distance.SAME) {
            for (int i = getTop(); i <= getBack(); i++) {
                if (new Vecteur(middlePoint, new Cell(0, i)).scalarProduct(originDirection) != 0) {
                    toRemove.add(i);
                }
            }
        } else if (state == Distance.UNKNOWN) {
            // Pas de restriction possible
        } else {
            throw new IllegalStateException("Impossible state");
        }

        rows.removeAll(toRemove);
    }

    public int getRight() {
        return cols.stream().max(Integer::compareTo).get();
    }

    public int getLeft() {
        return cols.stream().min(Integer::compareTo).get();
    }

    public int getTop() {
        return rows.stream().min(Integer::compareTo).get();
    }

    public int getBack() {
        return rows.stream().max(Integer::compareTo).get();
    }

    public Cell getVerticalTarget(Cell origin, double middle) {
        int targetOrdonnee = computeTarget(middle, origin.getOrdonnee());
        return new Cell(getLeft(), targetOrdonnee);
    }

    public Cell getHorizontalTarget(Cell origin, double middle) {
        int targetAbscisse = computeTarget(middle, origin.getAbscisse());
        return new Cell(targetAbscisse, origin.getOrdonnee());
    }

    private int computeTarget(double middle, int dimension) {
        int middleInt;
        if (middle % 1 == 0) {
            middleInt = (int) middle;
        } else {
            middleInt = (int) Math.floor(middle);
        }

        // Il ne faut pas rester bloqué au même endroit
        if (middleInt == dimension && getLeft()!= getRight()) {
            return middleInt + 1;
        } else {
            return middleInt;
        }
    }


}

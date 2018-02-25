package model;

import java.util.*;

public class Candidates extends ForwardingSet<Cell> {

    private int rowsNumber;

    private int colsNumber;

    protected Candidates(Set<Cell> set) {
        super(set);
    }

    public Candidates(int w, int h) {
        this(new HashSet<>());
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                this.add(new Cell(i, j));
            }
        }
        rowsNumber = w - 1;
        colsNumber = h - 1;
    }

    /**
     * Permet de restreindre l'ensemble des candidats possibles en fonction de l'origine,
     * de la cellule d'arrivée, et de l'"état de state"
     *
     * @param origin cellule de départ
     * @param target cellule d'arrivée
     * @param state  état
     */
    public void restrict(Cell origin, Cell target, Distance state) {
        Point middlePoint = new Point(((double) target.getAbscisse() + (double) origin.getAbscisse()) / 2,
                ((double) target.getOrdonnee() + (double) origin.getOrdonnee()) / 2);

        Vecteur originDirection = new Vecteur(middlePoint, origin);
        Vecteur targetDirection = new Vecteur(middlePoint, target);

        if (state == Distance.COLDER) {
            this.removeIf(cell -> new Vecteur(middlePoint, cell).scalarProduct(targetDirection) >= 0);
        } else if (state == Distance.WARMER) {
            this.removeIf(cell -> new Vecteur(middlePoint, cell).scalarProduct(originDirection) >= 0);
        } else if (state == Distance.SAME) {
            this.removeIf(cell -> cell.distance(origin) != cell.distance(target));
        } else if (state == Distance.UNKNOWN) {
            // Pas de restriction possible
        } else {
            throw new IllegalStateException("Impossible state");
        }
    }

    public int getRight(){
        return this.stream().map(cell -> cell.getAbscisse()).reduce(0, (i1, i2) -> i1 < i2 ? i2 : i1);
    }

    public int getLeft(){
        return this.stream().map(cell -> cell.getAbscisse()).reduce(10000, (i1, i2) -> i1 < i2 ? i1 : i2);
    }

    public int getTop(){
        return this.stream().map(cell -> cell.getOrdonnee()).reduce(10000, (i1, i2) -> i1 < i2 ? i1 : i2);
    }

    public int getBack(){
        return this.stream().map(cell -> cell.getOrdonnee()).reduce(0, (i1, i2) -> i1 < i2 ? i2 : i1);
    }

    public Cell getVerticalTarget(Cell origin, double middle) {
        int targetOrdonnee = computeTarget(middle, origin.getOrdonnee());
        return new Cell(((Cell)(this.toArray()[0])).getAbscisse(), targetOrdonnee);
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
        if (middleInt == dimension) {
            return middleInt + 1;
        } else {
            return middleInt;
        }
    }


}

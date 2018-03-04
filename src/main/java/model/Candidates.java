package model;

public class Candidates {

    private int i0;
    private int in;
    private int j0;
    private int jn;

    public Candidates(int w, int h) {
        this.i0 = 0;
        this.in = w - 1;
        this.j0 = 0;
        this.jn = h - 1;
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

        Vecteur targetDirection = new Vecteur(middlePoint, target);
        Vecteur vecteurRef = new Vecteur(middlePoint, new Cell(this.getRight() + 1, this.getTop() - 1));

        if (this.getLeft() == this.getRight()) {
            if (state == Distance.COLDER) {
                if (vecteurRef.scalarProduct(targetDirection) <= 0) {
                    jn = (int) middlePoint.getOrdonnee();
                } else {
                    if (middlePoint.getOrdonnee() % 1 == 0) {
                        j0 = (int) middlePoint.getOrdonnee();
                    } else {
                        j0 = (int) middlePoint.getOrdonnee() + 1;
                    }
                }
            } else if (state == Distance.WARMER) {
                if (vecteurRef.scalarProduct(targetDirection) <= 0) {
                    if (middlePoint.getOrdonnee() % 1 == 0) {
                        j0 = (int) middlePoint.getOrdonnee();
                    } else {
                        j0 = (int) middlePoint.getOrdonnee() + 1;
                    }
                } else {
                    jn = (int) middlePoint.getOrdonnee();
                }
            } else if (state == Distance.SAME) {
                j0 = (int) middlePoint.getOrdonnee();
                jn = (int) middlePoint.getOrdonnee();
            } else if (state == Distance.UNKNOWN) {
                // Pas de restriction possible
            } else {
                throw new IllegalStateException("Impossible state");
            }
        } else {
            if (state == Distance.COLDER) {
                if (vecteurRef.scalarProduct(targetDirection) >= 0) {
                    in = (int) middlePoint.getAbscisse();
                } else {
                    if (middlePoint.getAbscisse() % 1 == 0) {
                        i0 = (int) middlePoint.getAbscisse();
                    } else {
                        i0 = (int) middlePoint.getAbscisse() + 1;
                    }
                }
            } else if (state == Distance.WARMER) {
                if (vecteurRef.scalarProduct(targetDirection) >= 0) {
                    if (middlePoint.getAbscisse() % 1 == 0) {
                        i0 = (int) middlePoint.getAbscisse();
                    } else {
                        i0 = (int) middlePoint.getAbscisse() + 1;
                    }
                } else {
                    in = (int) middlePoint.getAbscisse();
                }
            } else if (state == Distance.SAME) {
                i0 = (int) middlePoint.getAbscisse();
                in = (int) middlePoint.getAbscisse();
            } else if (state == Distance.UNKNOWN) {
                // Pas de restriction possible
            } else {
                throw new IllegalStateException("Impossible state");
            }
        }
    }

    public int getRight() {
        return in;
    }

    public int getLeft() {
        return i0;
    }

    public int getTop() {
        return j0 ;
    }

    public int getBack() {
        return jn ;
    }

    public Cell getVerticalTarget(Cell origin, double middle) {
        int targetOrdonnee = computeTarget(middle, origin.getOrdonnee());
        return new Cell(i0, targetOrdonnee);
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

    public int size() {
        return (in - i0 + 1) * (jn - j0 + 1);
    }


    public boolean contains(Object o) {
        Cell cell = (Cell) o;

        return i0 <= cell.getAbscisse()
                && cell.getAbscisse() <= in
                && j0 <= cell.getOrdonnee()
                && cell.getOrdonnee() <= jn;
    }

    @Override
    public String toString() {
        return "Candidates{" +
                "i0=" + i0 +
                ", in=" + in +
                ", j0=" + j0 +
                ", jn=" + jn +
                '}';
    }
}

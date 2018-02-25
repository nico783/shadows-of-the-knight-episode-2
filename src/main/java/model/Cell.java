package model;

import static java.lang.Math.sqrt;

public class Cell {

    private int abscisse;

    private int ordonnee;

    public Cell(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    public Cell(Cell other){
        this.abscisse = other.abscisse;
        this.ordonnee = other.ordonnee;
    }

    public int getAbscisse() {
        return abscisse;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    public double distance(Cell other){
        return sqrt((abscisse - other.abscisse) * (abscisse - other.abscisse) +
                (ordonnee - other.ordonnee) * (ordonnee - other.ordonnee));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (abscisse != cell.abscisse) return false;
        return ordonnee == cell.ordonnee;
    }

    @Override
    public int hashCode() {
        int result = abscisse;
        result = 31 * result + ordonnee;
        return result;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "abscisse=" + abscisse +
                ", ordonnee=" + ordonnee +
                '}';
    }
}

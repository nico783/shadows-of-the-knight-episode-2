package model;

public class Vecteur {

    private double abscisse;

    private double ordonnee;

    public Vecteur(Cell cell1, Cell cell2) {
        abscisse = cell2.getAbscisse() - cell1.getAbscisse();
        ordonnee = cell2.getOrdonnee() - cell1.getOrdonnee();
    }

    public Vecteur(Point point1, Cell cell2) {
        abscisse = cell2.getAbscisse() - point1.getAbscisse();
        ordonnee = cell2.getOrdonnee() - point1.getOrdonnee();
    }

    public double getAbscisse() {
        return abscisse;
    }

    public double getOrdonnee() {
        return ordonnee;
    }

    public double scalarProduct(Vecteur vecteur){
        return abscisse * vecteur.abscisse + ordonnee * vecteur.ordonnee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vecteur vecteur = (Vecteur) o;

        if (Double.compare(vecteur.abscisse, abscisse) != 0) return false;
        return Double.compare(vecteur.ordonnee, ordonnee) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(abscisse);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ordonnee);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Vecteur{" +
                "abscisse=" + abscisse +
                ", ordonnee=" + ordonnee +
                '}';
    }
}

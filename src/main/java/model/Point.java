package model;

public class Point {

    private double abscisse;

    private double ordonnee;

    public Point(double abscisse, double ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    public double getAbscisse() {
        return abscisse;
    }

    public double getOrdonnee() {
        return ordonnee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.abscisse, abscisse) != 0) return false;
        return Double.compare(point.ordonnee, ordonnee) == 0;
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
        return "Point{" +
                "abscisse=" + abscisse +
                ", ordonnee=" + ordonnee +
                '}';
    }
}

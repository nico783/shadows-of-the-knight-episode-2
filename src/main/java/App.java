import model.Cell;
import model.Vecteur;

import java.util.*;

import static java.lang.Math.sqrt;

public class App {

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
        Cell old;

        // game loop
        while (true) {
            String bombDir = in.next(); // Current distance to the bomb compared to previous distance (COLDER, WARMER, SAME or UNKNOWN)

            Distance state = Distance.valueOf(bombDir);
            candidates.restrict(origin, target, state);

            old = new Cell(origin.getAbscisse(), origin.getOrdonnee());
            origin = new Cell(target.getAbscisse(), target.getOrdonnee());

            if (state == Distance.COLDER) {
                target = candidates.getDistantCell(old);
            } else {
                target = candidates.getDistantCell(origin);
            }

            System.out.println(target.getAbscisse() + " " + target.getOrdonnee());
        }
    }


    public static class Candidates extends ForwardingSet<Cell> {

        public Candidates(Set<Cell> set) {
            super(set);
        }

        public Candidates(int h, int w) {
            this(new HashSet<>());
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    this.add(new Cell(i, j));
                }
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
        public void restrict(Cell origin, Cell target, Distance state) {
            Objects.requireNonNull(origin);
            Objects.requireNonNull(target);
            Objects.requireNonNull(state);

            Point middlePoint = new Point(((double) target.getAbscisse() + (double) origin.getAbscisse()) / 2,
                    ((double) target.getOrdonnee() + (double) origin.getOrdonnee()) / 2);

            Vecteur originDirection = new Vecteur(middlePoint, origin);
            Vecteur targetDirection = new Vecteur(middlePoint, target);

            if (state == Distance.COLDER) {
                this.removeIf(cell -> new Vecteur(middlePoint, cell).vectorialProduct(targetDirection) >= 0);
            } else if (state == Distance.WARMER) {
                this.removeIf(cell -> new Vecteur(middlePoint, cell).vectorialProduct(originDirection) >= 0);
            } else if (state == Distance.SAME) {
                this.removeIf(cell -> cell.distance(origin) != cell.distance(target));
            } else if (state == Distance.UNKNOWN) {
                // Pas de restriction possible
            } else {
                throw new IllegalStateException("Impossible state");
            }
        }


        public Cell random() {
            List<Cell> list = new ArrayList<>(this);
            return list.get(new Random().nextInt(list.size()));
        }


        public Cell getDistantCell(Cell current) {
            double distance = 0;
            return this.stream().reduce(current, (c1, c2) -> c1.distance(current) > c2.distance(current) ? c1 : c2);
        }

    }

    public static class Vecteur {

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

        public double vectorialProduct(Vecteur vecteur) {
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

    public static class Point {

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


    public static class Cell {

        private int abscisse;

        private int ordonnee;

        public Cell(int abscisse, int ordonnee) {
            this.abscisse = abscisse;
            this.ordonnee = ordonnee;
        }

        public int getAbscisse() {
            return abscisse;
        }

        public int getOrdonnee() {
            return ordonnee;
        }

        public double distance(Cell other) {
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

    public enum Distance {
        COLDER,

        WARMER,

        SAME,

        UNKNOWN;
    }

    public static class ForwardingSet<T> implements Set<T> {

        private Set<T> set;

        public ForwardingSet(Set<T> set) {
            this.set = set;
        }

        public int size() {
            return set.size();
        }

        public boolean isEmpty() {
            return set.isEmpty();
        }

        public boolean contains(Object o) {
            return set.contains(o);
        }

        public Iterator<T> iterator() {
            return set.iterator();
        }

        public Object[] toArray() {
            return set.toArray();
        }

        public <T> T[] toArray(T[] a) {
            return set.toArray(a);
        }

        public boolean add(T t) {
            return set.add(t);
        }

        public boolean remove(Object o) {
            return set.remove(o);
        }

        public boolean containsAll(Collection<?> c) {
            return set.containsAll(c);
        }

        public boolean addAll(Collection<? extends T> c) {
            return set.addAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return set.retainAll(c);
        }

        public boolean removeAll(Collection<?> c) {
            return set.removeAll(c);
        }

        public void clear() {
            set.clear();
        }

    }


}

/**
 * Interfejs metody zamiany głosów na mandaty.
 */
public interface AllocationMethod {
    /** Wypisuję nazwę metody */
    void printMethodName();
    /** Metoda zamienia głosy na mandaty */
    int[] allocate(int[] voices, int mandatesNumber);

}
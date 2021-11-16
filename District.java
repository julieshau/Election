/**
 * Interfejs okręgu wyborczego.
 */
public interface District {
    int getElectorsAmount();

    /** Metoda głosowania całego okręgu, zwraca tablicę głosów (element tablicy - liczba głosów za jedną partię)*/
    int[] vote(Party[] parties);

    /** Metoda wypisuje wyniki całego głosowania w tym okręgu (wyniki dla wyborców i kandydatów) */
    void printElectionInfo(Party[] parties, int[] mandates);

    int getMandates_number();

    /** Metoda zmienia wagi wyborców tego okręgu */
    void changeWeights(int[] change);

    /** Metoda oblicza sumę danych zmian dla kandydatów z danego okręgu (stosuję się w partii zachłannej) */
    int evaluateChangesSum(int[] change, Party party);
}

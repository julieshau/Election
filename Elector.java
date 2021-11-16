/**
 * Klasa abstrakcyjna wyborca.
 */
public abstract class Elector extends Person {
    public static final int NO_EXTRA_DISTRICT = 0;
    protected Candidate elected_candidate;

    public Elector(String name, String surname, int district) {
        super(name, surname, district);
    }

    /** Metoda głosowania. W przypadku okręgu scalonego parametr extraDistrict równa się numerowi innego niż wyborcy okręgu */
    public abstract void vote(Party[] parties, int extraDistrict);

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(surname).append(" ").append(elected_candidate.name).append(" ").append(elected_candidate.surname);
        return sb.toString();
    }

    /** Metoda zmienia wagi wyborcy */
    public abstract void changeWeights(int[] changes);

    /** Metoda oblicza sumę danych zmian dla kandydatów z danego okręgu (stosuję się w partii zachłannej) */
    public abstract int evaluateChangesSum(int[] change, Party party, int extraDistrict);
}

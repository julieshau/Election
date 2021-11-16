import java.util.Arrays;
/**
 * Klasa kandydat.
 */
public class Candidate extends Person {
    private String party;
    private int number;
    private int voices_number;
    private int[] features;

    public Candidate(String name, String surname, int district, String party, int number, int[] features) {
        super(name, surname, district);
        this.party = party;
        this.number = number;
        this.features = Arrays.copyOf(features, features.length);
        this.voices_number = 0;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(surname).append(" ").append(party).append(" ").append(number).append(" ").append(voices_number);
        return sb.toString();
    }

    /** Metoda zwiększa ilość głosów za kandydata*/
    public void increaseVoicesNumber(){
        voices_number++;
    }

    public String getParty() {
        return party;
    }

    public int[] getFeatures() {
        return features;
    }

    public int getVoices_number() {
        return voices_number;
    }
}

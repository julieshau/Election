import java.util.Arrays;
import java.util.Random;
/**
 * Klasa wyborca wszechstronny.
 */
public class AllFeaturesSelecting extends Elector {
    public static final int NO_PREFERRED_PARTY = -1;
    public static final int MAX_FEATURE_VALUE = 100;
    public static final int MIN_FEATURE_VALUE = -100;
    private int[] featureWeights;
    /** Numer partii do której ogranicza się wyborca (-1 w przypadku braku) */
    protected int preferredPartyNumber;

    /** Konstruktor. W przypadku wyborcy, ograniczającego się do jednej partii, ostatni parametr != null */
    public AllFeaturesSelecting(String name, String surname, int district, int[] featureWeights, String party) {
        super(name, surname, district);
        this.featureWeights = Arrays.copyOf(featureWeights, featureWeights.length);
        if (party == null) this.preferredPartyNumber = NO_PREFERRED_PARTY;
        else this.preferredPartyNumber = Party.info.get(party);
    }

    /** Metoda oblicza sumę ważoną cech dla danego kandydata */
    private int evaluateSum(Candidate candidate){
        int result = 0;
        for (int i = 0; i < featureWeights.length; i++) {
            result += featureWeights[i] * candidate.getFeatures()[i];
        }
        return result;
    }

    /** Metoda wybiera kandydata z danego okręgu na którego wyborca będzie głosować */
    private Candidate choose(Party[] parties, int district){
        Candidate temp = parties[0].getCandidate(district, 1);
        int max = evaluateSum(temp);
        for (Party party : parties){
            for (Candidate candidate : party.getCandidates(district)){
                int sum = evaluateSum(candidate);
                if (sum > max) {
                    max = sum;
                    temp = candidate;
                }
                else if (sum == max) { //losujemy jeżeli kandydaci mają ten sam wynik
                    Random r = new Random();
                    if (r.nextBoolean()){
                        temp = candidate;
                    }
                }
            }
        }
        return temp;
    }

    /** Prywatna metoda głosowania. Dokonuje wyboru kandydata z dwóch okręgów podstawowych scalonych w jeden*/
    protected void voteP(Party[] parties, int extraDistrict) {
        Candidate cand1 = choose(parties, district);
        if (extraDistrict == NO_EXTRA_DISTRICT){
            elected_candidate = cand1;
        }
        else{ //wybiera kandydata z okręgu wyborcy i okręgu scalonego z nim
            Candidate cand2 = choose(parties, extraDistrict);
            int sum1 = evaluateSum(cand1);
            int sum2 = evaluateSum(cand2);
            if (sum1 > sum2)
                elected_candidate = cand1;
            else if (sum2 > sum1)
                elected_candidate = cand2;
            else{
                Random r = new Random();
                if (r.nextBoolean()){
                    elected_candidate = cand1;
                }
                else {
                    elected_candidate = cand2;
                }
            }
        }
        elected_candidate.increaseVoicesNumber();
    }

    /** Metoda głosowania. W przypadku okręgu scalonego parametr extraDistrict równa się numerowi innego niż wyborcy okręgu */
    @Override
    public void vote(Party[] parties, int extraDistrict){
        if (preferredPartyNumber == NO_PREFERRED_PARTY) { //wyborca nie ogranicza się do jednej partii
            voteP(parties, extraDistrict);
        }
        else {
            voteP(new Party[]{parties[preferredPartyNumber]}, extraDistrict);
        }
    }

    /** Metoda zmienia wagi wyborcy */
    @Override
    public void changeWeights(int[] changes) {
        for (int i = 0; i < featureWeights.length; i++){
            featureWeights[i] += changes[i];
            if (featureWeights[i] > MAX_FEATURE_VALUE) featureWeights[i] = MAX_FEATURE_VALUE;
            if (featureWeights[i] < MIN_FEATURE_VALUE) featureWeights[i] = MIN_FEATURE_VALUE;
        }
    }

    /** Metoda oblicza sumę danych zmian dla kandydatów z danego okręgu (stosuję się w partii zachłannej) */
    @Override
    public int evaluateChangesSum(int[] changes, Party party, int extraDistrict) {
        int[] temp = Arrays.copyOf(featureWeights, featureWeights.length);
        for (int i = 0; i < temp.length; i++) {
            temp[i] += changes[i];
            if (temp[i] > MAX_FEATURE_VALUE) temp[i] = MAX_FEATURE_VALUE;
            if (temp[i] < MIN_FEATURE_VALUE) temp[i] = MIN_FEATURE_VALUE;
            temp[i] -= featureWeights[i];
        }
        int sum = 0;
        for (Candidate candidate : party.getCandidates(district)) {
            for (int i = 0; i < temp.length; i++) {
                sum += temp[i] * candidate.getFeatures()[i];
            }
        }
        if (extraDistrict != NO_EXTRA_DISTRICT) {
            for (Candidate candidate : party.getCandidates(extraDistrict)) {
                for (int i = 0; i < temp.length; i++) {
                    sum += temp[i] * candidate.getFeatures()[i];
                }
            }
        }
        return sum;
    }
}

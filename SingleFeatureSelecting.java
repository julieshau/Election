import java.util.Random;
/**
 * Klasa abstrakcyjna wyborca jednocechowy.
 */
public abstract class SingleFeatureSelecting extends Elector{
    public static final int NO_PREFERRED_PARTY = -1;
    protected int featureNumber;
    /** Numer partii do której ogranicza się wyborca (-1 w przypadku braku) */
    protected int preferredPartyNumber;

    /** Konstruktor. W przypadku wyborcy, ograniczającego się do jednej partii, ostatni parametr != null */
    public SingleFeatureSelecting(String name, String surname, int district, int featureNumber, String party) {
        super(name, surname, district);
        this.featureNumber = featureNumber - 1;
        if (party == null) this.preferredPartyNumber = NO_PREFERRED_PARTY;
        else this.preferredPartyNumber = Party.info.get(party);
    }

    /** istnieje maksymalizujący lub minimalizujący*/
    protected abstract boolean requiredComparison(int first, int second);

    /** Metoda wybiera kandydata z danego okręgu na którego wyborca będzie głosować */
    protected Candidate choose(Party[] parties, int district){
        int curj = 0;
        int curi = 0;
        for (int j = 0; j < parties.length; j++){
            for(int i = 0; i < parties[j].getCandidatesAmount(district);i++){
                //maksymalizuje lub minimalizuje
                if (requiredComparison(parties[j].getCandidate(district, i + 1).getFeatures()[featureNumber], parties[curj].getCandidate(district, curi + 1).getFeatures()[featureNumber] )){
                    curi = i;
                    curj = j;
                }
                //jeśli taką wartość ma więcej niż 1 kandydat, to wybór kandydata jest losowy
                else if (parties[j].getCandidate(district, i + 1).getFeatures()[featureNumber] == parties[curj].getCandidate(district, curi + 1).getFeatures()[featureNumber] ){
                    Random r = new Random();
                    if (r.nextBoolean()){
                        curi = i;
                        curj = j;
                    }
                }
            }
        }
        return parties[curj].getCandidate(district, curi + 1);
    }

    /** Prywatna metoda głosowania. Dokonuje wyboru kandydata z dwóch okręgów podstawowych scalonych w jeden*/
    private void voteP(Party[] parties, int extraDistrict) {
        Candidate temp1 = choose(parties, district);
        if (extraDistrict == NO_EXTRA_DISTRICT) {
            elected_candidate = temp1;
        }
        else{ //wybiera kandydata z okręgu wyborcy i okręgu scalonego z nim
            Candidate temp2 = choose(parties, extraDistrict);
            if (requiredComparison(temp1.getFeatures()[featureNumber], temp2.getFeatures()[featureNumber])) {
                elected_candidate = temp1;
            }
            else if (temp1.getFeatures()[featureNumber] == temp2.getFeatures()[featureNumber]){
                Random r = new Random();
                if (r.nextBoolean()) { //losowy wybór w pzypadku równości cech
                    elected_candidate = temp1;
                } else {
                    elected_candidate = temp2;
                }
            } else {
                elected_candidate = temp2;
            }
        }
        elected_candidate.increaseVoicesNumber();
    }

    /** Metoda głosowania. W przypadku okręgu scalonego parametr extraDistrict równa się numerowi innego niż wyborcy okręgu */
    @Override
    public void vote(Party[] parties, int extraDistrict){
        if (preferredPartyNumber == NO_PREFERRED_PARTY) {
            voteP(parties, extraDistrict);
        }
        else {
            voteP(new Party[]{parties[preferredPartyNumber]}, extraDistrict);
        }
    }

    @Override
    public void changeWeights(int[] changes) {}

    @Override
    public int evaluateChangesSum(int[] change, Party party, int extraDistrict) {
        return 0;
    }
}

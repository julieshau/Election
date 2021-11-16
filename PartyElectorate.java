import java.util.Random;
/**
 * Klasa żelazny elektorat partyjny.
 */
public class PartyElectorate extends Elector {
    private int partyNumber;

    public PartyElectorate(String name, String surname, int district, String party) {
        super(name, surname, district);
        this.partyNumber = Party.info.get(party);
    }

    /** Metoda głosowania */
    @Override
    public void vote(Party[] parties, int extraDistrict) {
        Random r = new Random();
        Candidate temp = parties[partyNumber].getCandidate(district, r.nextInt(parties[partyNumber].getCandidatesAmount(district)) + 1);
        if (extraDistrict == NO_EXTRA_DISTRICT){ // wyborca z okręgu podstawowego, nie ma dodatkowych okręgów
            elected_candidate = temp;
        }
        else {
            if (r.nextBoolean()) { //wyborca z okręgu scalonego, losuje z którego będzie wybierać
                elected_candidate = temp;
            } else {
                elected_candidate = parties[partyNumber].getCandidate(extraDistrict, r.nextInt(parties[partyNumber].getCandidatesAmount(extraDistrict)) + 1);
            }
        }
        elected_candidate.increaseVoicesNumber();
    }

    @Override
    public void changeWeights(int[] changes) {}

    @Override
    public int evaluateChangesSum(int[] change, Party party, int extraDistrict) {
        return 0;
    }
}

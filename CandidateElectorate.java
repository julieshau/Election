/**
 * Klasa żelazny elektorat kandydata.
 */
public class CandidateElectorate extends Elector{
    private int candidatePosition;
    private int partyNumber;

    public CandidateElectorate(String name, String surname, int district, int candidatePosition, String candidateParty) {
        super(name, surname, district);
        this.candidatePosition = candidatePosition;
        this.partyNumber = Party.info.get(candidateParty);
    }

    /** Metoda głosowania */
    @Override
    public void vote(Party[] parties, int extraDistrict) {
        elected_candidate = parties[partyNumber].getCandidate(district, candidatePosition);
        elected_candidate.increaseVoicesNumber();
    }

    @Override
    public void changeWeights(int[] changes) {}

    @Override
    public int evaluateChangesSum(int[] change, Party party, int extraDistrict) {
        return 0;
    }
}

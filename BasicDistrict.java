/**
 * Klasa okręg wyborczy podstawowy.
 */
public class BasicDistrict implements District {

    public static final int NO_EXTRA_DISTRICT = 0;
    private int number;
    private Elector[] electors;
    private int mandates_number;
    private int total_electors_amount;

    public BasicDistrict(int number, int electors_amount) {
        this.number = number;
        this.electors = new Elector[electors_amount];
        this.total_electors_amount = 0;
        this.mandates_number = electors_amount/10;
    }

    public void addElector(Elector elector) {
        electors[total_electors_amount] = elector;
        total_electors_amount++;
    }


    @Override
    public int getElectorsAmount(){
        return electors.length;
    }

    public Elector[] getElectors() {
        return electors;
    }

    public int getNumber() {
        return number;
    }

    /** Metoda głosowania całego okręgu, zwraca tablicę głosów (element tablicy - liczba głosów za jedną partię)*/
    @Override
    public int[] vote(Party[] parties) {
        for (Elector elector : electors){
            elector.vote(parties, NO_EXTRA_DISTRICT);
        }
        int[] voices = new int[parties.length];
        for (int i = 0; i < voices.length; i++){
            voices[i] = parties[i].getVoices(number);
        }
        return voices;
    }

    /** Metoda wypisuje wyniki całego głosowania w tym okręgu (wyniki dla wyborców i kandydatów) */
    @Override
    public void printElectionInfo(Party[] parties, int[] mandates) {
        printElectorInfo();
        printCandidatesInfo(parties, number);
        printDistrictInfo(mandates, parties);
    }

    /** Metoda wypisuje wynik głosowania wyborców tego okręgu */
    public void printElectorInfo() {
        for (Elector elector : electors){
            System.out.println(elector.toString());
        }
    }

    /** Metoda wypisuje wynik głosowania na kandydatów danego okręgu */
    public void printCandidatesInfo(Party[] parties, int DistrictNumber) {
        for (Party party : parties){
            party.printCandidatesInfo(DistrictNumber);
        }
    }

    /** Metoda wypisuje wynik głosowania w tym okręgu */
    public void printDistrictInfo(int[] mandates, Party[] parties) {
        for (int i = 0; i < mandates.length - 1; i++){
            StringBuilder sb = new StringBuilder();
            sb.append("(").append(parties[i].getName()).append(", ").append(mandates[i]).append(")").append(" ");
            System.out.print(sb.toString());
        }
        System.out.println("(" + parties[mandates.length - 1].getName() + ", " + mandates[mandates.length - 1] + ")");
    }

    /** Metoda zmienia wagi wyborców tego okręgu */
    @Override
    public void changeWeights(int[] change) {
        for (Elector elector : electors){
            elector.changeWeights(change);
        }
    }

    /** Metoda oblicza sumę danych zmian dla kandydatów z danego okręgu (stosuję się w partii zachłannej) */
    @Override
    public int evaluateChangesSum(int[] change, Party party) {
        int sum = 0;
        for (Elector elector : electors){
            sum += elector.evaluateChangesSum(change, party, NO_EXTRA_DISTRICT);
        }
        return sum;
    }

    @Override
    public int getMandates_number() {
        return mandates_number;
    }


}

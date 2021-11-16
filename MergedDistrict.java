/**
 * Klasa okręg wyborczy scalony.
 */
public class MergedDistrict implements District {
    private BasicDistrict first;
    private BasicDistrict second;
    private int mandates_number;
    /** Część głosów pierwszej partii*/
    private double[] firstVoices;

    public MergedDistrict(BasicDistrict first, BasicDistrict second){
        this.first = first;
        this.second = second;
        this.mandates_number = first.getMandates_number() + second.getMandates_number();
    }

    /** Podział mandatów pomiędzy podstawowymi okręgami*/
    private int[][] divideMandates(int[] mandates){
        int[] first = new int[mandates.length];
        int[] second = new int[mandates.length];
        for (int i = 0; i < mandates.length; i++){
            first[i] = (int)(firstVoices[i] * mandates[i]);
            second[i] = mandates[i] - first[i];
        }
        int[][] result = new int[2][];
        result[0] = first;
        result[1] = second;
        return result;
    }

    @Override
    public int getElectorsAmount() {
        return first.getElectorsAmount() + second.getElectorsAmount();
    }

    @Override
    public int getMandates_number() {
        return mandates_number;
    }

    /** Metoda zmienia wagi wyborców tego okręgu */
    @Override
    public void changeWeights(int[] change) {
        first.changeWeights(change);
        second.changeWeights(change);
    }

    /** Metoda oblicza sumę danych zmian dla kandydatów z danego okręgu (stosuję się w partii zachłannej) */
    @Override
    public int evaluateChangesSum(int[] change, Party party) {
        int sum = 0;
        for (Elector elector : first.getElectors()){
            sum += elector.evaluateChangesSum(change, party, second.getNumber());
        }
        for (Elector elector : second.getElectors()){
            sum += elector.evaluateChangesSum(change, party, first.getNumber());
        }
        return sum;
    }

    /** Wylicza proporcję głosów jednego okręgu podstawowego od całośći */
    private double countProportion(int number, int generalNumber){
        return ((double)number/generalNumber);
    }

    /** Metoda głosowania całego okręgu, zwraca tablicę głosów (element tablicy - liczba głosów za jedną partię)*/
    @Override
    public int[] vote(Party[] parties) {
        for (Elector elector : first.getElectors()){
            elector.vote(parties, second.getNumber()); //dodatkowy okręg dla  wyborców pierwszego okręgu jest drugi
        }
        for (Elector elector : second.getElectors()){
            elector.vote(parties, first.getNumber());
        }
        int[] voices = new int[parties.length];
        double[] firstVoicesProportion = new double[parties.length];
        for (int i = 0; i < voices.length; i++){
            int firstVoices = parties[i].getVoices(first.getNumber());
            int secondVoices = parties[i].getVoices(second.getNumber());
            voices[i] = firstVoices + secondVoices;
            firstVoicesProportion[i] = countProportion(firstVoices, voices[i]);
        }
        this.firstVoices = firstVoicesProportion;
        return voices;
    }

    /** Metoda wypisuje wyniki całego głosowania w tym okręgu (wyniki dla wyborców i kandydatów) */
    @Override
    public void printElectionInfo(Party[] parties, int[] mandates) {
        int[][] divided = divideMandates(mandates);
        first.printElectionInfo(parties, divided[0]);
        second.printElectionInfo(parties, divided[1]);
    }
}

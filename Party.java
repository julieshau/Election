import java.util.ArrayList;
import java.util.HashMap;
/**
 * Klasa abstakcyjna partia.
 */
public abstract class Party {
    public static int partyAmount = 0;
    /** Połączenie nazwy partii z numerem */
    public static HashMap<String, Integer> info = new HashMap<>();
    protected String name;
    protected int budget;
    /** Kandydaci partii we wszystkich okręgach (kandydaci z jednego okręgu w jednym ArrayList */
    protected ArrayList<ArrayList<Candidate>> candidates;
    protected int mandates;

    public Party(String name, int budget){
        this.name = name;
        this.budget = budget;
        this.candidates = new ArrayList<>(Main.districtAmount);
        for (int i = 0; i < Main.districtAmount; i++){
            this.candidates.add(new ArrayList<>());
        }
        this.mandates = 0;
        info.put(name, partyAmount);
        partyAmount++;
    }

    public Candidate getCandidate(int district, int position){
        return candidates.get(district - 1).get(position - 1);
    }

    /** Metoda zwraca wszystkich kandydatów danego okręgu */
    public ArrayList<Candidate> getCandidates (int district){
        return candidates.get(district - 1);
    }

    public int getCandidatesAmount(int district){
        return candidates.get(district - 1).size();
    }

    public void addCandidate(Candidate candidate, int district) { ///!!!!!!!!!!!change
        candidates.get(district - 1).add(candidate);
    }

    /** Metoda zwraca głosy, oddane na kandydatów partii z danego okręgu */
    public int getVoices(int districtNumber){
        int voices = 0;
        for (Candidate candidate : candidates.get(districtNumber - 1)){
            voices += candidate.getVoices_number();
        }
        return voices;
    }

    /** Metoda wypisuje wynik głosowania na kandydatów partii z danego okręgu */
    public void printCandidatesInfo(int districtNumber){
        for (Candidate candidate : candidates.get(districtNumber - 1)){
            System.out.println(candidate.toString());
        }
    }

    public void addMandates(int mandates){
        this.mandates += mandates;
    }

    public void printElectionInfo(){
        System.out.print("(" + name + ", " + mandates + ")");
    }

    public String getName() {
        return name;
    }

    /** Metoda przeprowadzi kampanię wyborczą */
    public abstract void campaign(District[] districts, Activity[] activities);
}

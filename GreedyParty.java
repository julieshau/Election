import java.util.Random;
/**
 * Klasa partia zachłanna.
 */
public class GreedyParty extends Party {

    public GreedyParty(String name, int budget) {
        super(name, budget);
    }

    /** Metoda przeprowadzi kampanię wyborczą */
    @Override
    public void campaign(District[] districts, Activity[] activities) {
        District chosenDistrict;
        Activity chosenActivity;
        do {
            chosenActivity = null;
            chosenDistrict = null;
            int sumChange;
            int curr = 0;
            int cost = 0;
            for (Activity activity : activities) {
                for (District district : districts) {
                    sumChange = district.evaluateChangesSum(activity.getWeights_change(), this); //oblicza sumę sum ważonych cech swoich kandydatów w okręgu wyborczym
                    cost = activity.evaluateCost(district); //oblicza cenę działania
                    if (sumChange > curr && cost <= budget) { //wybiera działanie które w największym stopniu zwiększy sumę
                        curr = sumChange;
                        chosenActivity = activity;
                        chosenDistrict = district;
                    } else if (sumChange == curr && sumChange <= budget) { //jeżeli zmiana sumy jest taka sama, to losuje
                        Random r = new Random();
                        if (r.nextBoolean()) {
                            chosenActivity = activity;
                            chosenDistrict = district;
                        }
                    }
                }
            }
            if (chosenActivity != null) { //jeżeli udało się dokonać wyboru
                budget -= cost;
                chosenActivity.influenceElectors(chosenDistrict);
            }
        } while (budget > 0 && chosenActivity != null); //wychodzimy z pętli jezeli nie mamy pieniędzy lub już nie możemy nic wybrać
    }
}

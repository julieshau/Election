import java.util.Random;
/**
 * Klasa abstrakcyjna wspólna dla partii skromnej i działającej z rozmachem.
 */
public abstract class SimpleThinkingParty extends Party {

    public SimpleThinkingParty(String name, int budget) {
        super(name, budget);
    }

    /** wybiera najdroższe lub najtańsze działanie */
    protected abstract boolean requiredComparison(int cost, int curr);

    /** najmniejsza lub największa możliwa wartość budżetu na początku kampanii */
    protected abstract int getStartedValue();

    /** Metoda przeprowadzi kampanię wyborczą */
    @Override
    public void campaign(District[] districts, Activity[] activities) {
        District chosenDistrict;
        Activity chosenActivity;
        do {
            chosenActivity = null;
            chosenDistrict = null;
            int cost = 0;
            int curr = getStartedValue();
            for (Activity activity : activities) {
                for (District district : districts) {
                    cost = activity.evaluateCost(district);
                    if (requiredComparison(cost, curr) && cost <= budget) { //wybiera najdroższe lub najtańsze działanie
                        curr = cost;
                        chosenActivity = activity;
                        chosenDistrict = district;
                    } else if (cost == curr && cost <= budget) { //losuje jeżeli cena jest taka sama
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

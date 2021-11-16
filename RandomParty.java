import java.util.Random;
/**
 * Klasa partia losowa.
 */
public class RandomParty extends Party {
    public static int RANDOM_BOUND = 20;
    public RandomParty(String name, int budget) {
        super(name, budget);
    }

    /** Metoda przeprowadzi kampanię wyborczą */
    @Override
    public void campaign(District[] districts, Activity[] activities) {
        Random r = new Random();
        int i = 0;
        int num = r.nextInt(RANDOM_BOUND);
        while (i < num && budget > 0) {
            int districtNr = r.nextInt(districts.length);
            int activityNr = r.nextInt(activities.length);
            int cost = activities[activityNr].evaluateCost(districts[districtNr]);
            if (cost <= budget) {
                budget -= cost;
                activities[activityNr].influenceElectors(districts[districtNr]);
            }
            i++;
        }
        if (budget > 0) {
            District chosenDistrict;
            Activity chosenActivity;
            do {
                chosenActivity = null;
                for (Activity activity : activities) {
                    for (District district : districts) {
                        int cost = activity.evaluateCost(district); //po prostu po kolei sprawdza, czy możemy coś jeszcze wykonać
                        if (cost <= budget) {
                            budget -= cost;
                            chosenDistrict = district;
                            chosenActivity = activity;
                            chosenActivity.influenceElectors(chosenDistrict);
                        }
                    }
                }
            } while (budget > 0 && chosenActivity != null); //wychodzimy z pętli jezeli nie mamy pieniędzy lub już nie możemy nic wybrać
        }
    }
}

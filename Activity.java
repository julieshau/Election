import java.util.Arrays;
/**
 * Klasa działanie.
 */
public class Activity {

    /** Opis działania (zmiana wag cech)*/
    private int[] weights_change;

    public Activity(int[] change){
        this.weights_change = Arrays.copyOf(change, change.length);
    }

    /** Metoda oblicza cenę działania dla danego okręgu wyborczego */
    public int evaluateCost(District district){
        int cost = 0;
        for (int value : weights_change) cost += Math.abs(value);
        cost *= district.getElectorsAmount();
        return  cost;
    }

    /** Metoda oddziałuje na wyborców danego okręgu (zmienia ich wagi zgodnie z opisem działania) */
    public void influenceElectors(District district){
        district.changeWeights(weights_change);
    }

    public int[] getWeights_change() {
        return weights_change;
    }
}

/**
 * Klasa metoda D'Hondta.
 */
public class DHondtMethod implements AllocationMethod{

    @Override
    public void printMethodName() {
        System.out.println("Metoda D’Hondta");
    }

    /** Metoda zamienia głosy na mandaty */
    @Override
    public int[] allocate(int[] voices, int mandatesNumber) {
        int[] result = new int[voices.length];
        for (int j = 0; j < mandatesNumber; j++){
            int max = 0;
            for (int i = 0; i < result.length; i++){
                if (voices[i]/(result[i] + 1) > voices[max]/(result[max] + 1)) {
                    max = i;
                }
            }
            result[max]++;
        }
        return result;
    }
}

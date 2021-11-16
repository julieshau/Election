/**
 * Klasa metoda Hare’a-Niemeyera.
 */
public class HareNiemeyerMethod implements AllocationMethod {
    @Override
    public void printMethodName() {
        System.out.println("Metoda Hare’a-Niemeyera");
    }

    /** Metoda zamienia głosy na mandaty */
    @Override
    public int[] allocate(int[] voices, int mandatesNumber) {
        int[] result = new int[voices.length];
        int allVoices = 0;
        for (int voice : voices){
            allVoices += voice;
        }
        double[] temp = new double[voices.length];
        for (int i = 0; i < voices.length; i++){
            temp[i] =(double) voices[i] * mandatesNumber / allVoices;
        }
        for (int i = 0; i < voices.length; i++){
            result[i] = (int)temp[i];
            temp[i] -= result[i];
            mandatesNumber -= result[i];
        }
        for (int i = 0; i < mandatesNumber; i++) {
            int max = 0;
            for (int j = 0; j < result.length; j++) {
                if (temp[j] > temp[max]) {
                    max = j;
                }
            }
            temp[max] = 0;
            result[max]++;
        }
        return result;
    }
}

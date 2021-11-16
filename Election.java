import java.util.Random;
/**
 * Klasa wybory.
 */
public class Election {
    /** Ilość dopuszczalnych metod zamiany głosów na mandaty */
    public static final int METHOD_AMOUNT = 3;

    /** Metoda przeprowadzi głosowanie oraz przeliczanie głosów*/
    public static void vote(District[] districts, AllocationMethod method, Party[] parties){
        method.printMethodName();
        for (District district : districts){
            int[] voices =  district.vote(parties);
            int[] mandates = method.allocate(voices, district.getMandates_number());
            district.printElectionInfo(parties, mandates);
            for (int i = 0; i < mandates.length; i++){
                parties[i].addMandates(mandates[i]);
            }
        }
        for (Party party : parties){
            party.printElectionInfo();
            System.out.print(" ");
        }
        System.out.println();
    }

    /** Metoda przeprowadzi kampanię wyborczą */
    public static void campaign(District[] districts, Party[] parties, Activity[] activities){
        for (Party party : parties) {
            party.campaign(districts, activities);
        }
    }

    /** Metoda losuję metodę przeliczania głosów */
    public static AllocationMethod chooseMethod(){
        AllocationMethod method = null;
        Random r = new Random();
        switch (r.nextInt(METHOD_AMOUNT)){
            case 0 : method = new DHondtMethod(); break;
            case 1 : method = new SainteLagueMethod(); break;
            case 2 : method = new HareNiemeyerMethod(); break;
        }
        return method;
    }

    /** Metoda przeprowadzi formowanie okręgów wyborczych */
    public static District[] formDistricts(BasicDistrict[] basicDistricts, int[] merge){
        District[] districts = new District[basicDistricts.length - merge.length];
        int i = 0; int j = 0; int d = 0;
        while (i < districts.length) {
            if (j < merge.length && d + 1 == merge[j]){
                districts[i] = new MergedDistrict(basicDistricts[d], basicDistricts[d + 1]);
                d+=2;
                j++;
            }
            else {
                districts[i] = basicDistricts[d];
                d++;
            }
            i++;
        }
        return districts;
    }

}

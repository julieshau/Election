import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int districtAmount;
    public static int partyAmount;
    public static int activityAmount;
    public static int featuresAmount;

    public static void main(String[] args) throws IOException {
        ///////////////////////////// czytanie danych /////////////////////////////////////////////
        String path = args[0];
        FileReader fr = new FileReader(path);
        Scanner s = new Scanner(fr);

        districtAmount = s.nextInt();
        BasicDistrict[] basicDistricts = new BasicDistrict[districtAmount];
        partyAmount = s.nextInt();
        Party[] parties = new Party[partyAmount];
        activityAmount = s.nextInt();
        Activity[] activities = new Activity[activityAmount];
        featuresAmount = s.nextInt();

        int[] merge = new int[s.nextInt()];
        for (int i = 0; i < merge.length; i++){
            String m = s.next();
            merge[i] = Character.getNumericValue(m.charAt(1));
        }
        String line = s.nextLine();
        line = s.nextLine();
        String[] name = line.split(" ");
        int[] budget = new int[partyAmount];
        for (int i = 0; i < partyAmount; i++){
            budget[i] = s.nextInt();
        }
        line = s.nextLine();
        line = s.nextLine();
        String[] strategy = line.split(" ");
        for (int i = 0; i < partyAmount; i++){
            Party p = null;
            switch (strategy[i]){
                case "R" : p = new WastefulParty(name[i], budget[i]); break;
                case "S" : p = new ModestParty(name[i], budget[i]); break;
                case "W" : p = new RandomParty(name[i], budget[i]); break;
                case "Z" : p = new GreedyParty(name[i], budget[i]); break;
            }
            parties[i] = p;
        }

        for (int i = 0; i < districtAmount; i++){
            basicDistricts[i] = new BasicDistrict(i + 1, s.nextInt());
        }
        line = s.nextLine();
        for (int i = 0; i < districtAmount; i++){
            int number = basicDistricts[i].getMandates_number();
            for (int j = 0; j < partyAmount; j++) {
                for (int k = 0; k < number; k++){
                    line = s.nextLine();
                    String[] cand = line.split(" ");
                    int[] features = new int[featuresAmount];
                    for (int f = 0; f < featuresAmount; f++)
                        features[f] = Integer.parseInt(cand[f + 5]);
                    assert parties[j] != null;
                    parties[j].addCandidate(new Candidate(cand[0], cand[1], Integer.parseInt(cand[2]), cand[3], Integer.parseInt(cand[4]), features), Integer.parseInt(cand[2]));
                }
            }
        }

        for (int i = 0; i < districtAmount; i++){
            int number = basicDistricts[i].getElectorsAmount();
            for (int j = 0; j < number; j++){
                line = s.nextLine();
                String[] elect = line.split(" ");
                Elector e = null;
                switch (elect[3]){
                    case "1" : e = new PartyElectorate(elect[0], elect[1], Integer.parseInt(elect[2]), elect[4]); break;
                    case "2" : e = new CandidateElectorate(elect[0], elect[1], Integer.parseInt(elect[2]), Integer.parseInt(elect[5]), elect[4]); break;
                    case "3" : e = new SingleFeatureMinimizing(elect[0], elect[1], Integer.parseInt(elect[2]), Integer.parseInt(elect[4]), null); break;
                    case "4" : e = new SingleFeatureMaximizing(elect[0], elect[1], Integer.parseInt(elect[2]), Integer.parseInt(elect[4]), null); break;
                    case "5" :{
                        int[] weights = new int[featuresAmount];
                        for (int w = 0; w < featuresAmount; w++){
                           weights[w] = Integer.parseInt(elect[w + 4]);
                        }
                        e = new AllFeaturesSelecting(elect[0], elect[1], Integer.parseInt(elect[2]), weights, null);
                        break;
                    }
                    case "6" : e = new SingleFeatureMinimizing(elect[0], elect[1], Integer.parseInt(elect[2]), Integer.parseInt(elect[4]), elect[5]); break;
                    case "7" : e = new SingleFeatureMaximizing(elect[0], elect[1], Integer.parseInt(elect[2]), Integer.parseInt(elect[4]), elect[5]); break;
                    case "8" : {
                        int[] weights = new int[featuresAmount];
                        for (int w = 0; w < featuresAmount; w++){
                            weights[w] = Integer.parseInt(elect[w + 4]);
                        }
                        e = new AllFeaturesSelecting(elect[0], elect[1], Integer.parseInt(elect[2]), weights, elect[featuresAmount + 4]);
                        break;
                    }
                }
                basicDistricts[i].addElector(e);
            }
        }

        for (int i = 0; i < activityAmount; i++){
            int[] act = new int[featuresAmount];
            for (int j = 0; j < featuresAmount; j++){
                act[j] = s.nextInt();
            }
            activities[i] = new Activity(act);
        }
        s.close();
        fr.close();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        District[] districts = Election.formDistricts(basicDistricts, merge);
        AllocationMethod method = Election.chooseMethod();
        Election.campaign(districts, parties, activities);
        Election.vote(districts, method , parties);
    }
}

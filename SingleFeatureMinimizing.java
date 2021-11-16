/**
 * Klasa wyborca minimalizujący jednocechowy.
 */
public class SingleFeatureMinimizing extends SingleFeatureSelecting {

    public SingleFeatureMinimizing(String name, String surname, int district, int featureNumber, String party) {
        super(name, surname, district, featureNumber, party);
    }

    /** wybiera tego spośród kandydatów wszystkich partii, który ma najniższy poziom cechy*/
    @Override
    protected boolean requiredComparison(int first, int second) {
        return first < second;
    }
}

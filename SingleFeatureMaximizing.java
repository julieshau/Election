/**
 * Klasa wyborca maksymalizujący jednocechowy.
 */
public class SingleFeatureMaximizing extends SingleFeatureSelecting {

    public SingleFeatureMaximizing(String name, String surname, int district, int featureNumber, String party) {
        super(name, surname, district, featureNumber, party);
    }

    /** wybiera tego spośród kandydatów wszystkich partii, który ma najwyższy poziom cechy*/
    @Override
    protected boolean requiredComparison(int first, int second) {
        return first > second;
    }
}

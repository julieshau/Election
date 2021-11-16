/**
 * Klasa partia działająca z rozmachem.
 */
public class WastefulParty extends SimpleThinkingParty {

    public WastefulParty(String name, int budget) {
        super(name, budget);
    }

    /** wybiera najdroższe działanie */
    @Override
    protected boolean requiredComparison(int cost, int curr) {
        return cost > curr;
    }

    /** najmniejsza możliwa wartość budżetu na początku kampanii */
    @Override
    protected int getStartedValue() {
        return 0;
    }
}

/**
 * Klasa partia skromna.
 */
class ModestParty extends SimpleThinkingParty {

    public ModestParty(String name, int budget) {
        super(name, budget);
    }

    /** wybiera najtańsze działanie */
    @Override
    protected boolean requiredComparison(int cost, int curr) {
        return cost < curr;
    }

    /** największa możliwa wartość budżetu na początku kampanii */
    @Override
    protected int getStartedValue() {
        return budget;
    }

}

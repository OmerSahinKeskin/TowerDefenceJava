package observerfiles;

/**
 * A simplistic model that can be observed and updated.
 *
 * @author Pelle Evensen, evensen@chalmers.se
 */
public class ExampleModel implements Observable<ExampleModel, ObserverMessage> {
	private final ObservableSupport<ExampleModel, ObserverMessage> obs;
	private long ctr;
	private final String name;

	public ExampleModel(final String name, final long initCtr) {
		this.name = name;
		this.obs = new ObservableSupport<>(this);
		this.ctr = initCtr;
	}

	@Override
	public boolean addObserver(final Observer<ExampleModel, ObserverMessage> observer) {
		return this.obs.addObserver(observer);
	}

	@Override
	public boolean removeObserver(final Observer<ExampleModel, ObserverMessage> observer) {
		return this.obs.removeObserver(observer);
	}

	private void updateInternalState() {
		this.ctr = this.ctr * 6364136223846793005L + 1442695040888963407L;
	}

	public void updateState() {
		updateInternalState();
		final long output = (this.ctr ^ this.ctr >>> 32) >>> 1;
		this.obs.update(new ConcreteMessage1(output % 1000));
	}

	@Override
	public String toString() {
		return this.getClass() + ", " + this.name;
	}
}

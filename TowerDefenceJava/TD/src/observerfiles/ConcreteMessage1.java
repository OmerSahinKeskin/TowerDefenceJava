package observerfiles;

/**
 * A simple message class. Passing messages as {@code String}s is quite fragile
 * and should be avoided.
 *
 * @author Pelle Evensen, evensen@chalmers.se
 */
public class ConcreteMessage1 implements ObserverMessage {
	private final long v;

	public ConcreteMessage1(final long v) {
		this.v = v;
	}

	@Override
	public String toString() {
		return "ConcreteMessage1 [v=" + this.v + "]";
	}

}

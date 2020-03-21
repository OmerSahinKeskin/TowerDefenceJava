package observerfiles;

import java.util.HashSet;
import java.util.Set;

/**
 * A support class intending to be a type-safe alternative to
 * {@code java.beans.PropertyChangeSupport}.
 *
 * @author Pelle Evensen, evensen@chalmers.se
 *
 * @param <M> The type of the observable to be observed.
 * @param <T> The message type for updates to the observers.
 */
public class ObservableSupport<M, T> implements Observable<M, T> {
	private final Set<Observer<M, T>> observers;
	private final M model;

	/**
	 * @param model The underlying, observable, model.
	 */
	public ObservableSupport(final M model) {
		this.model = model;
		this.observers = new HashSet<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addObserver(final Observer<M, T> observer) {
		final boolean wasNotObserving = !this.observers.contains(observer);
		if (wasNotObserving) {
			this.observers.add(observer);
		}
		return wasNotObserving;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeObserver(final Observer<M, T> observer) {
		final boolean wasObserving = this.observers.contains(observer);
		if (wasObserving) {
			this.observers.remove(observer);
		}
		return wasObserving;
	}

	/**
	 * Sends the message to all observers.
	 *
	 * @param message The message to be sent.
	 */
	public void update(final T message) {
		for (final Observer<M, T> o : this.observers) {
			o.update(this.model, message);
		}
	}
}

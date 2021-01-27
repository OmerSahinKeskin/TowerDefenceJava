package observerfiles;

/**
 * @author Pelle Evensen, evensen@chalmers.se
 *
 * @param <M> The type of the observable to be observed.
 * @param <T> The message type for updates to observers.
 */
public interface Observer<M, T> {
	/**
	 * The method that will be called when an observable makes a relevant state
	 * change.
	 *
	 * @param observable The observable that generated the message.
	 * @param message    The message.
	 */
	void update(final M observable, final T message);
}

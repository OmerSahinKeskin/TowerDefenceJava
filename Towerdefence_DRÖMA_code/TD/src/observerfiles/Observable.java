package observerfiles;

/**
 * @author Pelle Evensen, evensen@chalmers.se
 *
 * @param <M> The type of the observable to be observed.
 * @param <T> The message type for updates to observers.
 */
public interface Observable<M, T> {
	/**
	 * Adds an observer to this observable.
	 *
	 * The observers are a {@code Set} -- i.e. adding two (or more) equal observers
	 * will only register the first one.
	 *
	 * @param observer An observer that observes observables of type {@code M} with
	 *                 message type {@code T}.
	 * @return {@code true} if the observer was not already in the {@code Set} of
	 *         observers.
	 * @return {@code false} otherwise -- the operation had no effect.
	 */
	boolean addObserver(Observer<M, T> observer);

	/**
	 * Removes an observer from this observable.
	 *
	 * Removal of an observer not in the {@code Set} of observers has no effect
	 * except on the value returned.
	 *
	 * @param observer An observer that observes observables of type {@code M} with
	 *                 message type {@code T}.
	 * @return {@code true} if the observer was already in the {@code Set} of
	 *         observers.
	 * @return {@code false} otherwise -- the operation had no effect.
	 */
	boolean removeObserver(Observer<M, T> observer);
}

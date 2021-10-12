/**
 * Subclasses exist roughly per type of board object (in some cases, different types of board objects may share an
 * output state type, if they behave in a very similar way). If the state for a board object does not exist, or is
 * using the wrong type, then that object should render as inactive.
 */
export abstract class BoardObjectOutputState {
}

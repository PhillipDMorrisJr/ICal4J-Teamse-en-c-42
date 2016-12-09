package net.fortuna.ical4j.model;

public class NotFoundException extends Exception {

	/**
	 * Creates a NotFoundException containing no particular message.
	 */
	public NotFoundException() {
		super();
	}

	/**
	 * Creates a NotFoundException based on inability to find the given name in a search.
	 *
	 * @param searchName the name of the item searched for
	 */
	public NotFoundException(final String searchName) {
		super("Could not find item with name: " + searchName);
	}

}

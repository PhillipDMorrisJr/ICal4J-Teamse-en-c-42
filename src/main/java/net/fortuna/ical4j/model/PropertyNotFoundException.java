package net.fortuna.ical4j.model;

public class PropertyNotFoundException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for PropertyNotFoundException
	 */
	public PropertyNotFoundException() {
		super();
	}

	/**
	 * Initialization constructor for PropertyNotFoundException that accepts a message
	 * @param message reason for exception
	 */
	public PropertyNotFoundException(String message) {
		super(message);
	}

	/**
	 * Initialization constructor for PropertyNotFoundException that accepts a message and cause
	 * @param message reason for exception
	 * @param cause 
	 */
	public PropertyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PropertyNotFoundException(Throwable cause){
		super(cause);
	}
}

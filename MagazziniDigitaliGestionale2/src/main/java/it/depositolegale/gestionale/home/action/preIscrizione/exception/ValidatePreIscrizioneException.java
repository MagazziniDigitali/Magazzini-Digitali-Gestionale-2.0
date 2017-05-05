/**
 * 
 */
package it.depositolegale.gestionale.home.action.preIscrizione.exception;

/**
 * @author massi
 *
 */
public class ValidatePreIscrizioneException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3451323278521310281L;

	/**
	 * @param message
	 */
	public ValidatePreIscrizioneException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ValidatePreIscrizioneException(String message, Throwable cause) {
		super(message, cause);
	}

}

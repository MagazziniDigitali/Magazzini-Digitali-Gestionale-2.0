/**
 * 
 */
package it.depositolegale.gestionale.verifica.preRegistrazione.exception;

/**
 * @author massi
 *
 */
public class ReadGoogleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5661078133907639775L;

	/**
	 * @param message
	 */
	public ReadGoogleException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ReadGoogleException(String message, Throwable cause) {
		super(message, cause);
	}

}

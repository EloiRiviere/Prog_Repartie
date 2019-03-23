package tp5_serveur;

public class IllegalMagicException extends Exception {
	private static final long serialVersionUID = 2659355398418681314L;

	public IllegalMagicException() {
	}

	public IllegalMagicException(String message) {
		super(message);
	}

	public IllegalMagicException(Throwable cause) {
		super(cause);
	}

	public IllegalMagicException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalMagicException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

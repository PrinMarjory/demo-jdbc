package fr.diginamic.jdbc.exception;

public class ExceptionTechnique extends RuntimeException {

	public ExceptionTechnique() {
	}

	public ExceptionTechnique(String message) {
		super(message);
	}

	public ExceptionTechnique(Throwable cause) {
		super(cause);
	}

	public ExceptionTechnique(String message, Throwable cause) {
		super(message, cause);
	}

}

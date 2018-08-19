package edu.gandhi.prajit.moviecruiser.exception;

public class MovieAlredayExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public MovieAlredayExistsException(final String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "MovieAlredayExistsException [message=" + getMessage() + "]";
	}
}

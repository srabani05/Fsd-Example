package edu.gandhi.prajit.moviecruiser.exception;

public class MovieNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public MovieNotFoundException(final String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "MovieNotFoundException [message=" + getMessage() + "]";
	}
}

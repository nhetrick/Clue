package main;


public class BadConfigFormatException extends Exception {
	private String message;

	public BadConfigFormatException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "BadConfigFormatException [message=" + message + "]";
	}
}

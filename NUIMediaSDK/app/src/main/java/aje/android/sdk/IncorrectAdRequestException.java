package aje.android.sdk;

public class IncorrectAdRequestException extends Exception {
	private static final long serialVersionUID = 1L;

	public IncorrectAdRequestException() {
    }

    public IncorrectAdRequestException(Exception e) {
        super(e);
    }

    public IncorrectAdRequestException(String message) {
        super(message);
    }
}

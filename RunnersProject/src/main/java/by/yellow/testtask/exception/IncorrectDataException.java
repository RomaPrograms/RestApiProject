package by.yellow.testtask.exception;


public class IncorrectDataException extends Exception {
    public IncorrectDataException() {
    }

    public IncorrectDataException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IncorrectDataException(final String parameter, final String value) {
        super("Parameter '" + parameter + "' got empty or incorrect value '"
                + value + "'");
    }

    public IncorrectDataException(final String message) {
        super(message);
    }

    public IncorrectDataException(final Throwable cause) {
        super(cause);
    }
}

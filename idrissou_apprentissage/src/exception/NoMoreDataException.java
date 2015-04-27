package exception;

@SuppressWarnings("serial")
public class NoMoreDataException extends Exception {

    public NoMoreDataException() {
        super();
    }

    public NoMoreDataException(String s) {
        super(s);
    }
}

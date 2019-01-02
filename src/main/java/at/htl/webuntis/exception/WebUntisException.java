package at.htl.webuntis.exception;

public class WebUntisException extends Exception{
    private int code;
    private String message;

    public WebUntisException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}

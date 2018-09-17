package exceptions;

public class NameException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameException() {
        // TODO Auto-generated constructor stub
    }

    public NameException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public NameException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public NameException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}

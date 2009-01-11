package modelo.excecoes;

/**
 *
 * @author pinheiro Pinheiro
 */
public class DAOException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>DAOException</code> without detail message.
     */
    public DAOException() {
    }
    
    
    /**
     * Constructs an instance of <code>DAOException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DAOException(String msg) {
        super(msg);
    }

        /**
     * Constructs an instance of <code>DAOException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DAOException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}

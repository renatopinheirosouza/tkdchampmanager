package modelo.excecoes;

/**
 *
 * @author Renato Pinheiro
 */
public class LutaDadoIncorretoException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>LutaDadoIncorretoException</code> without detail message.
     */
    public LutaDadoIncorretoException() {
    }
    
    
    /**
     * Constructs an instance of <code>LutaDadoIncorretoException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public LutaDadoIncorretoException(String msg) {
        super(msg);
    }
}

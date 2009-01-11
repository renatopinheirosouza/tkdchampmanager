package modelo.excecoes;

/**
 *
 */
public class LutadorDadoIncorretoException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>LutadorDadoIncorreto</code> without 
     * detail message.
     */
    public LutadorDadoIncorretoException() {
    }
    
    
    /**
     * Constructs an instance of <code>LutadorDadoIncorreto</code> with the 
     * specified detail message.
     * @param msg the detail message.
     */
    public LutadorDadoIncorretoException(String msg) {
        super(msg);
    }
}

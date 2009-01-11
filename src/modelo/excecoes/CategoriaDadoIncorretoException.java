package modelo.excecoes;

/**
 *
 * @author Renato Pinheiro
 */
public class CategoriaDadoIncorretoException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>CategoriaDadosIncorretos</code> without detail message.
     */
    public CategoriaDadoIncorretoException() {
    }
    
    
    /**
     * Constructs an instance of <code>CategoriaDadosIncorretos</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CategoriaDadoIncorretoException(String msg) {
        super(msg);
    }
}

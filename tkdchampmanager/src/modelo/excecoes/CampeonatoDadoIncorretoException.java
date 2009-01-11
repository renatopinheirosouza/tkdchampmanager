package modelo.excecoes;

/**
 *
 * @author Renato Pinheiro
 */
public class CampeonatoDadoIncorretoException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>CampeonatoCategoriaExistente</code> without detail message.
     */
    public CampeonatoDadoIncorretoException() {
    }
    
    
    /**
     * Constructs an instance of <code>CampeonatoCategoriaExistente</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CampeonatoDadoIncorretoException(String msg) {
        super(msg);
    }
}

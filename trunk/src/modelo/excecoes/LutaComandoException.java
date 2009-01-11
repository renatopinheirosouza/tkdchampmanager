package modelo.excecoes;

/**
 *
 * @author Renato Pinheiro
 */
public class LutaComandoException extends Exception {

    /**
     * Creates a new instance of <code>LutaComandoException</code> 
     * without detail message.
     */
    public LutaComandoException() {
    }


    /**
     * Constructs an instance of <code>LutaComandoException</code> with 
     * the specified detail message.
     * @param msg the detail message.
     */
    public LutaComandoException(String msg) {
        super(msg);
    }
}

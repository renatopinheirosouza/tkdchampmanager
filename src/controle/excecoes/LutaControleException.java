package controle.excecoes;

/**
 *
 */
public class LutaControleException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>ControleLutaException</code> without 
     * detail message.
     */
    public LutaControleException() {
    }
    
    
    /**
     * Constructs an instance of <code>ControleLutaException</code> with the 
     * specified detail message.
     * @param msg the detail message.
     */
    public LutaControleException(String msg) {
        super(msg);
    }
}

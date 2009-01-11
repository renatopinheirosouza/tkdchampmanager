/*
 * ParametrosDadoIncorretoExcpetion.java
 *
 * Created on April 15, 2008, 4:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package modelo.excecoes;

/**
 *
 * @author pinheiro
 */
public class ParametrosDadoIncorretoExcpetion extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>ParametrosDadoIncorretoExcpetion</code> without detail message.
     */
    public ParametrosDadoIncorretoExcpetion() {
    }
    
    
    /**
     * Constructs an instance of <code>ParametrosDadoIncorretoExcpetion</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ParametrosDadoIncorretoExcpetion(String msg) {
        super(msg);
    }
}

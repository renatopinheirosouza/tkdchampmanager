package controle;

import java.util.concurrent.ArrayBlockingQueue;
import modelo.Ponto;

/**
 *  Classe buffer para os pontos
 * 
 *  Pontos são gerado pelo produtor são armazenados aqui para serem consumidos
 *  posteriormente pelo consumidos de pontos.
 */
public class PontoBuffer {
    
    /**
     * colecao buffer para os pontos. 
     * 
     * O ArrayBlockingQueue controla os locks necesSórios a uma colecao 
     * compartilhada por várias threads
     */
    private ArrayBlockingQueue<Ponto> pontosBuffer;
    
    /**
     *  Construtor de PontoBuffer
     */
    public PontoBuffer() {
        
        pontosBuffer = new ArrayBlockingQueue<Ponto>( 30 );
    
    }
    
    /**
     *  Adiciona um novo ponto na colecao
     *
     *  @param  ponto   novo ponto     
     */
    public void add(Ponto ponto){

        try {
            pontosBuffer.put(ponto);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
    }
    
    /**
     *  Retora (e retira) um ponto da colecao
     *
     * @return Um ponto armazenado no buffer
     */    
    public Ponto get(){
        
        Ponto ponto = null;

        try {
            ponto = pontosBuffer.take();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
                
        return ponto;
        
    }
    
    /**
     * Limpa o buffer
     */
    public void clear(){
        pontosBuffer.clear();
    }
}

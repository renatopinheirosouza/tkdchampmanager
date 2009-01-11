package controle;

import java.util.Observable;
import modelo.Parametros;

/**
 * Classe relogio 
 */
public class Relogio extends Observable implements Runnable {
    
    /** tempoEmMils tempo em milissegundo */
    private volatile long tempoEmMils;
    
    /** Para o controle de pausa do relogio */
    private volatile boolean pausar = false;
   
    /** Controle de encerramento da thread */
    private volatile boolean stopRequested;
    private Thread runThread;    
    
    /** Construtor da classe relogio */
    public Relogio(){
        this.tempoEmMils = 0;
    }
    
    /**
     *  metodo chamado quando a thread é lançada, controla a passagem de tempo
     *  do relogio. 
     * 
     *  A cada passagem de 1 segundo notifica aos observadores (placares etc.)
     *  para que se atualizem. Envia junto com a notificação o tempo atual, 
     *  em milisegundos.
     * 
     */
    @Override
    public void run() {
        
        // Ajustando o controle de parada da thread
        runThread = Thread.currentThread();
        stopRequested = false;         
        
        while(!stopRequested){
            
            tempoEmMils += 1000;

            this.setChanged();
            this.notifyObservers(this.getTempoEmMils());

            // Dorme 1 segundo e atualiza
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                // re-assert interrupt
                Thread.currentThread().interrupt();
            }
            
            // Relógio pausado               
            while(pausar) {
                
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
               
            }            

        }
        
    }

    public long getTempoEmMils() {
        return tempoEmMils;
    }
    
    public void zerar(){
        this.tempoEmMils = 0;
    }

    public void iniciar()  {
        
        Parametros.getInstance().getPoolThreads().execute(this);
            
    }
    
    public void pausar() {
        
        this.pausar = true;
        
    }
    
    public void continuar() {
        
        this.pausar = false;
        
    }
        
    public void stopRequest() {
        stopRequested = true;

        if (runThread != null) {
          runThread.interrupt();
        }
        
    }    
   
}

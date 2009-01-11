package controle;

import java.util.Calendar;
import java.util.Observable;
import modelo.Luta;
import modelo.Parametros;
import modelo.Ponto;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 *  Classe produtora de novos pontos
 *
 *  Pontos são produzidos a partir do monitoramento dos controles
 */
public class PontoProdutor extends Observable implements Runnable {

    /**Referência para o buffer de pontos*/
    private volatile PontoBuffer pontoBuffer;
    
    /** Luta ativa para a qual este produtor está produzindo pontos*/
    private volatile Luta lutaAtual;    
    
    /** Controle de encerramento da thread */
    private volatile boolean stopRequested;
    private Thread runThread;
    
    /** Array utilizado para notificar aos observadores qual arbitro 
     * pressionou um botão e para qual lutador o ponto foi "enviado".
     * 
     *  indice 0 = On ou Off do botão
     *  indice 1 = Lutador 1 ou 2
     *  indice 2 = Arbitro
     */
    private int[] ledArbitros = { 0, 0, 0};
    
    //  Construtor de PontoProdutor
    public PontoProdutor(PontoBuffer pontoBuffer, Luta lutaAtual) {
        this.pontoBuffer = pontoBuffer;
        this.lutaAtual = lutaAtual;
    }
    
    /**
     *  metodo chamado quando a thread é lançada, monitora os controles
     * 
     *  Cada componente (tecla, botão etc.) pressionado gera um ponto
     *  que é colocado no buffer de pontos para ser utilizado pelo
     *  consumidor de pontos
     * 
     */    
    @Override
    public void run() {

        // Ajustando o controle de parada da thread
        runThread = Thread.currentThread();
        stopRequested = false;  
        
        // Coleção de controles obtido através do JInput
        Controller[] controllers = ControllerEnvironment.
                getDefaultEnvironment().getControllers();

        // Se não achar nenhum controle fecha o sistema
        if(controllers.length==0) {
            util.Util.gravaExcecao(new Exception("Nenhum controle " +
                    "encontrado"), this);
            throw new RuntimeException();
        }        
        
        while(!stopRequested) {
            
            for(int indice=0;indice<controllers.length;indice++) {

                // Olhando apenas os controles cadastrados
                if(Parametros.getInstance().getIdControle1() != indice &&
                        Parametros.getInstance().getIdControle2() != indice &&
                        Parametros.getInstance().getIdControle3() != indice &&
                        Parametros.getInstance().getIdControle4() != indice )
                    continue;
                
                controllers[indice].poll();
                EventQueue queue = controllers[indice].getEventQueue();
                
                Event event = new Event();
                
                while(queue.getNextEvent(event)) {
                    
                    Component componente = event.getComponent();
                    
                    // Olhando apenas os componentes que marcam pontos
                    if(!Parametros.getInstance().getC1PontoAzul().equals(componente.getName()) &&
                            !Parametros.getInstance().getC2PontosAzul().equals(componente.getName()) &&
                            !Parametros.getInstance().getC1PontoVermelho().equals(componente.getName()) &&
                            !Parametros.getInstance().getC2PontosVermelho().equals(componente.getName())) 
                            continue;
                    
                    // Ponto gerado
                    Ponto novoPonto = new Ponto(
                            indice,
                            componente.getName(),
                            Calendar.getInstance().getTimeInMillis(),
                            lutaAtual,
                            "C",
                            lutaAtual.getRoundAtual());                    
                    
                    /* Envia para obuffer  apenas o pressionar(1.0f) do botão
                     * do contrario, o "des"pressionar tb seria computado. */
                    if((event.getValue()==1.0f)){
                        // Grava o novo ponto buffer de pontos
                        pontoBuffer.add(novoPonto);
                    }
                    
                    /* Montando e enviando os dados para os observadores */
                    
                    // On ou Off do botão
                    ledArbitros[0]= event.getValue() == 0f ? 0 : 1;
                    
                    // Lutador para o qual foi "enviado" o ponto
                    if(componente.getName().equals(Parametros.getInstance().getC1PontoVermelho()) ||
                            componente.getName().equals(Parametros.getInstance().getC2PontosVermelho())) {
                        ledArbitros[1] = 1;
                    }
                    
                    if(componente.getName().equals(Parametros.getInstance().getC1PontoAzul()) ||
                            componente.getName().equals(Parametros.getInstance().getC2PontosAzul())) {
                        ledArbitros[1] = 2;
                    }
                    
                    ledArbitros[2] = indice;
                    
                    this.setChanged();
                    this.notifyObservers(ledArbitros);
                    
                }

            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                // re-assert interrupt
                Thread.currentThread().interrupt();
            }
            
            /* Luta pausada */               
            while(lutaAtual.isPausada()) {
                
                try {
                    // Limpando o buffer enquanto esta pausado
                    pontoBuffer.clear();
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
               
            }

        }   
                
    }
        
    public void stopRequest() {
        stopRequested = true;

        if (runThread != null) {
          runThread.interrupt();
        }
        
    }
    
}

package controle;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import modelo.Luta;
import modelo.Parametros;
import modelo.Ponto;
import modelo.excecoes.DAOException;
import modelo.excecoes.LutaComandoException;

/**
 *  Classe consumidora de novos pontos
 *
 *  Pontos são consumidos de ponto buffer e adicionados na colecao de pontos
 *  obtidos para, em seguida, serem processados
 */
public class PontoConsumidor implements Runnable {

    /** pontos obtidos no buffer de pontos */
    private Vector<Ponto> pontosObtidos = new Vector<Ponto>();
    
    /**Referência para o buffer de pontos */
    private volatile PontoBuffer pontoBuffer;
    
    /** Luta ativa */
    private volatile Luta lutaAtual;
    
    /** momento atual em milisegundos */
    private long momentoAtual;
    
    /** Número mínimo de árbitros para validar um ponto */
    private final long NUM_MIN_ARBITROS = 
            Parametros.getInstance().getNumMinArbitros();
    
    /** Intervalo máximo entre os pontos para serem validos */
    private final long INTERVALO_MAXIMO = 
            Parametros.getInstance().getIntervaloMaximo();
    
    /** Id para marcar pontos iguais que compuseram um ponto validado */
    private int idPontoValidado = 1;
        
    /** Controle de encerramento da thread */
    private volatile boolean stopRequested;
    private Thread runThread;

    public PontoConsumidor() {
    }
    
    /** Contrutor do consumidor de pontos */
    public PontoConsumidor(PontoBuffer pontoBuffer, Luta lutaAtual){

        this.lutaAtual = lutaAtual;
        this.pontoBuffer = pontoBuffer;
        
    }

    /**
     *  metodo chamado quando a thread é lançada, obtem os pontos do buffer
     * e inicia o seu processamento
     *
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        
        // Ajustando o controle de parada da thread
        runThread = Thread.currentThread();
        stopRequested = false; 
        
        // Continua rodando enquanto não encerrar
        while(!stopRequested) {
            
            // Busca um ponto no buffer
            Ponto ponto = pontoBuffer.get();

            // Se conseguiu um ponto, adiciona na coleção e processa
            if(ponto != null){
                pontosObtidos.add(ponto);

                // Momento atual
                momentoAtual = Calendar.getInstance().getTimeInMillis();

                // Processa os pontos
                this.processaPontos();
                
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
                    /* Se a luta está pausada, os pontos são salvos
                    Pontos não processados são marcados */
                    this.persitePontos();
                } catch (DAOException ex) {
                    ;
                }
                                
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
               
            }
            
        }
        
    }
    
    /**
     *  Processa os pontos obtidos do buffer
     *
     */
    private void processaPontos() {
        
        // colecao para armazenar os pontos iguais
        Map<Integer, Ponto> pontosIguais = new HashMap<Integer, Ponto>();
                
        // Percorre a colecao de pontos obtidos no buffer de pontos
        for(int indice1 = 0; indice1 < pontosObtidos.size(); indice1++){

            /* Se o ponto for mais velho que 500ms, marca como processado
             * e salta está interação do laço */
            if(momentoAtual - pontosObtidos.elementAt(indice1).getMomento()
                > INTERVALO_MAXIMO){
                pontosObtidos.elementAt(indice1).setProcessado(true);
                continue;
            }

            // O ponto que será comparado
            Ponto pontoAtivo = pontosObtidos.elementAt(indice1);
            
            /* Adiciona o ponto ativo na colecao de pontos iguais
             * pontosIguais.add(pontoAtivo) */
            pontosIguais.put(pontoAtivo.getIdControle(), pontoAtivo);
            
            // Se o ponto não foi processado, processa (hehe)
            if(!pontoAtivo.isProcessado()) {
            
                // Compara os demais pontos com o ponto atual
                for(int indice2 = indice1 + 1; indice2 < pontosObtidos.size();
                    indice2++){

                    // Só compara com pontos não processados
                    if(!pontosObtidos.elementAt(indice2).isProcessado()){
                        if(pontoAtivo.equals(pontosObtidos.elementAt(indice2))){

                            /*
                             *  Se o ponto é igual adiciona no map de pontos
                             *  iguais. Como a chave do map é o id do controle
                             *  Só permite controles diferentes.
                             */
                            pontosIguais.put(pontosObtidos.elementAt(indice2).
                                    getIdControle(),
                                    pontosObtidos.elementAt(indice2));
                            
                        }
                    }
                    
                    // Atingiu o Número de árbitros no sistema
                    if(pontosIguais.size() >= 
                            Parametros.getInstance().getNumArbitrosSistema()){
                        break;
                    }

                }
                
                /*
                 *  Se o tamanho da colecao de pontos iguais for maior ou igual
                 *  ao Número mínimo de árbitros para validar os pontos, marca
                 *  um ponto "real".
                 *
                 *  A quantidade de pontos e o lutador que recebe os pontos
                 *  depende do componente pressionado
                 */
                if(pontosIguais.size() >= NUM_MIN_ARBITROS){
                    
                    if(pontoAtivo.getComponente().equals(
                            Parametros.getInstance()
                            .getC1PontoVermelho())){
                        try {
                            lutaAtual.addPontoLutador1(1);
                        } catch (LutaComandoException ex) {
                            util.Util.gravaExcecao(ex, this);
                        } catch (DAOException ex){
                            throw new RuntimeException("Erro no acesso ao banco de dados");
                        }
                    }

                    if(pontoAtivo.getComponente().equals(
                            Parametros.getInstance()
                            .getC2PontosVermelho())){
                        try {
                            lutaAtual.addPontoLutador1(2);
                        } catch (LutaComandoException ex) {
                            util.Util.gravaExcecao(ex, this);
                        } catch (DAOException ex){
                            throw new RuntimeException("Erro no acesso ao banco de dados");
                        }
                    }

                    if(pontoAtivo.getComponente().equals(
                            Parametros.getInstance().getC1PontoAzul())){
                        try {
                            lutaAtual.addPontoLutador2(1);
                        } catch (LutaComandoException ex) {
                            util.Util.gravaExcecao(ex, this);
                        } catch (DAOException ex){
                            throw new RuntimeException("Erro no acesso ao banco de dados");
                        }
                    }

                    if(pontoAtivo.getComponente().equals(
                            Parametros.getInstance().getC2PontosAzul())){
                        try {
                            lutaAtual.addPontoLutador2(2);
                        } catch (LutaComandoException ex) {
                            util.Util.gravaExcecao(ex, this);
                        } catch (DAOException ex){
                            throw new RuntimeException("Erro no acesso ao banco de dados");
                        }
                    }                    
                        
                    /* Marca os pontos iguais como processados, validados e seta
                     o id dos pontos validados*/
                    for (Entry<Integer, Ponto> ponto : pontosIguais.entrySet()){
                        
                        ponto.getValue().setProcessado(true);
                        ponto.getValue().setValidado(true);
                        ponto.getValue().setIdPontoValidado(idPontoValidado);
                        
                        idPontoValidado++;

                    }                    
                    
                }
                
            }
            
            // Zera a colecao de pontos iguais
            pontosIguais.clear();
            
        }
        
    }
    
    public void persitePontos() throws DAOException{
        
        for(Ponto pontoAtual: this.pontosObtidos){

            /* Se não está processado, marca como processado antes de salvar
             * 
             * Este método deve ser chamado apenas em locais que iria expirar
             * os pontos*/
            if(!pontoAtual.isProcessado()){
                pontoAtual.setProcessado(true);
            }

            pontoAtual.save();
        
        }
        
        // Coleção de pontos - agora processados - é limpa
        this.pontosObtidos.clear();        
        
    }
    
    public void stopRequest() {
        stopRequested = true;

        if (runThread != null) {
          runThread.interrupt();
        }
        
    }

}

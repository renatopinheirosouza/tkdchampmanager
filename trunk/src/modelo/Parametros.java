package modelo;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import modelo.excecoes.DAOException;
import modelo.dao.DAOFactory;
import modelo.dao.interfaces.ParametrosDAO;

/**
 *  Classe com os atributos de configuração e acompanhamento do sistema
 */
public class Parametros implements Serializable{

    private static transient ParametrosDAO parametrosDAO = 
            DAOFactory.getInstance().getParametrosDAO();
    
    /**
     * Intervalo máximo para que os pontos sejam recebidos. (Default = 500ms)
     * Após este intervalo os pontos são descartados
     */
    private int intervaloMaximo = 1000;
    
    /** Número mínimo de arbitros para validação de um ponto (Default = 2)*/
    private int numMinArbitros = 2;
    
    /** Número de árbitros no sistema */
    private int numArbitrosSistema = 3;
    
    /** Componente para marcacao de 1 ponto para o lutador vermelho*/
    private String c1PontoVermelho;
    
    /**Componente para marcacao de 2 pontos para o lutador vermelho */
    private String c2PontosVermelho;
    
    /** Componente para marcacao de 1 ponto para o lutador azul */
    private String c1PontoAzul;
    
    /** Componente para marcacao de 2 pontos para o lutador azul */
    private String c2PontosAzul;
    
    /** Ids dos controles de cada juiz */
    private int idControle1, idControle2, idControle3, idControle4;
    
    /** duração, em milisegundos, de cada round (Default = 2 Minutos [120000])*/
    private long duracaoRound = 90000; 
    
    /** Duração do intervalo entre os rounds*/
    private long duracaoIntervalo = 30000; 

    /** Número máximo de rounds (Default = 3) */
    private int numRounds = 2;
    
    /** difrença de pontos entre lutadores quando a luta deve ser encerrada */
    private int diferencaPontos = 7;
    
    /** Número máximo de pontos */
    private int maximoPontos = 12;
    
    /** Única instância desta classe (Singleton) */
    private static Parametros instancia = null;
    
    /** Duracao do tempo medico */
    private long duracaoTempoMedico = 60000;
        
    /** Pool de threads*/
    private transient ExecutorService poolThreads;    
        
    /**
     * Retorna a instância de ControleGeral (SINGLETON)
     *
     * @return A única instância desta classe (SINGLETON)
     */
    public static Parametros getInstance(){
                
        if(instancia==null){

            // Tenta fazer o load, se não consegue, cria uma instância nova
            try {
                instancia = parametrosDAO.load();
            } catch (DAOException ex) {
                instancia = new Parametros();
            }
            
        }
        
        return instancia;
    }
    
    /** Construtor default - Privado (SINGLETON) */
    
    private Parametros() {      

        this.c1PontoAzul = "Button 1";
        this.c2PontosAzul = "Button 2";
        this.c1PontoVermelho = "Button 0";
        this.c2PontosVermelho = "Button 3";        
        
        this.idControle1 = 0;
        this.idControle2 = 1;
        this.idControle3 = 2;
        this.idControle4 = 3;
        
        // Criando o pool de threads
        poolThreads = Executors.newCachedThreadPool();
        
    }

    /**
     * Retorna o intervalo máximo entre cada ponto para que seja v�lido
     * @return INTERVALO_MAXIMO
     */
    public int getIntervaloMaximo() {
        return intervaloMaximo;
    }

    /**
     * Seta o intervalo máximo entre cada ponto para que seja v�lido
     *
     * @param intervaloMaximo intervalo, em milessegundos, entre cada ponto
     *                        para que seja v�lido
     */
    public void setIntervaloMaximo(int intervaloMaximo) {
        this.intervaloMaximo = intervaloMaximo;
    }

    /**
     * Retorna no Número mínimos de árbitros para que um ponto seja validado
     *
     * @return Numero mínimo de árbitros para validar um ponto
     */
    public int getNumMinArbitros() {
        return numMinArbitros;
    }

    /**
     * Seta o Número mínimos de árbitros para que um ponto seja validado
     *
     * @param numMinArbitros O Número mínimos de árbitros para que um ponto
     * seja validado
     */
    public void setNumMinArbitros(int numMinArbitros) {
        this.numMinArbitros = numMinArbitros;
    }

    /**
     * Retorna o componente para marcacao de 1 ponto para o lutador vermelho
     *
     * @return componente para marcacao de 1 ponto para o lutador vermelho
     */
    public String getC1PontoVermelho() {
        return c1PontoVermelho;
    }

    /**
     * Seta o omponente para marcacao de 1 ponto para o lutador vermelho
     *
     * @param c1PontoVermelho Componente para marcacao de 1 ponto para o lutador
     * vermelho
     */
    public void setC1PontoVermelho(String c1PontoVermelho) {
        this.c1PontoVermelho = c1PontoVermelho;
    }

    /**
     * Componente para marcacao de 2 pontos para o lutador vermelho
     *
     * @return Componente para marcacao de 2 pontos para o lutador vermelho
     */
    public String getC2PontosVermelho() {
        return c2PontosVermelho;
    }

    /**
     * Seta o componente para marcacao de 2 pontos para o lutador vermelho
     *
     * @param c2PontosVermelho Componente para marcacao de 2 pontos para o 
     * lutador vermelho
     */
    public void setC2PontosVermelho(String c2PontosVermelho) {
        this.c2PontosVermelho = c2PontosVermelho;
    }

    /**
     * Retorna o componente para marcacao de 1 ponto para o lutador azul
     *
     * @return Retorna o Componente para marcacao de 1 ponto para o lutador azul
     */
    public String getC1PontoAzul() {
        return c1PontoAzul;
    }

    /**
     * Seta o componente para marcacao de 1 ponto para o lutador azul
     *
     * @param c1PontoAzul componente para marcacao de 1 ponto para o lutador 
     * azul
     */
    public void setC1PontoAzul(String c1PontoAzul) {
        this.c1PontoAzul = c1PontoAzul;
    }

    /**
     * Retorna o componente para marcacao de 2 pontos para o lutador azul
     *
     * @return componente para marcacao de 2 pontos para o lutador azul
     */
    public String getC2PontosAzul() {
        return c2PontosAzul;
    }

    /**
     * Seta o componente para marcacao de 2 pontos para o lutador azul
     *
     * @param c2PontosAzul componente para marcacao de 2 pontos para o lutador 
     * azul
     */
    public void setC2PontosAzul(String c2PontosAzul) {
        this.c2PontosAzul = c2PontosAzul;
    }

    /**
     * Retorna a duração, em milisegundos, de cada round 
     *
     * @return duração, em milisegundos, de cada round 
     */
    public long getDuracaoRound() {
        return duracaoRound;
    }

    /**
     * Seta a duração, em milisegundos, de cada round
     *
     * @param duracaoRound duração, em milisegundos, de cada round
     */
    public void setDuracaoRound(long duracaoRound) {
        this.duracaoRound = duracaoRound;
    }

    /**
     * Retorna o Número máximo de rounds 
     *
     * @return Número máximo de rounds
     */
    public int getNumRounds() {
        return numRounds;
    }

    /**
     * Seta o Número máximo de rounds
     *
     * @param numRounds Número máximo de rounds 
     */
    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
    }

    /**
     * Retorna a diferenca de pontos permitida entre os dois lutadores
     *
     * @return A diferenca de pontos permitida entre os dois lutadores
     */
    public int getDiferencaPontos() {
        return diferencaPontos;
    }

    /**
     * Seta a diferenca de pontos permitida entre os dois lutadores
     *
     * @param diferencaPontos A diferenca de pontos permitida entre os dois 
     * lutadores
     */
    public void setDiferencaPontos(int diferencaPontos) {
        this.diferencaPontos = diferencaPontos;
    }

    /**
     * Retorna o Número máximo de pontos que um lutador pode atingir
     *
     * @return O Número máximo de pontos que um lutador pode atingir
     */
    public int getMaximoPontos() {
        return maximoPontos;
    }

    /**
     * Seta o Número máximo de pontos que um lutador pode atingir
     *
     * @param maximoPontos O Número máximo de pontos que um lutador pode atingir
     */
    public void setMaximoPontos(int maximoPontos) {
        this.maximoPontos = maximoPontos;
    }

    /**
     * Retorna a duração do tempo medico
     *
     * @return A duração do tempo medico
     */
    public long getDuracaoTempoMedico() {
        return duracaoTempoMedico;
    }

    /**
     * Seta a duração do tempo medico
     *
     * @param duracaoTempoMedico A duração do tempo medico
     */
    public void setDuracaoTempoMedico(long duracaoTempoMedico) {
        this.duracaoTempoMedico = duracaoTempoMedico;
    }

    public int getNumArbitrosSistema() {
        return numArbitrosSistema;
    }

    public void setNumArbitrosSistema(int numArbitrosSistema) {
        this.numArbitrosSistema = numArbitrosSistema;
    }

    @Override
    public String toString() {
        
        Parametros parametros = this;
        
        return "PontoAzulC1: " +parametros.getC1PontoAzul() +"\n" +"PontoVermelhoC1: "
                +parametros.getC1PontoVermelho() +"\n" +"PontoAzulC2: " +parametros.getC2PontosAzul()
                +"\n" +"PontoVermelhoC2: " +parametros.getC2PontosVermelho() +"\n" +"Diferenca Pontos: "
                +parametros.getDiferencaPontos() +"\n" +"Duracao Round: " +parametros.getDuracaoRound() +"\n"
                +"Duracao Tempo Medico: " +parametros.getDuracaoTempoMedico() +"\n" +"Intervalo Maximo"
                +parametros.getIntervaloMaximo() +"\n" +"Maximo de Pontos: " +parametros.getMaximoPontos()
                +"\n" +"Numero de Arbitros no Sistema: " +parametros.getNumArbitrosSistema() +"\n"
                +"Numero minimo de Arbitros: " +parametros.getNumMinArbitros() +"\n" +"Numero de Rounds"
                +parametros.getNumRounds();
    }
    
    public void save() throws DAOException{
        
        Parametros.parametrosDAO.save(this);
        
    }

    public long getDuracaoIntervalo() {
        return duracaoIntervalo;
    }

    public int getIdControle1() {
        return idControle1;
    }

    public void setIdControle1(int idControle1) {
        this.idControle1 = idControle1;
    }

    public int getIdControle2() {
        return idControle2;
    }

    public void setIdControle2(int idControle2) {
        this.idControle2 = idControle2;
    }

    public int getIdControle3() {
        return idControle3;
    }

    public void setIdControle3(int idControle3) {
        this.idControle3 = idControle3;
    }

    public int getIdControle4() {
        return idControle4;
    }

    public void setIdControle4(int idControle4) {
        this.idControle4 = idControle4;
    }

    public ExecutorService getPoolThreads() {
        return poolThreads;
    }

    public void setDuracaoIntervalo(long duracaoIntervalo) {
        this.duracaoIntervalo = duracaoIntervalo;
    }
    
}
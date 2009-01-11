package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import modelo.dao.DAOFactory;
import modelo.dao.interfaces.PontoDAO;
import modelo.excecoes.DAOException;

/**
 * Classe que define um ponto
 */
@Entity
public class Ponto implements Serializable {

    /***/
    private static PontoDAO pontoDAO = DAOFactory.getInstance().getPontoDAO();
    
    /** id do BD*/
    private int id;
    
    /**O id do controle que originou o ponto*/
    private int idControle;
    
    /**O componente (Botão, tecla etc.) pressionado para gerar o ponto*/
    private String componente;
    
    /**O momento em que o botão foi pressionado em milisegundos*/
    private long momento;
    
    /**Ponto já processados*/
    private boolean processado = false;
    
    /**Intervalo máximo entre dois pontos para que sejam iguais*/
    private final long INTERVALO_MAXIMO =
            Parametros.getInstance().getIntervaloMaximo();
    
    /** Luta onde este ponto foi marcado */
    private Luta luta = null;
    
    /** Se este ponto foi validado */
    private boolean validado = false;
    
    /** Id dos pontos que, com este, validaram o ponto*/
    private int idPontoValidado;
    
    /** Origem do ponto - K para teclado e C para controle */
    private String origemPonto;
    
    /** Round onde o ponto foi marcado */
    private int round;

    public Ponto() {
    }

    /**Construtor da classe ponto. */
    public Ponto(int idControle, String componente, long momento, Luta luta,
            String origemPonto, int round) {

        this.idControle = idControle;
        this.componente = componente;
        this.momento = momento;
        this.luta = luta;
        this.round = round;
        
        // O padrão é controle assim, se não recebeu, seta como tal
        if(origemPonto.equals(""))
            origemPonto = "C";
        
        // para pegar apenas o primeiro caracter
        this.origemPonto = origemPonto.substring(0,1);
        
    }
    
    /**Construtor da classe ponto para load do banco. */
    public Ponto(int id, int idControle, String componente, long momento, 
            boolean processado, Luta luta, int idPontoValidado, 
            String origemPonto, int round) {

        this(idControle, componente, momento, luta, origemPonto, round);
        
        this.processado = processado;
        this.idPontoValidado = idPontoValidado;
        this.id = id;
        
    }
    
    /**
     * Retorna o componente (botão, tecla etc.) que gerou o ponto
     *
     * @return Componente pressionado na gera��o deste ponto
     */
    public String getComponente() {
        return componente;
    }

    /**
     * Seta o componente (botão, tecla etc.) que gerou o ponto
     * @param componente o componente que gerou o ponto
     */
    public void setComponente(String componente) {
        this.componente = componente;
    }

    /**
     * Retorna o momento (em milissegundo) em que o ponto foi gerado
     * @return Momento em que o ponto foi gerado
     */
    public long getMomento() {
        return momento;
    }

    /**
     * Seta o momento (em milissegundo) em que o ponto foi gerado
     * @param momento o momento em que o ponto foi gerado
     */
    public void setMomento(long momento) {
        this.momento = momento;
    }

    /**
     * Retorna o id do controle que gerou o ponto
     * @return id do controle que gerou este ponto
     */
    public int getIdControle() {
        return idControle;
    }

    /**
     * Seta o id do controle que gerou o ponto
     * @param idControle o id do controle
     */
    public void setIdControle(int idControle) {
        this.idControle = idControle;
    }

    /**
     * Retorna se este ponto já foi processado 
     * (Se é mais antigo que o INTERVALO_MAXIMO ou se já foi usado em um ponto
     * real)
     * @return true se o ponto já foi processado
     */
    public boolean isProcessado() {
        return processado;
    }

    /**
     * Seta se este ponto já foi processado 
     * (Se é mais antigo que o INTERVALO_MAXIMO ou se já foi usado em um ponto
     * real)
     * @param processado o status (true/false) de processamento do ponto
     */
    public void setProcessado(boolean processado) {
        this.processado = processado;
    }

    /**
     * Verifica se dois pontos são iguais, sendo que pontos iguais tem:
     *      idControle diferente
     *      Componente (Tecla, bot�o etc.) igual
     *      difrença de tempo em milisegundos menor que INTERVALO_MAXIMO
     * @param obj O ponto que deverá ser comparado com este ponto
     * @return true se o ponto é igual ao ponto passado
     */
    @Override
    public boolean equals(Object obj) {

        // Casting de obj para ponto
        Ponto ponto = (Ponto) obj;

        // Pontos nulos nunca são iguais a outros
        if (ponto == null) {
            return false;
        }

        /* Pontos processados são mais antigos que o tempo máximo ou já foram
         * utilizados para marcar um ponto real, por isso são sempre diferentes
         */
        if ((this.isProcessado()) || (ponto.isProcessado())) {
            return false;
        }

        /*
         * idControle diferente
         * Componente (Tecla, bot�o etc.) igual
         * difrença de tempo em milisegundos menor que INTERVALO_MAXIMO
         */
        return ((ponto.getIdControle() != this.getIdControle()) &&
                (ponto.getComponente().equals(this.getComponente())) &&
                ((ponto.getMomento() - this.getMomento()) <= INTERVALO_MAXIMO));
    }

    @ManyToOne
    public Luta getLuta() {
        return luta;
    }

    public void setLuta(Luta luta) {
        this.luta = luta;
    }

    public int getIdPontoValidado() {
        return idPontoValidado;
    }

    public void setIdPontoValidado(int idPontoValidado) {
        this.idPontoValidado = idPontoValidado;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getOrigemPonto() {
        return origemPonto;
    }

    public void setOrigemPonto(String origemPonto) {
        this.origemPonto = origemPonto;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void save() throws DAOException{
        this.setId(pontoDAO.save(this));
    }
    
}

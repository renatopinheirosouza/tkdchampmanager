package modelo;

import controle.PontoBuffer;
import controle.PontoConsumidor;
import controle.PontoProdutor;
import controle.Relogio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import modelo.dao.DAOFactory;
import modelo.excecoes.DAOException;
import modelo.excecoes.LutaComandoException;
import modelo.excecoes.LutaDadoIncorretoException;
import util.StaticObservable;

/**
 * Classe Luta, define uma luta controlada pelo sistema
 */
@Entity
public class Luta extends StaticObservable implements Observer, Serializable{

    /** Consumidor de pontos */
    transient private PontoConsumidor pontoConsumidor = null;

    /** Produtor de pontos */
    transient private PontoProdutor pontoProdutor = null;
    
    /** Buffer de pontos */
    transient private PontoBuffer pontoBuffer = null;
    
    /** Pool de threads*/
    transient private ExecutorService poolThreads;
    
    /** Id do objeto no banco. */
    private int id;

    /** Id da luta no campeonato */
    private int idLutaCampeonato;
       
    /** Campeonato */
    private Campeonato campeonato;
    
    /** Categoria */
    private Categoria categoria;
    
    /** Rodada, dentro do campeonato/categoria, que está luta aconteceu */
    private int rodada;
    
    /** Luta anterior 1 */
    private Luta lutaAnterior1;
    
    /** Luta anterior 2 */
    private Luta lutaAnterior2;
    
    /** Lutador lutador 1 (Protetor Vermelho) */
    private Lutador lutador1 = null;

    /** Pontos do lutador 1 */
    private volatile int pontosLutador1 = 0;

    /** Meias faltas do lutador 1 */
    private volatile int kyongoLutador1 = 0;
    
    /** Falta inteira do lutador 1 */
    private volatile int kamtchonLutador1 = 0;

    /** Se o WO foi do Lutador 1*/
    private boolean woLutador1 = false;

    /** Lutador lutador 2 (Protetor Azul)*/
    private Lutador lutador2 = null;

    /** Pontos do lutador 2 */
    private volatile int pontosLutador2 = 0;

    /** Meias faltas do lutador 2 */
    private volatile int kyongoLutador2 = 0;
    
    /** Falta inteira do lutador 2*/
    private volatile int kamtchonLutador2 = 0;
    
    /** Se o WO foi do Lutador 2*/
    private boolean woLutador2 = false;

    /** Lutador vencedor */
    private Lutador lutadorVencedor = null;

    /** Round atual */
    private volatile int roundAtual = 0;
    
    /** Controla se a luta "está em" golden point*/
    private volatile boolean goldenPoint = false;
    
    /** Relogio da luta */
    transient private Relogio relogio;
    
    /** Tempo de luta */
    transient private volatile long tempoLuta = 0;    
  
    /** Controla se a luta está pausa */
    transient private volatile boolean pausada = false;
    
    /** Se a luta está no intervalo entre rounds*/
    transient private volatile boolean intervalo = false;
    
    /** Controla se a luta está ativa */
    private volatile boolean ativa = false;
    
    /** Controla se a luta já ocorreu */
    private volatile boolean encerrada = false;    
    
    /** Controla se a luta foi cancelada */
    private boolean cancelada = false;
    
    /** Controla se a luta é a disputa pelo primeiro lugar */
    private boolean primeiroLugar = false;
    
    /** Observações sobre a luta */
    private String observacoes = "";

    /** JPA */
    public Luta() {
    }

    /** Construtor base */
    public Luta(Campeonato campeonato, Categoria categoria, int rodada){
        this.setCampeonato(campeonato);
        this.setCategoria(categoria);
        this.setRodada(rodada);        
    }
    
    /** Construtor para load do banco */
    public Luta(int id, Lutador lutador1, int pontosLutador1,
            int kyongoLutador1, int kamtchonLutador1, boolean woLutador1,
            Lutador lutador2, int pontosLutador2, int kyongoLutador2, 
            int kamtchonLutador2, boolean woLutador2, Campeonato campeonato,
            int idLutaCampeonato, Categoria categoria, int rodada, 
            boolean encerrada, Lutador lutadorVencedor, boolean primeiroLugar,
            boolean cancelada, String observacoes){

        this(campeonato, categoria, rodada);
        
        this.id = id;
        this.lutador1 = lutador1;
        this.pontosLutador1 = pontosLutador1;
        this.kyongoLutador1 = kyongoLutador1;
        this.kamtchonLutador1 = kamtchonLutador1;
        this.woLutador1 = woLutador1;
        this.lutador2 = lutador2;
        this.pontosLutador2 = pontosLutador2;
        this.kyongoLutador2 = kyongoLutador2;
        this.kamtchonLutador2 = kamtchonLutador2;
        this.woLutador2 = woLutador2;
        this.idLutaCampeonato = idLutaCampeonato;
        this.encerrada = encerrada;
        this.lutadorVencedor = lutadorVencedor;
        this.cancelada = cancelada;
        this.primeiroLugar = primeiroLugar;
        this.observacoes = observacoes;
        
    }
    
    /** Luta com dois lutadores conhecidos */
    public Luta(Campeonato campeonato, Categoria categoria, int rodada,
            Lutador lutador1, Lutador lutador2, boolean primeiroLugar) throws
            LutaDadoIncorretoException, DAOException {
        
        this(campeonato, categoria, rodada);
        
        if(lutador1 == lutador2){
            throw new LutaDadoIncorretoException(
                    "Lutadores cominformações iguais");
        }
        
        this.setLutador1(lutador1);
        this.setLutador2(lutador2);
        this.setPrimeiroLugar(primeiroLugar);
        
        this.setId(DAOFactory.getInstance().getLutaDAO().save(this));
                
    }
        
    /** Luta com WO por falta de lutador na categoria*/
    public Luta(Campeonato campeonato,  Categoria categoria, int rodada,
            Lutador lutador1, boolean primeiroLugar) throws DAOException {
        
        this(campeonato, categoria, rodada);
        this.setLutador1(lutador1);
        this.setWoLutador2(true);
        this.setPrimeiroLugar(primeiroLugar);
        this.lutadorVencedor = lutador1;
        this.encerrada = true;
        
        this.setId(DAOFactory.getInstance().getLutaDAO().save(this));
                
    }

    /** Luta com um lutador conhecido e com referência para a luta de origem
     do outro lutador */
    public Luta(Campeonato campeonato, Categoria categoria, int rodada,
            Lutador lutador1, Luta lutaAnterior2, boolean primeiroLugar) throws DAOException {
        
        this(campeonato, categoria, rodada);
        this.setLutador1(lutador1);
        this.setLutaAnterior2(lutaAnterior2);
        this.setPrimeiroLugar(primeiroLugar);
        
        this.setId(DAOFactory.getInstance().getLutaDAO().save(this));
                
    }
    
    /** Luta com lutadores desconhecidos e com Referência para as lutas de
     origem dos lutadores */
    public Luta(Campeonato campeonato,  Categoria categoria, int rodada,
            Luta lutaAnterior1, Luta lutaAnterior2, boolean primeiroLugar) throws DAOException {
        
        this(campeonato, categoria, rodada);
        this.setLutaAnterior1(lutaAnterior1);
        this.setLutaAnterior2(lutaAnterior2);
        this.setPrimeiroLugar(primeiroLugar);
        
        this.setId(DAOFactory.getInstance().getLutaDAO().save(this));
                
    }
    
    /** Adiciona pontos ao lutador 1 */            
    public void addPontoLutador1(int pontosAdicionar) throws
            LutaComandoException, DAOException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();
        
        // Soma um ponto ao lutador 1
        this.pontosLutador1 += pontosAdicionar;
        
        // Se estiver em golden point, o lutador1 vence
        if(isGoldenPoint()){
            this.setLutadorVencedor(this.getLutador1());
        } else {
        
            // Lutador 1 vence por pontos ou por difrença de pontos
            if((pontosLutador1 >= Parametros.getInstance().getMaximoPontos()) || 
                    ((pontosLutador1 - pontosLutador2) >= 
                    Parametros.getInstance().getDiferencaPontos())) {
                this.setLutadorVencedor(this.getLutador1());
            } else {
                
                /*  Notificando aos observadores, já que o setLutadorVencedor 
                 * não notificará. */      
                Luta.notifyObservers();                        
                
            }
            
        }
        
    }

    /** Subtrai pontos do lutador 1 */            
    public void subPontoLutador1(int pontosSubtrair) throws
            LutaComandoException {
                
        // Verifica se a luta está ativa
        this.validaAtiva();

        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();
        
        this.pontosLutador1 -= pontosSubtrair;

        /*  Notificando aos observadores */                
        Luta.notifyObservers();
        
    }    

    /** Adiciona um kyongo (meia falta) ao lutador 1 */         
    public void addKyongoLutador1() throws LutaComandoException, DAOException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();
        
        // Adiciona um kyongo
        this.kyongoLutador1++;
        
        // Se forem 2 kyongos, zera e vira um kamtchon
        if(this.kyongoLutador1 == 2){
            
            // Zerando
            this.kyongoLutador1 = 0;
            
            // Adicionando kyongos
            this.addKamtchonLutador1();
            
        } else {
            /*  Notificando aos observadores. Dentro do else pois 
             addKamtchon também notifica */                
            Luta.notifyObservers();        
        }
        
        
    }

    /** Subtrai um kyongo (meia falta) do lutador 1 */             
    public void subKyongoLutador1() throws LutaComandoException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();    
        
        // Subtraindo um kyongo
        this.kyongoLutador1--;
        
        /* Se Kyongo ficou menor que zero (Não haviam kyongos, apenas kamtchons)
        tenta tirar kamtchon. Se não houver kamtchon, zera o kyongo para não 
        ficar negativo */
        if(this.kyongoLutador1 < 0){
            
            // Se kamtchon maior que zero, pode subtrair 1
            if(this.kamtchonLutador1 > 0){
                /* Subtraindo direto para não chamar o subKamtchon e notificar
                 duas vezes aos observadores*/
                this.kamtchonLutador1--; 
                this.pontosLutador1++;
                this.kyongoLutador1 = 1;
            } else {
                this.kyongoLutador1 = 0;
            }
        }
        
        /*  Notificando aos observadores */
        Luta.notifyObservers();        
    }
        
    /** Adiciona um kamtchon (falta) neste lutador */         
    public void addKamtchonLutador1() throws LutaComandoException, DAOException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
    
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();
        
        // Adicionando kamtcons
        this.kamtchonLutador1++;
        
        // Cada kamtchon desconta um ponto
        this.pontosLutador1--;
        
        // 4 faltas, lutador 1 perde
        if(this.kamtchonLutador1==4){
            this.setLutadorVencedor(this.getLutador2());
        } else {
            /*  Notificando aos observadores, já que não entrou no 
             setLutadorVencedor para notificar */                
            Luta.notifyObservers();        
        }
        
    }

    /** Subtrai um kamtchon (falta) neste lutador */         
    public void subKamtchonLutador1() throws LutaComandoException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
 
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();
        
        // Subtraindo
        this.kamtchonLutador1--;
        
        /* Se for menor que zero, mantem em zero e não faz nada 
         do contrario adiciona um ponto já que uma falta foi retirada */
        if(this.kamtchonLutador1 < 0){
            this.kamtchonLutador1 = 0;
        } else {
            this.pontosLutador1++;
        }
        
        /*  Notificando aos observadores */                
        Luta.notifyObservers();        
    }
    
    /** Adiciona pontos ao lutador 2 */            
    public void addPontoLutador2(int pontosAdicionar) throws
            LutaComandoException, DAOException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();        
        
        // Soma um ponto ao lutador 2
        this.pontosLutador2 += pontosAdicionar;
        
        // Se estiver em golden point o lutador 2 vence
        if(isGoldenPoint()){
            this.setLutadorVencedor(this.getLutador2());
        } else {
        
            // Lutador 2 vence por pontos ou por difrença de pontos
            if((pontosLutador2 >= Parametros.getInstance().getMaximoPontos()) || 
                    ((pontosLutador2 - pontosLutador1) >= 
                    Parametros.getInstance().getDiferencaPontos())) {
                this.setLutadorVencedor(this.getLutador2());
            } else {
                
                /*  Notificando aos observadores, já que o setLutadorVencedor 
                 * não notificará. */      
                Luta.notifyObservers();                        
                
            }
             
        }
                
    }

    /**
     * Subtrai pontos do lutador 2
     * @param pontosSubtrair A quantidade de pontos a remover
     */            
    public void subPontoLutador2(int pontosSubtrair) throws
            LutaComandoException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();
        
        // Subtraindo pontos
        this.pontosLutador2 -= pontosSubtrair;

        /*  Notificando aos observadores */                
        Luta.notifyObservers();
        
    }    

    /** Adiciona um kyongo (meia falta) ao lutador 2 */         
    public void addKyongoLutador2() throws LutaComandoException, DAOException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();        
        
        // Adicionando kyongo ao lutador 2
        this.kyongoLutador2++;
        
        // Se atingiu 2 kyongos, zera e adiciona um kamtchon
        if(this.kyongoLutador2 == 2){
            this.kyongoLutador2 = 0;
            
            this.addKamtchonLutador2();
        } else {
            /*  Notificando aos observadores. Dentro do else pois 
             addKamtchon também notifica */          
            Luta.notifyObservers();        
        }
        
    }

    /** Subtrai um kyongo (meia falta) do lutador 2*/             
    public void subKyongoLutador2() throws LutaComandoException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();        
        
        // Subtrai um kyongo do lutador 2
        this.kyongoLutador2--;

        /* Se Kyongo ficou menor que zero (Não haviam kyongos, apenas kamtchons)
        tenta tirar kamtchon. Se não houver kamtchon, zera o kyongo para não 
        ficar negativo */
        if(this.kyongoLutador2 < 0){

            // Se kamtchon maior que zero, pode subtrair 1 kamtchon
            if(this.kamtchonLutador2 > 0){
                /* Subtraindo direto para não chamar o subKamtchon e notificar
                 duas vezes aos observadores*/            
                this.kamtchonLutador2--;
                this.pontosLutador2++;
                this.kyongoLutador2 = 1;
            } else {
                this.kyongoLutador2 = 0;
            }
        }
                
        /*  Notificando aos observadores */                
        Luta.notifyObservers();        
    }    
    
    /** Adiciona um kamtchon (falta) neste lutador */         
    public void addKamtchonLutador2() throws LutaComandoException, DAOException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();

        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();        
        
        // Adicionando um kamtchon para o lutador 2
        this.kamtchonLutador2++;
        
        // Cada kamtchon desconta um ponto
        this.pontosLutador2--;
        
        // 4 faltas, lutador 2 perde
        if(this.kamtchonLutador2==4){
            this.setLutadorVencedor(this.getLutador1());
        } else {
            /*  Notificando aos observadores, já que não entrou no 
             setLutadorVencedor para notificar */  
            Luta.notifyObservers();        
        }
    }

    /** Subtrai um kamtchon (falta) neste lutador */         
    public void subKamtchonLutador2() throws LutaComandoException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();
        
        // Subtraindo um kamtchon do lutador 2
        this.kamtchonLutador2--;
        
        /* Se for menor que zero, mantem em zero e não faz nada 
         do contrario adiciona um ponto já que uma falta foi retirada */
        if(this.kamtchonLutador2 < 0){
            this.kamtchonLutador2 = 0;
        } else {
            this.pontosLutador2++;
        }
        
        /*  Notificando aos observadores */        
        Luta.notifyObservers();        
    }

    /** Adiciona um round ao contador de rounds (Round Atual) */
    public void addRound() throws LutaComandoException, DAOException {
        
        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Verifica se atingiu o limite de rounds da categoria
        if( (roundAtual + 1) > categoria.getNumRounds()) {
            
            // Se está empatado, vai para o golden point
            if(pontosLutador1 == pontosLutador2){
                this.goldenPoint = true;
                this.roundAtual += 1;                
                
                /* Notifica aos observadores - interno para evitar que 
                 várias nofitificações sejam feitas */
                Luta.notifyObservers();                
                
            } else {
            
                // Se não está empatado, vence que tem maior pontuação
                if(pontosLutador2 > pontosLutador1) {
                    this.setLutadorVencedor(this.getLutador2());
                } else {
                    this.setLutadorVencedor(this.getLutador1());
                }
                
            }
            
        } else {
            this.roundAtual += 1;
            
            /* Notifica aos observadores - dentro do else para evitar que 
             várias nofitificações sejam feitas */
            Luta.notifyObservers();
            
        }
        
    }

    /** Tenta pausar a luta */
    public void pausar() throws LutaComandoException {
        
        // Verifica se já está pausada
        if(this.isPausada())
            throw new LutaComandoException("A luta já está pausada");                
        
        // Marca como pausada
        this.pausada = true;
        
        // Pausa o relogio
        if(this.relogio !=null)
            this.relogio.pausar();
        
        // Notificando aos observadores
        Luta.notifyObservers();        
        
    }
    
    /** Tentar continuar a luta*/
    public void continuar() throws LutaComandoException{

        // Verifica se a luta está ativa
        this.validaAtiva();
        
        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();        
        
        // Verifica se está pausada
        if(!this.isPausada())
            throw new LutaComandoException("A luta não está pausada");

        // Se estava em intervalo, muda para fora de intervalo
        if(isIntervalo())
            intervalo = false;
        
        // Desmarca como pausada
        this.pausada = false;
        
        // Reativa o relogio
        this.relogio.continuar();
        
        // Notificando aos observadores
        Luta.notifyObservers();        
        
    }

    /***/
    public void iniciar() throws LutaComandoException, DAOException{
        
        if(this.isAtiva()){
            throw new LutaComandoException("A luta já ativa, não é possível " +
                    "iniciá-la novamente");
        }
        
        // Verifica se algum lutado tem WO
        if(this.isWoLutador1() || this.isWoLutador2()){
            throw new LutaComandoException("Não é possível iniciar uma luta " +
                    "com WO");
        }        
        
        // Se ainda não foram setados os lutadores a luta não está ativa
        if(this.lutador1 == null || this.lutador2 == null){
            throw new LutaComandoException("A luta ainda não está disponível");
        }        

        // Se já tem um lutador vencedor, não faz nada
        this.validaVencedor();
        
        // Verifica se a luta já foi encerrada
        if(this.isEncerrada()){
            throw new LutaComandoException("A luta já foi encerrada, " +
                    "não é possível iniciá-la.");
        }        

        // Torna a luta ativa
        this.ativa = true;
        
        // Adiciona um round
        this.addRound();
        
        /* INICIO - Originalmente na classe LutaControle */
        // Pegando referência para o pool de threads
        poolThreads = Parametros.getInstance().getPoolThreads();
        
        // Buffer de pontos
        this.pontoBuffer = new PontoBuffer();
        
        // Consumidor de pontos
        this.pontoConsumidor = new PontoConsumidor(this.pontoBuffer, this);
        
        // Produtor de pontos
        this.pontoProdutor = new PontoProdutor(this.pontoBuffer, this);

        // Iniciando o produtor e o consumidor        
        poolThreads.execute(getPontoProdutor());
        poolThreads.execute(getPontoConsumidor());
        
        /* FIM - Originalmente na classe LutaControle */
        
        // Consegue um relogio para controlar o tempo da luta
        this.relogio = new Relogio();

        // Inicia o Relógio e o observa
        this.relogio.iniciar();
        this.relogio.addObserver(this);
        
        // Notificando aos observadores
        Luta.notifyObservers();        
        
    }
    
    /** Encerrar a luta */
    public void encerrar() throws DAOException, LutaComandoException{

        // Se o vencedor é null e não foi WO duplo
        if(this.lutadorVencedor == null &&
                (!this.woLutador1 && !this.woLutador2)) {

            // E se foi empate, não é possível encerrar a luta 
            if(this.getPontosLutador1() == getPontosLutador2() &&
                    this.lutadorVencedor == null){
                throw new LutaComandoException("Não é possível determinar um " +
                        "vencedor, por isso não é possível encerrar a luta");
            }         

            // Definindo o vencedor pelos pontos
            if(this.getPontosLutador1() > getPontosLutador2())
                this.setLutadorVencedor(lutador1);
            else
                this.setLutadorVencedor(lutador2);
                                    
        }
               
        // Marca a luta como encerrada
        this.encerrada = true;
        
        // Marca a luta como não ativa
        this.ativa = false;
        
        // Parando de observar e encerrando o relogio 
        if(this.relogio !=null){
            this.relogio.deleteObserver(this);
            this.relogio.stopRequest();
        }
        
        /* INICIO - Originalmente em LutaControle */
        
        // Encerrando o produtor e o consumidor
        getPontoProdutor().stopRequest();
        getPontoConsumidor().stopRequest();
        
        // Persistindo os pontos que restaram na coleção
        getPontoConsumidor().persitePontos();
        
        // Fechando as referências
        pontoConsumidor = null;
        pontoProdutor = null;
        pontoBuffer = null;    
        
        /* FIM - Originalmente em LutaControle */
        
        // Atualiza está luta no banco de dados
        this.update();
        
        /* Notifica aos observadores apenas se for duplo WO, pois os outros
         casos já foram notificados */
        if(this.woLutador1 && this.isWoLutador2()){
            Luta.notifyObservers();
        }
       
    }

    /** Define o lutador vencedor */
    public void setLutadorVencedor(Lutador lutadorVencedor) throws
            LutaComandoException, DAOException {
        
        // Seta o vencedor
        this.lutadorVencedor = lutadorVencedor;
        
        // Atualizando o vencedor na luta que depende desta luta
        // DAOFactory.getInstance().getLutaDAO().update(this);
        
        /*  Notificando aos observadores */      
        Luta.notifyObservers();                
    }
    
    /***/
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null)
            return false;
        
        if (!(obj instanceof Luta)) 
            return false;        
        
        Luta luta = (Luta) obj;
        
        /* Se o ID do banco for igual é o mesmo objeto
         Se o id for = -1 ainda não foi persistida, não compara */
        if( (this.getId() == luta.getId()) && (this.getId() != -1) )
            return true;
        
        return false;
        
    }

    /***/
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }
    
    /** Salva está luta no BD */
    public void save() throws DAOException{
       this.setId(DAOFactory.getInstance().getLutaDAO().save(this));
       
       Luta.notifyObservers();
       
    }
    
    /** Atualiza esta luta no BD */
    public void update() throws DAOException{
        DAOFactory.getInstance().getLutaDAO().update(this);
    }
    
    /** Apaga está luta do BD */
    public void delete() throws DAOException{
        DAOFactory.getInstance().getLutaDAO().delete(this);
    }
        
    /** Retornas as lutas de um determinado campeonato */
    public static ArrayList<Luta> lutasPorCampeonato(Campeonato campeonato) throws DAOException {
        
        return DAOFactory.getInstance().getLutaDAO().lutasPorCampeonato(campeonato);
        
    }
    
    public static void deleteLutaPorCampeonato(Campeonato campeonato){
        DAOFactory.getInstance().getLutaDAO().deleteLutaPorCampeonato(campeonato);
    }
    
    /** Método chamado pelas classes observadas por está luta - Até agora
     apenas a classe relógio */
    @Override
    public void update(Observable o, Object arg) {
        
        if(o instanceof Relogio){
            this.tempoLuta = (Long) arg;

            // Se atingir o tempo máximo do round, adiciona 
            // um round e pausa a luta
            if(this.tempoLuta == Parametros.getInstance().getDuracaoRound()){
                try {
                    
                    // Zera o relogio
                    this.relogio.zerar();               
                    
                    // Pausa a luta
                    this.pausar();
                                        
                    // Adiciona um round 
                    this.addRound();
                    
                    // Coloca em intervalo
                    this.intervalo = true;
                    
                    // Salva a luta
                    this.update();
                    
                    Luta.notifyObservers();
                    
                } catch (DAOException ex) {
                    throw new RuntimeException("Erro no acesso ao banco de dados");
                } catch (LutaComandoException ex) {
                    util.Util.gravaExcecao(ex, this);
                }
                
            } else {
                /* Notificando os observadores - Dentro do else para evitar 
                 multiplas notificações */                
                Luta.notifyObservers();
                
            }

        }
        
    }

    /** Valida se a luta está ativa*/
    private void validaAtiva() throws LutaComandoException{
        
        if(!this.isAtiva())
            throw new LutaComandoException("A luta não está ativa");        
        
    }
    
    /** Encerra a luta por WO */
    public void encerrarPorWO(boolean woLutador1, boolean woLutador2) throws
            DAOException {
        
        // Ajustando os WOs dos lutadores
        this.woLutador1 = woLutador1;
        this.woLutador2 = woLutador2;
        
        // Setando o lutador 2 como vencedor da luta
        if(this.woLutador1 && !this.woLutador2)
            this.lutadorVencedor = this.lutador2;

        // Setando o lutador 1 como vencedor da luta
        if(this.woLutador2 && !this.woLutador1)
            this.lutadorVencedor = this.lutador1;
        
        if(this.woLutador1 || this.woLutador2){
            // Marca a luta como encerrada
            this.encerrada = true;

            // Marca a luta como não ativa
            this.ativa = false;
            
            // Tirando a pausa
            this.pausada = false;

            // Se existe um relógio ativo, para
            if(this.relogio != null){
                // Parando de observar o relogio
                this.relogio.deleteObserver(this);

                // Encerra o relogio
                this.relogio.stopRequest();
            }
            
            // Atualiza no banco
            this.update();

            // Notificando aos observadores
            Luta.notifyObservers();        

        }
        
    }

    /** Valida se já existe um lutador vencedor*/
    private void validaVencedor() throws LutaComandoException{
        
        if(this.lutadorVencedor != null)
            throw new LutaComandoException("Já existe um lutador vencedor\n" +
                    "precisa resetar a luta");        
        
    }
    
    /**
     * Retorna o objeto referente ao lutador 1
     * @return O objeto referente ao lutador 1
     */
    @OneToOne
    public Lutador getLutador1() {
        return lutador1;
    }
    
    /**
     * Retorna o Número de pontos do lutador 1
     *
     * @return O Número de pontos deste lutador
     */            
    public int getPontosLutador1() {
        return pontosLutador1;
    }

    /**
     * Retorna os kyongos (meia falta) do Lutador 1
     *
     * @return O Número de kyongos recebidos
     */     
    public int getKyongoLutador1() {
        return this.kyongoLutador1;
    }

    /**
     * Retorna os kamtchon (falta) deste lutador
     *
     * @return A quantidade de kamtchons deste lutador
     *
     */      
    public int getKamtchonLutador1() {
        return this.kamtchonLutador1;
    }

    /**
     * Retorna o objeto referente ao lutador 2
     * @return O objeto referente ao lutador 2
     */
    @OneToOne
    public Lutador getLutador2() {
        return lutador2;
    }
    
    /**
     * Retorna o Número de pontos do lutador 2
     * @return O Número de pontos do lutador 2
     */            
    public int getPontosLutador2() {
        return pontosLutador2;
    }
    
    /**
     * Retorna os kyongos (meia falta) do Lutador 2
     * @return O Número de kyongos recebidos
     */     
    public int getKyongoLutador2() {
        return this.kyongoLutador2;
    }
    
    /**
     * Retorna os kamtchon (falta) deste lutador
     * @return A quantidade de kamtchons do lutador 2
     */      
    public int getKamtchonLutador2() {
        return this.kamtchonLutador2;
    }

    /***/
    public Lutador getLutadorVencedor() {
        
        return lutadorVencedor;
    }
    
    /**
     * Retorna o round atual
     * @return  Round atual
     */
    public int getRoundAtual() {
        return roundAtual;
    }

    /***/
    @ManyToOne
    public Campeonato getCampeonato() {
        return campeonato;
    }

    /***/
    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    /***/
    @ManyToOne
    public Categoria getCategoria() {
        return categoria;
    }

    /***/
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /***/
    @OneToOne
    public Luta getLutaAnterior1() {
        return lutaAnterior1;
    }

    /***/
    public void setLutaAnterior1(Luta lutaAnterior1) {
        this.lutaAnterior1 = lutaAnterior1;
    }

    /***/
    @OneToOne
    public Luta getLutaAnterior2() {
        return lutaAnterior2;
    }

    /***/
    public void setLutaAnterior2(Luta lutaAnterior2) {
        this.lutaAnterior2 = lutaAnterior2;
    }

    /***/
    protected void setRodada(int rodada) {
        this.rodada = rodada;
    }
    
    /***/
    public int getRodada() {
        return rodada;
    }
    
    /***/
    public synchronized boolean isEncerrada() {
        return encerrada;
    }
    
    /**
     * Retorna se o sistema está monitorando a luta
     * @return true se estiver monitorando
     */
    public boolean isAtiva() {
        return ativa;
    }
    
    /**
     * Retorna o status da luta (Pausada ou não)
     * @return  true se a luta estiver pausada
     */
    public boolean isPausada() {
        return pausada;
    }
    
    public boolean isGoldenPoint() {
        return goldenPoint;
    }

    /***/
    public int getIdLutaCampeonato() {
        return idLutaCampeonato;
    }

    /***/
    public void setIdLutaCampeonato(int idLutaCampeonato) {
        this.idLutaCampeonato = idLutaCampeonato;
    }

    /***/
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /***/
    public void setId(int id) {
        this.id = id;
    }

    /***/
    public void setLutador1(Lutador lutador1) {
        this.lutador1 = lutador1;
    }

    /***/
    public void setLutador2(Lutador lutador2) {
        this.lutador2 = lutador2;
    }

    /***/
    public boolean isWoLutador1() {
        return woLutador1;
    }

    /***/
    public void setWoLutador1(boolean woLutador1) {
        this.woLutador1 = woLutador1;
    }

    /***/
    public boolean isWoLutador2() {
        return woLutador2;
    }

    /***/
    public void setWoLutador2(boolean woLutador2) {        
        this.woLutador2 = woLutador2;  
    }

    /***/
    public long getTempoLuta() {
        return tempoLuta;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    public boolean isPrimeiroLugar() {
        return primeiroLugar;
    }

    public void setPrimeiroLugar(boolean primeiroLugar) {
        this.primeiroLugar = primeiroLugar;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isIntervalo() {
        return intervalo;
    }

    @Transient
    public PontoConsumidor getPontoConsumidor() {
        return pontoConsumidor;
    }

    @Transient
    public PontoProdutor getPontoProdutor() {
        return pontoProdutor;
    }
        
}

package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import modelo.dao.DAOFactory;
import modelo.excecoes.CampeonatoDadoIncorretoException;
import modelo.excecoes.DAOException;
import modelo.excecoes.LutaDadoIncorretoException;
import util.StaticObservable;

/**
 *
 * @author Renato Pinheiro
 */
@Entity
public class Campeonato extends StaticObservable implements Serializable{
    
    /** Id do objeto no banco.  */
    private int id;
    
    /** Nome do campeonato */
    private String nome;
    
    /** Data do Início do campeonato */
    private Calendar dataInicial;
    
    /** Data do fim do campeonato */
    private Calendar dataFinal;

    /** Categorias deste campeonato */
    private List<Categoria> categorias;

    /** Lutadores inscritos neste campeonato */
    private List<Lutador> lutadoresInscritos;
    
    /** Lutadores que não se enquadram em nenhuma categoria */
    private ArrayList<Lutador> lutadoresNaoCategorizados;
    
    /** Lutadores inscritos classificados por categoria 
     Cada linha da colecao equivale a uma linha da colecao de categorias */
    private ArrayList<ArrayList<Lutador>> lutadoresPorCategoria;
    
    /** Lutadores cabeça de chave */
    private List<Lutador> lutadoresCabecaDeChave;
    
    /** Lutas chaveadas */
    private List<Luta> lutasChaveadas;

    /** Construtor vazio para o JPA */
    public Campeonato() {
        this.inicializaColecoes();
    }
    
    private void inicializaColecoes(){
        /* Inicializando as coleções */
        categorias = new ArrayList<Categoria>(); 
        lutadoresInscritos = new ArrayList<Lutador>();
        lutadoresNaoCategorizados = new ArrayList<Lutador>();
        lutadoresPorCategoria = new ArrayList<ArrayList<Lutador>>();
        lutadoresCabecaDeChave = new ArrayList<Lutador>();
        lutasChaveadas = new ArrayList<Luta>();        
    }
    
    /** Construtor padrão do compeonato */
    public Campeonato(String nome, Calendar dataInicial, Calendar dataFinal) throws DAOException {

        // Inicializando coleções
        this.inicializaColecoes();
        
        this.setNome(nome);
        this.setDataInicial(dataInicial);
        this.setDataFinal(dataFinal);

        this.setId(DAOFactory.getInstance().getCampeonatoDAO().save(this));
        
        Campeonato.notifyObservers();
        
    }
    
    /** Construtor para load do banco */
    public Campeonato(int id, String nome, Calendar dataInicial,
            Calendar dataFinal) {

        // Inicializando coleções
        this.inicializaColecoes();
        
        this.setId(id);
        this.setNome(nome);
        this.setDataInicial(dataInicial);
        this.setDataFinal(dataFinal);
        
    }
    
    /** Construtor para load do banco */
    public Campeonato(int id, String nome, Calendar dataInicial,
            Calendar dataFinal, ArrayList<Categoria> categorias,
            ArrayList<Lutador> lutadoresInscritos, 
            ArrayList<Lutador> lutadoresCabecaDeChave) {

        // Inicializando coleções
        this.inicializaColecoes();
        
        this.id = id;
        this.nome = nome;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.categorias = categorias;
        this.lutadoresInscritos = lutadoresInscritos;
        this.lutadoresCabecaDeChave = lutadoresCabecaDeChave;
        
    }    
    
    /**
     * Recategoriza os lutadores
     *     
     */
    public void recategoriza () {
        
        /** Zerando Lutadores inscritos classificados por categoria 
         Cada linha da colecao equivale é linha da colecao de categorias */
        lutadoresPorCategoria = new ArrayList<ArrayList<Lutador>>();
        
        /** Zerando lutadores não categorizados*/
        lutadoresNaoCategorizados = new ArrayList<Lutador>();
        
        // Criando arraylist para cada categoria
        for(Categoria temp: categorias) {
            
            /* Cria o arraylist para os lutadores categorizados */
            this.lutadoresPorCategoria.add(new ArrayList<Lutador>());

        }
        
        // Percorrendo todos os lutadores
        for(Lutador tmpLutador: lutadoresInscritos){
            
            /* Buscar a categoria deste lutador */
            Categoria categoria = this.getCategoriaLutador(tmpLutador);

            if (categoria != null) {
                /* Se existir uma categoria para o lutador, adiciona na
                 colecao de lutadores categorizados */            
                lutadoresPorCategoria.get(categorias.indexOf(categoria)).add(tmpLutador);

            } else {
                /* Se não existir, adiciona é relação de lutadores não 
                 categorizados */
                this.lutadoresNaoCategorizados.add(tmpLutador);
            }

        }            
        
    }
    
    /**
     *
     *  Faz o chaveamento dos lutadores inscritos
     *
     */
    public void chaveamento() throws LutaDadoIncorretoException,
            CampeonatoDadoIncorretoException, DAOException {
        
        /* Menos de dois lutadores */
        if(this.lutadoresInscritos.size() < 2)
            throw new CampeonatoDadoIncorretoException("Não existem " +
                    "lutadores suficientes para o chaveamento");
        
        /* Sem pelo menos 1 catoria*/
        if(this.categorias.size() == 0)
            throw new CampeonatoDadoIncorretoException("Pelo menos" +
                    "uma categoria é necessária para o chaveamento");
        
        /* Recategorizando */
        this.recategoriza();        
        
        /* Todos os lutadores inscritos não foram categorizados */
        if(this.lutadoresInscritos.size() == 
                this.lutadoresNaoCategorizados.size())
            throw new CampeonatoDadoIncorretoException("Nenhum lutador foi " +
                    "categorizado");
        
        // Zerando o chaveamento (inclusive no BD)
        this.zeraChaveamento();
                
        /* Faz o chaveamento para cada categoria */
        for(Categoria tmpCategoria: categorias){
            
            /* Pega a colecao com os lutadores da categoria atual */
            ArrayList<Lutador> lutadoresCategoria = lutadoresPorCategoria.get(categorias.indexOf(tmpCategoria));
            
            /* Se não existe nenhum lutador para esta categoria, passa para a
             próxima*/
            if (lutadoresCategoria.size() <= 0)
                continue;
            
            /* Numero de baias */
            int numeroDeBaias = retornaNumeroBaias(lutadoresCategoria.size());            
            
            /* Se Só houver um lutador nesta categoria, cria uma luta e seta
             a mesma como W.O. */
            if (lutadoresCategoria.size()==1){
                    lutasChaveadas.add(new Luta( this, tmpCategoria, 1,
                            lutadoresCategoria.get(0), true));
                    continue;
            }              
            
            /* Inicio - Calculos do Número de lutas por rodada */
            /* Lutas na primeira rodada */
            int numeroLutasRodada1 = (lutadoresCategoria.size() - 
                    numeroDeBaias) / 2;
            
            /* Lutas na segunda rodada */
            int numeroLutasRodada2 = numeroLutasPossiveis(
                    lutadoresCategoria.size()) - numeroLutasRodada1;
            
            if(numeroLutasRodada1 % 2 != 0) {
                numeroLutasRodada2 += (numeroLutasRodada1 - 1) / 2;
            } else {
                numeroLutasRodada2 += numeroLutasRodada1 / 2;
            }
            
            /* Lutas na primeira rodada seguinte */
            int numeroLutasRodadaSeguinte = numeroLutasRodada2 / 2;
            
            /* Fim - Calculos do Número de lutas por rodada */
            
            /* Gerador dos Números randômicos para o sorteio */
            Random randomico = new Random();
            
            /* Vetor dos lutadores para o sorteio, cópia do arraylist
             lutadoresCategoria - Usando vetor pelo removeElementAt */
            Vector<Lutador> lutadoresSorteio = new 
                    Vector<Lutador>(lutadoresCategoria);
            
            /* Encontrando lutador cabeça de chave*/
            Lutador lutadorCabecaDeChave = this.getCabecaChavePorCategoria(
                    tmpCategoria);
            
            /* Se existem baias e se existir um cabeça de chave para a 
             categoria atual, o remove do sorteio */
            if((numeroDeBaias > 0) && ( lutadorCabecaDeChave != null )){
                
                lutadoresSorteio.removeElement(lutadorCabecaDeChave);
                
            }            
            
            /* Lutas desta categoria */
            ArrayList<Luta> lutasCategoria = new ArrayList<Luta>();
            
            /*  Marcador de rodada*/
            int rodadaAtual = 1;
            
            /* Lutas na rodada atual */
            int lutasRodadaAtual = 0;
            
            /* Indice para controle das lutas já utilizadas para compor novas
             lutas*/
            int lutaUtilizada = 0;

            /* Criando todas as lutas posSóveis para a categoria atual */
            /* lutas possiveis = lutadores da categoria - 1 */
            while( lutasCategoria.size() < lutadoresCategoria.size() - 1){
               
                // Verifica se é a última luta da categoria
                boolean primeiroLugar = (
                        lutasCategoria.size() + 1 == 
                        lutadoresCategoria.size() - 1 ? true:false);                
                
                /* Inicio - Controle de rodadas */
                
                /* 1ª rodada */
                if(rodadaAtual == 1){
                    if(lutasRodadaAtual >= numeroLutasRodada1){
                        rodadaAtual = 2;
                        lutasRodadaAtual = 0;
                    } else {
                        lutasRodadaAtual +=1;
                    }
                }
                
                /* 2ª rodada */
                if(rodadaAtual == 2){
                    if(lutasRodadaAtual >= numeroLutasRodada2){
                        rodadaAtual = 3;
                        lutasRodadaAtual = 0;
                    } else {
                        lutasRodadaAtual +=1;
                    }
                }

                /* Outras rodada */
                if(rodadaAtual > 2){
                    if(lutasRodadaAtual >= numeroLutasRodadaSeguinte){
                        rodadaAtual += 1;
                        lutasRodadaAtual = 0;
                        numeroLutasRodadaSeguinte = 
                                numeroLutasRodadaSeguinte / 2;
                    } else {
                        lutasRodadaAtual +=1;
                    }                    
                }
                
                /* Fim - Controle de rodadas */

                /* Lutadores para o sorteio*/
                Lutador lutador1 = null, lutador2 = null;
                
                /* Sorteio do lutador 1 */
                if(lutadoresSorteio.size() > 0){
                    
                    /* Pega o próximo inteiro até o tamanho do vetor, 
                     exclusivo */
                    int sorteado = randomico.nextInt(lutadoresSorteio.size());
                    
                    /* Pegando o lutador sorteado */
                    lutador1 = lutadoresSorteio.elementAt(sorteado);

                    /* Removendo o lutador sorteado */
                    lutadoresSorteio.removeElementAt(sorteado);
                    
                }
                    
                /* Sorteio do lutador 2 */
                if(lutadoresSorteio.size() > 0){
                    
                    /* Pega o próximo inteiro até o tamanho do vetor, 
                     exclusivo */
                    int sorteado = randomico.nextInt(lutadoresSorteio.size());
                    
                    /* Pegando o lutador sorteado */
                    lutador2 = lutadoresSorteio.elementAt(sorteado);
                    
                    /* Removendo o lutador sorteado */
                    lutadoresSorteio.removeElementAt(sorteado);
                    
                }
                
                /* Criando a luta para dois lutadores conhecidos */
                if (lutador1 != null && lutador2 != null){

                    lutasCategoria.add(
                            new Luta( this, tmpCategoria, rodadaAtual, lutador1, 
                            lutador2, primeiroLugar));

                }

                /* Criando a luta para um lutador conhecido e uma luta 
                 anterior */
                if (lutador1 != null && lutador2 == null){
                    
                    /* Se existir um cabeça de chave, re-insere o lutador1
                     na colecao para sorteio e usa o cabeça de chave para a
                     primeira baia */
                    if(lutadorCabecaDeChave != null){
                        lutadoresSorteio.add(lutador1);
                        lutador1 = lutadorCabecaDeChave;
                        lutadorCabecaDeChave = null;
                    }
                    
                    lutasCategoria.add(
                            new Luta( this, tmpCategoria, rodadaAtual, 
                            lutador1, lutasCategoria.get(lutaUtilizada++),
                            primeiroLugar));
                    
                }
                
                /* Criando a luta para dois lutadores desconhecidos */
                if (lutador1 == null && lutador2 == null){

                    /* Se existir um cabeça de chave usa para criar a próxima
                     luta, do contrario usa duas lutas */
                    if(lutadorCabecaDeChave != null){
                        lutasCategoria.add(
                                new Luta( this, tmpCategoria, rodadaAtual,
                                lutadorCabecaDeChave, 
                                lutasCategoria.get(lutaUtilizada++),
                                primeiroLugar));
                        lutadorCabecaDeChave = null;                  
                    } else {
                        lutasCategoria.add(
                                new Luta( this, tmpCategoria, rodadaAtual,
                                lutasCategoria.get(lutaUtilizada++), 
                                lutasCategoria.get(lutaUtilizada++), 
                                primeiroLugar));
                        
                    }
                    
                    
                }                
                
            }
                       
            /* Adicionando as lutas desta categoria nas lutas chaveadas */
            lutasChaveadas.addAll(lutasCategoria);
            
        }
        
        /* Ordenando e setando o ID das lutas */
        
        int idNumeroLutaGeral = 1;
        int indice = 0;
        Categoria categoriaAtual = null;
        boolean maisDeUmaCategoria = false;
        
        Vector<Luta> lutas = new Vector<Luta>(lutasChaveadas);
        
        while (lutas.size() > 0) {
            
           if(!(categoriaAtual == lutas.elementAt(indice).getCategoria())) {
               lutas.elementAt(indice).setIdLutaCampeonato(idNumeroLutaGeral++);
               categoriaAtual = lutas.elementAt(indice).getCategoria();
               lutas.removeElementAt(indice);
               indice--;
               maisDeUmaCategoria = true;
           }
           
           if(!maisDeUmaCategoria) {
               lutas.elementAt(indice).setIdLutaCampeonato(idNumeroLutaGeral++);
               lutas.removeElementAt(indice);
               indice--;
           }           
           
           if(indice == lutas.size() - 1) {
               indice = 0;
               maisDeUmaCategoria = false;
           } else {
               indice++;
           }
            
        }
        
        DAOFactory.getInstance().getCampeonatoDAO().update(this);
        
        Campeonato.notifyObservers();
                
    }

    /**
     * Retorna o Número de baias de acordo com a quantidade de inscritos     
     */
    private int retornaNumeroBaias(int numeroInscritos){
        
        int numeroMagico = 1; /* não achei nome melhor =] */
        
        while(numeroMagico < numeroInscritos){
            
            numeroMagico = numeroMagico * 2;           
            
        }

        return numeroMagico - numeroInscritos;
    }
    
    /** 
     *  Retorna o número de lutas possíveis dado o número de inscritos no
     * campeonato
     */
    private int numeroLutasPossiveis(int numeroInscritos){
        
        if( numeroInscritos % 2 != 0){
            return (numeroInscritos + 1) / 2;
        } else {
            return numeroInscritos / 2;
        }
        
    }
    
    /**
     * Adiciona categorias ao campeonado     
     */
    public void addCategoria(Categoria categoria) throws
            CampeonatoDadoIncorretoException, DAOException {
        
        /* Verificando se a categoria conhecide com outra no campeonato */
        for(Categoria tmpCategoria:categorias) {
            
            if (tmpCategoria.equals(categoria)){
                throw new CampeonatoDadoIncorretoException("Esta categoria " +
                        "conhecide com outra já cadastrada");
            }
            
        }
        
        /* Adiciona a categoria ao campeonato */
        this.categorias.add(categoria);
        
        /* Cria o arraylist para os lutadores categorizados */
        this.lutadoresPorCategoria.add(new ArrayList<Lutador>());
        
        /* Recategoriza */
        this.recategoriza();
        
        // Zerando o chaveamento (inclusive no BD)
        this.zeraChaveamento();

        // Atualizando campeonato
        DAOFactory.getInstance().getCampeonatoDAO().update(this);
        
        /* Notificando aos observadores */
        Campeonato.notifyObservers();
        
    }
    
    /**
     * Remove categorias do campeonato     
     */
    public void delCategoria(Categoria categoria) throws
            CampeonatoDadoIncorretoException, DAOException {
        
        boolean encontrou = false;
        
        // Verificando se a categoria existe neste campeonato
        for(Categoria tmpCategoria:categorias) {
            
            if (tmpCategoria.equals(categoria)){
                encontrou = true;
                break;
            }
            
        }
        
        // Se não encontrou lança exceção
        if (!encontrou){
            throw new CampeonatoDadoIncorretoException("Esta categoria " +
                    "não está cadastrada");
        }
        
        /* Verificando se existe algum cabeça de chave para está categoria */
        for(Lutador lutador: lutadoresCabecaDeChave){
            
            /* Se ol utador não tiver categoria, salta para próxima interação */
            if(this.getCategoriaLutador(lutador) == null)
                continue;
            
            if(this.getCategoriaLutador(lutador).equals(categoria)){
                this.deleteCabecaDeChave(lutador);
                break;
            }
            
        }        
        
        /* Remove a categoria ao campeonato */
        this.categorias.remove(categoria);
        
        /* Recategoriza */
        this.recategoriza();
        
        // Zerando o chaveamento (inclusive no BD)
        this.zeraChaveamento();
        
        // Atualizando campeonato
        DAOFactory.getInstance().getCampeonatoDAO().update(this);        
        
        /* Notificando aos observadores */
        Campeonato.notifyObservers();
        
    }

    /**
     * Inscrive um lutador no campeonato
     */
    public void addLutadorInscrito(Lutador lutador) throws
            CampeonatoDadoIncorretoException, DAOException {
        
        /* Verificando se o lutador já está inscrito */
        for(Lutador tmpLutador: lutadoresInscritos){
            
            if(lutador.equals(tmpLutador))
                throw new CampeonatoDadoIncorretoException("Este lutador já " +
                        "está inscrito");
            
        }
        
        /* Adiciona o lutador na colecao de lutadores inscritos*/
        lutadoresInscritos.add(lutador);        
        
        /* Buscar a categoria deste lutador */
        Categoria categoria = this.getCategoriaLutador(lutador);

        /* Se existir uma categoria para o lutador, adiciona na colecao de 
         lutadores categorizados. Se não existir, adiciona é relação de 
         lutadores não categorizados */        
        if (categoria != null) {            
            lutadoresPorCategoria.get(categorias.indexOf(categoria)).add(lutador);            
        } else {
            this.lutadoresNaoCategorizados.add(lutador);
        }
        
        /* Recategoriza */
        this.recategoriza();
        
        // Zerando o chaveamento (inclusive no BD)
        this.zeraChaveamento();       
        
        // Atualizando campeonato
        DAOFactory.getInstance().getCampeonatoDAO().update(this);
        
        /* Notificando aos observadores */
        Campeonato.notifyObservers();
        
    }
    
    /**
     * Retirando um lutador do campeonato
     */
    public void delLutadorInscrito(Lutador lutador) throws
            CampeonatoDadoIncorretoException, DAOException {

        boolean encontrou = false;
        
        // Verificando se o lutador está inscrito
        for(Lutador tmpLutador: lutadoresInscritos){
            
            if(lutador.equals(tmpLutador)) {
                encontrou = true;
                break;
            }
            
        }        
        
        // Se não está inscrito, lança exceção
        if (!encontrou){
            throw new CampeonatoDadoIncorretoException("Este lutador não " +
                        "está inscrito");            
        }
        
        // Removendo da coleção
        lutadoresInscritos.remove(lutador);
        
        /* Buscar a categoria deste lutador */
        Categoria categoria = this.getCategoriaLutador(lutador);
        
        if (categoria != null) {
            /* Se existir uma categoria para o lutador, adiciona na colecao
             de lutadores categorizados */            
            lutadoresPorCategoria.get(categorias.indexOf(categoria)).remove(lutador);
            
        } else {
            /* Se não existir, adiciona é relação de lutadores não categorizados 
             */
            this.lutadoresNaoCategorizados.remove(lutador);
        }
        
        /* Recategoriza */
        this.recategoriza();
        
        // Zerando o chaveamento (inclusive no BD)
        this.zeraChaveamento();        
        
        // Atualizando campeonato
        DAOFactory.getInstance().getCampeonatoDAO().update(this);        
        
        /* Notificando aos observadores */
        Campeonato.notifyObservers();
        
    }    

    /**
     * Adicionando lutador cabeça de chave
     */
    public void addCabecaDeChave(Lutador lutador)
            throws CampeonatoDadoIncorretoException, DAOException{
        
        /* Verificando se o lutador já é cabeça de chave */
        for(Lutador tmpLutador: lutadoresCabecaDeChave){
            
            if(lutador.equals(tmpLutador))
                throw new CampeonatoDadoIncorretoException("Este lutador já " +
                        "é cabeça de chave");
            
        }
       
        // Buscando a categoria do lutador
        Categoria categoria = this.getCategoriaLutador(lutador);
        
        if(!categorias.contains(categoria)){
            throw new CampeonatoDadoIncorretoException("A categoria deste " +
                    "lutador não está cadastrada neste campeonato");
        }
        
        for(Lutador lutadorAtual: lutadoresCabecaDeChave){
            
            /* Verificando se o lutador atual (não o cabeça de chave) tem 
             categoria). Se não tiver, salta está interação  */
            if(this.getCategoriaLutador(lutadorAtual) == null)
                continue;
            
            if(this.getCategoriaLutador(lutadorAtual).equals(categoria))
                throw new CampeonatoDadoIncorretoException("Já existe um " +
                        "cabeça de chave para está categoria");
            
        }
        
        // Adicionando à coleção
        lutadoresCabecaDeChave.add(lutador);
        
        // Zerando o chaveamento (inclusive no BD)
        this.zeraChaveamento();

        // Atualizando campeonato
        DAOFactory.getInstance().getCampeonatoDAO().update(this);        
        
        // Notificando aos observadores
        Campeonato.notifyObservers();
        
    }

    /**
     * Removendo lutador cabeça de chave
     */    
    public void deleteCabecaDeChave(Lutador lutador)
            throws CampeonatoDadoIncorretoException, DAOException{
        
        boolean encontrado = false;
        
        // Verificando se o lutador é um cabeça de chave
        for(Lutador lutadorAtual: lutadoresCabecaDeChave){
            
            if(lutadorAtual.equals(lutador)){
                encontrado = true;
                break;
            }
            
        }
        
        // Caso seja, remove (Da coleção e do BD), do contrário levanta exceção
        if(encontrado){
            lutadoresCabecaDeChave.remove(lutador);
                        
        } else {
            throw new CampeonatoDadoIncorretoException("Lutador não " +
                    "encontrado");
        }
        
        // Zerando o chaveamento (inclusive no BD)
        this.zeraChaveamento();
        
        // Atualizando campeonato
        DAOFactory.getInstance().getCampeonatoDAO().update(this);
        
        // Notificando os observadores
        Campeonato.notifyObservers();
        
    }
    
    /** 
     * Retorna a categoria de um determinado lutador 
     */
    private Categoria getCategoriaLutador(Lutador lutador) {
       
        // Calculando a idade
        int idade = (Calendar.getInstance().get(Calendar.YEAR) - 
                lutador.getAnoNasc());
        
        // Percorrendo as categorias
        for(Categoria categoriaAtual: categorias){
            
            /* Sexo */
            if( !lutador.getSexo().equals(categoriaAtual.getSexo()))
                continue;
            
            /* Peso */
            if( lutador.getPeso() > categoriaAtual.getPesoMaximo() ||
                    lutador.getPeso() < categoriaAtual.getPesoMinimo())
                continue;
            
            /* Idade */
            if( idade > categoriaAtual.getIdadeMaxima() ||
                    idade < categoriaAtual.getIdadeMinima())
                continue;
            
            /* Graduação */
            if( lutador.getGraduacao() < categoriaAtual.getGraduacaoMaxima() ||
                    lutador.getGraduacao() > categoriaAtual.getGraduacaoMinima())
                continue;
            
            /* Se não se enquadra em nenhuma das condições acima */
            return categoriaAtual;
            
        }
        
        return null;
        
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null)
            return false;
        
        if (!(obj instanceof Campeonato)) 
            return false;
        
        Campeonato campeonato = (Campeonato) obj;
          
        /* Se o ID do banco for igual é o mesmo objeto
         Se o id for = -1 ainda não foi persistida, não compara */
        if( (this.getId() == campeonato.getId()) && (this.getId() != -1) )
            return true;        
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }
    
    /**
     * Setando os lutadores inscritos
     * 
     * Obs.: Utilizado para a carga do banco de dados
     */
    public void setLutadoresInscritos(List<Lutador> lutadores) {
        
        this.lutadoresInscritos = new ArrayList<Lutador>(lutadores);
        
        /* Notificando aos observadores */
        //Campeonato.notifyObservers();        
        
    }    

    @ManyToMany
    public List<Lutador> getLutadoresInscritos() {
        return lutadoresInscritos;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Calendar dataInicial) {
        this.dataInicial = dataInicial;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Calendar dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @ManyToMany
    public List<Categoria> getCategorias() {
        return categorias;
    }
    
    @Transient
    public ArrayList<ArrayList<Lutador>> getLutadoresPorCategoria() {
        return lutadoresPorCategoria;
    }

    @Transient
    public ArrayList<Lutador> getLutadoresNaoCategorizados() {
        return lutadoresNaoCategorizados;
    }    

    public void setLutasChaveadas(List<Luta> lutas) {
        this.lutasChaveadas = lutas;
    }
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "campeonato")
    public List<Luta> getLutasChaveadas() {
        return lutasChaveadas;
    }
    
    public void delete() throws DAOException{
        DAOFactory.getInstance().getCampeonatoDAO().delete(this);
        
        Campeonato.notifyObservers();
    }
    
    public void update() throws DAOException{
        DAOFactory.getInstance().getCampeonatoDAO().update(this);
        
        Campeonato.notifyObservers();
    }
    
    public static ArrayList listCampeonatos() throws DAOException {
        
        return DAOFactory.getInstance().getCampeonatoDAO().listCampeonatos();
        
    }
    
    public static ArrayList listCampeonatosPorNome(String nome) throws DAOException, CampeonatoDadoIncorretoException {
        
        if(nome.equals("")){
            throw new CampeonatoDadoIncorretoException("O nome do campeonato " +
                    "não pode estar em branco");
        }
        
        return DAOFactory.getInstance().getCampeonatoDAO().listCampeonatoPorNome(nome);
        
    }
    
    public static Campeonato getCampeonatoPorNome(String nome) throws
            CampeonatoDadoIncorretoException, DAOException {
        
        if(nome.equals("")){
            throw new CampeonatoDadoIncorretoException("O nome da campeonato " +
                    "não pode estar em branco");
        }
        
        return DAOFactory.getInstance().getCampeonatoDAO().getCampeonatoPorNome(nome);
        
    }
    
    public static Campeonato getCampeonatoPorId(int id) throws DAOException {
        
        return DAOFactory.getInstance().getCampeonatoDAO().getCampeonatoPorId(id);
        
    }
    
    public static void addCampeonatoObserver(Observer o){
        
        Campeonato.addObserver(o);
        
    }
    
    public static void delCampeonatoObserver(Observer o){
        
        Campeonato.deleteObserver(o);
        
    }      

    @ManyToMany(mappedBy = "campeonatosCabecaDeChave")
    public List<Lutador> getLutadoresCabecaDeChave() {
        return lutadoresCabecaDeChave;
    }

    public void setLutadoresCabecaDeChave(List<Lutador> lutadoresCabecaDeChave) {
        this.lutadoresCabecaDeChave = lutadoresCabecaDeChave;
    }
    
    /**
     * Retorna um cabeça de chave de acordo com a categoria
     */
    private Lutador getCabecaChavePorCategoria(Categoria categoria) {
        
        Lutador lutador = null;
        
        // Buscando na coleção de cabeças de chave
        for(Lutador lutadorAtual: lutadoresCabecaDeChave){

            /* Verificando se o lutador atual (não o cabeça de chave tem 
             categoria). Se não tiver, salta está interação  */
            if(this.getCategoriaLutador(lutadorAtual) == null)
                continue;            
            
            if(this.getCategoriaLutador(lutadorAtual).equals(categoria)){
                lutador = lutadorAtual;
                break;
            }
            
        }
        
        return lutador;
        
    }
    
    /** Informa se o campeonato já foi iniciado */
    public boolean isIniciado(){
        
        for(Luta luta: lutasChaveadas){
            
            if(luta.isAtiva())
                return true;
                
            if(luta.isEncerrada())
                return true;
            
        }
        
        return false;
        
    }
    
    /** Informa se lutas já foram chaveadas */
    public boolean isChaveado(){
        
        return (this.lutasChaveadas.size() > 0);
        
    }
    
    private void zeraChaveamento() throws DAOException {
        
        /* Zerando lutas chaveadas */
        
        // Apagando as lutas 
        Luta.deleteLutaPorCampeonato(this);

        this.lutasChaveadas = new ArrayList<Luta>();
        this.update();
        
    }
             
}
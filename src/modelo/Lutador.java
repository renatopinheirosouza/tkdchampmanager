package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import modelo.excecoes.DAOException;
import modelo.dao.DAOFactory;
import modelo.excecoes.EquipeDadoIncorretoException;
import modelo.excecoes.LutadorDadoIncorretoException;
import util.StaticObservable;

/**
 * Classe que define o lutador
 */
@Entity
public class Lutador extends StaticObservable implements Serializable {
    
    /** Id do lutador no banco de dados - mantido pelo JPA */
    private int id = -1;
    
    /** Nome */
    private String nome;
    
    /** Equipe */
    private Equipe equipe;
    
    /** Graduação, positivo = gub, negavito = dan */ 
    private int graduacao; 
    
    /** Peso */
    private double peso;
    
    /** Ano de nascimento */
    private int anoNasc;
    
    /** Sexo M ou F*/
    private String sexo;
    
    /** Documento de identificação */
    private String documento;
    
    /** Endereço de email */
    private String email;
    
    /** Campeonatos que participou */
    private List<Campeonato> campeonatos;
    
    /** Capeonatos onde foi cabeça de chave */
    private List<Campeonato> campeonatosCabecaDeChave;
    
    /** Construtor vazio para o JPA*/
    public Lutador() {
    }
    
    /**
     * Construtor do lutador
     */
    public Lutador(String nome, Equipe equipe, int graduacao, double peso, 
            int anoNasc, String documento, String email, String sexo) throws
            LutadorDadoIncorretoException, DAOException,
            EquipeDadoIncorretoException {
        
        this.setNome(nome);
        this.setEquipe(equipe);
        this.setGraduacao(graduacao);
        this.setPeso(peso);
        this.setAnoNasc(anoNasc);
        this.setDocumento(documento);
        this.setEmail(email);
        this.setSexo(sexo);

        this.setId(DAOFactory.getInstance().getLutadorDAO().save(this));
        
        notifyObservers();
        
    }

    /**
     * Construtor do lutador, usado para load do banco
     */
    public Lutador(int id, String nome, Equipe equipe, int graduacao, 
            double peso, int anoNasc, String documento, String email, String sexo)
            throws LutadorDadoIncorretoException{
        
        this.nome = nome;
        this.equipe = equipe;
        this.graduacao = graduacao;
        this.peso = peso;
        this.anoNasc = anoNasc;
        this.documento = documento;
        this.email = email;
        this.sexo = sexo;
        this.id = id;
        
    }    
    
    /**
     * Retorna o nome do lutador
     *
     * @return O nome do lutador
     */
    public String getNome() {
        return nome;
    }

    /**
     * Seta o o nome do lutador
     *
     */
    public void setNome(String nome) throws LutadorDadoIncorretoException {
        
        if(nome.equals("")){
            throw new LutadorDadoIncorretoException("O nome do lutador " +
                    "precisar ser preenchido");
        }
        
        this.nome = nome;
    }

    /**
     * Retorna o nome da equipe deste lutador
     *
     * @return A equipe deste lutador
     */    
    @ManyToOne
    public Equipe getEquipe() {
        return equipe;
    }

    /**
     * Seta o nome da equipe deste lutador
     * 
     * @param equipe o nome da equipe deste lutador
     * 
     */    
    public void setEquipe(Equipe equipe){
        this.equipe = equipe;
    }

    public int getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(int graduacao) throws
            LutadorDadoIncorretoException {
        
        if (graduacao == 0) {
            throw new LutadorDadoIncorretoException("� necesSório informar a " +
                    "Graduação do lutador");
        }
        
        if (graduacao < -10 || graduacao > 10) {
            throw new LutadorDadoIncorretoException("A gradu��o não pode ser " +
                    "menor que 10� gub ou maior que 10� dan");
        }
        
        this.graduacao = graduacao;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) throws LutadorDadoIncorretoException {
        
        if(peso <= 0){
            throw new LutadorDadoIncorretoException("O peso do lutador não " +
                    "pode ser menor ou igual a zero");
        }
               
        this.peso = peso;
    }

    public int getAnoNasc() {
        return anoNasc;
    }

    public void setAnoNasc(int anoNasc) throws LutadorDadoIncorretoException {
        
        if(anoNasc<=0){
            throw new LutadorDadoIncorretoException("O ano de nascimento do" +
                    "não pode ser igual ou menor que zero");
        }
        
        this.anoNasc = anoNasc;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) throws
            LutadorDadoIncorretoException, DAOException,
            EquipeDadoIncorretoException {
        
        if(documento.length() > 20)
            documento = documento.substring(0,20);
        
        if(documento.equals(""))
            throw new LutadorDadoIncorretoException("O documento precisa ser " +
                    "preenchido");

        /*Lutador lutador = DAOFactory.getInstance().getLutadorDAO().getLutadorPorDocumento(documento);
        
        // Se o lutador não é null e não é este (this) lutador
        if((lutador != null) && 
                (!lutador.getDocumento().equals(this.getDocumento())))
            throw new LutadorDadoIncorretoException("Já existe um lutador com" +
                    " este documento");            */
        
        this.documento = documento;
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws LutadorDadoIncorretoException {
        
        if(!util.Util.emailValido(email))
            throw new LutadorDadoIncorretoException("O email precisa ser " +
                    "preenchido corretamente");
        
        this.email = email;
    }

    public String getSexo() {
        return this.sexo.substring(0,1);
    }

    public void setSexo(String sexo) throws LutadorDadoIncorretoException {
        
        sexo = sexo.substring(0,1);
        
        if(sexo.equals("") || 
                (!sexo.equalsIgnoreCase("F") && !sexo.equalsIgnoreCase("M"))){
            throw new LutadorDadoIncorretoException("O sexo do lutador deve " +
                    "ser Masculino ou Feminio");
        }
        
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object obj) {
        
        if(obj == null)
            return false;
        
        if(!(obj instanceof Lutador) )
            return false;
        
        Lutador lutador = (Lutador) obj;
        
        /* Se o ID do banco for igual é o mesmo objeto
         Se o id for = -1 ainda não foi persistida, não compara */
        if( (this.getId() == lutador.getId()) && (this.getId() != -1) )
            return true;
        
        if (this.nome.equals(lutador.getNome()) && 
                this.equipe.equals(lutador.getEquipe()) && 
                this.graduacao == lutador.getGraduacao() &&
                this.peso == lutador.getPeso() && 
                this.anoNasc == lutador.getAnoNasc() && 
                this.sexo.equalsIgnoreCase(lutador.getSexo()) &&
                this.documento.equals(lutador.getDocumento()) &&
                this.email.equals(lutador.getEmail())) {
                    return true;
                }
        
        return false;
        
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 29 * hash + (this.equipe != null ? this.equipe.hashCode() : 0);
        hash = 29 * hash + this.graduacao;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.peso) ^ (Double.doubleToLongBits(this.peso) >>> 32));
        hash = 29 * hash + this.anoNasc;
        hash = 29 * hash + (this.sexo != null ? this.sexo.hashCode() : 0);
        hash = 29 * hash + (this.documento != null ? this.documento.hashCode() : 0);
        hash = 29 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }
    
    public void delete() throws DAOException {
        
        DAOFactory.getInstance().getLutadorDAO().delete(this);
        
        Lutador.notifyObservers();        
        
    }

    public void update() throws DAOException {
        
        DAOFactory.getInstance().getLutadorDAO().update(this);
        
        notifyObservers();        
        
    }
    
    public void update(int id, String nome, String nomeEquipe, 
            int graduacao, double peso, int anoNasc, String documento, 
            String email, String sexo) throws
            LutadorDadoIncorretoException, DAOException,
            EquipeDadoIncorretoException {

        this.setNome(nome);
        this.setEquipe(equipe);
        this.setGraduacao(graduacao);
        this.setPeso(peso);
        this.setAnoNasc(anoNasc);
        this.setDocumento(documento);
        this.setEmail(email);
        this.setSexo(sexo);
        this.setId(id);
        
        DAOFactory.getInstance().getLutadorDAO().update(this);
        
        Lutador.notifyObservers();        
        
    }

    public static ArrayList getLutadores() throws
            DAOException, LutadorDadoIncorretoException,
            EquipeDadoIncorretoException {
        
        return DAOFactory.getInstance().getLutadorDAO().listLutadores();
        
    }
    
    public static ArrayList getLutadoresPorNome(String nome) throws
            DAOException, LutadorDadoIncorretoException,
            EquipeDadoIncorretoException {
        
        return DAOFactory.getInstance().getLutadorDAO().getLutadoresPorNome(nome);
        
    }    
    
    public static Lutador getLutadoresPorId(int id) throws
            DAOException, LutadorDadoIncorretoException,
            EquipeDadoIncorretoException {
        
        return DAOFactory.getInstance().getLutadorDAO().getLutadorPorId(id);
        
    }
    
    public static void addLutadorObserver(Observer o){
        
        Lutador.addObserver(o);
        
    }
    
    public static void delLutadorObserver(Observer o){
        
        Lutador.deleteObserver(o);
        
    }

    @ManyToMany(mappedBy = "lutadoresInscritos")
    public List<Campeonato> getCampeonatos() {
        return campeonatos;
    }

    public void setCampeonatos(List<Campeonato> campeonatos) {
        this.campeonatos = campeonatos;
    }

    @ManyToMany
    public List<Campeonato> getCampeonatosCabecaDeChave() {
        return campeonatosCabecaDeChave;
    }

    public void setCampeonatosCabecaDeChave(List<Campeonato> campeonatosCabecaDeChave) {
        this.campeonatosCabecaDeChave = campeonatosCabecaDeChave;
    }

}

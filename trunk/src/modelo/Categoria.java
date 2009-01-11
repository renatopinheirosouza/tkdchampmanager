package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observer;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import modelo.dao.DAOFactory;
import modelo.excecoes.CategoriaDadoIncorretoException;
import modelo.excecoes.DAOException;
import util.StaticObservable;

/**
 *
 * @author Renato Pinheiro
 */
@Entity
public class Categoria extends StaticObservable implements Serializable {
        
    /** Id do objeto no banco. */
    private int id;
    
    /** Nome */
    private String nome;
    
    /** Peso mínimo do lutador */
    private double pesoMinimo;
    
    /** Peso máximo do lutador */
    private double pesoMaximo;
    
    /** Idade mínima do lutador */
    private int idadeMinima;
    
    /** Idade máxima do lutador */
    private int idadeMaxima;
    
    /** Graduação minima do lutador */
    private int graduacaoMinima;
    
    /** Graduação máxima do lutador */
    private int graduacaoMaxima;
    
    /** Sexo do lutador */
    private String sexo;
    
    /** Número de rounds das lutas desta categoria */
    private int numRounds;
    
    /** Campeonatos utilizando esta categoria */
    private Collection<Campeonato> campeonatos;
    
    /** Lutas desta categoria */
    private List<Luta> lutas;

    /** Construtor para o JPA */
    public Categoria() {
    }
    
    public Categoria(String nome, double pesoMinimo, double pesoMaximo, 
            int idadeMinima, int idadeMaxima, int graduacaoMinima,
            int graduacaoMaxima, String sexo, int numRounds) throws
            CategoriaDadoIncorretoException, DAOException {
        
        if (pesoMinimo >= pesoMaximo)
            throw new CategoriaDadoIncorretoException("O peso mínimo não " +
                    "pode ser maior ou igual ao peso máximo");

        if (idadeMinima >= idadeMaxima)
            throw new CategoriaDadoIncorretoException("A idade mínima não " +
                    "pode ser maior ou igual é idade máxima");
        
        if (graduacaoMinima <= graduacaoMaxima)
            throw new CategoriaDadoIncorretoException("A Graduação mínima " +
                    "não pode ser maior ou igual é Graduação máxima");
        
        this.nome = nome;
        this.setPesoMinimo(pesoMinimo);                
        this.setPesoMaximo(pesoMaximo);
        this.setIdadeMinima(idadeMinima);
        this.setIdadeMaxima(idadeMaxima);
        this.setGraduacaoMinima(graduacaoMinima);
        this.setGraduacaoMaxima(graduacaoMaxima);
        this.setSexo(sexo);
        this.setNumRounds(numRounds);
        
        this.setId(DAOFactory.getInstance().getCategoriaDAO().save(this));        
       
        Categoria.notifyObservers();
    }
    
    public Categoria(int id, String nome, double pesoMinimo, double pesoMaximo, 
            int idadeMinima, int idadeMaxima, int graduacaoMinima,
            int graduacaoMaxima, String sexo, int numRounds) throws
            CategoriaDadoIncorretoException {
        
        this.nome = nome;
        this.setPesoMinimo(pesoMinimo);                
        this.setPesoMaximo(pesoMaximo);
        this.setIdadeMinima(idadeMinima);
        this.setIdadeMaxima(idadeMaxima);
        this.setGraduacaoMinima(graduacaoMinima);
        this.setGraduacaoMaxima(graduacaoMaxima);
        this.setSexo(sexo);
        this.setId(id);
        this.setNumRounds(numRounds);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPesoMinimo() {
        
        return pesoMinimo;
    }

    public void setPesoMinimo(double pesoMinimo) throws
            CategoriaDadoIncorretoException {

        if(pesoMinimo <= 0)
            throw new CategoriaDadoIncorretoException("O peso não pode ser " +
                    "igual ou inferior a zero");

        this.pesoMinimo = pesoMinimo;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(double pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public int getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(int idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public int getIdadeMaxima() {
        return idadeMaxima;
    }

    public void setIdadeMaxima(int idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }

    public int getGraduacaoMinima() {
        return graduacaoMinima;
    }

    public void setGraduacaoMinima(int graduacaoMinima) throws
            CategoriaDadoIncorretoException {
        
        if (graduacaoMinima > 10 || graduacaoMinima < -10)
            throw new CategoriaDadoIncorretoException("A Graduação mínima" +
                    " está fora dos valores aceitos");
                    
        this.graduacaoMinima = graduacaoMinima;
    }

    public int getGraduacaoMaxima() {
        return graduacaoMaxima;
    }

    public void setGraduacaoMaxima(int graduacaoMaxima) throws
            CategoriaDadoIncorretoException {
        
        if (graduacaoMaxima > 10 || graduacaoMaxima < -10)
            throw new CategoriaDadoIncorretoException("A Graduação máxima" +
                    " está fora dos valores aceitos");
        
        this.graduacaoMaxima = graduacaoMaxima;
    }

    public String getSexo() {
        return this.sexo.substring(0,1);
    }

    public void setSexo(String sexo) throws CategoriaDadoIncorretoException {
        sexo = sexo.substring(0,1);
        
        if(sexo.equals("") || 
                (!sexo.equalsIgnoreCase("F") && !sexo.equalsIgnoreCase("M"))){
            throw new CategoriaDadoIncorretoException("O sexo deve ser " +
                    "Masculino ou Feminio");
        }        
        
        this.sexo = sexo;
    }

    @Override
   public boolean equals(Object obj) {

        if (obj == null)
            return false;
        
        if (!(obj instanceof Categoria)) 
            return false;
        
        Categoria categoria = (Categoria) obj;
        boolean peso = false, idade = false, graduacao = false;        
        
        /* Se o ID do banco for igual é o mesmo objeto
         Se o id for = -1 ainda não foi persistida, não compara */
        if( (this.getId() == categoria.getId()) && (this.getId() != -1) )
            return true;
        
        if(!categoria.getSexo().equals(this.getSexo()))
            return false;
        
        // Início - Peso
        
        if(categoria.getPesoMinimo() == this.getPesoMinimo() ||
                categoria.getPesoMaximo() == this.getPesoMaximo())
            peso = true;
        
        if(categoria.getPesoMinimo() > this.getPesoMinimo() && 
                categoria.getPesoMinimo() < this.getPesoMaximo())
            peso = true;
        
        if(categoria.getPesoMaximo() >= this.getPesoMinimo() && 
                categoria.getPesoMaximo() < this.getPesoMaximo())
            peso = true;        

        if(categoria.getPesoMinimo() < this.getPesoMinimo() && 
                categoria.getPesoMaximo() > this.getPesoMaximo())
            peso = true;
        
        // Fim - Peso
        
        // Início - Idade
        
        if(categoria.getIdadeMinima() == this.getIdadeMinima() ||
                categoria.getIdadeMaxima() == this.getIdadeMaxima())
            idade = true;
        
        if(categoria.getIdadeMinima() > this.getIdadeMinima() && 
                categoria.getIdadeMinima() < this.getIdadeMaxima())
            idade = true;
        
        if(categoria.getIdadeMaxima() >= this.getIdadeMinima() && 
                categoria.getIdadeMaxima() < this.getIdadeMaxima())
            idade = true;        

        if(categoria.getIdadeMinima() < this.getIdadeMinima() && 
                categoria.getIdadeMaxima() > this.getIdadeMaxima())
            idade = true;
        
        // Fim - Idade
        
        // Início - Graduação
        
        if(categoria.getGraduacaoMinima() == this.getGraduacaoMinima() ||
                categoria.getGraduacaoMaxima() == this.getGraduacaoMaxima())
            graduacao = true;
        
        if(categoria.getGraduacaoMinima() < this.getGraduacaoMinima() && 
                categoria.getGraduacaoMinima() > this.getGraduacaoMaxima())
            graduacao = true;
        
        if(categoria.getGraduacaoMaxima() < this.getGraduacaoMinima() && 
                categoria.getGraduacaoMaxima() > this.getGraduacaoMaxima())
            graduacao = true;        

        if(categoria.getGraduacaoMinima() > this.getGraduacaoMinima() && 
                categoria.getGraduacaoMaxima() < this.getGraduacaoMaxima())
            graduacao = true;
        
        // Fim - Graduação        

        /* Só considera igual ser os 3 Parâmetros conhecidirem */
        if( peso && idade && graduacao){
            return true;
        } else {
            return false;
        }
        
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @ManyToMany(mappedBy = "categorias")
    public Collection<Campeonato> getCampeonatos() {
        return campeonatos;
    }

    public void setCampeonatos(Collection<Campeonato> campeonatos) {
        this.campeonatos = campeonatos;
    }
    
    public void delete() throws DAOException {
        DAOFactory.getInstance().getCategoriaDAO().delete(this);
        
        Categoria.notifyObservers();
    }
    
    public void update() throws DAOException {
        DAOFactory.getInstance().getCategoriaDAO().update(this);
        
        Categoria.notifyObservers();
    }

    public static ArrayList listCategorias() throws
            DAOException, CategoriaDadoIncorretoException {
        
        return DAOFactory.getInstance().getCategoriaDAO().listCategorias();
        
    }
    
    public static ArrayList listCategoriasPorNome(String nome) throws
            CategoriaDadoIncorretoException, DAOException {
        
        if(nome.equals("")){
            throw new CategoriaDadoIncorretoException("O nome da categoria " +
                    "não pode estar em branco");
        }
        
        return DAOFactory.getInstance().getCategoriaDAO().listCategoriasPorNome(nome);
        
    }
    
    public static Categoria getCategoriaPorNome(String nome) throws
            CategoriaDadoIncorretoException, DAOException {
        
        if(nome.equals("")){
            throw new CategoriaDadoIncorretoException("O nome da categoria " +
                    "não pode estar em branco");
        }
        
        return DAOFactory.getInstance().getCategoriaDAO().getCategoriaPorNome(nome);
        
    }
    
    public static Categoria getCategoriaPorId(int id) throws
            DAOException, CategoriaDadoIncorretoException {
        
        return DAOFactory.getInstance().getCategoriaDAO().getCategoriaPorId(id);
        
    }    
    
    public static void addCategoriaObserver(Observer o){
        
        Categoria.addObserver(o);
        
    }
    
    public static void delCategoriaObserver(Observer o){
        
        Categoria.deleteObserver(o);
        
    }

    public int getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(int numRounds) throws
            CategoriaDadoIncorretoException {
        
        if(numRounds <= 0)
            throw new CategoriaDadoIncorretoException("O Número de rounds" +
                    " não pode ser inferior a um");
        
        this.numRounds = numRounds;
    }

    @OneToMany(mappedBy = "categoria")
    public List<Luta> getLutas() {
        return lutas;
    }

    public void setLutas(List<Luta> lutas) {
        this.lutas = lutas;
    }
    
}

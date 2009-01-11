package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import modelo.excecoes.DAOException;
import modelo.dao.DAOFactory;
import modelo.excecoes.EquipeDadoIncorretoException;
import util.StaticObservable;

@Entity
public class Equipe extends StaticObservable implements Serializable {
    
    /** Id do objeto no banco. */
    private int id;
    
    /***/
    private String nome;
    
    /***/
    private String emailContato;
    
    /***/
    private String telefoneContato;
    
    /***/
    private String nomeContato;
    
    /***/
    private List<Lutador> lutadores;

    public Equipe() {
        lutadores = new ArrayList<Lutador>();
    }
    
    public Equipe(String nome, String emailContato, String telefoneContato,
            String nomeContato) throws EquipeDadoIncorretoException, 
            DAOException {
        
        this.setNome(nome);
        this.setEmailContato(emailContato);
        this.setTelefoneContato(telefoneContato);
        this.setNomeContato(nomeContato);
        
        this.setId(DAOFactory.getInstance().getEquipeDAO().save(this));
        
        lutadores = new ArrayList<Lutador>();
        
        Equipe.notifyObservers();
        
    }
    
    public Equipe(int id, String nome, String emailContato, 
            String telefoneContato, String nomeContato) 
            throws EquipeDadoIncorretoException {
        
        this.setNome(nome);
        this.setEmailContato(emailContato);
        this.setTelefoneContato(telefoneContato);
        this.setNomeContato(nomeContato);
        this.setId(id);
        
        lutadores = new ArrayList<Lutador>();
        
    }    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) throws EquipeDadoIncorretoException {
                
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws EquipeDadoIncorretoException {
        
        if(nome.equals(""))
            throw new EquipeDadoIncorretoException("O nome precisa ser" +
                    " preenchido");
        
        this.nome = nome;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato)
            throws EquipeDadoIncorretoException {
        
        if(emailContato.equals(""))
            throw new EquipeDadoIncorretoException("O email precisa ser" +
                    " preenchido");
        
        this.emailContato = emailContato;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) 
            throws EquipeDadoIncorretoException {
        
        if(telefoneContato.equals(""))
            throw new EquipeDadoIncorretoException("O telefone precisa ser" +
                    " preenchido");
        
        this.telefoneContato = telefoneContato;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) 
            throws EquipeDadoIncorretoException {
        
        if(nomeContato.equals(""))
            throw new EquipeDadoIncorretoException("O contato precisa ser" +
                    " preenchido");
        
        this.nomeContato = nomeContato;
    }

    @OneToMany(mappedBy="equipe")
    public List<Lutador> getLutadores() {
        return lutadores;
    }

    public void setLutadores(List<Lutador> lutadores) {
        this.lutadores = lutadores;
    }
    
    public void delete() throws DAOException{
        DAOFactory.getInstance().getEquipeDAO().delete(this);
        
        Equipe.notifyObservers();
    }
    
    public void update() throws DAOException {
        DAOFactory.getInstance().getEquipeDAO().update(this);
        
        Equipe.notifyObservers();
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null)
            return false;
        
        if (!(obj instanceof Equipe)) 
            return false;
        
        Equipe equipe = (Equipe) obj;
        
        /* Se o ID do banco for igual é o mesmo objeto
         Se o id for = -1 ainda não foi persistida, não compara */
        if( (this.getId() == equipe.getId()) && (this.getId() != -1) )
            return true;
        
        return false;
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }
    
    public static ArrayList listEquipes() throws DAOException {
        
        return DAOFactory.getInstance().getEquipeDAO().listEquipes();
        
    }
    
    public static ArrayList listEquipesPorNome(String nome) throws
            EquipeDadoIncorretoException, DAOException {
        
        if(nome.equals("")){
            throw new EquipeDadoIncorretoException("O nome da equipe " +
                    "não pode estar em branco");
        }
        
        return DAOFactory.getInstance().getEquipeDAO().listEquipesPorNome(nome);
        
    }
    
    public static Equipe getEquipePorNome(String nome) throws
            EquipeDadoIncorretoException, DAOException {
        
        if(nome.equals("")){
            throw new EquipeDadoIncorretoException("O nome da equipe não pode" +
                    " estar em branco");
        }
        
        return DAOFactory.getInstance().getEquipeDAO().getEquipeporNome(nome);
        
    }
    
    public static Equipe getEquipePorId(int id) throws DAOException {
        
        return DAOFactory.getInstance().getEquipeDAO().getEquipePorId(id);
        
    }    

    @Override
    public String toString() {
        return this.getNome();
    }    

    public static void addEquipeObserver(Observer o){
        
        Equipe.addObserver(o);
        
    }
    
    public static void delEquipeObserver(Observer o){
        
        Equipe.deleteObserver(o);
        
    }      
    
}

package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import modelo.Lutador;
import modelo.dao.interfaces.LutadorDao;
import modelo.excecoes.DAOException;
import modelo.excecoes.EquipeDadoIncorretoException;
import modelo.excecoes.LutadorDadoIncorretoException;

public class JPALutadorDAO implements LutadorDao {
    
    private EntityManager em = null;

    public JPALutadorDAO() {

        em = ConexaoJPA.getInstance().getEntityManager();
                
    }
    
    @Override
    public int save(Lutador lutador) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.persist(lutador);
            
            em.getTransaction().commit();
            
        } catch (EntityExistsException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Lutador já cadastrado", ex.getCause());
        } catch (IllegalStateException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (TransactionRequiredException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());            
        }       
        
        // Ajustar interface após testes com JPA (TODO)
        return lutador.getId();
        
    }

    @Override
    public void delete(Lutador lutador) throws DAOException {
        try {
            
            em.getTransaction().begin();

            em.remove(lutador);
            
            em.getTransaction().commit();
            
        } catch (IllegalStateException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (TransactionRequiredException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());            
        }        
    }

    @Override
    public void update(Lutador lutador) throws DAOException{

        try {
            
            em.getTransaction().begin();

            em.merge(lutador);
            
            em.getTransaction().commit();
            
        } catch (IllegalStateException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (TransactionRequiredException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());            
        }
        
    }

    @Override
    public ArrayList listLutadores() throws DAOException {

        List<Lutador> lutadores = null;
        
        try {
            
            Query q = em.createQuery("select lutador from Lutador lutador");
            lutadores = q.getResultList();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return new ArrayList<Lutador>(lutadores);
        
    }

    @Override
    public ArrayList getLutadoresPorNome(String nome) throws DAOException {

        List<Lutador> lutadores = null;
        
        try {
            
            Query q = em.createQuery("select lutador from Lutador lutador where lutador.nome LIKE '%"+nome+"%'");
            lutadores = q.getResultList();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return new ArrayList<Lutador>(lutadores);
                
    }
        
    @Override
    public Lutador getLutadorPorId(int id) throws DAOException {
        
        Lutador lutador = null;
        
        try {

            lutador = em.find(Lutador.class, id);
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }                
        
        return lutador;
        
    }    

    @Override
    public Lutador getLutadorPorDocumento(String documento) throws DAOException {
        
        Lutador lutador = null;
        
        try {
            
            Query q = em.createQuery("select lutador from Lutador lutador where lutador.documento = :documento");
            q.setParameter("documento", documento);
            lutador = (Lutador) q.getSingleResult();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return lutador;
        
    }
    
}

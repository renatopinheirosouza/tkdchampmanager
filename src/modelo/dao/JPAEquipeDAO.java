package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import modelo.Equipe;
import modelo.dao.interfaces.EquipeDAO;
import modelo.excecoes.DAOException;

public class JPAEquipeDAO implements EquipeDAO {

    private EntityManager em = null;
    
    public JPAEquipeDAO() {

        em = ConexaoJPA.getInstance().getEntityManager();
        
    }
    
    @Override
    public int save(Equipe equipe) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.persist(equipe);
            
            em.getTransaction().commit();
            
        } catch (EntityExistsException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Equipe já cadastrada", ex.getCause());
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
        return equipe.getId();
        
    }

    @Override
    public void delete(Equipe equipe) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.remove(equipe);
            
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
    public void update(Equipe equipe) throws DAOException{
       
        try {
            
            em.getTransaction().begin();

            em.merge(equipe);
            
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
    public ArrayList listEquipes() throws DAOException {

        List<Equipe> equipes = null;
        
        try {
            
            Query q = em.createQuery("select equipe from Equipe equipe");
            equipes = q.getResultList();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return new ArrayList<Equipe>(equipes);
        
    }
    
    @Override
    public ArrayList listEquipesPorNome(String nome) throws DAOException {

        List<Equipe> equipes = null;
        
        try {
            
            Query q = em.createQuery("select equipe from Equipe equipe where equipe.nome '%"+nome+"%'");
            equipes = q.getResultList();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return new ArrayList<Equipe>(equipes);
                
    }    
    
    @Override
    public Equipe getEquipePorId(int id) throws DAOException {

        Equipe equipe = null;
        
        try {

            equipe = em.find(Equipe.class, id);
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }                
        
        return equipe;
                
    }
    
    @Override
    public Equipe getEquipeporNome(String nome) throws DAOException {

        Equipe equipe = null;
        
        try {
            
            Query q = em.createQuery("select equipe from Equipe equipe where equipe.nome = :nome");
            q.setParameter("nome", nome);
            equipe = (Equipe) q.getSingleResult();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return equipe;
                
    }
        
}

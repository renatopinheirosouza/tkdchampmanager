package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import modelo.Campeonato;
import modelo.dao.interfaces.CampeonatoDAO;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public class JPACampeonatoDAO implements CampeonatoDAO{

    private EntityManager em = null;
    
    public JPACampeonatoDAO() {

        em = ConexaoJPA.getInstance().getEntityManager();
        
    }
    
    @Override
    public int save(Campeonato campeonato) throws DAOException {
        
        try {

            em.getTransaction().begin();

            em.persist(campeonato);
            
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
        return campeonato.getId();        
        
    }
    
    @Override
    public void delete(Campeonato campeonato) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.remove(campeonato);
            
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
                
    }

    @Override
    public void update(Campeonato campeonato) throws DAOException {

        try {
            
            em.getTransaction().begin();

            em.merge(campeonato);
            
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
        
    }
    
    @Override
    public ArrayList listCampeonatos() throws DAOException {

         List<Campeonato> campeonatos = null;
        
        try {
            
            Query q = em.createQuery("select campeonato from Campeonato campeonato");
            campeonatos = q.getResultList();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return new ArrayList<Campeonato>(campeonatos);
        
    }
    
    @Override
    public ArrayList<Campeonato> listCampeonatoPorNome(String nome) throws DAOException {

        List<Campeonato> campeonatos = null;
        
        try {

            Query q = em.createQuery("select campeonato from Campeonato campeonato where campeonato.nome LIKE '%"+nome+"%'");
            q.setParameter("nome", nome);
            campeonatos = q.getResultList();

        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }

        return new ArrayList<Campeonato>(campeonatos);
                
    }    
        
    @Override
    public Campeonato getCampeonatoPorId(int id) throws DAOException {

        Campeonato campeonato = null;
        
        try {

            campeonato = em.find(Campeonato.class, id);
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }                
        
        return campeonato;
                
    }
    
    @Override
    public Campeonato getCampeonatoPorNome(String nome) throws DAOException {

        Campeonato campeonato = null;
        
        try {

            Query q = em.createQuery("select campeonato from Campeonato campeonato where campeonato.nome LIKE '%"+nome+"%'");
            campeonato = (Campeonato) q.getSingleResult();

        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }

        return campeonato;          
                
    }
        
}

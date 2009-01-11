package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import modelo.Categoria;
import modelo.dao.interfaces.CategoriaDAO;
import modelo.excecoes.CategoriaDadoIncorretoException;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public class JPACategoriaDAO implements CategoriaDAO {

    private EntityManager em = null;
    
    public JPACategoriaDAO() {

        em = ConexaoJPA.getInstance().getEntityManager();

    }
    
    @Override
    public int save(Categoria categoria) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.persist(categoria);
            
            em.getTransaction().commit();
            
        } catch (EntityExistsException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Categoria já cadastrada", ex.getCause());
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
        return categoria.getId();
        
    }

    @Override
    public void delete(Categoria categoria) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.remove(categoria);
            
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
    public void update(Categoria categoria) throws DAOException{

        try {
            em.getTransaction().begin();

            em.merge(categoria);
            
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
    public ArrayList listCategorias() throws DAOException {

        List<Categoria> categorias = null;
        
        try {
            
            Query q = em.createQuery("select categoria from Categoria categoria");
            categorias = q.getResultList();
            
        } catch (IllegalStateException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return new ArrayList<Categoria>(categorias);
        
    }
    
    @Override
    public ArrayList listCategoriasPorNome(String nome) throws  DAOException {

        List<Categoria> categorias = null;
        
        try {
            
            Query q = em.createQuery("select categoria from Categoria categoria where categoria.nome LIKE '%"+nome+"%'");
            categorias = q.getResultList();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return new ArrayList<Categoria>(categorias);
                
    }    

    @Override
    public Categoria getCategoriaPorId(int id) throws DAOException {

        Categoria categoria = null;
        
        try {

            categoria = em.find(Categoria.class, id);
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }                
        
        return categoria;
                
    }
    
    @Override
    public Categoria getCategoriaPorNome(String nome) throws DAOException {

        Categoria categoria = null;
        
        try {
            
            Query q = em.createQuery("select categoria from Categoria categoria where categoria.nome LIKE '%"+nome+"%'");
            categoria = (Categoria) q.getSingleResult();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return categoria;     
        
    }
        
}

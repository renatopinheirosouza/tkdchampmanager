package modelo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.Parametros;
import modelo.dao.interfaces.ParametrosDAO;
import modelo.excecoes.DAOException;
        

/**
 *
 * Classe que realiza a gravacao do objeto Parametros para um arquivo
 *
 * @author Samir Azzam
 */
public class SerializadoParametrosDAO implements ParametrosDAO {
    
    private FileOutputStream arqParametrosOut;
    private ObjectOutputStream objParametrosOut;
    private FileInputStream arqParametrosIn;
    private ObjectInputStream objParametrosIn;
    
    /** Creates a new instance of ParametrosSerializado */
    public SerializadoParametrosDAO() {
    }
    
    /**Salva o objeto Parametro em arquivo txt*/
    public void save(Parametros parametros) throws DAOException {

        try {
            arqParametrosOut = new FileOutputStream("Parametros.dat");
        } catch (FileNotFoundException ex) {
            throw new DAOException("Erro na gravacao dos dados", 
                    ex.getCause());
        }
        
        try {
            objParametrosOut = new ObjectOutputStream(arqParametrosOut);
            objParametrosOut.writeObject(parametros);
            
            objParametrosOut.flush();
            objParametrosOut.close();
            arqParametrosOut.flush();
            arqParametrosOut.close();
            
        } catch (IOException ex) {
            throw new DAOException("Erro na gravacao dos dados", 
                    ex.getCause());
        }
            

    }
    
    /**Tras o objeto Parametros serializado do arquivo para memoria */
    public Parametros load() throws DAOException {

        Parametros parametros = null;
        
        try {
            arqParametrosIn = new FileInputStream("Parametros.dat");
        } catch (FileNotFoundException ex) {
            throw new DAOException("Erro na carga dos dados dos dados", 
                    ex.getCause());
        }
        
        try {
            objParametrosIn = new ObjectInputStream(arqParametrosIn);
            parametros = (Parametros)objParametrosIn.readObject();
            arqParametrosIn.close();
            objParametrosIn.close();            
            
        } catch (IOException ex) {
            throw new DAOException("Erro na gravacao dos dados", 
                    ex.getCause());
        } catch (ClassNotFoundException ex) {
            throw new DAOException("Erro na gravacao dos dados", 
                    ex.getCause());
        }

        return parametros;
                    
    }
    
}

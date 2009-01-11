package testes;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Campeonato;
import modelo.Categoria;
import modelo.Luta;
import modelo.Lutador;
import modelo.dao.DAOFactory;
import modelo.excecoes.CampeonatoDadoIncorretoException;
import modelo.excecoes.DAOException;
import modelo.excecoes.LutaDadoIncorretoException;

public class TesteCampeonato {
    
    public static void main(String args[]){
        
        List<Campeonato> campeonatos = null;
        
        try {
            campeonatos = DAOFactory.getInstance().getCampeonatoDAO().listCampeonatos();
        } catch (DAOException ex) {
            Logger.getLogger(TesteCampeonato.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(Campeonato campeonato:campeonatos){
            
            System.out.println(campeonato.getNome());
            
            for(Lutador lutador:campeonato.getLutadoresInscritos()){
                System.out.println(lutador.getNome());                
            }
            
            for(Categoria categoria:campeonato.getCategorias()){
                System.out.println(categoria.getNome());
            }

            try {
                campeonato.chaveamento();
            } catch (LutaDadoIncorretoException ex) {
                Logger.getLogger(TesteCampeonato.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CampeonatoDadoIncorretoException ex) {
                Logger.getLogger(TesteCampeonato.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DAOException ex) {
                Logger.getLogger(TesteCampeonato.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(Luta luta: campeonato.getLutasChaveadas()){
                
                System.out.println(luta.getId());
                
                if(luta.getLutador1() != null)
                    System.out.println(luta.getLutador1().getNome());
                else
                    System.out.println("== AGUARDANDO ==");

                if(luta.getLutador2() != null)
                    System.out.println(luta.getLutador2().getNome());
                else
                    System.out.println("== AGUARDANDO ==");
                
                System.out.println("");
                System.out.println("");
            }
            
        }
        
    }

}

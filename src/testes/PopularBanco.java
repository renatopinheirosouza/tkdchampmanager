package testes;

import java.util.Calendar;
import modelo.excecoes.CampeonatoDadoIncorretoException;
import modelo.excecoes.CategoriaDadoIncorretoException;
import modelo.excecoes.DAOException;
import modelo.excecoes.EquipeDadoIncorretoException;
import modelo.excecoes.LutaDadoIncorretoException;
import modelo.excecoes.LutadorDadoIncorretoException;
import modelo.*;

public class PopularBanco {
    
    public static void main(String[] args) throws EquipeDadoIncorretoException, 
            DAOException, CategoriaDadoIncorretoException, 
            CampeonatoDadoIncorretoException, LutadorDadoIncorretoException, LutaDadoIncorretoException{

        Equipe equipe1 = new Equipe("APTERJ","apterj@apterj.com.br",
                "5555-2323","Mestre Helio");
        Equipe equipe2 = new Equipe("BlackBelt","blackbelt@blackbelt.com.br",
                "5555-0012","Mestre Rafael");

        Campeonato campeonato = new Campeonato("Torneio APTERJ", 
                Calendar.getInstance(), Calendar.getInstance());        

        campeonato.addCategoria(new Categoria("CAT01MASCULINO", 50, 80, 18, 30,
                3, -4, "M", 2));
        campeonato.addCategoria(new Categoria("CAT02MASCULINO", 50, 100, 18, 
                40, 8, 4, "M", 2));
        campeonato.addCategoria(new Categoria("CAT01INFANTIL", 30, 45, 8, 13,
                8, 1, "M", 2));
        campeonato.addCategoria(new Categoria("CAT02FEMININO", 40, 80, 18, 30,
                8, 1, "F", 2));
        
        Lutador lutador = new Lutador("Mota", equipe1, -2, 63, 1984,
                "0000000001", "teste6@teste.com", "M");
        
        campeonato.addLutadorInscrito(lutador);
        campeonato.addLutadorInscrito(new Lutador("Renato", equipe2, 7, 100, 1975,"0000000002", "teste5@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Rafael", equipe2, -4, 80, 1983,"0000000003", "teste6@teste.com", "M")); 
        campeonato.addLutadorInscrito(new Lutador("Carlos Henrique",equipe1,-1,65, 1983, "0040000000", "teste4@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Henrique", equipe1, 3, 72, 1988, "0000006000", "teste5@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Elias", equipe1, 6, 76, 1973,"0000000040", "teste6@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Paulo", equipe2, 5, 78, 1988,"0000070000", "teste7@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Thiago", equipe1, 8, 79, 1985,"0000002000", "teste8@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Guilherme", equipe2, 3, 62,1990, "0003400000", "teste9@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Lucas", equipe1, 3, 40, 1998, "0000005500", "teste0@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Edison", equipe1, 3, 38, 1997,"00000333000", "teste4@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Mauricio", equipe2, 4, 69,1990, "0000222000", "teste5@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Samir", equipe2, 3, 78, 1978, "0000045600", "teste4@teste.com", "M")); 
        campeonato.addLutadorInscrito(new Lutador("Marcos", equipe1, -2, 79, 1971,"0002340000", "teste6@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Marcao", equipe2, 2, 78, 1984,"0000022200", "teste6@teste.com", "M"));
        campeonato.addLutadorInscrito(new Lutador("Jocilene", equipe2, 3, 58, 1984, "0000324234", "teste6@teste.com", "F")); 

        campeonato.addCabecaDeChave(lutador);
        
        campeonato.chaveamento();
        
        System.exit(0);
        
    }
    
}

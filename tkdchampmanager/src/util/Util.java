package util;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.UIManager;

/**
 * Classe utilit�ria - metodos gen�ricos
 */
public class Util {
    
    /**
     * Ajuste do look and feel para o Padrão windows
     * @param componente Componente que deve ter o look and feel ajustado
     */
    public static void ajustaLookFeel(Object componente) {
        
        UIManager.LookAndFeelInfo[] looks;
        
        looks = javax.swing.UIManager.getInstalledLookAndFeels();
        
        // Ajustando look and feel do JDialog
        try {
            javax.swing.UIManager.setLookAndFeel( looks[3].getClassName() );
            javax.swing.SwingUtilities.updateComponentTreeUI( (Component) componente);
        } catch (Exception e) {
            e.printStackTrace();
        }                    
    }

    /**
     * Converte uma string RGB para o Padrão uma COLOR
     * @param rgb String rgb da cor desejada
     * @return COLOR baseado no string passado
     */
    public static java.awt.Color string2Color(String rgb){

        int rgb1 = Integer.parseInt(rgb.substring(0, rgb.indexOf(",")));
        int rgb2 = Integer.parseInt(rgb.substring(rgb.indexOf(",")+1,
                 rgb.indexOf(",", rgb.indexOf(",")+1)));
        int rgb3 = Integer.parseInt(rgb.substring(rgb.lastIndexOf(",")+1));        
        
        return new java.awt.Color( rgb1, rgb2, rgb3);
    }
    
    /** 
     *  Grava em arquivo o printstack da exce��o
     *
     *  @param  excecao A exce��o gerada
     *  @param  obj A classe que originou a exce��o
     *     
     */
    public static void gravaExcecao(Exception excecao, Object obj) {
        
        gravaExcecao(excecao, obj.getClass().getName());
    
    }

    /** 
     *  Grava em arquivo o printstack da exce��o
     *
     *  @param  excecao A exce��o gerada
     *     
     */
    public static void gravaExcecao(Exception excecao) {
        
        gravaExcecao(excecao,"unamed");
    
    }    
    
    /** 
     *  Grava em arquivo o printstack da exce��o
     *
     *  @param  excecao A exce��o gerada
     *  @param  nomeArquivo prefixo do nome do arquivo de exce��o
     *     
     */
    private static void gravaExcecao(Exception excecao, String nomeArquivo) {
    
        PrintStream pstream = null;
        java.text.SimpleDateFormat formato = 
                new java.text.SimpleDateFormat("ddMMyy-HHmm");
        
        // Criando diret�rio de logs, caso não exista
        if(!(new File("./logs").exists())){
            new File("./logs").mkdir();
        }         
        
        /** Criando a sa�da para os erros*/
        File arquivoErro = new File("./logs/"+nomeArquivo+"-" +
                formato.format(java.util.Calendar.getInstance().getTime())+
                ".err");
        
        try {
            pstream = new PrintStream(arquivoErro);
        } catch (FileNotFoundException ex) {
            ; 
        }        
        
        excecao.printStackTrace(pstream);
    }
    
    public static int retornaChave(PreparedStatement stmt) throws SQLException {
        
        ResultSet rset;
        
        rset = stmt.getGeneratedKeys();

        rset.first();

        int id = rset.getInt(1);
        
        return id;
    }    
    
    public static Calendar timestampToCalendar(Timestamp timeStamp){

        Calendar cal = Calendar.getInstance();

        /*
         *  O driver não consegue converter de 0000-00-00 00:00:00 para 
         * TimeStamp, gera uma SQLException. Como esse valor equivale a 
         * NULL, transformo em NULL.
         */
        try{
            cal.setTimeInMillis(timeStamp.getTime());
        } catch (NullPointerException ex){
            cal = null;
        }
        
        return cal;
        
    }    

    /**
     * Validador de formato de email
     * 
     * @param email email que deve ser validado
     * 
     * @return true se o email for v�lido, false caso contrario
     */
    public static boolean emailValido(String email){

        Pattern padrao = Pattern.compile("^[a-z0-9_\\+-]+(\\.[a-z0-9_\\+-]+" +
                ")*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2,4})$");

        Matcher pesquisa = padrao.matcher(email);

        return pesquisa.matches();
    }
    
    public static String readTextFile(String fullPathFilename) {

        FileInputStream fis = null;
        String conteudo = "";
        
        try {
            fis = new FileInputStream(fullPathFilename);

            int tamanho = fis.available();

            byte[] b = new byte[tamanho];

            fis.read(b);
            
            conteudo = new String(b);
            
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);       
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     
        return conteudo;
        
    }   
    
}

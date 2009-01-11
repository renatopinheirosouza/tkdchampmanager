package aplicacao.campeonato;

import aplicacao.GuiResultadoBusca;
import aplicacao.interfaces.ResultadoBusca;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelo.Campeonato;
import modelo.Categoria;
import modelo.excecoes.CampeonatoDadoIncorretoException;
import modelo.excecoes.DAOException;

/**
 *
 * @author  Renato Pinheiro
 */
public class CRUDCampeonato extends javax.swing.JDialog implements FocusListener,
        KeyListener, MouseListener, ResultadoBusca {

    /** Largura das colunas do jTable*/
    private final int[] larguraColunas = { 150, 130, 130, 130};
    
    /** Flag do modo atual de operação
     1 - Inclusão, 2 - Alteração e 3 Exclusão */
    private int modoAtual = 0;
    
    /** Creates new form CRUDCampeonato */
    public CRUDCampeonato(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        // Setando ouvintes da categoria
        jTableCampeonatos.addFocusListener(this);
        jTableCampeonatos.addMouseListener(this);
        jTableCampeonatos.addKeyListener(this);

        try {
            // Ajusta o modelo do JTable
            jTableCampeonatos.setModel(new TableModelCampeonato());
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro no acesso ao banco " +
                    "de dados", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro no acesso ao banco de dados");
        }

        // Centralizando
        this.setLocationRelativeTo(null);
        
        // Ajustando o tamanho e exibindo
        this.setSize(600, 280); // 586, 247
        this.setVisible(true); 
                
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane = new javax.swing.JScrollPane();
        jTableCampeonatos = new javax.swing.JTable();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jtfNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfDataInicial = new javax.swing.JFormattedTextField();
        jtfDataFinal = new javax.swing.JFormattedTextField();
        jmbCampeonato = new javax.swing.JMenuBar();
        jmIncluir = new javax.swing.JMenu();
        jmAlterar = new javax.swing.JMenu();
        jmExcluir = new javax.swing.JMenu();
        jmProcurar = new javax.swing.JMenu();
        jmGerenciar = new javax.swing.JMenu();
        jmSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Campeonatos");
        jTableCampeonatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane.setViewportView(jTableCampeonatos);

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacao/imagens/icones/accept.png")));
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacao/imagens/icones/cancel.png")));
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtfNome.setFont(new java.awt.Font("Arial", 0, 12));
        jtfNome.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel2.setText("Nome");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel3.setText("Data Inicial");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel4.setText("Data Final");

        jtfDataInicial.setEnabled(false);
        jtfDataInicial.setFont(new java.awt.Font("Arial", 0, 12));
        jtfDataInicial.setFormatterFactory(getDefaultFormatterFactory());

        jtfDataFinal.setEnabled(false);
        jtfDataFinal.setFont(new java.awt.Font("Arial", 0, 12));
        jtfDataFinal.setFormatterFactory(getDefaultFormatterFactory());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfDataInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(42, 42, 42))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jmIncluir.setText("Incluir");
        jmIncluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmIncluirMousePressed(evt);
            }
        });

        jmbCampeonato.add(jmIncluir);

        jmAlterar.setText("Alterar");
        jmAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmAlterarMousePressed(evt);
            }
        });

        jmbCampeonato.add(jmAlterar);

        jmExcluir.setText("Excluir");
        jmExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmExcluirMousePressed(evt);
            }
        });

        jmbCampeonato.add(jmExcluir);

        jmProcurar.setText("Procurar");
        jmProcurar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmProcurarMousePressed(evt);
            }
        });

        jmbCampeonato.add(jmProcurar);

        jmGerenciar.setText("Gerenciar");
        jmGerenciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmGerenciarMousePressed(evt);
            }
        });

        jmbCampeonato.add(jmGerenciar);

        jmSair.setText("Sair");
        jmSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmSairMousePressed(evt);
            }
        });

        jmbCampeonato.add(jmSair);

        setJMenuBar(jmbCampeonato);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmGerenciarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmGerenciarMousePressed

        /* Se não estiver no modo de navegação ou se o lutador ativo for
         *nulo, não permite a exclusão
         */
        if(modoAtual!=0 || this.getCampeonatoAtivo() == null)
            return;
        
        /* Tirando o observer de campeonatos do model */
        Campeonato.delCampeonatoObserver( (TableModelCampeonato) 
            jTableCampeonatos.getModel());
        
        /* Fechando a tela de campeonatos*/
        this.dispose();
        
        new GerenciarCampeonato( this, true,
                ((TableModelCampeonato) jTableCampeonatos.getModel()).getCampeonato(jTableCampeonatos.getSelectedRow())).setVisible(true);
                
    }//GEN-LAST:event_jmGerenciarMousePressed

    private void jmSairMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmSairMousePressed
        if(modoAtual != 0)
            return;

        /* Tirando o observer de campeonatos do model */
        Campeonato.delCampeonatoObserver( (TableModelCampeonato) 
            jTableCampeonatos.getModel());        
        
        this.dispose();        
    }//GEN-LAST:event_jmSairMousePressed

    private void jmProcurarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmProcurarMousePressed
        //  permite se estiver em modo de navegação
        if(modoAtual!=0)
            return;
        
        String consulta = JOptionPane.showInputDialog(this, "Digite o nome " +
                "do campeonato: ", "Consulta pelo nome do campeonato", 
                JOptionPane.QUESTION_MESSAGE);

        // Se cancelou retorna
        if(consulta == null)
            return;
        
        // Se não digitou nada retorna
        if(consulta.trim().equals(""))
            return;
      
        ArrayList<Campeonato> campeonatos;
 
        try {
            campeonatos = Campeonato.listCampeonatosPorNome(consulta);
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Problemas com o banco de "+
                    "dados", "Erro", JOptionPane.ERROR_MESSAGE);
            return; 
        } catch (CampeonatoDadoIncorretoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return; 
        }
        
        if(campeonatos.size() > 0) 
            new GuiResultadoBusca( this, true, new TableModelCampeonato(
                    campeonatos), this, larguraColunas, false);
        else 
            JOptionPane.showMessageDialog(this, "Nenhuma categoria encontrada",
                    "Erro", JOptionPane.ERROR_MESSAGE);

    }//GEN-LAST:event_jmProcurarMousePressed

    private void jmExcluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmExcluirMousePressed
        
        /* Se não estiver no modo de navegação ou se o lutador ativo for
         *nulo, não permite a exclusão
         */
        if(modoAtual!=0 || this.getCampeonatoAtivo() == null)
            return;
        
        Object[] options = { "NÃO", "SIM" };
        int opcao;
        
        // 0 = não, 1 = sim
        opcao = JOptionPane.showOptionDialog(null,
                "Deseja remover este lutador?", "Atenção",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        
        if (opcao==1){
            try {
                this.getCampeonatoAtivo().delete();
                
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, "Problemas com o banco de "+
                        "dados", "Erro", JOptionPane.ERROR_MESSAGE);
                return;       
            } finally {
                modoAtual = 0;
                ajustaControles();
                limpaCampos();
            }
            
        }
    }//GEN-LAST:event_jmExcluirMousePressed

    private void jmAlterarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmAlterarMousePressed

        if(modoAtual!=0 || this.getCampeonatoAtivo() == null)
            return;
        
        // Alteração
        modoAtual = 2;
        
        preencheCampos();
        
        ajustaControles();
    }//GEN-LAST:event_jmAlterarMousePressed

    private void jmIncluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmIncluirMousePressed

        if(modoAtual!=0)
            return;
        
        // Trocando modo atual para inclusão
        modoAtual = 1;

        this.limpaCampos();

        this.ajustaControles();
        
    }//GEN-LAST:event_jmIncluirMousePressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        modoAtual = 0;
        
        this.limpaCampos();
        this.ajustaControles();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        
        switch (modoAtual) {
            
            case 1:

                try {

                    new Campeonato( jtfNome.getText(),
                            String2Calendar(jtfDataInicial.getText()),
                            String2Calendar(jtfDataFinal.getText()));
                    
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;  
                }
                
                modoAtual = 0;
                ajustaControles();
                limpaCampos();
                break;
                 
            case 2:
                
                this.getCampeonatoAtivo().setNome(jtfNome.getText());
                this.getCampeonatoAtivo().setDataInicial(
                        String2Calendar(jtfDataInicial.getText()));
                this.getCampeonatoAtivo().setDataFinal(
                        String2Calendar(jtfDataFinal.getText()));

                try {
                    this.getCampeonatoAtivo().update();
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;  
                }

                modoAtual = 0;
                ajustaControles();
                limpaCampos();
                
                break;
            
        }
        
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void limpaCampos() {

       jtfNome.setText("");
       jtfDataInicial.setText("");
       jtfDataFinal.setText("");
        
    }
    
    private void ajustaControles() {
        
        boolean habilitado = false;
        
        switch (modoAtual) {
            
            case 0:
                habilitado = false;
                break;
                
            default:
                habilitado = true;
                break;                        
            
        }
        
       jtfNome.setEnabled(habilitado);
       jtfDataFinal.setEnabled(habilitado);
       jtfDataInicial.setEnabled(habilitado);
       
       btnConfirmar.setEnabled(habilitado);
       btnCancelar.setEnabled(habilitado);
       
       jTableCampeonatos.setEnabled(!habilitado);
        
    }
    
    private void preencheCampos(Campeonato campeonato) {

        if(campeonato == null)
            return;
        
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        jtfNome.setText(campeonato.getNome());
        jtfDataInicial.setText(df.format(campeonato.getDataInicial().getTime()));
        jtfDataFinal.setText(df.format(campeonato.getDataFinal().getTime()));
        
    }
    
    private void preencheCampos(){

        preencheCampos(this.getCampeonatoAtivo());        
        
    }
    
    private Campeonato getCampeonatoAtivo() {

        return ( (TableModelCampeonato) 
            jTableCampeonatos.getModel()).getCampeonato(
                jTableCampeonatos.getSelectedRow());
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        
        if (modoAtual != 0)
            return;
        
        preencheCampos();  
        
    }

    @Override
    public void focusLost(FocusEvent e) {

        if (modoAtual != 0)
            return;
        
        preencheCampos();  
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (modoAtual != 0)
            return;
        
        preencheCampos();          
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (modoAtual != 0)
            return;
        
        preencheCampos();          
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override        
    public void setResultadoBusca(Object o) {
        
        if(o instanceof Campeonato)
            this.preencheCampos((Campeonato) o);
        
    }
    
    private Calendar String2Calendar(String data){
        
        DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");  
        
        df.setLenient(false);  
        
        Date dt = null;  
        
        try { 
            dt = df.parse(data); 
        } catch (ParseException ex) {  
            util.Util.gravaExcecao(ex, this);
            System.exit (1);  
        }  
        
        Calendar cal = Calendar.getInstance();  
        
        cal.setTime(dt);
        
        return (cal);
        
    }
    
    private DefaultFormatterFactory getDefaultFormatterFactory(){
        
        DefaultFormatterFactory retorno = null;
        
        try {
            retorno =  new DefaultFormatterFactory(
                    new MaskFormatter("##/##/####"), 
                    new MaskFormatter("##/##/####"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        return retorno;
        
    }
    
       
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jTableCampeonatos;
    private javax.swing.JMenu jmAlterar;
    private javax.swing.JMenu jmExcluir;
    private javax.swing.JMenu jmGerenciar;
    private javax.swing.JMenu jmIncluir;
    private javax.swing.JMenu jmProcurar;
    private javax.swing.JMenu jmSair;
    private javax.swing.JMenuBar jmbCampeonato;
    private javax.swing.JFormattedTextField jtfDataFinal;
    private javax.swing.JFormattedTextField jtfDataInicial;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
    
}

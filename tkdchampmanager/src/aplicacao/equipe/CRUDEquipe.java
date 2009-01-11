package aplicacao.equipe;

import aplicacao.GuiResultadoBusca;
import aplicacao.interfaces.ResultadoBusca;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.Equipe;
import modelo.excecoes.DAOException;
import modelo.excecoes.EquipeDadoIncorretoException;

/**
 *
 * @author  Renato Pinheiro
 */
public class CRUDEquipe extends javax.swing.JDialog implements FocusListener,
        KeyListener, MouseListener, ResultadoBusca {

    /** Largura das colunas do jTable*/
    private final int[] larguraColunas = { 250, 150};
        
    /***/
    private int modoAtual = 0;
    
    private void inicializador() {
        initComponents();
        
        // Setando ouvintes do Lutador
        jtEquipes.addFocusListener(this);
        jtEquipes.addMouseListener(this);
        jtEquipes.addKeyListener(this);
        
        try {
            jtEquipes.setModel(new TableModelEquipe());
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Problemas no acesso ao banco" +
                    " de dados", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro no acesso ao banco de dados");            
        } catch (EquipeDadoIncorretoException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar os dados " +
                    "das equipes\n\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            util.Util.gravaExcecao(ex, this);
            throw new RuntimeException("Erro no acesso ao banco de dados");
        }
        
        // Ajustando a largura das colunas
        jtEquipes.getColumnModel().getColumn(0).setPreferredWidth(
                larguraColunas[0]);
        jtEquipes.getColumnModel().getColumn(1).setPreferredWidth(
                larguraColunas[1]);
        
        // Ajustando tamanho, centralizando e exibindo a tela
        this.setSize(420, 480); // 400, 456
        this.setLocationRelativeTo(null);
        this.setVisible(true);        
                
    }
    
    /** Creates new form CRUDEquipe */
    public CRUDEquipe(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        this.inicializador();
        
    }
    
    public CRUDEquipe(JDialog parent, boolean modal) {
        super(parent, modal);
        
        this.inicializador();        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpDadosEquipe = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfContato = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtfEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtfTelefone = new javax.swing.JTextField();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane = new javax.swing.JScrollPane();
        jtEquipes = new javax.swing.JTable();
        jmnEquipe = new javax.swing.JMenuBar();
        jmIncluir = new javax.swing.JMenu();
        jmAlterar = new javax.swing.JMenu();
        jmExcluir = new javax.swing.JMenu();
        jmProcurar = new javax.swing.JMenu();
        jmSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Dados da equipes");

        jpDadosEquipe.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel1.setText("Nome");

        jtfNome.setFont(new java.awt.Font("Arial", 0, 12));
        jtfNome.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel2.setText("Contato");

        jtfContato.setFont(new java.awt.Font("Arial", 0, 12));
        jtfContato.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel3.setText("Email");

        jtfEmail.setFont(new java.awt.Font("Arial", 0, 12));
        jtfEmail.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel4.setText("Telefone");

        jtfTelefone.setFont(new java.awt.Font("Arial", 0, 12));
        jtfTelefone.setEnabled(false);

        javax.swing.GroupLayout jpDadosEquipeLayout = new javax.swing.GroupLayout(jpDadosEquipe);
        jpDadosEquipe.setLayout(jpDadosEquipeLayout);
        jpDadosEquipeLayout.setHorizontalGroup(
            jpDadosEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDadosEquipeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtfTelefone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(jtfEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(jtfContato, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jpDadosEquipeLayout.setVerticalGroup(
            jpDadosEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosEquipeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacao/imagens/icones/accept.png"))); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacao/imagens/icones/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jtEquipes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane.setViewportView(jtEquipes);

        jmIncluir.setText("Incluir");
        jmIncluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmIncluirMousePressed(evt);
            }
        });
        jmnEquipe.add(jmIncluir);

        jmAlterar.setText("Alterar");
        jmAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmAlterarMousePressed(evt);
            }
        });
        jmnEquipe.add(jmAlterar);

        jmExcluir.setText("Excluir");
        jmExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmExcluirMousePressed(evt);
            }
        });
        jmnEquipe.add(jmExcluir);

        jmProcurar.setText("Procurar");
        jmProcurar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmProcurarMousePressed(evt);
            }
        });
        jmnEquipe.add(jmProcurar);

        jmSair.setText("Sair");
        jmSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmSairMousePressed(evt);
            }
        });
        jmnEquipe.add(jmSair);

        setJMenuBar(jmnEquipe);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jpDadosEquipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpDadosEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmProcurarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmProcurarMousePressed

        // Só permite se estiver em modo de navegação
        if(modoAtual!=0)
            return;
        
        String consulta = JOptionPane.showInputDialog(this, "Digite o nome " +
                "da equipe: ", "Consulta pelo nome da equipe", 
                JOptionPane.QUESTION_MESSAGE);

        // Se cancelou retorna
        if(consulta == null)
            return;
        
        // Se não digitou nada retorna
        if(consulta.trim().equals(""))
            return;
        
        ArrayList<Equipe> equipes = null;

        try {
            equipes = Equipe.listEquipesPorNome(consulta);
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Problemas com o banco de "+
                    "dados", "Erro", JOptionPane.ERROR_MESSAGE);
            return; 
        } catch (EquipeDadoIncorretoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return; 
        }
        
        if(equipes.size() > 0) 
            new GuiResultadoBusca( this, true, new TableModelEquipe(equipes), 
                    this, larguraColunas, false);
        else 
            JOptionPane.showMessageDialog(this, "Nenhuma equipe encontrada",
                    "Erro", JOptionPane.ERROR_MESSAGE);

                     
    }//GEN-LAST:event_jmProcurarMousePressed

    private void jmExcluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmExcluirMousePressed

        /* Se não estiver no modo de navegação ou se o lutador ativo for
         *nulo, não permite a exclusão
         */
        if(modoAtual!=0 || this.getEquipeAtiva() == null)
            return;
        
        Object[] options = { "NÃO", "SIM" };
        int opcao;
        
        // 0 = não, 1 = sim
        opcao = JOptionPane.showOptionDialog(null,
                "deseja remover esta equipe?", "Atenção",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        
        if (opcao==1){
            try {
                
                TableModelEquipe tableModelEquipe = 
                        (TableModelEquipe) jtEquipes.getModel();
                
                tableModelEquipe.getEquipe(jtEquipes.getSelectedRow()).delete();
                
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

        if(modoAtual!=0 || this.getEquipeAtiva() == null)
            return;
        
        // Alteração
        modoAtual = 2;
        
        this.preencheCampos();
        
        this.ajustaControles();
    }//GEN-LAST:event_jmAlterarMousePressed

    private void jmSairMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmSairMousePressed

        if(modoAtual != 0)
            return;
        
        this.dispose();
        
    }//GEN-LAST:event_jmSairMousePressed

    private void jmIncluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmIncluirMousePressed
    
        // Só permite se estiver em modo de navegação
        if (modoAtual != 0)
            return;

        // Inclusão
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
       
        if (modoAtual == 2){

            try {

                this.getEquipeAtiva().setNome(jtfNome.getText());
                this.getEquipeAtiva().setEmailContato(jtfEmail.getText());
                this.getEquipeAtiva().setTelefoneContato(jtfTelefone.getText());
                this.getEquipeAtiva().setNomeContato(jtfContato.getText());
                
                this.getEquipeAtiva().update();
                
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, "Problemas com o banco de "+
                        "dados", "Erro", JOptionPane.ERROR_MESSAGE);
                return; 
            } catch (EquipeDadoIncorretoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return; 
            }
            
            modoAtual = 0;
            this.ajustaControles();
            this.limpaCampos();

        }
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void limpaCampos() {

        jtfNome.setText("");
        jtfContato.setText("");
        jtfEmail.setText("");
        jtfTelefone.setText("");
        
    }
    
    private void preencheCampos(Equipe equipe){
        
        if (equipe == null)
            return;
        
        jtfNome.setText(equipe.getNome());
        jtfContato.setText(equipe.getNomeContato());
        jtfEmail.setText(equipe.getEmailContato());
        jtfTelefone.setText(equipe.getTelefoneContato());
        
    }
    
    private void preencheCampos(){
        
        preencheCampos(this.getEquipeAtiva());
        
    }

    private Equipe getEquipeAtiva() {
        
        return ((TableModelEquipe) 
            jtEquipes.getModel()).getEquipe(jtEquipes.getSelectedRow());     

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
        jtfContato.setEnabled(habilitado);
        jtfEmail.setEnabled(habilitado);
        jtfTelefone.setEnabled(habilitado);
        btnCancelar.setEnabled(habilitado);
        btnConfirmar.setEnabled(habilitado);
        
        jtEquipes.setEnabled(!habilitado);
    
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
        
        if(o instanceof Equipe)
            this.preencheCampos((Equipe) o);
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JMenu jmAlterar;
    private javax.swing.JMenu jmExcluir;
    private javax.swing.JMenu jmIncluir;
    private javax.swing.JMenu jmProcurar;
    private javax.swing.JMenu jmSair;
    private javax.swing.JMenuBar jmnEquipe;
    private javax.swing.JPanel jpDadosEquipe;
    private javax.swing.JTable jtEquipes;
    private javax.swing.JTextField jtfContato;
    private javax.swing.JTextField jtfEmail;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfTelefone;
    // End of variables declaration//GEN-END:variables
    
}

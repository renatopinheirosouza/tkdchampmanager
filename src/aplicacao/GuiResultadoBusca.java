package aplicacao;

import aplicacao.interfaces.ResultadoBusca;
import javax.swing.JDialog;
import javax.swing.table.TableModel;
import aplicacao.interfaces.BuscaItem;
import java.util.ArrayList;

/**
 *
 * @author  Renato Pinheiro
 */
public class GuiResultadoBusca extends javax.swing.JDialog {
    
    /** */
    private TableModel tableModel;
    
    /***/
    private ResultadoBusca crud = null;
    
    /***/
    private boolean multiSelecao = false;
    
    /** Creates new form BuscaLutador */
    public GuiResultadoBusca(JDialog parent, boolean modal, 
            TableModel tableModel, ResultadoBusca preencheCampos, 
            int[] larguraColunas, boolean multiSelecao) {
        
        super(parent, modal);
        initComponents();
        
        this.crud = preencheCampos;
        this.tableModel = tableModel;

        // Ajustando o modelo do jTable
        jtResultado.setModel(tableModel);
        
        // Valor para o calculo da largura da tela
        int largura = 0;
        
        // Ajustando a largura das colunas
        for(int indice = 0; indice < larguraColunas.length; indice++){
            jtResultado.getColumnModel().getColumn(indice).setPreferredWidth(
                    larguraColunas[indice]);
            
            largura += larguraColunas[indice];
        }
        
        // Ajustando modo de seleção
        this.multiSelecao = multiSelecao;
        
        if(multiSelecao){
            // multi seleção
            jtResultado.setSelectionMode(
                    javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        } else {
            // seleção simples
            jtResultado.setSelectionMode(
                    javax.swing.ListSelectionModel.SINGLE_SELECTION);            
        }
        
        // Ajustando tamanho, centralizando e exibindo a tela
        this.setSize(largura + 10, 380); 
        this.setLocationRelativeTo(null);
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
        jtResultado = new javax.swing.JTable();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Resultados da pesquisa");
        jScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane.setViewportView(jtResultado);

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacao/imagens/icones/accept.png")));
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacao/imagens/icones/cancel.png")));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        this.dispose();
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        if(jtResultado.getSelectedRowCount() == 0)
            this.dispose();
        
        /* Se estiver ajustado para multi seleção devolve um colecao dos 
         objetos selecionados, do contrario devolve apenas O objeto 
         selecionado */
        if(multiSelecao) {
            
            int[] selecao = jtResultado.getSelectedRows();        
            
            ArrayList<Object> colecao = new ArrayList<Object>();

            for(int ind = 0; ind < selecao.length; ind++){

                colecao.add( ((BuscaItem)
                    tableModel).getUknowTypeItem(selecao[ind]));

                }
            
            crud.setResultadoBusca(colecao);
            
        } else {
            int index = jtResultado.getSelectedRow();

            Object obj = 
                    ((BuscaItem) tableModel).getUknowTypeItem(index);

            crud.setResultadoBusca(obj);
        }
        
        this.dispose();        
                
    }//GEN-LAST:event_btnConfirmarActionPerformed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jtResultado;
    // End of variables declaration//GEN-END:variables
    
}

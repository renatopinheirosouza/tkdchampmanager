package aplicacao.campeonato;

import javax.swing.JOptionPane;
import modelo.Luta;
import modelo.excecoes.DAOException;
import modelo.excecoes.LutaComandoException;

/**
 *
 * @author  Renato Pinheiro
 */
public class DefineWO extends javax.swing.JDialog {

    private Luta lutaAtiva;
    
    /** Creates new form DisplayWO */
    public DefineWO(java.awt.Dialog parent, boolean modal, Luta lutaAtiva) {
        super(parent, modal);
        initComponents();
        
        this.lutaAtiva = lutaAtiva;
        
        try {
            // Pausa a luta
            lutaAtiva.pausar();
        } catch (LutaComandoException ex) {
            JOptionPane.showMessageDialog( this, ex.getMessage(), "Erro", 
                    JOptionPane.ERROR_MESSAGE);            
        }
        
        // Se o lutador existe, preenche, do contrario, desabilita
        if(lutaAtiva.getLutador1() != null){
            jchkLutador1.setText(lutaAtiva.getLutador1().getNome());
        } else {
            jchkLutador1.setText("---------");
            jchkLutador1.setEnabled(false);
        }

        // Se o lutador existe, preenche, do contrario, desabilita
        if(lutaAtiva.getLutador2() != null){
            jchkLutador2.setText(lutaAtiva.getLutador2().getNome());
        } else {
            jchkLutador2.setText("---------");
            jchkLutador2.setEnabled(false);
        }
        
        // Marca o WO 
        jchkLutador1.setSelected(lutaAtiva.isWoLutador1());
        jchkLutador2.setSelected(lutaAtiva.isWoLutador2());
        
        // Posicionando, ajustando o tamanho e exibindo a tela
        this.setLocationRelativeTo(null);
        this.setSize(410, 180); //400, 162
        this.setVisible(true);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jchkLutador1 = new javax.swing.JCheckBox();
        jchkLutador2 = new javax.swing.JCheckBox();
        jlTitulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jbConfirmar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("WO");

        jchkLutador1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jchkLutador1.setText("Lutador 1");

        jchkLutador2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jchkLutador2.setText("Lutador 2");

        jlTitulo.setFont(new java.awt.Font("Arial", 1, 12));
        jlTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitulo.setText("Selecione o (os) lutador (es) para o WO");

        jbConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacao/imagens/icones/accept.png"))); // NOI18N
        jbConfirmar.setText("Confirmar");
        jbConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConfirmarActionPerformed(evt);
            }
        });

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacao/imagens/icones/cancel.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchkLutador2)
                    .addComponent(jchkLutador1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                        .addComponent(jbCancelar)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbCancelar, jbConfirmar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchkLutador1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchkLutador2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbConfirmar)
                    .addComponent(jbCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jbConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConfirmarActionPerformed
        try {
            lutaAtiva.encerrarPorWO(jchkLutador1.isSelected(), 
                    jchkLutador2.isSelected());
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog( this, ex.getMessage(), "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro no acesso ao banco de dados");
        }
        
        this.dispose();
}//GEN-LAST:event_jbConfirmarActionPerformed

private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
    this.dispose();
}//GEN-LAST:event_jbCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbConfirmar;
    private javax.swing.JCheckBox jchkLutador1;
    private javax.swing.JCheckBox jchkLutador2;
    private javax.swing.JLabel jlTitulo;
    // End of variables declaration//GEN-END:variables

}
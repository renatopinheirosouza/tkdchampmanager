package aplicacao.campeonato;

import aplicacao.GuiResultadoBusca;
import aplicacao.categoria.TableModelCategoria;
import aplicacao.interfaces.ResultadoBusca;
import aplicacao.lutador.TableModelLutador;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.Campeonato;
import modelo.Categoria;
import modelo.Luta;
import modelo.Lutador;
import modelo.excecoes.CampeonatoDadoIncorretoException;
import modelo.excecoes.CategoriaDadoIncorretoException;
import modelo.excecoes.DAOException;
import modelo.excecoes.EquipeDadoIncorretoException;
import modelo.excecoes.LutaDadoIncorretoException;
import modelo.excecoes.LutadorDadoIncorretoException;

/**
 *
 * @author  Renato Pinheiro
 */
public class GerenciarCampeonato extends javax.swing.JDialog implements 
        ResultadoBusca{

    /** Campeonato sendo gerenciado*/
    private Campeonato campeonatoAtivo;
    
    /** Modo atual de inclusão para retorno da GuiResultadoBusca*/
    private int modoAtual = 0;
                
    /** Creates new form GerenciarCampeonato */
    public GerenciarCampeonato(java.awt.Dialog parent, boolean modal,
            Campeonato campeonato) {
        
        super(parent, modal);
        initComponents();
        
        // setando o campeonato
        this.campeonatoAtivo = campeonato;
        
        this.setTitle(campeonatoAtivo.getNome());

        // Padrão para alinhamento ao centro
        DefaultTableCellRenderer modeloCelulaAlinhamento = 
                new DefaultTableCellRenderer();
        // Alinhando as coluna 1 e 6 para o centro
        modeloCelulaAlinhamento.setHorizontalAlignment(JLabel.CENTER);
        
        try {
            // Table de lutas
            jTableLutas.setModel(new TableModelLuta(this.campeonatoAtivo));
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro no acesso ao banco de dados");
        }
                
        // Ajustando o jtable de Lutas
        jTableLutas.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTableLutas.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTableLutas.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTableLutas.getColumnModel().getColumn(3).setPreferredWidth(200);
        jTableLutas.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTableLutas.getColumnModel().getColumn(5).setPreferredWidth(70);
        jTableLutas.getColumnModel().getColumn(6).setPreferredWidth(200);
                
        jTableLutas.getColumnModel().getColumn(0).setCellRenderer(
                modeloCelulaAlinhamento);        
        
        jTableLutas.getColumnModel().getColumn(5).setCellRenderer(
                modeloCelulaAlinhamento);        
        
        
        // Table de Lutadores
        TableModelLutador tableModelLutador = new TableModelLutador( 
                campeonatoAtivo, "getLutadoresInscritos");
        //Campeonato.addCampeonatoObserver(tableModelLutador);
        jTableLutadores.setModel(tableModelLutador);
        jTableLutadores.getColumnModel().getColumn(0).setCellRenderer(
                modeloCelulaAlinhamento);
        jTableLutadores.setSelectionMode(
                javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);        

        // Table de Lutadores não categorizados
        TableModelLutador tableModelLutadorNC = new TableModelLutador( 
                campeonatoAtivo, "getLutadoresNaoCategorizados");        
        //Campeonato.addCampeonatoObserver(tableModelLutadorNC);
        jTableNaoCategorizados.setModel(tableModelLutadorNC);
        jTableNaoCategorizados.getColumnModel().getColumn(0).setCellRenderer(
                modeloCelulaAlinhamento);

        // Table de Lutadores cabeça de chave
        TableModelLutador tableModelLutadorCC = new TableModelLutador( 
                campeonato, "getLutadoresCabecaDeChave");
        //Campeonato.addCampeonatoObserver(tableModelLutadorCC);
        jTableCabecasChave.setModel(tableModelLutadorCC);
        jTableCabecasChave.getColumnModel().getColumn(0).setCellRenderer(
                modeloCelulaAlinhamento);
        jTableCabecasChave.setSelectionMode(
                javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);        
        
        // Table de categorias
        TableModelCategoria tableModelCategoria = new TableModelCategoria(
                campeonatoAtivo, "getCategorias");
        //Campeonato.addCampeonatoObserver(tableModelCategoria);
        jTableCategorias.setModel(tableModelCategoria);
        jTableCategorias.setSelectionMode(
                javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);        
        
        /**/
        this.setSize( 765, 530); //756, 508
        this.setLocationRelativeTo(null);
               
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jScrollPaneLutadores = new javax.swing.JScrollPane();
        jTableLutadores = new javax.swing.JTable();
        jScrollPaneCabecasChave = new javax.swing.JScrollPane();
        jTableCabecasChave = new javax.swing.JTable();
        jScrollPaneCategorias = new javax.swing.JScrollPane();
        jTableCategorias = new javax.swing.JTable();
        jScrollPaneNaoCategorizados = new javax.swing.JScrollPane();
        jTableNaoCategorizados = new javax.swing.JTable();
        jScrollPaneLutas = new javax.swing.JScrollPane();
        jTableLutas = new javax.swing.JTable();
        jMenuBar = new javax.swing.JMenuBar();
        jmLutadores = new javax.swing.JMenu();
        jmiAdicionarInscricao = new javax.swing.JMenuItem();
        jmiCancelarInscricao = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jmiAdicionarCC = new javax.swing.JMenuItem();
        jmiRemoverCC = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jmiEditarLutador = new javax.swing.JMenuItem();
        jmCategorias = new javax.swing.JMenu();
        jmiAdicionarCategoria = new javax.swing.JMenuItem();
        jmiRemoverCategoria = new javax.swing.JMenuItem();
        jmiEditarCategoria = new javax.swing.JMenuItem();
        jmLutas = new javax.swing.JMenu();
        jmiChavear = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jmiLancarLuta = new javax.swing.JMenuItem();
        jmSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Gerenciamento do campeonato");

        jTabbedPane.setFont(new java.awt.Font("Arial", 0, 12));

        jTableLutadores.setFont(new java.awt.Font("Arial", 0, 12));
        jTableLutadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneLutadores.setViewportView(jTableLutadores);

        jTabbedPane.addTab("Lutadores Inscritos", jScrollPaneLutadores);

        jTableCabecasChave.setFont(new java.awt.Font("Arial", 0, 12));
        jTableCabecasChave.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneCabecasChave.setViewportView(jTableCabecasChave);

        jTabbedPane.addTab("Cabeças de chave", jScrollPaneCabecasChave);

        jTableCategorias.setFont(new java.awt.Font("Arial", 0, 12));
        jTableCategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneCategorias.setViewportView(jTableCategorias);

        jTabbedPane.addTab("Categorias", jScrollPaneCategorias);

        jTableNaoCategorizados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneNaoCategorizados.setViewportView(jTableNaoCategorizados);

        jTabbedPane.addTab("Lutadores não categorizados", jScrollPaneNaoCategorizados);

        jTableLutas.setFont(new java.awt.Font("Arial", 0, 12));
        jTableLutas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneLutas.setViewportView(jTableLutas);

        jTabbedPane.addTab("Lutas", jScrollPaneLutas);

        jmLutadores.setText("Lutadores");
        jmLutadores.setFont(new java.awt.Font("Arial", 0, 12));
        jmLutadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmLutadoresMousePressed(evt);
            }
        });

        jmiAdicionarInscricao.setFont(new java.awt.Font("Arial", 0, 12));
        jmiAdicionarInscricao.setText("Inscrever lutador");
        jmiAdicionarInscricao.setToolTipText("");
        jmiAdicionarInscricao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jmiAdicionarInscricaoMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiAdicionarInscricaoMousePressed(evt);
            }
        });
        jmLutadores.add(jmiAdicionarInscricao);

        jmiCancelarInscricao.setFont(new java.awt.Font("Arial", 0, 12));
        jmiCancelarInscricao.setText("Cancelar inscrição");
        jmiCancelarInscricao.setToolTipText("Selecione o (os) lutador (es) que desejam cancelar a inscrição");
        jmiCancelarInscricao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jmiCancelarInscricaoMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiCancelarInscricaoMousePressed(evt);
            }
        });
        jmLutadores.add(jmiCancelarInscricao);
        jmLutadores.add(jSeparator1);

        jmiAdicionarCC.setFont(new java.awt.Font("Arial", 0, 12));
        jmiAdicionarCC.setText("Adicionar Cabeça de Chave");
        jmiAdicionarCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jmiAdicionarCCMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiAdicionarCCMousePressed(evt);
            }
        });
        jmLutadores.add(jmiAdicionarCC);

        jmiRemoverCC.setFont(new java.awt.Font("Arial", 0, 12));
        jmiRemoverCC.setText("Remover Cabeça de Chave");
        jmiRemoverCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jmiRemoverCCMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiRemoverCCMousePressed(evt);
            }
        });
        jmLutadores.add(jmiRemoverCC);
        jmLutadores.add(jSeparator2);

        jmiEditarLutador.setFont(new java.awt.Font("Arial", 0, 12));
        jmiEditarLutador.setText("Editar lutador");
        jmLutadores.add(jmiEditarLutador);

        jMenuBar.add(jmLutadores);

        jmCategorias.setText("Categorias");
        jmCategorias.setFont(new java.awt.Font("Arial", 0, 12));
        jmCategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmCategoriasMousePressed(evt);
            }
        });

        jmiAdicionarCategoria.setFont(new java.awt.Font("Arial", 0, 12));
        jmiAdicionarCategoria.setText("Adicionar");
        jmiAdicionarCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiAdicionarCategoriaMousePressed(evt);
            }
        });
        jmCategorias.add(jmiAdicionarCategoria);

        jmiRemoverCategoria.setFont(new java.awt.Font("Arial", 0, 12));
        jmiRemoverCategoria.setText("Remover");
        jmiRemoverCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiRemoverCategoriaMousePressed(evt);
            }
        });
        jmCategorias.add(jmiRemoverCategoria);

        jmiEditarCategoria.setFont(new java.awt.Font("Arial", 0, 12));
        jmiEditarCategoria.setText("Editar");
        jmCategorias.add(jmiEditarCategoria);

        jMenuBar.add(jmCategorias);

        jmLutas.setText("Lutas");
        jmLutas.setFont(new java.awt.Font("Arial", 0, 12));

        jmiChavear.setFont(new java.awt.Font("Arial", 0, 12));
        jmiChavear.setText("Chavear");
        jmiChavear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jmiChavearMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiChavearMousePressed(evt);
            }
        });
        jmLutas.add(jmiChavear);
        jmLutas.add(jSeparator3);

        jmiLancarLuta.setText("Lançar Luta");
        jmiLancarLuta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiLancarLutaMousePressed(evt);
            }
        });
        jmLutas.add(jmiLancarLuta);

        jMenuBar.add(jmLutas);

        jmSair.setText("Sair");
        jmSair.setFont(new java.awt.Font("Arial", 0, 12));
        jmSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmSairMousePressed(evt);
            }
        });
        jMenuBar.add(jmSair);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiChavearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiChavearMouseEntered

        jTabbedPane.setSelectedIndex(4);
        
    }//GEN-LAST:event_jmiChavearMouseEntered

    private void jmiRemoverCCMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiRemoverCCMousePressed

        int[] selecao = jTableCabecasChave.getSelectedRows();
        
        if(selecao.length == 0) {
            JOptionPane.showMessageDialog( this, "Nenhum cabeça de chave " +
                    "selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /* Aviso de que o chaveamento precisará ser refeito */
        if(campeonatoAtivo.isIniciado() || campeonatoAtivo.isChaveado()){
            JOptionPane.showMessageDialog(null,
                    "Está ação removerá os dados de todas as lutas (" +
                    "Iniciadas,\nencerradas, incompletas e avulsas) deste " +
                    "campeonato.\nSe quiser cancelar, clique em NÃO na" +
                    " próxima tela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        
        Object[] options = { "NÃO", "SIM" };
        int opcao;
        
        // 0 = não, 1 = sim
        opcao = JOptionPane.showOptionDialog(null,
                "Deseja remover os cabeças de chave selecionados?",
                "Atenção", JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        
        if (opcao==1){

            ArrayList<Lutador> lutadores = new ArrayList<Lutador>();

            for(int ind = 0; ind < selecao.length; ind++){

                lutadores.add( ((TableModelLutador) 
                    jTableCabecasChave.getModel()).getLutador(selecao[ind]));

                }

            for(Lutador lutador: lutadores){

                try {
                    campeonatoAtivo.deleteCabecaDeChave(lutador);
                } catch (CampeonatoDadoIncorretoException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro no acesso " +
                            "ao banco de dados", "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException("Erro no acesso ao banco de dados");
                }
            }
                
        }

    }//GEN-LAST:event_jmiRemoverCCMousePressed

    private void jmiAdicionarCCMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiAdicionarCCMousePressed

        /* Aviso de que o chaveamento precisará ser refeito */
        if(campeonatoAtivo.isIniciado() || campeonatoAtivo.isChaveado()){
            JOptionPane.showMessageDialog(null,
                    "Está ação removerá os dados de todas as lutas (" +
                    "Iniciadas,\nencerradas, incompletas e avulsas) deste " +
                    "campeonato.\nSe quiser cancelar, clique em CANCELAR na" +
                    " próxima tela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        
        String consulta = JOptionPane.showInputDialog(this, "Digite o nome " +
                "(Todo ou parte) do cabeça de chave: ", "Consulta pelo nome " +
                "do lutador", JOptionPane.QUESTION_MESSAGE);

        // Se cancelou retorna
        if(consulta == null)
            return;
        
        // Se não digitou nada retorna
        if(consulta.trim().equals(""))
            return;
 
       ArrayList<Lutador> lutadoresCC = null;
         
        try {
            lutadoresCC = Lutador.getLutadoresPorNome(consulta);        
        } catch (EquipeDadoIncorretoException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar os dados " +
                    "das equipes.\n\n"+ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            util.Util.gravaExcecao(ex, this);
            throw new RuntimeException();
        } catch (LutadorDadoIncorretoException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar os dados " +
                    "dos lutadores.\n\n"+ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            util.Util.gravaExcecao(ex, this);            
            throw new RuntimeException();
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Problemas no acesso ao banco" +
                    " de dados", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro no acesso ao banco de dados");
        }        
        
        if(lutadoresCC.size()==0)
            return;
        
        for(Lutador lutador:campeonatoAtivo.getLutadoresCabecaDeChave()){
            
            lutadoresCC.remove(lutador);
            
        }
        
        if(lutadoresCC.size() == 0) {
            JOptionPane.showMessageDialog(this, "Todos os lutadores " +
                    "cujo nome possui o texto digitado já são cabeças de chave", 
                    "Erro", JOptionPane.WARNING_MESSAGE);
            
            return;
        }
        
        // Table model
        TableModelLutador tableModelLutador = new TableModelLutador(
                lutadoresCC);
        
        //Campeonato.addCampeonatoObserver(tableModelLutador);

        /** Largura das colunas do jTable*/
        int[] larguraColunas = { 50, 250, 110, 240};

        // Inclusão de categorias
        modoAtual = 3;
        
        new GuiResultadoBusca( this, true, tableModelLutador, this, 
            larguraColunas, true);

    }//GEN-LAST:event_jmiAdicionarCCMousePressed

    private void jmiCancelarInscricaoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiCancelarInscricaoMouseEntered
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_jmiCancelarInscricaoMouseEntered

    private void jmiAdicionarInscricaoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiAdicionarInscricaoMouseEntered
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_jmiAdicionarInscricaoMouseEntered

    private void jmiRemoverCCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiRemoverCCMouseEntered
        jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_jmiRemoverCCMouseEntered

    private void jmiAdicionarCCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiAdicionarCCMouseEntered
        jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_jmiAdicionarCCMouseEntered

    private void jmiAdicionarCategoriaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiAdicionarCategoriaMousePressed
    
        /* Aviso de que o chaveamento precisará ser refeito */
        if(campeonatoAtivo.isIniciado() || campeonatoAtivo.isChaveado()){
            JOptionPane.showMessageDialog(null,
                    "Está ação removerá os dados de todas as lutas (" +
                    "Iniciadas,\nencerradas, incompletas e avulsas) deste " +
                    "campeonato.\nSe quiser cancelar, clique em CANCELAR na" +
                    " próxima tela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
                
        String consulta = JOptionPane.showInputDialog(this, "Digite o nome " +
                "(Todo ou parte) da categoria: ", "Consulta pelo nome da "+
                "categoria", JOptionPane.QUESTION_MESSAGE);

        // Se cancelou retorna
        if(consulta == null)
            return;

        // Se não digitou nada retorna
        if(consulta.trim().equals(""))
            return;

        ArrayList<Categoria> categoriasNaoAdicionadas = null;

        try {
            categoriasNaoAdicionadas = 
                    Categoria.listCategoriasPorNome(consulta);
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Problemas com o banco de "+
                    "dados", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro no acesso ao banco de dados");
        } catch (CategoriaDadoIncorretoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return; 
        }

        if(categoriasNaoAdicionadas.size()==0)
            return;

        for(Categoria categoria:campeonatoAtivo.getCategorias()){

            categoriasNaoAdicionadas.remove(categoria);

        }

        if(categoriasNaoAdicionadas.size() == 0) {
            JOptionPane.showMessageDialog(this, "Todos as categorias " +
                    "cujo nome possui o texto digitado já foram adicionadas", 
                    "Erro", JOptionPane.WARNING_MESSAGE);

            return;
        }

        // Table model
        TableModelCategoria tableModelCategoria = new TableModelCategoria(
                categoriasNaoAdicionadas);

        //Campeonato.addCampeonatoObserver(tableModelCategoria);

        /** Largura das colunas do jTable*/
        int[] larguraColunas = { 150, 130, 130, 130, 130};

        // Inclusão de categorias
        modoAtual = 2;

        new GuiResultadoBusca( this, true, tableModelCategoria, this, 
            larguraColunas, true);

        try {
            campeonatoAtivo.chaveamento();
        } catch (LutaDadoIncorretoException ex) {
            util.Util.gravaExcecao(ex, this);
            JOptionPane.showMessageDialog(this, "Problemas " +
                    "no chaveamento", "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException();
        } catch (CampeonatoDadoIncorretoException ex) {
            util.Util.gravaExcecao(ex, this);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Problemas " +
                    "no chaveamento", "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro no acesso ao banco de dados");
        }
            
    }//GEN-LAST:event_jmiAdicionarCategoriaMousePressed

    private void jmCategoriasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmCategoriasMousePressed
        jTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_jmCategoriasMousePressed

    private void jmiRemoverCategoriaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiRemoverCategoriaMousePressed
        
        int[] selecao = jTableCategorias.getSelectedRows();
        
        if(selecao.length == 0) {
            JOptionPane.showMessageDialog( this, "Nenhuma categoria " +
                    "selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /* Aviso de que o chaveamento precisará ser refeito */
        if(campeonatoAtivo.isIniciado() || campeonatoAtivo.isChaveado()){
            JOptionPane.showMessageDialog(null,
                    "Está ação removerá os dados de todas as lutas (" +
                    "Iniciadas,\nencerradas, incompletas e avulsas) deste " +
                    "campeonato.\nSe quiser cancelar, clique em NÃO na" +
                    " próxima tela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        
        int opcao = 0;
        
        Object[] options = { "NÃO", "SIM" };

        // 0 = não, 1 = sim
        opcao = JOptionPane.showOptionDialog(null,"Deseja deletar a(s) " +
                "categoria(s) selecionadas? ", "Atenção", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                options, options[0]);

        if(opcao == 1) {

            ArrayList<Categoria> categorias = new ArrayList<Categoria>();

            for(int ind = 0; ind < selecao.length; ind++){

                categorias.add( ((TableModelCategoria) 
                    jTableCategorias.getModel()).getCategoria(selecao[ind]));

                }

            for(Categoria categoria: categorias){

                try {
                    campeonatoAtivo.delCategoria(categoria);
                } catch (CampeonatoDadoIncorretoException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro no acesso " +
                            "ao banco de dados", "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException("Erro no acesso ao banco de dados");
                }
            }
            
            try {
                campeonatoAtivo.chaveamento();
            } catch (LutaDadoIncorretoException ex) {
                util.Util.gravaExcecao(ex, this);
                JOptionPane.showMessageDialog(this, "Problemas " +
                        "no chaveamento", "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException();
            } catch (CampeonatoDadoIncorretoException ex) {
                util.Util.gravaExcecao(ex, this);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, "Problemas " +
                        "no chaveamento", "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro no acesso ao banco de dados");
            }

        }

    }//GEN-LAST:event_jmiRemoverCategoriaMousePressed

    private void jmiAdicionarInscricaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiAdicionarInscricaoMousePressed
      
        /* Aviso de que o chaveamento precisará ser refeito */
        if(campeonatoAtivo.isIniciado() || campeonatoAtivo.isChaveado()){
            JOptionPane.showMessageDialog(null,
                    "Está ação removerá os dados de todas as lutas (" +
                    "Iniciadas,\nencerradas, incompletas e avulsas) deste " +
                    "campeonato.\nSe quiser cancelar, clique em CANCELAR na" +
                    " próxima tela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        
        String consulta = JOptionPane.showInputDialog(this, "Digite o nome " +
                "(Todo ou parte) do lutador: ", "Consulta pelo nome do lutador", 
                JOptionPane.QUESTION_MESSAGE);

        // Se cancelou retorna
        if(consulta == null)
            return;
        
        // Se não digitou nada retorna
        if(consulta.trim().equals(""))
            return;
 
       ArrayList<Lutador> lutadoresNaoInscritos = null;
         
        try {
            lutadoresNaoInscritos = Lutador.getLutadoresPorNome(consulta);        
        } catch (EquipeDadoIncorretoException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar os dados " +
                    "das equipes.\n\n"+ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            util.Util.gravaExcecao(ex, this);
            throw new RuntimeException();
        } catch (LutadorDadoIncorretoException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar os dados " +
                    "dos lutadores.\n\n"+ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            util.Util.gravaExcecao(ex, this);            
            throw new RuntimeException();
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Problemas no acesso ao banco" +
                    " de dados", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro no acesso ao banco de dados");
        }        
        
        if(lutadoresNaoInscritos.size()==0)
            return;
        
        for(Lutador lutador:campeonatoAtivo.getLutadoresInscritos()){
            
            lutadoresNaoInscritos.remove(lutador);
            
        }
        
        if(lutadoresNaoInscritos.size() == 0) {
            JOptionPane.showMessageDialog(this, "Todos os lutadores " +
                    "cujo nome possui o texto digitado já estão inscritos", 
                    "Erro", JOptionPane.WARNING_MESSAGE);
            
            return;
        }
        
        // Table model
        TableModelLutador tableModelLutador = new TableModelLutador(
                lutadoresNaoInscritos);
        
        //Campeonato.addCampeonatoObserver(tableModelLutador);

        /** Largura das colunas do jTable*/
        int[] larguraColunas = { 50, 250, 110, 240};

        // Inclusão de categorias
        modoAtual = 1;
        
        new GuiResultadoBusca( this, true, tableModelLutador, this, 
            larguraColunas, true);
        
    }//GEN-LAST:event_jmiAdicionarInscricaoMousePressed

    private void jmLutadoresMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmLutadoresMousePressed
        
    }//GEN-LAST:event_jmLutadoresMousePressed

    private void jmSairMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmSairMousePressed

        this.dispose();
        
    }//GEN-LAST:event_jmSairMousePressed

    private void jmiCancelarInscricaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiCancelarInscricaoMousePressed

        int[] selecao = jTableLutadores.getSelectedRows();
        
        if(selecao.length == 0) {
            JOptionPane.showMessageDialog( this, "Nenhum lutador selecionado",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /* Aviso de que o chaveamento precisará ser refeito */
        if(campeonatoAtivo.isIniciado() || campeonatoAtivo.isChaveado()){
            JOptionPane.showMessageDialog(null,
                    "Está ação removerá os dados de todas as lutas (" +
                    "Iniciadas,\nencerradas, incompletas e avulsas) deste " +
                    "campeonato.\nSe quiser cancelar, clique em NÃO na" +
                    " próxima tela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        
        Object[] options = { "NÃO", "SIM" };
        int opcao;
        
        // 0 = não, 1 = sim
        opcao = JOptionPane.showOptionDialog(null,
                "Deseja cancelar a inscrição dos lutadores selecionados?",
                "Atenção", JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        
        if (opcao==1){

            ArrayList<Lutador> lutadores = new ArrayList<Lutador>();

            for(int ind = 0; ind < selecao.length; ind++){

                lutadores.add( ((TableModelLutador) 
                    jTableLutadores.getModel()).getLutador(selecao[ind]));

                }

            for(Lutador lutador: lutadores){

                try {
                    campeonatoAtivo.delLutadorInscrito(lutador);
                } catch (CampeonatoDadoIncorretoException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro no acesso " +
                            "ao banco de dados", "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException("Erro no acesso ao banco de dados");
                }
            }
                
        }
        
    }//GEN-LAST:event_jmiCancelarInscricaoMousePressed

    private void jmiChavearMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiChavearMousePressed

        /* Aviso de que o chaveamento precisará ser refeito */
        if(campeonatoAtivo.isIniciado() || campeonatoAtivo.isChaveado()){
            JOptionPane.showMessageDialog(null,
                    "Está ação removerá os dados de todas as lutas (" +
                    "Iniciadas,\nencerradas, incompletas e avulsas) deste " +
                    "campeonato.\nSe quiser cancelar, clique em CANCELAR na" +
                    " próxima tela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        
        int opcao = 0;
                
        /* Zera lutas chaveadas */
        if(campeonatoAtivo.isIniciado() || campeonatoAtivo.isIniciado()){
            Object[] options = { "NÃO", "SIM" };

            // 0 = não, 1 = sim
            opcao = JOptionPane.showOptionDialog(null,
                    "Está ação removerá os dados de todas as lutas (" +
                    "Iniciadas,\nencerradas, incompletas e avulsas) deste " +
                    "campeonato.\nDeseja fazer o chaveamento?", "Atenção",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            
        }
        
        if(opcao == 1 || !campeonatoAtivo.isIniciado()) {

            try {
                campeonatoAtivo.chaveamento();
            } catch (LutaDadoIncorretoException ex) {
                util.Util.gravaExcecao(ex, this);
                JOptionPane.showMessageDialog(this, "Problemas " +
                        "no chaveamento", "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException();
            } catch (CampeonatoDadoIncorretoException ex) {
                util.Util.gravaExcecao(ex, this);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, "Problemas " +
                        "no chaveamento", "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro no acesso ao banco de dados");
            }

        }
        
    }//GEN-LAST:event_jmiChavearMousePressed

    private void jmiLancarLutaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiLancarLutaMousePressed

        if(jTableLutas.getSelectedRows().length == 0){
            JOptionPane.showMessageDialog(this, "Nenhuma luta selecionada", 
                    "ERRO", JOptionPane.ERROR_MESSAGE);
            return;
        }
                
        Luta luta = ((TableModelLuta) jTableLutas.getModel()).getLuta(
                jTableLutas.getSelectedRow());
        
        /* Os observadores são notificados a cada segundo. Se o jTable 
         continuasse a observar a luta, a cada segundo faria uma carga do BD.*/
        Luta.deleteObserver(((TableModelLuta)jTableLutas.getModel()));       
        
        new Placar( this, true, luta);
        
        // Voltando a observar a luta
        Luta.addObserver(((TableModelLuta)jTableLutas.getModel()));     
        
        // Forçando a atualização do jTable
        ((TableModelLuta)jTableLutas.getModel()).update(null, null);
        
}//GEN-LAST:event_jmiLancarLutaMousePressed

    @Override
    public void setResultadoBusca(Object o) {
        
        ArrayList colecao = null;
        
        if(o instanceof ArrayList){
            colecao = (ArrayList<Object>) o;
        } else {
            return;
        }
        
        // Se a colecao estiver vazia, não faz nada
        if(colecao.size()==0){
            modoAtual = 0;
            return;
        }
            
        switch (modoAtual) {
            
            case 1: // Adicionando lutador inscrito
                
                if(colecao.get(0) instanceof Lutador) {

                    for(Object obj: colecao){
                        try {
                            campeonatoAtivo.addLutadorInscrito((Lutador) obj);
                        } catch (DAOException ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(),
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Erro no acesso ao banco de dados");
                        } catch (CampeonatoDadoIncorretoException ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(),
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                    }

                }
        
                modoAtual = 0;
                break;
                
            case 2: // Adicionando categoria
                if(colecao.get(0) instanceof Categoria) {

                    for(Object obj: colecao){
                        try {
                            campeonatoAtivo.addCategoria((Categoria) obj);
                        } catch (DAOException ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(),
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Erro no acesso ao banco de dados");
                        } catch (CampeonatoDadoIncorretoException ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(),
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                    }

                }

                modoAtual = 0;
                break;

            case 3: // Adicionando cabeça de chave
                
                if(colecao.get(0) instanceof Lutador) {

                    for(Object obj: colecao){
                        try {
                            campeonatoAtivo.addCabecaDeChave((Lutador) obj);
                        } catch (DAOException ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(),
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Erro no acesso ao banco de dados");
                        } catch (CampeonatoDadoIncorretoException ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(),
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                    }

                }
        
                modoAtual = 0;
                break;
                
            default:
                break;
            
        }
        
    }   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPaneCabecasChave;
    private javax.swing.JScrollPane jScrollPaneCategorias;
    private javax.swing.JScrollPane jScrollPaneLutadores;
    private javax.swing.JScrollPane jScrollPaneLutas;
    private javax.swing.JScrollPane jScrollPaneNaoCategorizados;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableCabecasChave;
    private javax.swing.JTable jTableCategorias;
    private javax.swing.JTable jTableLutadores;
    private javax.swing.JTable jTableLutas;
    private javax.swing.JTable jTableNaoCategorizados;
    private javax.swing.JMenu jmCategorias;
    private javax.swing.JMenu jmLutadores;
    private javax.swing.JMenu jmLutas;
    private javax.swing.JMenu jmSair;
    private javax.swing.JMenuItem jmiAdicionarCC;
    private javax.swing.JMenuItem jmiAdicionarCategoria;
    private javax.swing.JMenuItem jmiAdicionarInscricao;
    private javax.swing.JMenuItem jmiCancelarInscricao;
    private javax.swing.JMenuItem jmiChavear;
    private javax.swing.JMenuItem jmiEditarCategoria;
    private javax.swing.JMenuItem jmiEditarLutador;
    private javax.swing.JMenuItem jmiLancarLuta;
    private javax.swing.JMenuItem jmiRemoverCC;
    private javax.swing.JMenuItem jmiRemoverCategoria;
    // End of variables declaration//GEN-END:variables
    
}
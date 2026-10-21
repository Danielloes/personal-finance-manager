/**
 * @author danie
 */

package screens;

import enums.CategoriaLancamento;
import enums.TipoLancamento;
import model.Lancamento;

import javax.swing.*;

public class ListarLancamentos extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;

    public ListarLancamentos() {
        initComponents();
        setTitle("Consultar lançamentos");
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        typeCombo = new javax.swing.JComboBox<>();
        categoryCombo = new javax.swing.JComboBox<>();
        filterText = new javax.swing.JLabel();
        buttonFilter = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        saldoAText = new javax.swing.JLabel();
        saldoBText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        typeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Todos os tipos", "salário", "alimentação", "residência", "entretenimento", "transporte", "saúde", "educação", "outras", "décimo terceiro"}));

        categoryCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Todas as categorias", "RECEITAS", "DESPESAS"}));
        categoryCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        filterText.setText("Filtrar por:");

        buttonFilter.setText("Filtrar");
        buttonFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonFiltrarActionPerformed(evt);
            }
        });

        backButton.setText("Voltar");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(categoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(46, 46, 46)
                                                                .addComponent(buttonFilter))
                                                        .addComponent(filterText, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(backButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(saldoAText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(saldoBText, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(filterText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(categoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonFilter))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                                .addComponent(saldoAText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(backButton)
                                        .addComponent(saldoBText, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22))
        );

        pack();
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        this.dispose();
        NewJFrame fa = new NewJFrame();
        fa.setVisible(true);

    }

    private void ButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            enums.CategoriaLancamento categoria = categoryCombo.getSelectedIndex() == 0 ? null
                : enums.CategoriaLancamento.values()[categoryCombo.getSelectedIndex() - 1];
            enums.TipoLancamento tipo = typeCombo.getSelectedIndex() == 0 ? null
                : enums.TipoLancamento.values()[typeCombo.getSelectedIndex() - 1];
            var resultado = new controller.LancamentoController().filtrar(categoria, tipo);
            StringBuilder texto = new StringBuilder();
            for (Lancamento l : resultado) texto.append(l).append('\n');
            if (resultado.isEmpty()) texto.append("Nenhum lançamento encontrado.");
            JTextArea area = new JTextArea(texto.toString(), 16, 65);
            area.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(area));
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro ao consultar", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListarLancamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarLancamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarLancamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarLancamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarLancamentos().setVisible(true);
            }
        });
    }

    private javax.swing.JButton buttonFilter;
    private javax.swing.JLabel saldoAText;
    private javax.swing.JLabel saldoBText;
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> categoryCombo;
    private javax.swing.JComboBox<String> typeCombo;
    private javax.swing.JLabel filterText;

}

package screens;

import controller.LancamentoController;
import enums.CategoriaLancamento;
import enums.TipoLancamento;
import model.Lancamento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @author danie
 */
public class LancarReceitas extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;

    public LancarReceitas() {
        initComponents();
        setTitle("Cadastrar lançamento");
        setLocationRelativeTo(null);
        dateText.setText("Data (AAAA-MM-DD)");
        inputDate.setText(LocalDate.now().toString());
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        backButton = new javax.swing.JButton();
        launchButton = new javax.swing.JButton();
        categoryComboBox = new javax.swing.JComboBox<>();
        typeComboBox = new javax.swing.JComboBox<>();
        inputValue = new javax.swing.JTextField();
        inputDate = new javax.swing.JTextField();
        valueText = new javax.swing.JLabel();
        dateText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backButton.setText("Voltar");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonAction(evt);
            }
        });

        launchButton.setText("Lançar");
        launchButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LancamentoController lancamentoController = new LancamentoController();

                    CategoriaLancamento category;
                    TipoLancamento type;
                    java.math.BigDecimal value;
                    try {
                        value = new java.math.BigDecimal(inputValue.getText().trim().replace(',', '.'));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valor inválido. Insira um número válido.");
                        return;
                    }

                    LocalDate date;
                    try {
                        date = LocalDate.parse(inputDate.getText().trim());
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Data inválida. Insira uma data válida no formato YYYY-MM-DD.");
                        return;
                    }

                    if (!(categoryComboBox.getSelectedIndex() == 0)) {

                        if (categoryComboBox.getSelectedIndex() == 1) {
                            category = CategoriaLancamento.RECEITA;
                        } else {
                            category = CategoriaLancamento.DESPESA;
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Categoria não selecionada");
                        return;
                    }

                    if (!(typeComboBox.getSelectedIndex() == 0)) {

                        switch (typeComboBox.getSelectedIndex()) {
                            case 1: {
                                type = TipoLancamento.SALARIO;
                                break;
                            }
                            case 2: {
                                type = TipoLancamento.ALIMENTACAO;
                                break;
                            }
                            case 3: {
                                type = TipoLancamento.RESIDENCIA;
                                break;
                            }
                            case 4: {
                                type = TipoLancamento.ENTRETENIMENTO;
                                break;
                            }
                            case 5: {
                                type = TipoLancamento.TRANSPORTE;
                                break;
                            }
                            case 6: {
                                type = TipoLancamento.SAUDE;
                                break;
                            }
                            case 7: {
                                type = TipoLancamento.EDUCACAO;
                                break;
                            }
                            case 8: {
                                type = TipoLancamento.OUTRAS;
                                break;
                            }
                            default: {
                                type = TipoLancamento.DECIMO_TERCEIRO;
                                break;
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Tipo não selecionado");
                        return;
                    }

                    Lancamento lancamento = new Lancamento(category, type, date, value);
                    lancamentoController.inserirLancamento(lancamento);

                    JOptionPane.showMessageDialog(null, "Lançamento adicionado!");

                } catch (IOException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(LancarReceitas.this, ex.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Categoria", "RECEITAS", "DESPESAS"}));
        categoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryComboBoxAction(evt);
            }
        });

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Tipo", "salário", "alimentacao", "residência", "entretenimento", "transporte", "saúde", "educacao", "outras", "decimo terceiro"}));

        inputValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputValueAction(evt);
            }
        });

        valueText.setText("Valor");

        dateText.setText("Data");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addComponent(backButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(launchButton)
                                                .addGap(73, 73, 73))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(valueText, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(inputValue, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(27, 27, 27)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(dateText, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(inputDate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(17, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(valueText)
                                        .addComponent(dateText, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(backButton)
                                        .addComponent(launchButton))
                                .addGap(16, 16, 16))
        );

        pack();
    }

    private void backButtonAction(java.awt.event.ActionEvent evt) {

        this.dispose();
        NewJFrame fa = new NewJFrame();
        fa.setVisible(true);

    }

    private void categoryComboBoxAction(java.awt.event.ActionEvent evt) {

    }

    private void inputValueAction(java.awt.event.ActionEvent evt) {

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
            java.util.logging.Logger.getLogger(LancarReceitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LancarReceitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LancarReceitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LancarReceitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LancarReceitas().setVisible(true);
            }
        });
    }

    private javax.swing.JButton backButton;
    private javax.swing.JButton launchButton;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JLabel valueText;
    private javax.swing.JLabel dateText;
    private javax.swing.JTextField inputValue;
    private javax.swing.JTextField inputDate;

}

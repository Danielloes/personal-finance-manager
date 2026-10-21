package screens;

import controller.LancamentoController;
import model.Conta;
import model.Lancamento;
import javax.swing.*;
import java.awt.GridLayout;
import java.io.IOException;

public class NewJFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    public NewJFrame() {
        super("Controle financeiro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 12, 12));
        JButton cadastrar = new JButton("Cadastrar receita ou despesa");
        JButton filtrar = new JButton("Listar e filtrar lançamentos");
        JButton saldo = new JButton("Extrato por data e saldo");
        JButton exportar = new JButton("Exportar CSV");
        cadastrar.addActionListener(e -> { new LancarReceitas().setVisible(true); dispose(); });
        filtrar.addActionListener(e -> { new ListarLancamentos().setVisible(true); dispose(); });
        saldo.addActionListener(e -> {
            try {
                LancamentoController controller = new LancamentoController();
                Conta conta = controller.obterConta();
                StringBuilder texto = new StringBuilder();
                for (Lancamento l : controller.listarLancamentosPorData(conta.getLancamentos())) texto.append(l).append('\n');
                texto.append("\nSaldo: R$ ").append(conta.getSaldo().toPlainString());
                JTextArea area = new JTextArea(texto.toString(), 16, 65);
                area.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(area), "Extrato", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) { erro(ex); }
        });
        exportar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
            try {
                new LancamentoController().gerarArquivo(chooser.getSelectedFile().toPath());
                JOptionPane.showMessageDialog(this, "Arquivo exportado.");
            } catch (IOException ex) { erro(ex); }
        });
        add(cadastrar); add(filtrar); add(saldo); add(exportar);
        setSize(450, 300);
        setLocationRelativeTo(null);
    }
    private void erro(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

import leituraArquivo.Leitura;
import screens.NewJFrame;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Path arquivo = Leitura.arquivoPadrao();

                if (System.getProperty("financeiro.arquivo") == null && Files.notExists(arquivo)) {
                    Files.createDirectories(arquivo.toAbsolutePath().getParent());
                    Files.copy(Path.of("data/exemplo.csv"), arquivo);
                }
                Leitura.lerArquivoCSV(arquivo);
                new NewJFrame().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Não foi possível abrir a conta", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

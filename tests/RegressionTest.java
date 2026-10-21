import controller.LancamentoController;
import enums.*;
import leituraArquivo.Leitura;
import model.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RegressionTest {

    private static int checks;
    private static void check(boolean condition) {
        checks++;
        if (!condition) throw new AssertionError("Verificação " + checks + " falhou.");
    }
    @FunctionalInterface private interface Action { void run() throws Exception; }
    private static void expect(Class<? extends Exception> type, Action action) throws Exception {
        try { action.run(); }
        catch (Exception e) {
            check(type.isInstance(e)); return;
        }
        throw new AssertionError("Esperada exceção " + type.getSimpleName());
    }

    private static Lancamento receita(String valor) {
        return new Lancamento(CategoriaLancamento.RECEITA, TipoLancamento.SALARIO, LocalDate.of(2026, 1, 1), new BigDecimal(valor));
    }
    public static void main(String[] args) throws Exception {
        Path temp = Files.createTempDirectory(Path.of("build"), "regression-");
        Path arquivo = temp.resolve("conta.csv");
        Files.writeString(arquivo, "RECEITA;SALARIO;2026-01-03;0.10\nRECEITA;SALARIO;2026-01-01;0.20\nDESPESA;ALIMENTACAO;2026-01-02;0.05\n");
        LancamentoController controller = new LancamentoController(arquivo);
        check(controller.obterConta().getSaldo().compareTo(new BigDecimal("0.25")) == 0);
        check(new Conta(new BigDecimal("500"), controller.listar()).getSaldo().compareTo(new BigDecimal("500.25")) == 0);
        List<Lancamento> lista = controller.listar();
        List<Lancamento> ordenada = controller.listarLancamentosPorData(lista);
        check(ordenada.get(0).getData().equals(LocalDate.of(2026, 1, 1)));
        check(lista.get(0).getData().equals(LocalDate.of(2026, 1, 3)));
        controller.listarLancamentosPorData(lista);
        check(controller.obterConta().getSaldo().compareTo(new BigDecimal("0.25")) == 0);
        check(controller.filtrar(CategoriaLancamento.DESPESA, TipoLancamento.SAUDE).isEmpty());
        check(controller.filtrar(null, null).size() == 3);
        check(controller.filtrar(CategoriaLancamento.RECEITA, null).size() == 2);
        expect(UnsupportedOperationException.class, () -> lista.add(receita("1")));
        expect(UnsupportedOperationException.class, () -> new Conta(lista).getLancamentos().clear());
        controller.inserirLancamento(receita("0.10"));
        check(new LancamentoController(arquivo).obterConta().getSaldo().compareTo(new BigDecimal("0.35")) == 0);
        check(controller.listar().size() == 4);
        Path exportado = temp.resolve("exportado.csv");
        controller.gerarArquivo(exportado);
        check(Leitura.lerArquivoCSV(exportado).size() == 4);
        String existente = Files.readString(exportado);
        expect(IOException.class, () -> controller.gerarArquivo(exportado));
        check(Files.readString(exportado).equals(existente));
        Path ausente = temp.resolve("ausente.csv");
        expect(IOException.class, () -> new LancamentoController(ausente).inserirLancamento(receita("1")));
        check(Files.notExists(ausente));
        String[] invalidos = {
            "RECEITA;SALARIO;2026-01-01;10\nDESPESA;SAUDE;2026-01-01",
            "INVALIDO;SALARIO;2026-01-01;10", "RECEITA;INVALIDO;2026-01-01;10",
            "RECEITA;SALARIO;2026-02-30;10", "RECEITA;SALARIO;2026-01-01;NaN",
            "RECEITA;SALARIO;2026-01-01;Infinity", "RECEITA;SALARIO;2026-01-01;-1",
            "RECEITA;SALARIO;2026-01-01;0", "RECEITA;SALARIO;2026-01-01;1.001",
            "RECEITA;SALARIO;2026-01-01;1;EXTRA", "RECEITA;SALARIO;2026-01-01;"
        };
        for (String invalido : invalidos) {
            Files.writeString(arquivo, invalido);
            expect(IOException.class, () -> controller.inserirLancamento(receita("1")));
            check(Files.readString(arquivo).equals(invalido));
        }
        expect(IllegalArgumentException.class, () -> receita("-1"));
        expect(IllegalArgumentException.class, () -> receita("1.001"));
        expect(NullPointerException.class, () -> new Lancamento(null, TipoLancamento.SALARIO, LocalDate.now(), BigDecimal.ONE));
        Files.writeString(arquivo, "\uFEFFRECEITA; SALARIO ;2026-01-01; 1.25\n\n");
        check(controller.obterConta().getSaldo().compareTo(new BigDecimal("1.25")) == 0);
        Files.writeString(arquivo, "");
        controller.inserirLancamento(receita("2.50"));
        check(controller.obterConta().getSaldo().compareTo(new BigDecimal("2.50")) == 0);
        System.out.println("Financeiro: " + checks + " verificações OK");
    }

}

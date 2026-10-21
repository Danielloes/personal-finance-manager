package controller;

import enums.CategoriaLancamento;
import enums.TipoLancamento;
import leituraArquivo.Leitura;
import model.Conta;
import model.Lancamento;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class LancamentoController {
    private final Path arquivo;
    public LancamentoController() { this(Leitura.arquivoPadrao()); }
    public LancamentoController(Path arquivo) { this.arquivo = Objects.requireNonNull(arquivo).toAbsolutePath().normalize(); }
    public List<Lancamento> listar() throws IOException { return List.copyOf(Leitura.lerArquivoCSV(arquivo)); }
    public Conta obterConta() throws IOException { return new Conta(listar()); }

    public void inserirLancamento(Lancamento lancamento) throws IOException {
        Objects.requireNonNull(lancamento);
        List<Lancamento> novos = new ArrayList<>(listar());
        novos.add(lancamento);
        gravar(novos, arquivo, true);
    }
    public List<Lancamento> filtrar(CategoriaLancamento categoria, TipoLancamento tipo) throws IOException {
        return listar().stream().filter(l -> (categoria == null || l.getCategoria() == categoria)
            && (tipo == null || l.getTipo() == tipo)).toList();
    }

    public List<Lancamento> listarLancamentosPorData(List<Lancamento> lancamentos) {
        return lancamentos.stream().sorted(Comparator.comparing(Lancamento::getData)).toList();
    }

    public void gerarArquivo(Path destino) throws IOException { gravar(listar(), destino, false); }

    private static void gravar(List<Lancamento> lancamentos, Path destino, boolean substituir) throws IOException {
        Path absoluto = destino.toAbsolutePath().normalize();
        Path temporario = Files.createTempFile(absoluto.getParent(), "financeiro-", ".tmp");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(temporario, StandardCharsets.UTF_8)) {
                for (Lancamento l : lancamentos) {
                    writer.write(l.getCategoria() + ";" + l.getTipo() + ";" + l.getData() + ";" + l.getValor().toPlainString());
                    writer.newLine();
                }
            }
            if (substituir) {
                // A substituição precisa ser atômica para preservar o arquivo em caso de falha.
                try { Files.move(temporario, absoluto, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING); }
                catch (AtomicMoveNotSupportedException e) { throw new IOException("O disco não suporta gravação atômica; arquivo preservado.", e); }
            } else {
                Files.move(temporario, absoluto);
            }
        } finally { Files.deleteIfExists(temporario); }
    }
}

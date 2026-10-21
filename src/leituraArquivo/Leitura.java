package leituraArquivo;

import enums.CategoriaLancamento;
import enums.TipoLancamento;
import model.Lancamento;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

public final class Leitura {
    private Leitura() { }
    public static Path arquivoPadrao() {
        return Path.of(System.getProperty("financeiro.arquivo", "data/lancamentos.csv"));
    }
    public static ArrayList<Lancamento> lerArquivoCSV() throws IOException { return lerArquivoCSV(arquivoPadrao()); }
    public static ArrayList<Lancamento> lerArquivoCSV(Path arquivo) throws IOException {
        ArrayList<Lancamento> lancamentos = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
            String linha;
            int numero = 0;
            while ((linha = reader.readLine()) != null) {
                numero++;
                if (numero == 1 && linha.startsWith("\uFEFF")) linha = linha.substring(1);
                if (linha.isBlank()) continue;
                String[] partes = linha.split(";", -1);
                try {
                    if (partes.length != 4) throw new IllegalArgumentException("São necessárias quatro colunas.");
                    lancamentos.add(new Lancamento(CategoriaLancamento.valueOf(partes[0].trim()),
                        TipoLancamento.valueOf(partes[1].trim()), LocalDate.parse(partes[2].trim()),
                        new BigDecimal(partes[3].trim())));
                } catch (RuntimeException e) {
                    throw new IOException("CSV inválido na linha " + numero + ": " + e.getMessage(), e);
                }
            }
        }
        return lancamentos;
    }
}

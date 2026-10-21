package model;

import enums.CategoriaLancamento;
import enums.TipoLancamento;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public final class Lancamento {
    private final CategoriaLancamento categoria;
    private final TipoLancamento tipo;
    private final LocalDate data;
    private final BigDecimal valor;

    public Lancamento(CategoriaLancamento categoria, TipoLancamento tipo, LocalDate data, BigDecimal valor) {
        this.categoria = Objects.requireNonNull(categoria, "Informe a categoria.");
        this.tipo = Objects.requireNonNull(tipo, "Informe o tipo.");
        this.data = Objects.requireNonNull(data, "Informe a data.");
        Objects.requireNonNull(valor, "Informe o valor.");
        if (valor.signum() <= 0) throw new IllegalArgumentException("O valor deve ser positivo.");
        try { this.valor = valor.setScale(2, RoundingMode.UNNECESSARY); }
        catch (ArithmeticException e) { throw new IllegalArgumentException("Use no máximo duas casas decimais.", e); }
    }

    public CategoriaLancamento getCategoria() { return categoria; }
    public TipoLancamento getTipo() { return tipo; }
    public LocalDate getData() { return data; }
    public BigDecimal getValor() { return valor; }
    public BigDecimal getValorComSinal() {
        return categoria == CategoriaLancamento.DESPESA ? valor.negate() : valor;
    }
    @Override public String toString() {
        return data + " | " + categoria + " | " + tipo + " | R$ " + valor.toPlainString();
    }
}

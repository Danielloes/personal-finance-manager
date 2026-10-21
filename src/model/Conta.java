package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public final class Conta {
    private final BigDecimal saldoInicial;
    private final List<Lancamento> lancamentos;
    public Conta(List<Lancamento> lancamentos) { this(BigDecimal.ZERO, lancamentos); }
    public Conta(BigDecimal saldoInicial, List<Lancamento> lancamentos) {
        this.saldoInicial = Objects.requireNonNull(saldoInicial).setScale(2, RoundingMode.UNNECESSARY);
        this.lancamentos = List.copyOf(lancamentos);
    }
    public List<Lancamento> getLancamentos() { return lancamentos; }
    public BigDecimal getSaldo() {
        return lancamentos.stream().map(Lancamento::getValorComSinal).reduce(saldoInicial, BigDecimal::add);
    }
}

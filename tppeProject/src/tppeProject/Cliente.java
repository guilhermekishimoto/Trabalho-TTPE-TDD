package tppeProject;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String tipo;
    private String estado;
    private boolean capital;
    private double saldoCashback;
    private List<Venda> historicoVendas;

    public Cliente(String nome, String tipo, String estado, boolean capital) {
        this.nome = nome;
        this.tipo = tipo;
        this.estado = estado;
        this.capital = capital;
        this.saldoCashback = 0;
        this.historicoVendas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isCapital() {
        return capital;
    }

    public double getSaldoCashback() {
        return saldoCashback;
    }

    public void setSaldoCashback(double saldoCashback) {
        this.saldoCashback = saldoCashback;
    }

    public void adicionarVenda(Venda venda) {
        historicoVendas.add(venda);
    }

    public double calcularTotalVendasUltimoMes() {
        double total = 0;
        for (Venda venda : historicoVendas) {
            if (venda.isUltimoMes()) {
                total += venda.calcularValorTotal();
            }
        }
        return total;
    }

    public boolean verificarElegibilidadeEspecial() {
        double totalVendasUltimoMes = calcularTotalVendasUltimoMes();
        if (totalVendasUltimoMes > 100 && this.tipo.equals("padrão")) {
            this.tipo = "especial";
            return true;
        } else if (totalVendasUltimoMes <= 100 && this.tipo.equals("especial")) {
            this.tipo = "padrão";
            return false;
        }
        return this.tipo.equals("especial");
    }
}


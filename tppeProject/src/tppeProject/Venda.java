package tppeProject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class Venda {
    private String data;
    private Cliente cliente;
    private List<Produto> itensVendidos;
    private String metodoPagamento;
    private Date dataVenda;

    public Venda(String data, Cliente cliente, List<Produto> itensVendidos, String metodoPagamento) {
        this.data = data;
        this.cliente = cliente;
        this.itensVendidos = itensVendidos;
        this.metodoPagamento = metodoPagamento;
        try {
            this.dataVenda = new SimpleDateFormat("yyyy-MM-dd").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Produto item : itensVendidos) {
            total += item.getValorVenda();
        }
        return total;
    }

    public double calcularFrete() {
        double frete = 0;
        String estado = cliente.getEstado();
        boolean capital = cliente.isCapital();

        switch (estado) {
            case "DF":
                frete = 5.0;
                break;
            case "Centro-oeste":
                frete = capital ? 10.0 : 13.0;
                break;
            case "Nordeste":
                frete = capital ? 15.0 : 18.0;
                break;
            case "Norte":
                frete = capital ? 20.0 : 25.0;
                break;
            case "Sudeste":
                frete = capital ? 7.0 : 10.0;
                break;
            case "Sul":
                frete = capital ? 10.0 : 13.0;
                break;
        }

        if (cliente.getTipo().equals("especial")) {
            frete *= 0.7; // 30% de desconto no frete
        } else if (cliente.getTipo().equals("prime")) {
            frete = 0; // Frete grátis para clientes prime
        }

        return frete;
    }

    public double calcularDesconto() {
        double desconto = 0;
        double total = calcularValorTotal();

        if (cliente.getTipo().equals("especial")) {
            desconto += total * 0.1; // Desconto de 10%
            if (metodoPagamento.startsWith("4296 13")) {
                desconto += total * 0.1; // Desconto adicional de 10%
            }
        }

        return desconto;
    }

    public double calcularImpostos() {
        double total = calcularValorTotal();
        double icms = 0;
        double impostoMunicipal = 0;

        if (cliente.getEstado().equals("DF")) {
            icms = total * 0.18;
        } else {
            icms = total * 0.12;
            impostoMunicipal = total * 0.04;
        }

        return icms + impostoMunicipal;
    }

    public double calcularTotal() {
        double total = calcularValorTotal();
        double frete = calcularFrete();
        double desconto = calcularDesconto();
        double impostos = calcularImpostos();
        
        double totalComDesconto = total - desconto;
        double totalComImpostos = totalComDesconto + impostos;
        double totalComFrete = totalComImpostos + frete;

        return totalComFrete;
    }

    public double calcularCashback() {
        double total = calcularValorTotal();
        double cashback = 0;

        if (cliente.getTipo().equals("prime")) {
            cashback = total * (metodoPagamento.startsWith("4296 13") ? 0.05 : 0.03);
        }

        return cashback;
    }

    public boolean isUltimoMes() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date ultimoMes = cal.getTime();
        return dataVenda.after(ultimoMes);
    }

    public Cliente getCliente() {
        return cliente;
    }
}

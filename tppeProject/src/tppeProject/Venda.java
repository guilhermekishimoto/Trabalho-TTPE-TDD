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
    private boolean utilizaCashback;

    public Venda(String data, Cliente cliente, List<Produto> itensVendidos, String metodoPagamento, boolean utilizaCashback) {
        this.data = data;
        this.cliente = cliente;
        this.itensVendidos = itensVendidos;
        this.metodoPagamento = metodoPagamento;
        try {
            this.dataVenda = new SimpleDateFormat("yyyy-MM-dd").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.utilizaCashback = utilizaCashback;
    }

    public double calcularValorTotal() {
        return new CalculadoraTotalVenda(this).calcularValorTotal();
    }
    public double calcularFrete() {
        return new CalculadoraTotalVenda(this).calcularFrete();
    }
    public double calcularDesconto() {
        return new CalculadoraTotalVenda(this).calcularDesconto();
    }
    public double calcularImpostos() {
        return new CalculadoraTotalVenda(this).calcularImpostos();
    }
    public double calcularTotalVenda() {
        return new CalculadoraTotalVenda(this).calcular();
    }

    public double calcularCashback() {
        return new CalculadoraTotalVenda(this).calcularCashback();
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

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public List<Produto> getItensVendidos() {
        return itensVendidos;
    }

    public boolean getUtilizaCashback() {
        return utilizaCashback;
    }

    public Date getDataVenda() {
        return dataVenda;
    }
}

package tppeProject;

 /* 
 Classe resultante da refatoração objeto-método. Aproveitamos para centralizar todos os cálculos que antes estavam em 
 venda, fazendo essa classe como a única responsável. Venda apenas utiliza esses métodos.
*/

public class CalculadoraTotalVenda {
    private Venda venda;
    private Cliente cliente;

    public CalculadoraTotalVenda(Venda venda) {
        this.venda = venda;
        this.cliente = venda.getCliente();
    }

    public double calcular() {
        double total = calcularValorTotal();
        double frete = calcularFrete();
        double desconto = calcularDesconto();
        double impostos = calcularImpostos();
        
        double totalComDesconto = total - desconto;
        double totalComImpostos = totalComDesconto + impostos;
        double totalComFrete = totalComImpostos + frete;

        if (venda.getUtilizaCashback() && cliente.getSaldoCashback() > 0) {
            double totalComCashback = totalComFrete - cliente.getSaldoCashback();
            cliente.setSaldoCashback(0);
            return totalComCashback;
        }

        return totalComFrete;
    }

    public double calcularCashback() {
        double total = calcular();
        double cashback = 0;

        if (cliente.getTipo().equals("prime")) {
            cashback = total * (venda.getMetodoPagamento().startsWith("4296 13") ? 0.05 : 0.03);
        }
        
        cliente.setSaldoCashback(cliente.getSaldoCashback() + cashback);

        return cashback;
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Produto item : venda.getItensVendidos()) {
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
            if (venda.getMetodoPagamento().startsWith("4296 13")) {
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
}

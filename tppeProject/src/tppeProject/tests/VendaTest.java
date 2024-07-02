package tppeProject.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tppeProject.Cliente;
import tppeProject.Produto;
import tppeProject.Venda;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class VendaTest {

    private Venda venda;
    private double valorTotalEsperado;
    private double freteEsperado;
    private double descontoEsperado;
    private double impostosEsperado;
    private double totalEsperado;
    private double cashbackEsperado;
    private String tipoEsperado;
    private boolean utilizaCashback;

    public VendaTest(Venda venda, double valorTotalEsperado, double freteEsperado, double descontoEsperado, double impostosEsperado, double totalEsperado, double cashbackEsperado, String tipoEsperado, boolean utilizaCashback) {
        this.venda = venda;
        this.valorTotalEsperado = valorTotalEsperado;
        this.freteEsperado = freteEsperado;
        this.descontoEsperado = descontoEsperado;
        this.impostosEsperado = impostosEsperado;
        this.totalEsperado = totalEsperado;
        this.cashbackEsperado = cashbackEsperado;
        this.tipoEsperado = tipoEsperado;
        this.utilizaCashback = utilizaCashback;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Produto produto1 = new Produto("001", "Produto A", 100.0, "unidade");
        Produto produto2 = new Produto("002", "Produto B", 50.0, "unidade");
        Produto produto3 = new Produto("003", "Produto C", 25.0, "unidade");
        Produto produto4 = new Produto ("004", "Produto D", 200.0, "metro");
        List<Produto> produtos1 = Arrays.asList(produto1, produto2);
        List<Produto> produtos2 = Arrays.asList(produto1);
        List<Produto> produtos3 = Arrays.asList(produto2);
        List<Produto> produtos4 = Arrays.asList(produto1, produto1, produto3);
        List<Produto> produtos5 = Arrays.asList(produto4);
        List<Produto> produtos6 = Arrays.asList(produto3);

        Cliente cliente1 = new Cliente("João", "padrão", "DF", true);
        Cliente cliente2 = new Cliente("Maria", "especial", "Centro-oeste", false);
        Cliente cliente3 = new Cliente("Carlos", "prime", "Sudeste", true);
        Cliente cliente4 = new Cliente("Kishi", "prime", "Norte", true);

        Venda venda1 = new Venda("2024-07-01", cliente1, produtos3, "1234 5678 9101 1121", false);
        Venda venda2 = new Venda("2024-07-01", cliente2, produtos1, "1234 5678 9101 1121", false);
        Venda venda3 = new Venda("2024-07-01", cliente3, produtos2, "4000 1312 3456 7890", false);
        Venda venda4 = new Venda("2024-07-01", cliente4, produtos4, "4296 1344 6743 2222", false);
        Venda venda5 = new Venda ("2024-07-01", cliente4, produtos5, "4296 1344 6743 2222", false);
        Venda venda6 = new Venda("2024-07-01", cliente4, produtos6, "4296 1344 6743 2222", true);

        cliente1.adicionarVenda(venda1);
        cliente2.adicionarVenda(venda2);
        cliente3.adicionarVenda(venda3);
        cliente4.adicionarVenda(venda4);
        cliente4.adicionarVenda(venda5);

        return Arrays.asList(new Object[][]{
        	//Valor total, Frete, Desconto, Impostos, Total, cashBack, Tipo Cliente, utilizaCashback
                {venda1, 50.0, 5.0, 0.0, 9, 64.0, 0.0, "padrão", false},
                {venda2, 150.0, 13.0 * 0.7, 15.0, 24.0, 168.1, 0.0, "especial", false},
                {venda3, 100.0, 0.0, 0.0, 16.0, 116.0, 116.0 * 0.03, "prime", false},
                {venda4, 225.0, 0.0, 0.0, 225.0 * 0.16, 225.0 + 225.0 * 0.16, (225.0 + 225.0 * 0.16) * 0.05, "prime", false},
                {venda5, 200.0, 0.0, 0.0, 32.0, 232.0, 232.0 * 0.05, "prime", false},
                {venda6, 25.0, 0.0, 0.0, 25 * 0.16, (25.0 + 25.0 * 0.16) - (((225.0 + 225.0 * 0.16) * 0.05) + (232.0 * 0.05)), (25.0 + 25.0 * 0.16) * 0.05, "prime", true}
        });
    }

    @Test
    public void testCalcularValorTotal() {
        assertEquals(valorTotalEsperado, venda.calcularValorTotal(), 0.01);
    }

    @Test
    public void testCalcularFrete() {
        assertEquals(freteEsperado, venda.calcularFrete(), 0.01);
    }

    @Test
    public void testCalcularDesconto() {
        assertEquals(descontoEsperado, venda.calcularDesconto(), 0.01);
    }

    @Test
    public void testCalcularImpostos() {
        assertEquals(impostosEsperado, venda.calcularImpostos(), 0.01);
    }

    @Test
    public void testCalcularTotal() {
        assertEquals(totalEsperado, venda.calcularTotal(), 0.01);
    }

    @Test
    public void testCalcularCashback() {
        assertEquals(cashbackEsperado, venda.calcularCashback(), 0.01);
    }

    @Test
    public void testCalcularTotalVendasUltimoMes() {
        double totalVendasUltimoMes = venda.getCliente().calcularTotalVendasUltimoMes();
        assertTrue(totalVendasUltimoMes > 0);
    }

    @Test
    public void testVerificarElegibilidadeEspecial() {
        Cliente cliente = venda.getCliente();
        cliente.verificarElegibilidadeEspecial();
        assertEquals(tipoEsperado, cliente.getTipo());
    }
}

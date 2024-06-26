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

    public VendaTest(Venda venda, double valorTotalEsperado, double freteEsperado, double descontoEsperado, double impostosEsperado, double totalEsperado, double cashbackEsperado) {
        this.venda = venda;
        this.valorTotalEsperado = valorTotalEsperado;
        this.freteEsperado = freteEsperado;
        this.descontoEsperado = descontoEsperado;
        this.impostosEsperado = impostosEsperado;
        this.totalEsperado = totalEsperado;
        this.cashbackEsperado = cashbackEsperado;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Produto produto1 = new Produto("001", "Produto A", 100.0, "unidade");
        Produto produto2 = new Produto("002", "Produto B", 50.0, "unidade");
        List<Produto> produtos1 = Arrays.asList(produto1, produto2);
        List<Produto> produtos2 = Arrays.asList(produto1);

        Cliente cliente1 = new Cliente("João", "padrão", "DF", true);
        Cliente cliente2 = new Cliente("Maria", "especial", "GO", false);
        Cliente cliente3 = new Cliente("Carlos", "prime", "RJ", true);

        Venda venda1 = new Venda("2024-06-01", cliente1, produtos1, "1234 5678 9101 1121");
        Venda venda2 = new Venda("2024-06-01", cliente2, produtos1, "1234 5678 9101 1121");
        Venda venda3 = new Venda("2024-06-01", cliente3, produtos2, "4296 1312 3456 7890");

        cliente1.adicionarVenda(venda1);
        cliente2.adicionarVenda(venda2);
        cliente3.adicionarVenda(venda3);

        return Arrays.asList(new Object[][]{
                {venda1, 150.0, 5.0, 0.0, 27.0, 182.0, 0.0},
                {venda2, 150.0, 13.0 * 0.7, 15.0, 18.0, 155.1, 0.0},
                {venda3, 100.0, 0.0, 0.0, 12.0, 112.0, 5.0}
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
        
        double totalVendasUltimoMes = cliente.calcularTotalVendasUltimoMes();
        
        if (totalVendasUltimoMes > 100) {
            assertEquals("especial", cliente.getTipo());
        } else {
            assertEquals("padrão", cliente.getTipo());
        }
    }
}

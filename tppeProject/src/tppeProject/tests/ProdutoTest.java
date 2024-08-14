package tppeProject.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tppeProject.Produto;
import tppeProject.UnidadeMedida;
import tppeProject.UnidadeMedida.TipoUnidade;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RunWith(Parameterized.class)
public class ProdutoTest {

    private String codigo;
    private String descricao;
    private double valorVenda;
    private UnidadeMedida unidade;
    private Produto produto;

    public ProdutoTest(String codigo, String descricao, double valorVenda, TipoUnidade tipoUnidade) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valorVenda = valorVenda;
        this.unidade = new UnidadeMedida(tipoUnidade);
        this.produto = new Produto(codigo, descricao, valorVenda, this.unidade);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Set<String> codigosUnicos = new HashSet<>();
        Collection<Object[]> data = Arrays.asList(new Object[][]{
                {"001", "Tênis da nike", 700.0, TipoUnidade.UNIDADE},
                {"002", "Cenoura", 1.25, TipoUnidade.QUILOGRAMA},
                {"003", "Pano de seda", 50.0, TipoUnidade.METRO},
                {"004", "Coca-cola Ks", 8.50, TipoUnidade.UNIDADE},
        });

        for (Object[] entry : data) {
            if (!codigosUnicos.add((String) entry[0])) {
                throw new IllegalArgumentException("Duplicate product code found: " + entry[0]);
            }
        }
        return data;
    }

    @Test
    public void testCadastroProduto() {
        assertEquals(codigo, produto.getCodigo());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(valorVenda, produto.getValorVenda(), 0.01);
        assertEquals(unidade, produto.getUnidade());
    }

    @Test
    public void testUniqueCodigoProduto() {
        Set<String> codigosUnicos = new HashSet<>();
        for (Object[] entry : data()) {
            assertTrue("Duplicate product code found: " + entry[0], codigosUnicos.add((String) entry[0]));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipoUnidadeInvalido() {
        // Este teste verifica se o tipo inválido "Litros" lança uma exceção
        UnidadeMedida unidadeInvalida = new UnidadeMedida(UnidadeMedida.TipoUnidade.valueOf("LITROS"));
    }
}

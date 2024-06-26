package tppeProject.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import tppeProject.Cliente;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ClienteTest {

    private String nome;
    private String tipo;
    private String estado;
    private boolean capital;
    private Cliente cliente;

    public ClienteTest(String nome, String tipo, String estado, boolean capital) {
        this.nome = nome;
        this.tipo = tipo;
        this.estado = estado;
        this.capital = capital;
        this.cliente = new Cliente(nome, tipo, estado, capital);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"João", "padrão", "DF", true},
                {"Maria", "especial", "SP", false},
                {"Carlos", "prime", "RJ", true},
                {"Carlos", "prime", "MG", false}
        });
    }

    @Test
    public void testCadastroCliente() {
        assertEquals(nome, cliente.getNome());
        assertEquals(tipo, cliente.getTipo());
        assertEquals(estado, cliente.getEstado());
        assertEquals(capital, cliente.isCapital());
    }

    @Test
    public void testTipoCliente() {
        assertNotNull(cliente.getTipo());
        assertTrue(cliente.getTipo().equals("padrão") || 
                   cliente.getTipo().equals("especial") || 
                   cliente.getTipo().equals("prime"));
    }
}

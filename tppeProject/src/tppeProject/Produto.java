package tppeProject;

public class Produto {
    private String codigo;
    private String descricao;
    private double valorVenda;
    private UnidadeMedida unidade;  // Campo vindo da refatoração de extração de classe

    public Produto(String codigo, String descricao, double valorVenda, UnidadeMedida unidade) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valorVenda = valorVenda;
        this.unidade = unidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public UnidadeMedida getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeMedida unidade) {
        this.unidade = unidade;
    }
}

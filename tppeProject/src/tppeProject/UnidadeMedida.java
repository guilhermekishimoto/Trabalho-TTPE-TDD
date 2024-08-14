package tppeProject;


 /* 
 A classe foi refatorada para garantir a 
 integridade dos dados ao restringir os tipos de unidades de medida aceitos e melhorar a 
 segurança do código, evitando valores inválidos.
*/
public class UnidadeMedida {

    public enum TipoUnidade {
        UNIDADE("Unidade"),
        QUILOGRAMA("Quilograma"),
        METRO("Metro");

        private final String descricao;

        TipoUnidade(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    private TipoUnidade tipoUnidade;

    public UnidadeMedida(TipoUnidade tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public TipoUnidade getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(TipoUnidade tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    @Override
    public String toString() {
        return tipoUnidade.getDescricao();
    }
}

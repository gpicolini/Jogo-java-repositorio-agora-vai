package jogo;

public class Peca {

    private String substancia;
    private String funcao;
    private TipoQuimico tipo;

    public Peca(String substancia, String funcao, TipoQuimico tipo) {
        this.substancia = substancia;
        this.funcao = funcao;
        this.tipo = tipo;
    }

    public String getSubstancia() {
        return substancia;
    }

    public String getFuncao() {
        return funcao;
    }

    public TipoQuimico getTipo() {
        return tipo;
    }

    // ===================== REGRA DE CONEXÃO =====================
    public boolean conecta(Peca outra) {

        if (outra == null) return false;

        if (this.tipo == null || outra.tipo == null) return false;

      
        if (this.funcao.equalsIgnoreCase("Inútil") ||
            outra.funcao.equalsIgnoreCase("Inútil")) {
            return false;
        }

      
        return this.tipo == outra.tipo;
    }

    @Override
    public String toString() {
        return "[" + substancia + " | " + funcao + " | " + tipo + "]";
    }
}
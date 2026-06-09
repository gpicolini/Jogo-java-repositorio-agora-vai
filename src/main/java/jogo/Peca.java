package jogo;

public class Peca {

    private String substancia;
    private String funcao;

    public Peca(String substancia, String funcao) {
        this.substancia = substancia;
        this.funcao = funcao;
    }

    public String getSubstancia() {
        return substancia;
    }

    public String getFuncao() {
        return funcao;
    }

    public boolean conecta(Peca outra) {
    return this.funcao.equalsIgnoreCase(outra.getFuncao());
}
    

    @Override
    public String toString() {
        return "[" + substancia + " | " + funcao + "]";
    
}
 
}

// TOMITA ME MAMA

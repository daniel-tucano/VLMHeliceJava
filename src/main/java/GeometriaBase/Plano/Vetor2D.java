package GeometriaBase.Plano;

public class Vetor2D {
    public Direcao2D direcao;
    public Double modulo;
    public Ponto2D posicao;

    public Vetor2D(Direcao2D direcao, Double modulo, Ponto2D posicao) {
        this.direcao = direcao;
        this.modulo = modulo;
        this.posicao = posicao;
    }

    public Vetor2D(Direcao2D direcao, Double modulo) {
        this.direcao = direcao;
        this.modulo = modulo;
        this.posicao = new Ponto2D(0.0,0.0);
    }

    public Vetor2D(Ponto2D posicaoCabeca) {
        this.direcao = new Direcao2D(posicaoCabeca);
        this.modulo = posicaoCabeca.getDistanciaDaOrigem();
        this.posicao = new Ponto2D(0.0,0.0);
    }

    public Vetor2D(Ponto2D posicaoCabeca, Ponto2D posicaoCalda) {
        this.direcao = new Direcao2D(posicaoCabeca.subtraiPonto(posicaoCalda));
        this.modulo = posicaoCabeca.subtraiPonto(posicaoCalda).getDistanciaDaOrigem();
        this.posicao = posicaoCalda;
    }

    public Vetor2D(Vetor2D vetor) {
        this.direcao = vetor.direcao;
        this.modulo = vetor.modulo;
        this.posicao = vetor.posicao;
    }

    public Vetor2D escala(Double fatorDeEscala) {
        return new Vetor2D(this.direcao, this.modulo/fatorDeEscala, this.posicao);
    }
}

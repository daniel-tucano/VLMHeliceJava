package GeometriaBase.Plano;

import org.ejml.simple.SimpleMatrix;

public class Vetor2D extends Vetor2DBase {
    public Direcao2D direcao;
    public Ponto2D posicao;

    public Vetor2D(Direcao2D direcao, Double modulo, Ponto2D posicao) {
        super(direcao.matrizVetor.scale(modulo));
        this.direcao = direcao;
        this.posicao = posicao;
    }

    public Vetor2D(Direcao2D direcao, Double modulo) {
        super(direcao.matrizVetor.scale(modulo));
        this.direcao = direcao;
        this.posicao = new Ponto2D(0.0,0.0);
    }

    public Vetor2D(Ponto2D posicaoCabeca) {
        super(posicaoCabeca.converteParaVetorColuna());
        this.direcao = new Direcao2D(posicaoCabeca);
        this.posicao = new Ponto2D(0.0,0.0);
    }

    public Vetor2D(Ponto2D posicaoCabeca, Ponto2D posicaoCalda) {
        super(posicaoCabeca.subtraiPonto(posicaoCalda).converteParaVetorColuna());
        this.direcao = new Direcao2D(posicaoCabeca.subtraiPonto(posicaoCalda));
        this.posicao = posicaoCalda;
    }

    public Vetor2D(Ponto2D posicaoCabeca, Ponto2D posicaoCalda, Ponto2D posicao) {
        super(posicaoCabeca.subtraiPonto(posicaoCalda).converteParaVetorColuna());
        this.direcao = new Direcao2D(posicaoCabeca.subtraiPonto(posicaoCalda));
        this.posicao = posicao;
    }

    public Vetor2D(SimpleMatrix matrizVetor) {
        super(matrizVetor);
        this.direcao = new Direcao2D(matrizVetor);
        this.posicao = new Ponto2D(0.0,0.0);
    }

    public Vetor2D(Double componenteX, Double componenteY) {
        super(componenteX, componenteY);
        this.direcao = new Direcao2D(componenteX, componenteY);
        this.posicao = new Ponto2D(0.0,0.0);
    }

    public Vetor2D(Vetor2D vetor) {
        super(vetor.matrizVetor);
        this.direcao = vetor.direcao;
        this.posicao = vetor.posicao;
    }

    public Vetor2D escala(Double fatorDeEscala) {
        return new Vetor2D(this.direcao, this.modulo*fatorDeEscala, this.posicao);
    }
}

package GeometriaBase.Plano;

import GeometriaBase.Espaco.Direcao3D;
import GeometriaBase.Espaco.Direcoes3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Vetor3D;
import org.ejml.simple.SimpleMatrix;

public class Direcao2D extends Vetor2DBase{

    public Direcao2D(Ponto2D direcao) {
        super( normalizaCoordenadas(direcao.x, direcao.y));
    }

    public Direcao2D(SimpleMatrix matrizVetor) {
        super( normalizaCoordenadas(matrizVetor.get(0), matrizVetor.get(1)));
    }

    public Direcao2D(Double componenteX, Double componenteY) {
        super( normalizaCoordenadas(componenteX,componenteY));
    }

    public Direcao2D(EixoDeCoordenadas3D eixoDeCoordenadas, Double componenteX, Double componenteY) {
        super( eixoDeCoordenadas, normalizaCoordenadas(componenteX,componenteY));
    }

    public Direcao2D(EixoDeCoordenadas3D eixoDeCoordenadas, Direcao3D direcao3D) {
        super(eixoDeCoordenadas,normalizaCoordenadas(direcao3D.getModuloComponenteX(), direcao3D.getModuloComponenteY()));
    }

    public Direcao2D getDirecaoPerpendicular() {
        Vetor3D vetorPerpendicular = Direcoes3D.Z.produtoVetorial(this);
        return new Direcao2D(this.eixoDeCoordenadas,vetorPerpendicular.direcao);
    }

    public Vetor2D escala(Double escala) {
        return new Vetor2D(this.matrizVetor.scale(escala));
    }

    public Vetor2D posicionaNoPlano(Ponto2D posicao) {
        return new Vetor2D(this, 1.0, posicao);
    }

    static Ponto2D normalizaCoordenadas(Double componenteX, Double componenteY) {
        Double modulo = Math.sqrt(componenteX*componenteX + componenteY*componenteY);
        if (modulo.equals(0.0)) {
            return new Ponto2D(0.0,0.0);
        }
        return new Ponto2D(componenteX/modulo, componenteY/modulo);
    }
}

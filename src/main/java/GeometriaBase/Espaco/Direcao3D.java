package GeometriaBase.Espaco;

import org.ejml.simple.SimpleMatrix;

public class Direcao3D extends Vetor3DBase {

    public Direcao3D(Ponto3D direcao) {
        super( normalizaCoordenadas(direcao.x, direcao.y, direcao.z));
    }

    public Direcao3D(SimpleMatrix matrizVetor) {
        super( normalizaCoordenadas(matrizVetor.get(0), matrizVetor.get(1),  matrizVetor.get(2)));
    }

    public Direcao3D(Double componenteX, Double componenteY, Double componenteZ) {
        super( normalizaCoordenadas(componenteX,componenteY,componenteZ));
    }

    public Direcao3D getDirecaoPerpendicular() {
        double escala = Math.sqrt(1+this.matrizVetor.get(0)*this.matrizVetor.get(0)/(this.matrizVetor.get(2)*this.matrizVetor.get(2)));
        return new Direcao3D(1/escala,0.0,(-this.matrizVetor.get(0)/this.matrizVetor.get(2))/escala);
    }

    public Vetor3D escala(Double escala) {
        return new Vetor3D(this.matrizVetor.scale(escala));
    }

    public Vetor3D posicionaNoEspaco(Ponto3D posicao) {
        return new Vetor3D(this, 1.0, posicao);
    }

    static Ponto3D normalizaCoordenadas(Double componenteX, Double componenteY, Double componenteZ) {
        Double modulo = Math.sqrt(componenteX*componenteX + componenteY*componenteY + componenteZ*componenteZ);
        if (modulo.equals(0.0)) {
            return new Ponto3D(0.0,0.0,0.0);
        }
        return new Ponto3D(componenteX/modulo, componenteY/modulo, componenteZ/modulo);
    }
}

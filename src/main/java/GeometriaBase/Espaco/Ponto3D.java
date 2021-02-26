package GeometriaBase.Espaco;

import org.ejml.simple.SimpleMatrix;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Point;

import static util.MetodosEstaticos.geraMatrizDeRotacao;

public class Ponto3D {
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;
    public Double x;
    public Double y;
    public Double z;

    public Ponto3D(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Ponto3D(EixoDeCoordenadas3D eixoDeCoordenadas, Double x, Double y, Double z) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Ponto3D(SimpleMatrix matrizPonto) {
        this.x = matrizPonto.get(0);
        this.y = matrizPonto.get(1);
        this.z = matrizPonto.get(2);
    }

    public Ponto3D(EixoDeCoordenadas3D eixoDeCoordenadas, SimpleMatrix matrizPonto) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.x = matrizPonto.get(0);
        this.y = matrizPonto.get(1);
        this.z = matrizPonto.get(2);
    }

    public Double getDistanciaDaOrigem() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Ponto3D somaPonto3D(Ponto3D ponto) {
        return new Ponto3D(this.x + ponto.x, this.y + ponto.y, this.z + ponto.z);
    }

    public Ponto3D subtraiPonto3D(Ponto3D ponto) {
        return new Ponto3D(this.x - ponto.x, this.y - ponto.y, this.z - ponto.z);
    }

    public Ponto3D multiplicaCoordenadasPorEscalar(Double escalar) {
        return new Ponto3D(this.x*escalar, this.y*escalar, this.z*escalar);
    }

    public Ponto3D rotacionaPontoEmTornoDeEixo(Direcao3D eixo, Double anguloDeRotacao) {
        Double anguloEmRadianos = Math.PI*anguloDeRotacao/180;
        SimpleMatrix matrizDeRotacao = geraMatrizDeRotacao(eixo,anguloEmRadianos);
        SimpleMatrix pontoRotacionado = matrizDeRotacao.mult(this.converteParaVetorColuna());
        return new Ponto3D(pontoRotacionado.get(0), pontoRotacionado.get(1), pontoRotacionado.get(2));
    }

    public Ponto3D rotacionaPontoUtilizandoMatrizDeRotacao(SimpleMatrix matrizDeRotacao) {
        SimpleMatrix pontoRotacionado = matrizDeRotacao.mult(this.converteParaVetorColuna());
        return new Ponto3D(pontoRotacionado.get(0), pontoRotacionado.get(1), pontoRotacionado.get(2));
    }

    public Ponto3D descrevePontoEmEixoDeCoordenadasPrincipal() {
        return new Ponto3D(this.eixoDeCoordenadas.matrizEixo.mult(this.converteParaVetorColuna()))
                .somaPonto3D(this.eixoDeCoordenadas.origem);
    }

    public Ponto3D
    descrevePontoEmNovoEixoDeCoordenadas(EixoDeCoordenadas3D novoEixoDeCoordenadas) {
        SimpleMatrix matrizNovoEixoInvertida = novoEixoDeCoordenadas.matrizEixo.invert();
        return new Ponto3D(
                novoEixoDeCoordenadas,
                matrizNovoEixoInvertida
                        .mult(this.eixoDeCoordenadas.matrizEixo)
                        .mult(this.converteParaVetorColuna())
                        .plus(
                                matrizNovoEixoInvertida
                                        .mult(novoEixoDeCoordenadas.origem.subtraiPonto3D(this.eixoDeCoordenadas.origem).converteParaVetorColuna())
                        )
                );
    }

    public SimpleMatrix converteParaVetorColuna() {
        return new SimpleMatrix(3,1, false, new double[] {this.x,this.y,this.z});
    }

    public Ponto3D setEixoDeCoordenadas(EixoDeCoordenadas3D eixoDeCoordenadas) {
        return new Ponto3D(eixoDeCoordenadas, this.converteParaVetorColuna());
    }

    public Point converteParaPoint() {
        return new Point(new Coord3d(this.x,this.y,this.z));
    }
    public Point converteParaPoint(Color cor, float espessura) {
        return new Point(new Coord3d(this.x,this.y,this.z), cor, espessura);
    }

    public CoordenadaCilindrica converteParaCoordenadaCilindrica() {
        Vetor3D vetorPontoNoPlanoZ = new Vetor3D(this.x,this.y,0.0);
        return new CoordenadaCilindrica(this.eixoDeCoordenadas,vetorPontoNoPlanoZ.modulo, this.z, vetorPontoNoPlanoZ.anguloEntreVetores(Direcoes3D.X)*(this.y > 0 ? 1.0 : -1.0));
    }

    public boolean equals(Ponto3D ponto) {
        return this.x.equals(ponto.x) && this.y.equals(ponto.y) && this.z.equals(ponto.z);
    }
}

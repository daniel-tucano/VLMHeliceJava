package GeometriaBase.Plano;


import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Vetor3D;
import GeometriaBase.Espaco.Vetor3DBase;
import org.ejml.simple.SimpleMatrix;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Vetor2DBase {

    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;
    public SimpleMatrix matrizVetor;
    public Double modulo;

    public Vetor2DBase(SimpleMatrix matrizVetor) {
        this.matrizVetor = matrizVetor;
        this.modulo = Math.sqrt(matrizVetor.elementPower(2.0).elementSum());
    }

    public Vetor2DBase(EixoDeCoordenadas3D eixoDeCoordenadas, SimpleMatrix matrizVetor) {
        this.matrizVetor = matrizVetor;
        this.modulo = Math.sqrt(matrizVetor.elementPower(2.0).elementSum());
        this.eixoDeCoordenadas = eixoDeCoordenadas;
    }

    public Vetor2DBase(Ponto2D ponto) {
        this.matrizVetor = ponto.converteParaVetorColuna();
        this.modulo = ponto.getDistanciaDaOrigem();
        this.eixoDeCoordenadas = ponto.eixoDeCoordenadas;
    }

    public Vetor2DBase(EixoDeCoordenadas3D eixoDeCoordenadas, Ponto2D ponto) {
        this.matrizVetor = ponto.converteParaVetorColuna();
        this.modulo = ponto.getDistanciaDaOrigem();
        this.eixoDeCoordenadas = eixoDeCoordenadas;
    }

    public Vetor2DBase(Double componenteX, Double componenteY) {
        this.matrizVetor = new SimpleMatrix(3,1,false, new double[] {componenteX,componenteY,0.0});
        this.modulo = Math.sqrt(matrizVetor.elementPower(2.0).elementSum());
    }

    public Vetor2DBase(EixoDeCoordenadas3D eixoDeCoordenadas, Double componenteX, Double componenteY) {
        this.matrizVetor = new SimpleMatrix(3,1,false, new double[] {componenteX,componenteY,0.0});
        this.modulo = Math.sqrt(matrizVetor.elementPower(2.0).elementSum());
        this.eixoDeCoordenadas = eixoDeCoordenadas;
    }

    public Vetor2D getComponenteX() {
        return new Vetor2D(matrizVetor.get(0), 0.0);
    }

    public Vetor2D getComponenteY() {
        return new Vetor2D(0.0, matrizVetor.get(1));
    }

    public Double getModuloComponenteX() { return this.matrizVetor.get(0);}

    public Double getModuloComponenteY() { return this.matrizVetor.get(1);}

    public Vetor2D getComponenteNaDirecao(Direcao2D direcao) {
        return direcao.escala(this.produtoEscalar(direcao));
    }

    public Vetor3D produtoVetorial(Vetor2DBase vetor2D) {
        return new Vetor3D( 0.0, 0.0, this.matrizVetor.get(0)*vetor2D.matrizVetor.get(1) - this.matrizVetor.get(1)*vetor2D.matrizVetor.get(0));
    }

    public Vetor3D produtoVetorial(Vetor3DBase vetor3D) {
        return new Vetor3D(this.matrizVetor.get(1)*vetor3D.matrizVetor.get(2),
                - this.matrizVetor.get(0)*vetor3D.matrizVetor.get(2),
                this.matrizVetor.get(0)*vetor3D.matrizVetor.get(1) - this.matrizVetor.get(1)*vetor3D.matrizVetor.get(0));    }

    public Double produtoEscalar(Vetor2DBase vetor) {
        return this.matrizVetor.dot(vetor.matrizVetor);
    }

    public Double anguloEntreVetores(Vetor2DBase vetor) {
        return Math.acos(BigDecimal.valueOf(this.produtoEscalar(vetor)).setScale(9, RoundingMode.DOWN).divide(BigDecimal.valueOf(this.modulo).setScale(9, RoundingMode.DOWN).multiply(BigDecimal.valueOf(vetor.modulo).setScale(9, RoundingMode.DOWN)),RoundingMode.DOWN).doubleValue())*180/Math.PI;
    }

    public Vetor3D descreveVetorEmEixoDeCoordenadasPrincipal() {
        return new Vetor3D(this.eixoDeCoordenadas.matrizEixo.mult(this.matrizVetor)).somaVetor(this.eixoDeCoordenadas.origem);
    }

    public Vetor2D subtraiVetor(Vetor2DBase vetor) {
        return new Vetor2D(this.matrizVetor.minus(vetor.matrizVetor));
    }

    public Vetor2D subtraiVetor(Ponto2D ponto) {
        return new Vetor2D(this.matrizVetor.minus(ponto.converteParaVetorColuna()));
    }

    public Vetor2D somaVetor(Vetor2DBase vetor) {
        return new Vetor2D(this.matrizVetor.plus(vetor.matrizVetor));
    }

    public Vetor2D somaVetor(Ponto2D ponto) {
        return new Vetor2D(this.matrizVetor.plus(ponto.converteParaVetorColuna()));
    }
}

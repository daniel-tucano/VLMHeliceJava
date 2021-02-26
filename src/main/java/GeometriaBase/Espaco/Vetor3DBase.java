package GeometriaBase.Espaco;

import org.ejml.simple.SimpleMatrix;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public abstract class Vetor3DBase {

    public SimpleMatrix matrizVetor;
    public Double modulo;

    public Vetor3DBase(SimpleMatrix matrizVetor) {
        this.matrizVetor = matrizVetor;
        this.modulo = Math.sqrt(matrizVetor.elementPower(2.0).elementSum());
    }

    public Vetor3DBase(Ponto3D ponto) {
        this.matrizVetor = ponto.converteParaVetorColuna();
        this.modulo = ponto.getDistanciaDaOrigem();
    }

    public Vetor3DBase(Double componenteX, Double componenteY, Double componenteZ) {
        this.matrizVetor = new SimpleMatrix(3,1,false, new double[] {componenteX,componenteY,componenteZ});
        this.modulo = Math.sqrt(matrizVetor.elementPower(2.0).elementSum());
    }

    public Vetor3D getComponenteX() {
        return new Vetor3D(matrizVetor.get(0), 0.0, 0.0);
    }

    public Vetor3D getComponenteY() {
        return new Vetor3D(0.0, matrizVetor.get(1), 0.0);
    }

    public Vetor3D getComponenteZ() {
        return new Vetor3D(0.0, 0.0, matrizVetor.get(2));
    }

    public Double getModuloComponenteX() { return this.matrizVetor.get(0);}

    public Double getModuloComponenteY() { return this.matrizVetor.get(1);}

    public Double getModuloComponenteZ() { return this.matrizVetor.get(2);}

    public Vetor3D getComponenteNaDirecao(Direcao3D direcao) {
        return direcao.escala(this.produtoEscalar(direcao));
    }

    public Vetor3D produtoVetorial(Vetor3DBase vetor) {
        return new Vetor3D(this.matrizVetor.get(1)*vetor.matrizVetor.get(2) - this.matrizVetor.get(2)*vetor.matrizVetor.get(1),
                            this.matrizVetor.get(2)*vetor.matrizVetor.get(0) - this.matrizVetor.get(0)*vetor.matrizVetor.get(2),
                            this.matrizVetor.get(0)*vetor.matrizVetor.get(1) - this.matrizVetor.get(1)*vetor.matrizVetor.get(0));
    }

    public Double produtoEscalar(Vetor3DBase vetor) {
        return this.matrizVetor.dot(vetor.matrizVetor);
    }

    public Double anguloEntreVetores(Vetor3DBase vetor) {
        return Math.acos(BigDecimal.valueOf(this.produtoEscalar(vetor)).setScale(9, RoundingMode.DOWN).divide(BigDecimal.valueOf(this.modulo).setScale(9, RoundingMode.DOWN).multiply(BigDecimal.valueOf(vetor.modulo).setScale(9, RoundingMode.DOWN)),RoundingMode.DOWN).doubleValue())*180/Math.PI;
    }

    public Vetor3D subtraiVetor(Vetor3DBase vetor) {
        return new Vetor3D(this.matrizVetor.minus(vetor.matrizVetor));
    }

    public Vetor3D subtraiVetor(Ponto3D ponto) {
        return new Vetor3D(this.matrizVetor.minus(ponto.converteParaVetorColuna()));
    }

    public Vetor3D somaVetor(Vetor3DBase vetor) {
        return new Vetor3D(this.matrizVetor.plus(vetor.matrizVetor));
    }

    public Vetor3D somaVetor(Ponto3D ponto) {
        return new Vetor3D(this.matrizVetor.plus(ponto.converteParaVetorColuna()));
    }

    public LineStrip getLineStrip(Color cor, float espessura, double escala) {
        LineStrip linhasVetor = new LineStrip();
        linhasVetor.add(new Point(new Coord3d(0.0,0.0,0.0)));
        linhasVetor.add(new Point(new Coord3d(this.matrizVetor.get(0)*escala,this.matrizVetor.get(1)*escala,this.matrizVetor.get(2)*escala)));
        linhasVetor.setWireframeDisplayed(true);
        linhasVetor.setWireframeColor(cor);
        linhasVetor.setWireframeWidth(espessura);

        return linhasVetor;
    }

    public LineStrip getLineStrip(Color cor, float espessura, double escala, Ponto3D origem) {
        LineStrip linhasVetor = new LineStrip();
        linhasVetor.add(new Point(new Coord3d(origem.x,origem.y,origem.z)));
        linhasVetor.add(new Point(new Coord3d(this.matrizVetor.get(0)*escala + origem.x,this.matrizVetor.get(1)*escala + origem.y,this.matrizVetor.get(2)*escala + origem.z)));
        linhasVetor.setWireframeDisplayed(true);
        linhasVetor.setWireframeColor(cor);
        linhasVetor.setWireframeWidth(espessura);
        return linhasVetor;
    }
}

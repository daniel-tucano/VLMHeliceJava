package GeometriaBase.Espaco;

import org.ejml.simple.SimpleMatrix;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class Vetor3D extends Vetor3DBase {
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;
    public Direcao3D direcao;
    public Ponto3D posicao;

    public Vetor3D(Direcao3D direcao, Double modulo, Ponto3D posicao) {
        super(direcao.matrizVetor.scale(modulo));
        this.direcao = direcao;
        this.posicao = posicao;
    }

    public Vetor3D(EixoDeCoordenadas3D eixoDeCoordenadas, Direcao3D direcao, Double modulo, Ponto3D posicao) {
        super(direcao.matrizVetor.scale(modulo));
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        posicao.eixoDeCoordenadas = eixoDeCoordenadas;
        this.direcao = direcao;
        this.posicao = posicao;
    }

    public Vetor3D(Direcao3D direcao, Double modulo) {
        super(direcao.matrizVetor.scale(modulo));
        this.direcao = direcao;
        this.posicao = new Ponto3D(0.0,0.0,0.0);
    }

    public Vetor3D(EixoDeCoordenadas3D eixoDeCoordenadas, Direcao3D direcao, Double modulo) {
        super(direcao.matrizVetor.scale(modulo));
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.direcao = direcao;
        this.posicao = new Ponto3D(eixoDeCoordenadas,0.0,0.0,0.0);
    }

    public Vetor3D(Ponto3D posicaoCabeca) {
        super(posicaoCabeca);
        this.eixoDeCoordenadas = posicaoCabeca.eixoDeCoordenadas;
        this.direcao = new Direcao3D(posicaoCabeca);
        this.posicao = new Ponto3D(0.0,0.0,0.0);
    }

    public Vetor3D(Ponto3D posicaoCabeca, Ponto3D posicaoCauda) {
        super(posicaoCabeca.subtraiPonto3D(posicaoCauda));
        this.eixoDeCoordenadas = posicaoCabeca.eixoDeCoordenadas;
        this.direcao = new Direcao3D(posicaoCabeca.subtraiPonto3D(posicaoCauda));
        this.posicao = posicaoCauda;
    }

    public Vetor3D(Vetor3D posicaoCabeca, Vetor3D posicaoCauda) {
        super(posicaoCabeca.subtraiVetor(posicaoCauda).matrizVetor);
        this.eixoDeCoordenadas = posicaoCabeca.eixoDeCoordenadas;
        this.direcao = new Direcao3D(posicaoCabeca.subtraiVetor(posicaoCauda).matrizVetor);
        this.posicao = new Ponto3D(posicaoCauda.matrizVetor);
    }

    public Vetor3D(SimpleMatrix matrizVetor) {
        super(matrizVetor);
        this.direcao = new Direcao3D(matrizVetor);
        this.posicao = new Ponto3D(0.0,0.0,0.0);
    }

    public Vetor3D(SimpleMatrix matrizVetor, Ponto3D posicao) {
        super(matrizVetor);
        this.direcao = new Direcao3D(matrizVetor);
        this.posicao = posicao;
    }

    public Vetor3D(EixoDeCoordenadas3D eixoDeCoordenadas, SimpleMatrix matrizVetor) {
        super(matrizVetor);
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.direcao = new Direcao3D(matrizVetor);
        this.posicao = new Ponto3D(0.0,0.0,0.0);
    }

    public Vetor3D(Double componenteX, Double componenteY, Double componenteZ) {
        super(componenteX, componenteY, componenteZ);
        this.direcao = new Direcao3D(componenteX, componenteY, componenteZ);
        this.posicao = new Ponto3D(0.0, 0.0, 0.0);
    }

    public Vetor3D setComponenteX(double componenteX) {
        return new Vetor3D(this.eixoDeCoordenadas, new SimpleMatrix(new double[][]{{componenteX},{this.matrizVetor.get(1)},{this.matrizVetor.get(2)}}));
    }

    public Vetor3D setComponenteY(double componenteY) {
        return new Vetor3D(this.eixoDeCoordenadas, new SimpleMatrix(new double[][]{{this.matrizVetor.get(0)},{componenteY},{this.matrizVetor.get(2)}}));
    }

    public Vetor3D setComponenteZ(double componenteZ) {
        return new Vetor3D(this.eixoDeCoordenadas, new SimpleMatrix(new double[][]{{this.matrizVetor.get(0)},{this.matrizVetor.get(1)},{componenteZ}}));
    }

    public Vetor3D escala(Double fatorDeEscala) {
        return new Vetor3D(this.direcao, this.modulo*fatorDeEscala, this.posicao);
    }

    public Vetor3D descrevePontoEmEixoDeCoordenadasPrincipal() {
        return new Vetor3D(this.eixoDeCoordenadas.matrizEixo.mult(this.matrizVetor))
                .somaVetor(this.eixoDeCoordenadas.origem);
    }

    public Ponto3D getPontoCabeca() {
        return new Ponto3D(this.matrizVetor.get(0) + this.posicao.x, this.matrizVetor.get(1) + this.posicao.y, this.matrizVetor.get(2) + this.posicao.z);
    }


    public LineStrip getLineStripSeta(Color cor, float espessura) {
        Ponto3D pontoCabeca = this.getPontoCabeca();
        LineStrip lineStrips = this.converteParaLineStrip(cor,espessura);

        Vetor3D direcaoPerpendicular = this.direcao.getDirecaoPerpendicular().escala(this.modulo*0.05);

        LineStrip linhaCabecaSetaVetor1 = new LineStrip();
        linhaCabecaSetaVetor1.add(new Point(new Coord3d(pontoCabeca.x,pontoCabeca.y,pontoCabeca.z)));
        linhaCabecaSetaVetor1.add(new Point(new Coord3d((pontoCabeca.x*5 + this.posicao.x)/6 + direcaoPerpendicular.matrizVetor.get(0), (pontoCabeca.y*5 + this.posicao.y)/6 + direcaoPerpendicular.matrizVetor.get(1), (pontoCabeca.z*5 + this.posicao.z)/6 + direcaoPerpendicular.matrizVetor.get(2))));
        linhaCabecaSetaVetor1.setWireframeDisplayed(true);
        linhaCabecaSetaVetor1.setWireframeColor(cor);
        linhaCabecaSetaVetor1.setWireframeWidth(espessura);

        LineStrip linhaCabecaSetaVetor2 = new LineStrip();
        linhaCabecaSetaVetor2.add(new Point(new Coord3d(pontoCabeca.x,pontoCabeca.y,pontoCabeca.z)));
        linhaCabecaSetaVetor2.add(new Point(new Coord3d((pontoCabeca.x*5 + this.posicao.x)/6 - direcaoPerpendicular.matrizVetor.get(0), (pontoCabeca.y*5 + this.posicao.y)/6 - direcaoPerpendicular.matrizVetor.get(1), (pontoCabeca.z*5 + this.posicao.z)/6 - direcaoPerpendicular.matrizVetor.get(2))));
        linhaCabecaSetaVetor2.setWireframeDisplayed(true);
        linhaCabecaSetaVetor2.setWireframeColor(cor);
        linhaCabecaSetaVetor2.setWireframeWidth(espessura);

        lineStrips.addAll(linhaCabecaSetaVetor1);
        lineStrips.addAll(linhaCabecaSetaVetor2);

        return lineStrips;
    }

    public LineStrip converteParaLineStrip(Color cor, float espessura) {
        LineStrip linhasVetor = new LineStrip();
        linhasVetor.add(this.posicao.converteParaPoint());
        linhasVetor.add(new Point(new Coord3d(this.matrizVetor.get(0) + this.posicao.x,this.matrizVetor.get(1) + this.posicao.y,this.matrizVetor.get(2) + this.posicao.z)));
        linhasVetor.setWireframeDisplayed(true);
        linhasVetor.setWireframeColor(cor);
        linhasVetor.setWireframeWidth(espessura);

        return linhasVetor;
    }

    public void plotVetor3D(Color cor, float espessura) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        chart.getScene().getGraph().add(this.getLineStripSeta(cor, espessura));
        ChartLauncher.openChart(chart);
    }
}

package GeometriaBase.Espaco;

import org.ejml.simple.SimpleMatrix;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.AWTView;

import java.util.Arrays;
import java.util.List;

import static util.MetodosEstaticos.geraMatrizDeRotacao;

public class EixoDeCoordenadas3D {
    public Ponto3D origem;
    public Direcao3D eixoX;
    public Direcao3D eixoY;
    public Direcao3D eixoZ;
    public SimpleMatrix matrizEixo;

    public static final EixoDeCoordenadas3D eixoDeCoordenadasPrincipal = new EixoDeCoordenadas3D(Direcoes3D.X, Direcoes3D.Y, Direcoes3D.Z);
    public static final EixoDeCoordenadas3D eixoDeCoordenadasEixoDeRotacao = new EixoDeCoordenadas3D(Direcoes3D.Z, Direcoes3D.X);

    public EixoDeCoordenadas3D(Ponto3D origem, Direcao3D eixoX, Direcao3D eixoY, Direcao3D eixoZ) {
        this.origem = origem;
        this.eixoX = eixoX;
        this.eixoY = eixoY;
        this.eixoZ = eixoZ;
        this.matrizEixo = eixoX.matrizVetor
                .combine(0,1,eixoY.matrizVetor)
                .combine(0,2,eixoZ.matrizVetor);
    }

    public EixoDeCoordenadas3D(Ponto3D origem, Direcao3D eixoX, Direcao3D eixoY) {
        this.origem = origem;
        this.eixoX = eixoX;
        this.eixoY = eixoY;
        this.eixoZ = eixoX.produtoVetorial(eixoY).direcao;
        this.matrizEixo = eixoX.matrizVetor
                .combine(0,1,eixoY.matrizVetor)
                .combine(0,2,eixoZ.matrizVetor);
    }

    public EixoDeCoordenadas3D(Direcao3D eixoX, Direcao3D eixoY, Direcao3D eixoZ) {
        this.eixoX = eixoX;
        this.eixoY = eixoY;
        this.eixoZ = eixoZ;
        this.matrizEixo = eixoX.matrizVetor
                .combine(0,1,eixoY.matrizVetor)
                .combine(0,2,eixoZ.matrizVetor);
        this.origem = new Ponto3D(0.0,0.0,0.0);
    }

    public EixoDeCoordenadas3D(Direcao3D eixoX, Direcao3D eixoY) {
        this.eixoX = eixoX;
        this.eixoY = eixoY;
        this.eixoZ = eixoX.produtoVetorial(eixoY).direcao;
        this.matrizEixo = eixoX.matrizVetor
                .combine(0,1,eixoY.matrizVetor)
                .combine(0,2,eixoZ.matrizVetor);
        this.origem = new Ponto3D(0.0,0.0,0.0);
    }

    public EixoDeCoordenadas3D(Ponto3D origem, SimpleMatrix matrizEixo) {
        this.matrizEixo = matrizEixo;
        this.origem = origem;
        this.eixoX = new Direcao3D(matrizEixo.extractVector(false,0));
        this.eixoY = new Direcao3D(matrizEixo.extractVector(false,1));
        this.eixoZ = new Direcao3D(matrizEixo.extractVector(false,2));
    }

    public EixoDeCoordenadas3D(Ponto3D origem, EixoDeCoordenadas3D eixoDeCoordenadas) {
        this.origem = origem;
        this.eixoX = eixoDeCoordenadas.eixoX;
        this.eixoY = eixoDeCoordenadas.eixoY;
        this.eixoZ = eixoDeCoordenadas.eixoZ;
        this.matrizEixo = eixoDeCoordenadas.matrizEixo;
    }

    public EixoDeCoordenadas3D setOrigem(Ponto3D origem) {
        return new EixoDeCoordenadas3D(origem, this.matrizEixo);
    }

    public boolean equals(EixoDeCoordenadas3D eixoDeCoordenadas) {
        return this.matrizEixo.equals(eixoDeCoordenadas.matrizEixo);
    }

    public EixoDeCoordenadas3D rotacionaEmTornoDeEixoEmOrigemPrincipal(Vetor3DBase eixoDeRotacao, Double anguloDeRotacaoEmGraus) {
        Double anguloDeRotacaoEmRadianos = Math.PI*anguloDeRotacaoEmGraus/180;
        SimpleMatrix matrizDeRotacao = geraMatrizDeRotacao(eixoDeRotacao, -anguloDeRotacaoEmRadianos);
        return new EixoDeCoordenadas3D(new Ponto3D(matrizDeRotacao.mult(this.origem.converteParaVetorColuna())), matrizDeRotacao.mult(this.matrizEixo));
    }

    public List<LineStrip> getLinhasParaPlot(double escala, float espessura) {
        return Arrays.asList(this.eixoX.getLineStrip(Color.BLUE, espessura, escala,this.origem),
                this.eixoY.getLineStrip(Color.GREEN, espessura, escala, this.origem),
                this.eixoZ.getLineStrip(Color.RED, espessura, escala, this.origem));
    }

    public void plot(double escala) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        chart.getScene().getGraph().add(this.getLinhasParaPlot(escala , 1.0f));
        view.setScaleX(new Scale(-1,1));
        view.setScaleY(new Scale(-1,1));
        view.setScaleZ(new Scale(-1,1));
        ChartLauncher.openChart(chart);
    }

    public void plotComparacaoEixos(EixoDeCoordenadas3D eixoDeCoordenadas, double escala) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        chart.getScene().getGraph().add(this.getLinhasParaPlot(escala, 3.0f));
        chart.getScene().getGraph().add(eixoDeCoordenadas.getLinhasParaPlot(escala,1.0f));
        view.setScaleX(new Scale(-1.1*escala,1.1*escala));
        view.setScaleY(new Scale(-1.1*escala,1.1*escala));
        view.setScaleZ(new Scale(-1.1*escala,1.1*escala));
        ChartLauncher.openChart(chart);
    }
}

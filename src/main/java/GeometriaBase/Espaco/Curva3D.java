package GeometriaBase.Espaco;

import static util.MetodosEstaticos.geraMatrizDeRotacao;

import GeometriaBase.Plano.Curva2D;
import org.ejml.simple.SimpleMatrix;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.AWTView;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Curva3D {
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;
    public List<Ponto3D> pontos;
    private List<Double> pontosX;
    private List<Double> pontosY;
    private List<Double> pontosZ;
    private LineStrip linhaParaPlot;
    private List<Point> pontosLinhaParaPlot;

    public List<Double> getPontosX() {
        return pontosX;
    }

    public List<Double> getPontosY() {
        return pontosY;
    }

    public List<Double> getPontosZ() {
        return pontosZ;
    }

    public LineStrip getLinhaParaPlot() {
        return linhaParaPlot;
    }

    public List<Point> getPontosLinhaParaPlot() {
        return pontosLinhaParaPlot;
    }

    public Curva3D(List<Ponto3D> pontos) {
        this.pontos = pontos;
        pontosParaXYZ();
        geraLinhaParaPlot();
    }

    public Curva3D(EixoDeCoordenadas3D eixoDeCoordenadas, List<Ponto3D> pontos) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.pontos = pontos;
        pontosParaXYZ();
        geraLinhaParaPlot();
    }

    public Curva3D(List<Double> pontosX, List<Double> pontosY, List<Double> pontosZ) {
        this.pontosX = pontosX;
        this.pontosY = pontosY;
        this.pontosZ = pontosZ;
        XYZParaPontos();
        geraLinhaParaPlot();
    }

    public Curva3D(EixoDeCoordenadas3D eixoDeCoordenadas, List<Double> pontosX, List<Double> pontosY, List<Double> pontosZ) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.pontosX = pontosX;
        this.pontosY = pontosY;
        this.pontosZ = pontosZ;
        XYZParaPontos();
        geraLinhaParaPlot();
    }

    public Curva3D(Curva2D curva2D, Double distanciaEmZ, Double anguloDeRotacaoEmTornoDeY) {
        Curva3D curva = new Curva3D(curva2D.getPontosX(), curva2D.getPontosY(),IntStream.range(0,curva2D.getPontosX().size()).mapToObj(i -> distanciaEmZ).collect(Collectors.toList()))
                    .rotacionaCurvaEmTornoDeEixo(Direcoes3D.Y, anguloDeRotacaoEmTornoDeY);
        this.pontos = curva.pontos;
        this.pontosX = curva.pontosX;
        this.pontosY = curva.pontosY;
        this.pontosZ = curva.pontosZ;
        geraLinhaParaPlot();
    }

    public Curva3D(Curva3D curva) {
        this.eixoDeCoordenadas = curva.eixoDeCoordenadas;
        this.pontos = curva.pontos;
        this.pontosX = curva.pontosX;
        this.pontosY = curva.pontosY;
        this.pontosZ = curva.pontosZ;
        this.pontosLinhaParaPlot = curva.pontosLinhaParaPlot;
        this.linhaParaPlot = curva.linhaParaPlot;
    }

    private void pontosParaXYZ() {
        this.pontosX = this.pontos.stream().map(p -> p.x).collect(Collectors.toList());
        this.pontosY = this.pontos.stream().map(p -> p.y).collect(Collectors.toList());
        this.pontosZ = this.pontos.stream().map(p -> p.z).collect(Collectors.toList());
    }

    private void XYZParaPontos() {
        this.pontos = new ArrayList<Ponto3D>();
        for (int i = 0; i < this.pontosX.size(); i++) {
            this.pontos.add( new Ponto3D(this.eixoDeCoordenadas, this.pontosX.get(i), this.pontosY.get(i), this.pontosZ.get(i)));
        }
    }

    private void geraLinhaParaPlot() {
        this.linhaParaPlot = new LineStrip();
        this.pontosLinhaParaPlot = pontos.stream().map( p -> new Point(new Coord3d(p.x,p.y,p.z), Color.BLUE)).collect(Collectors.toList());
        this.linhaParaPlot.addAll(this.pontosLinhaParaPlot);
    }

    public Ponto3D getMaxX() {
        return this.pontos.stream().max(Comparator.comparing(p -> p.x)).get();
    }

    public Ponto3D getMinX() {
        return this.pontos.stream().min(Comparator.comparing(p -> p.x)).get();
    }

    public Ponto3D getMaxY() {
        return this.pontos.stream().max(Comparator.comparing(p -> p.y)).get();
    }

    public Ponto3D getMinY() {
        return this.pontos.stream().min(Comparator.comparing(p -> p.y)).get();
    }

    public Ponto3D getMaxZ() {
        return this.pontos.stream().max(Comparator.comparing(p -> p.z)).get();
    }

    public Ponto3D getMinZ() {
        return this.pontos.stream().min(Comparator.comparing(p -> p.z)).get();
    }

    public Double getDistanciaDoPontoMaisDistanteDaOrigem() {
        return this.pontos.stream().map(Ponto3D::getDistanciaDaOrigem).max(Comparator.comparingDouble(p->p)).get();
    }

    public Curva3D rotacionaCurvaEmTornoDeEixo(Vetor3DBase eixo, Double anguloDeRotacaoEmGraus) {
        Double anguloEmRadianos = Math.PI*anguloDeRotacaoEmGraus/180;
        SimpleMatrix matrizDeRotacao = geraMatrizDeRotacao(eixo,anguloEmRadianos);
        return new Curva3D(this.pontos.stream().map(p -> p.rotacionaPontoUtilizandoMatrizDeRotacao(matrizDeRotacao)).collect(Collectors.toList()));
    }

    public Curva3D transladaPontos(Ponto3D ponto) {
        return new Curva3D(this.pontos.stream().map(p -> p.somaPonto3D(ponto)).collect(Collectors.toList()));
    }

    public Curva3D descrevePontosEmEixoDeCoordenadasPrincipal() {
        return new Curva3D(
                this.pontos
                        .parallelStream()
                        .map(p -> new Ponto3D(
                                        this.eixoDeCoordenadas.matrizEixo
                                        .mult(p.converteParaVetorColuna())
                                        .plus(this.eixoDeCoordenadas.origem.converteParaVetorColuna())
                        ))
                        .collect(Collectors.toList())
        );
    }

    public Curva3D descrevePontosEmNovoEixoDeCoordenadas(EixoDeCoordenadas3D novoEixoDeCoordenadas) {
        SimpleMatrix matrizNovoEixoInvertida = novoEixoDeCoordenadas.matrizEixo.invert();
        SimpleMatrix matrizDeTransformacao = matrizNovoEixoInvertida.mult(this.eixoDeCoordenadas.matrizEixo);
        return new Curva3D( novoEixoDeCoordenadas,
                        this.pontos
                        .parallelStream()
                        .map(p -> new Ponto3D( novoEixoDeCoordenadas,
                                        matrizDeTransformacao
                                        .mult(p.converteParaVetorColuna())
                                        .plus(matrizNovoEixoInvertida.mult(
                                                novoEixoDeCoordenadas.origem.converteParaVetorColuna().minus(this.eixoDeCoordenadas.origem.converteParaVetorColuna())
                                        ))

                        ))
                        .collect(Collectors.toList())
        );
    }

    public Curva3D setEixoDeCoordenada(EixoDeCoordenadas3D eixoDeCoordenadas) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        return this;
    }

    public void plot() throws Exception {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        view.setScaleX(new Scale(this.getMinX().x - Math.abs(this.getMinX().x)*2,this.getMaxX().x*1.1));
        view.setScaleY(new Scale(this.getMinY().y - Math.abs(this.getMinY().y)*2,this.getMaxY().y*1.1));
        view.setScaleZ(new Scale(this.getMinZ().z - Math.abs(this.getMinZ().z)*2,this.getMaxZ().z*1.1));

        chart.getScene().getGraph().add(this.linhaParaPlot);
        ChartLauncher.openChart(chart);
    }

    public void plot(Double minX, Double maxX, Double minY, Double maxY, Double minZ, Double maxZ) throws Exception {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        view.setScaleX(new Scale(minX,maxX));
        view.setScaleY(new Scale(minY,maxY));
        view.setScaleZ(new Scale(minZ,maxZ));

        chart.getScene().getGraph().add(this.linhaParaPlot);
        ChartLauncher.openChart(chart);
    }

    public void plotComEixosIguals(Ponto3D origem, Double escala) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        view.setScaleX(new Scale(origem.x - escala, origem.x + escala));
        view.setScaleY(new Scale(origem.y - escala, origem.y + escala));
        view.setScaleZ(new Scale(origem.z - escala, origem.z + escala));

        chart.getScene().getGraph().add(this.linhaParaPlot);
        ChartLauncher.openChart(chart);
    }

}

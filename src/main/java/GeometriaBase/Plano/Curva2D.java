package GeometriaBase.Plano;

import GeometriaBase.Espaco.*;
import org.ejml.simple.SimpleMatrix;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Curva2D {
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;
    private List<Ponto2D> pontos;
    private List<Double> pontosX;
    private List<Double> pontosY;

    public Curva2D(List<Ponto2D> pontos) {
        this.pontos = pontos.stream().map(p -> new Ponto2D(p.x, p.y)).collect(Collectors.toList());
        pontosParaXeY();
    }

    public Curva2D(EixoDeCoordenadas3D eixoDeCoordenadas, List<Ponto2D> pontos) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.pontos = pontos.stream().map(p -> new Ponto2D(eixoDeCoordenadas,p.x, p.y)).collect(Collectors.toList());
        pontosParaXeY();
    }

    public Curva2D(List<Double> pontosX, List<Double> pontosY) {
        this.pontosX = pontosX;
        this.pontosY = pontosY;
        xeYParaPontos();
    }

    public Curva2D(EixoDeCoordenadas3D eixoDeCoordenadas, List<Double> pontosX, List<Double> pontosY) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.pontosX = pontosX;
        this.pontosY = pontosY;
        xeYParaPontos();
    }

    public Curva2D(Curva2D curva) {
        this.eixoDeCoordenadas = curva.eixoDeCoordenadas;
        this.pontos = curva.pontos;
        pontosParaXeY();
    }

    public List<Ponto2D> getPontos() {
        return this.pontos;
    }

    public List<Double> getPontosX() {
        return this.pontosX;
    }

    public List<Double> getPontosY() {
        return this.pontosY;
    }

    public Curva2D concat(Curva2D geometria) {
        List<Ponto2D> novosPontos = new ArrayList<Ponto2D>(this.pontos);
        novosPontos.addAll(geometria.getPontos());
        return new Curva2D(novosPontos);
    }

    private void pontosParaXeY() {
        this.pontosX = this.pontos.stream().map( ponto -> ponto.x).collect(Collectors.toList());
        this.pontosY = this.pontos.stream().map( ponto -> ponto.y).collect(Collectors.toList());
    }

    private void xeYParaPontos() {
        this.pontos = new ArrayList<Ponto2D>();
        for (int i = 0; i < this.pontosX.size(); i++) {
            this.pontos.add( new Ponto2D(this.pontosX.get(i), this.pontosY.get(i)) );
        }
    }

    public Ponto2D pontoMinimoX() {
        return this.pontos.stream().min((p1,p2) -> Double.compare(p1.x, p2.x)).get();
    }

    public Ponto2D pontoMaximoX() {
        return this.pontos.stream().max((p1,p2) -> Double.compare(p1.x, p2.x)).get();
    }

    public Ponto2D pontoMinimoY() {
        return this.pontos.stream().min((p1,p2) -> Double.compare(p1.y, p2.y)).get();
    }

    public Ponto2D pontoMaximoY() {
        return this.pontos.stream().max((p1,p2) -> Double.compare(p1.y, p2.y)).get();
    }

    public Curva2D transladaPontos(Double translacaoEmX, Double translacaoEmY) {
        return new Curva2D(this.pontosX.stream().map(x -> x + translacaoEmX).collect(Collectors.toList()), this.pontosY.stream().map(y -> y + translacaoEmY).collect(Collectors.toList()));
    }

    public Curva2D rotacionaPontosPelaOrigem(Double grausRotacionadosAntiHorario) {
        double radianosRotacionadosAntiHorario = Math.toRadians(grausRotacionadosAntiHorario);
        return new Curva2D(this.pontos.stream()
                .map(p -> new Ponto2D(p.x*Math.cos(radianosRotacionadosAntiHorario)-p.y*Math.sin(radianosRotacionadosAntiHorario), p.x*Math.sin(radianosRotacionadosAntiHorario) + p.y*Math.cos(radianosRotacionadosAntiHorario)))
                .collect(Collectors.toList()));
    }

    public Curva3D posicionaNoEspaco(Ponto3D novaOrigem) {
        return new Curva3D(this.pontos.stream().map(p -> new Ponto3D(p.x + novaOrigem.x, p.y + novaOrigem.y, novaOrigem.z)).collect(Collectors.toList()));
    }

    public Curva3D posicionaNoEspaco(Ponto3D novaOrigem, Direcao3D novaDirecaoZ) {
        return new Curva3D(this.pontos.stream()
                .map(p -> new Ponto3D(p.x , p.y, 0.0))
                .collect(Collectors.toList()))
                .rotacionaCurvaEmTornoDeEixo(
                        Direcoes3D.Z.produtoVetorial(novaDirecaoZ),Direcoes3D.Z.anguloEntreVetores(novaDirecaoZ)*180/Math.PI
                )
                .transladaPontos(novaOrigem);
    }

    public CurvaEmPlano3D descrevePontosEmEixoDeCoordenadasPrincipal() {
        return new CurvaEmPlano3D(this);
    }

    public CurvaEmPlano3D descrevePontosEmNovoEixoDeCoordenadas(EixoDeCoordenadas3D novoEixoDeCoordenadas) {
        SimpleMatrix matrizNovoEixoInversa = novoEixoDeCoordenadas.matrizEixo.invert();
        SimpleMatrix matrizDeTransformacao = matrizNovoEixoInversa.mult(this.eixoDeCoordenadas.matrizEixo);
        return new CurvaEmPlano3D(this.eixoDeCoordenadas, this);
    }

    public Curva2D adicionaPontoAoFinal(Ponto2D ponto) {
        List<Ponto2D> novosPontos = new ArrayList<Ponto2D>(this.pontos);
        novosPontos.add(ponto);
        return new Curva2D(novosPontos);
    }

    public Curva2D subtraiCurvaY(Curva2D curva ) {
        return new Curva2D(IntStream
            .range(0, this.pontos.size()-1)
            .mapToObj(i -> this.pontos.get(i).subtraiPontoY(curva.getPontos().get(i)))
            .collect(Collectors.toList()));
    }

    public Curva2D somaCurvaY(Curva2D curva) {
        return new Curva2D(IntStream
                .range(0, this.pontos.size()-1)
                .mapToObj(i -> this.pontos.get(i).somaPontoY(curva.getPontos().get(i)))
                .collect(Collectors.toList()));
    }

    public Curva2D divideCurvaY(Double divisor) {
        return new Curva2D(this.pontosX, this.pontosY.stream().map(y -> y/divisor).collect(Collectors.toList()));
    }

    public Curva2D multiplicaCurvaY(Double multiplicador) {
        return new Curva2D(this.pontosX, this.pontosY.stream().map(y -> y*multiplicador).collect(Collectors.toList()));
    }

    public Curva2D multiplicaCurvaX(Double multiplicador) {
        return new Curva2D(this.pontosX.stream().map(x -> multiplicador*x).collect(Collectors.toList()), this.pontosY);
    }

    public Curva2D setEixoDeCoordenadas(EixoDeCoordenadas3D eixoDeCoordenadas) {
        return new Curva2D(eixoDeCoordenadas, this.pontos);
    }

    public void plot(String nomePlot, Double xMin, Double xMax, Double yMin, Double yMax) {
        XYChart chart = QuickChart.getChart("plot", "X", "Y", nomePlot, this.pontosX, this.pontosY);
        chart.getStyler().setXAxisMax(xMax);
        chart.getStyler().setXAxisMin(xMin);
        chart.getStyler().setYAxisMin(yMin);
        chart.getStyler().setYAxisMax(yMax);
        new SwingWrapper(chart).displayChart();
    }

    public void plot(String nomePlot) {
        XYChart chart = QuickChart.getChart("plot", "X", "Y", nomePlot, this.pontosX, this.pontosY);
        chart.getStyler().setXAxisMax(this.pontoMaximoX().x);
        chart.getStyler().setXAxisMin(this.pontoMinimoX().x);
        chart.getStyler().setYAxisMin(this.pontoMinimoY().y);
        chart.getStyler().setYAxisMax(this.pontoMaximoY().y);
        new SwingWrapper(chart).displayChart();
    }
}

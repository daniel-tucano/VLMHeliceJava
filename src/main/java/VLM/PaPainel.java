package VLM;

import Aerodinamica.Fluido;
import GeometriaBase.Espaco.Vetor3D;
import GeometriaBase.Espaco.Vetor3DBase;
import GeometriaHelice.Pa;
import org.ejml.simple.SimpleMatrix;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Quad;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.scene.Graph;
import org.jzy3d.plot3d.rendering.view.AWTView;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaPainel {
    public Pa pa;
    public List<EstacaoPainel> paineisEstacoes = new ArrayList<>();
    double tracao;
    double torque;
    public Integer numeroDePaineisPorEstacao;

    public PaPainel(Pa pa, Integer numeroDePaineisPorEstacao, Integer numeroDePontosEsteira) {
        this.pa = pa;
        this.numeroDePaineisPorEstacao = numeroDePaineisPorEstacao;
        pa.seccoesDeTrasicao.forEach(seccao -> seccao.estacoes.forEach(estacao -> paineisEstacoes.add( new EstacaoPainel(estacao,numeroDePaineisPorEstacao,numeroDePontosEsteira))));
    }

    public PaPainel() {}

    public SimpleMatrix getInfluenciaDePaNessaPa(PaPainel paInfluenciadora) {
        SimpleMatrix matrizDeInfluenciaPa = new SimpleMatrix(this.pa.numeroDeEstacoes*this.numeroDePaineisPorEstacao, this.pa.numeroDeEstacoes*this.numeroDePaineisPorEstacao);
        IntStream.range(0,this.pa.numeroDeEstacoes).forEach(iEstacaoPaInfluenciada -> {
            IntStream.range(0,numeroDePaineisPorEstacao).forEach(iPainelEstacaoPaInfluenciada -> {

                IntStream.range(0, paInfluenciadora.pa.numeroDeEstacoes).forEach(iEstacaoPaInfluenciadora -> {
                    IntStream.range(0, paInfluenciadora.numeroDePaineisPorEstacao).forEach(iPainelEstacaoPaInfluenciadora -> {
                        matrizDeInfluenciaPa.set(iPainelEstacaoPaInfluenciada + this.numeroDePaineisPorEstacao*iEstacaoPaInfluenciada,
                                                 iPainelEstacaoPaInfluenciadora + paInfluenciadora.numeroDePaineisPorEstacao*iEstacaoPaInfluenciadora,
                                                    this.paineisEstacoes.get(iEstacaoPaInfluenciada).paineis.get(iPainelEstacaoPaInfluenciada)
                                                    .calculaInfluenciaDeOutroPainelNessePainel(
                                                            paInfluenciadora.paineisEstacoes.get(iEstacaoPaInfluenciadora).paineis.get(iPainelEstacaoPaInfluenciadora)
                                                    ));
                    });
                });

            });
        });

        return matrizDeInfluenciaPa;
    }

    public void calculaForca(Fluido fluido) {
        this.paineisEstacoes.forEach(estacaoPainel -> estacaoPainel.calculaForca(fluido));
        this.tracao = this.paineisEstacoes.stream().map(estacaoPainel -> estacaoPainel.tracao).reduce(Double::sum).get();
        this.torque = this.paineisEstacoes.stream().map(estacaoPainel -> estacaoPainel.torque).reduce(Double::sum).get();
    }

    public List<Quad> getQuadsPa(boolean wireframeDisplayed) {
        List<Quad> quadsPa = new ArrayList<>();
        this.paineisEstacoes.forEach(p -> quadsPa.addAll(p.getQuadsPaineis(wireframeDisplayed)));
        return quadsPa;
    }

    public List<LineStrip> getLineStripsVortices() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineisEstacoes.forEach(est -> lineStrips.addAll(est.getLineStripsVortices()));
        return lineStrips;
    }

    public List<Point> getPointsPontosDeControle() {
        List<Point> pontos = new ArrayList<>();
        this.paineisEstacoes.forEach(est -> pontos.addAll(est.getPointsPontosDeControle()));
        return pontos;
    }

    public List<LineStrip> getLineStripsVetoresNormais() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineisEstacoes.forEach(est -> lineStrips.addAll(est.getLineStripsVetoresNormais()));
        return lineStrips;
    }

    public List<LineStrip> getLineStripsVetoresForcaPaineis(Double escala) {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineisEstacoes.forEach(est -> lineStrips.addAll(est.getLineStripsVetoresForcaPaineis(escala)));
        return lineStrips;
    }

    public List<LineStrip> getLineStripsVelocidadeLocalUmQuartoDoPainel() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineisEstacoes.forEach(est -> lineStrips.addAll(est.getLineStripsVelocidadeLocalUmQuartoDoPainel()));
        return lineStrips;
    }

    public void plotPaineis(boolean wireframeDisplayed, boolean mostraVortices, boolean mostraVelocidadesLocais, boolean mostraForcasPaineis, Double escalaForca) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        Graph graph = chart.getScene().getGraph();
        graph.add(this.getQuadsPa(wireframeDisplayed));
        if (mostraVortices) {
            graph.add(this.getLineStripsVortices());
        }
        if (mostraVelocidadesLocais) {
            graph.add(this.getLineStripsVelocidadeLocalUmQuartoDoPainel());
        }
        if (mostraForcasPaineis) {
            graph.add(this.getLineStripsVetoresForcaPaineis(escalaForca));
        }
        view.setScaleX(new Scale(-this.pa.raio/2,this.pa.raio/2));
        view.setScaleY(new Scale(-this.pa.raio/2,this.pa.raio/2));
        view.setScaleZ(new Scale(0,this.pa.raio));
        ChartLauncher.openChart(chart);
    }

    public void plotTracaoEstacao() {
        List<Double> tracoesEstacoes = this.paineisEstacoes.stream().map(estacaoPainel -> estacaoPainel.tracao).collect(Collectors.toList());
        Double maxTracaoEstacao = tracoesEstacoes.stream().max(Double::compareTo).get();
        Double minTracaoEstacao = tracoesEstacoes.stream().min(Double::compareTo).get();
        XYChart chart = QuickChart.getChart("plot", "Estacao", "Tracao [N]", "Tração x estacao"
                , IntStream.range(0, this.pa.numeroDeEstacoes).mapToObj(a -> (double) a).collect(Collectors.toList())
                , tracoesEstacoes);
        chart.getStyler().setXAxisMin(0.0);
        chart.getStyler().setXAxisMax(this.pa.numeroDeEstacoes.doubleValue());
        chart.getStyler().setYAxisMin(minTracaoEstacao < 0.0 ? minTracaoEstacao : 0.0);
        chart.getStyler().setYAxisMax(maxTracaoEstacao > 0.3 ? maxTracaoEstacao : 0.3);
        new SwingWrapper(chart).displayChart();
    }
}

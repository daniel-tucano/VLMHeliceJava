package GeometriaHelice;

import Aerodinamica.AerofolioPuro;
import Estrutura.Material;
import GeometriaBase.Espaco.Direcao3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Plano.Funcao2D;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.Quad;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.scene.Graph;
import org.jzy3d.plot3d.rendering.view.AWTView;

import java.util.ArrayList;
import java.util.List;

public class Pa {
    public Double raio;
    public Double raioHub;
    public Double anguloAxial;
    public Integer numeroDeEstacoes;
    public Integer numeroDePerfils;
    public List<Integer> indicesDosPerfisDeAerofolioPuro;
    public List<AerofolioPuro> aerofoliosPuros;
    public List<SeccaoDeTrasicao> seccoesDeTrasicao = new ArrayList<>();
    public Funcao2D distribuicaoCordaDimensionalizada;
    public Funcao2D distribuicaoAnguloBetaDimensionalizada;
    public Double dR;
    public Material material;
    public EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao = EixoDeCoordenadas3D.eixoDeCoordenadasEixoDeRotacao;

    public Pa(Double raio,
              Double raioHub,
              Double anguloAxial,
              List<AerofolioPuro> aerofoliosPuros,
              Integer numeroDeEstacoes,
              List<Integer> indicesDosPerfisDeAerofolioPuro,
              Funcao2D distribuicaoCordaDimensionalizada,
              Funcao2D distribuicaoAnguloBetaDimensionalizada,
              Material material,
              EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao ) {
        this.raio = raio;
        this.raioHub = raioHub;
        this.anguloAxial = anguloAxial;
        this.aerofoliosPuros = aerofoliosPuros;
        this.numeroDeEstacoes = numeroDeEstacoes;
        this.numeroDePerfils = numeroDeEstacoes + 1;
        this.dR = (raio - raioHub)/numeroDeEstacoes;
        this.indicesDosPerfisDeAerofolioPuro = indicesDosPerfisDeAerofolioPuro;
        this.distribuicaoCordaDimensionalizada = distribuicaoCordaDimensionalizada;
        this.distribuicaoAnguloBetaDimensionalizada = distribuicaoAnguloBetaDimensionalizada;
        this.material = material;
        this.eixoDeCoordenadasDeRotacao = eixoDeCoordenadasDeRotacao;

        for (int i = 0; i < indicesDosPerfisDeAerofolioPuro.size()-1; i++) {
            this.seccoesDeTrasicao.add(new SeccaoDeTrasicao(aerofoliosPuros.get(i),
                    aerofoliosPuros.get(i+1),
                    raioHub + this.dR*indicesDosPerfisDeAerofolioPuro.get(i),
                    indicesDosPerfisDeAerofolioPuro.get(i+1) - indicesDosPerfisDeAerofolioPuro.get(i),
                    this.anguloAxial,
                    this.dR,
                    this.distribuicaoCordaDimensionalizada,
                    this.distribuicaoAnguloBetaDimensionalizada,
                    this.material,
                    this.eixoDeCoordenadasDeRotacao));
        }
    }

    public Pa() {}

    public List<Quad> getQuadsPa(Boolean wireframeDisplayed) {
        List<Quad> quadsPa = new ArrayList<>();
        this.seccoesDeTrasicao.forEach(seccaoDeTrasicao -> {
            quadsPa.addAll(seccaoDeTrasicao.getQuadsSeccaoDeTransicao(wireframeDisplayed));
        });
        return quadsPa;
    }

    public void plotaPerfisPa() {
        AWTChart chart = new AWTChart(Quality.Fastest);
        this.seccoesDeTrasicao.forEach(s -> {
            s.perfis.forEach(p -> chart.getScene().getGraph().add(p.geometria.getLinhaParaPlot()));
        });
        AWTView view = chart.getAWTView();
        view.setScaleX(new Scale(-this.raio/2,this.raio/2));
        view.setScaleY(new Scale(-this.raio/2,this.raio/2));
        view.setScaleZ(new Scale(0,this.raio));
        ChartLauncher.openChart(chart);
    }

    public void plotSuperficiePa(Boolean wireframeDisplayed) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        Graph graph = chart.getScene().getGraph();
        this.getQuadsPa(wireframeDisplayed).forEach(graph::add);
        view.setScaleX(new Scale(-this.raio/2,this.raio/2));
        view.setScaleY(new Scale(-this.raio/2,this.raio/2));
        view.setScaleZ(new Scale(0,this.raio));
        ChartLauncher.openChart(chart);
    }
}

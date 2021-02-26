package GeometriaHelice;

import Aerodinamica.AerofolioPuro;
import Estrutura.Material;
import GeometriaBase.Espaco.Direcao3D;
import GeometriaBase.Espaco.Direcoes3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Plano.Funcao2D;
import GeometriaBase.Plano.FuncaoBezier;
import GeometriaBase.Plano.Ponto2D;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.Quad;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.scene.Graph;
import org.jzy3d.plot3d.rendering.view.AWTView;

import java.util.ArrayList;
import java.util.List;

public class Helice {
    public Double raio;
    public Double diametro;
    public Double raioHub;
    public Integer nPas;
    public Integer numeroDeEstacoes;
    public Integer numeroDePerfils;
    public List<Integer> indicesDosPerfisDeAerofolioPuro;
    public List<AerofolioPuro> aerofoliosPuros;
    public Funcao2D distribuicaoCordaNormalizada;
    public Funcao2D distribuicaoAnguloBetaNormalizada;
    public Funcao2D distribuicaoCordaDimensionalizada;
    public Funcao2D distribuicaoAnguloBetaDimensionalizada;
    public Material material;
    public List<Pa> pas = new ArrayList<>();
    public EixoDeCoordenadas3D eixoDeCoordenadasRotacao = EixoDeCoordenadas3D.eixoDeCoordenadasEixoDeRotacao;

    public Helice(Integer nPas,
                      Double raio,
                      Double raioHub,
                      Integer numeroDeEstacoes,
                      List<AerofolioPuro> aerofoliosPuros,
                      List<Integer> indicesDosPerfisDeAerofolioPuro,
                      List<Ponto2D> pontosDeControleDisctribuicaoNormalizadaCorda,
                      List<Ponto2D> pontosDeControleDisctribuicaoNormalizadaAnguloBeta,
                      Material material,
                      EixoDeCoordenadas3D eixoDeCoordenadasRotacao ) {
        this.nPas = nPas;
        this.raio = raio;
        this.raioHub = raioHub;
        this.diametro = raio*2;
        this.numeroDeEstacoes = numeroDeEstacoes;
        this.numeroDePerfils = numeroDeEstacoes + 1;
        this.aerofoliosPuros = aerofoliosPuros;
        this.indicesDosPerfisDeAerofolioPuro = indicesDosPerfisDeAerofolioPuro;
        this.distribuicaoCordaNormalizada = new FuncaoBezier(pontosDeControleDisctribuicaoNormalizadaCorda);
        this.distribuicaoAnguloBetaNormalizada = new FuncaoBezier(pontosDeControleDisctribuicaoNormalizadaAnguloBeta);
        this.distribuicaoCordaDimensionalizada = this.distribuicaoCordaNormalizada.escala(raio);
        this.distribuicaoAnguloBetaDimensionalizada = this.distribuicaoAnguloBetaNormalizada.escalaEmX(raio);
        this.material = material;
        this.eixoDeCoordenadasRotacao = eixoDeCoordenadasRotacao;

        for (int i = 0; i < nPas; i++) {
            this.pas.add( new Pa(raio,
                    raioHub,
                    i*360/nPas.doubleValue(),
                    aerofoliosPuros,
                    numeroDeEstacoes,
                    indicesDosPerfisDeAerofolioPuro,
                    distribuicaoCordaDimensionalizada,
                    distribuicaoAnguloBetaDimensionalizada,
                    material,
                    eixoDeCoordenadasRotacao));
        }
    }

    public Helice() {}

    public List<Quad> getQuadsHelice(Boolean wireframeDisplayed) {
        List<Quad> quadsPa = new ArrayList<>();
        this.pas.forEach(pa -> {
            quadsPa.addAll(pa.getQuadsPa(wireframeDisplayed));
        });
        return quadsPa;
    }

    public void plotPerfisHelice() {
        AWTChart chart = new AWTChart(Quality.Fastest);
        this.pas.forEach(pa -> {
            pa.seccoesDeTrasicao.forEach(s -> {
                s.perfis.forEach(perfil -> chart.getScene().getGraph().add(perfil.geometria.getLinhaParaPlot()));
            });
        });
        AWTView view = chart.getAWTView();
        view.setScaleX(new Scale(-this.raio,this.raio));
        view.setScaleY(new Scale(-this.raio,this.raio));
        view.setScaleZ(new Scale(-this.raio,this.raio));
        ChartLauncher.openChart(chart);
    }

    public void plotSuperficieHelice(Boolean wireframeDisplayed, boolean mostraEixoDeCoordenadas) throws Exception {
        double raioExternoHub = this.pas.get(0).seccoesDeTrasicao.get(0).perfis.get(0).geometria.getDistanciaDoPontoMaisDistanteDaOrigem();
        double raioInternoHub = raioExternoHub*0.3;
        AWTChart chart = new AWTChart(Quality.Nicest);
        AWTView view = chart.getAWTView();
        Graph graph = chart.getScene().getGraph();
        graph.add(this.getQuadsHelice(wireframeDisplayed));
        graph.add(new Hub(raioExternoHub,raioInternoHub,0.008, this.eixoDeCoordenadasRotacao).getQuads(Color.GRAY, false, Color.GRAY));
        if (mostraEixoDeCoordenadas) {
            graph.add(EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal.getLinhasParaPlot(this.raioHub*0.5, 3.0f));
        }
        view.setScaleX(new Scale(-this.raio,this.raio));
        view.setScaleY(new Scale(-this.raio,this.raio));
        view.setScaleZ(new Scale(-this.raio,this.raio));
        ChartLauncher.openChart(chart);
    }
}

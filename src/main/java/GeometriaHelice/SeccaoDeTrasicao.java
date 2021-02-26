package GeometriaHelice;

import Aerodinamica.AerofolioMesclado;
import Aerodinamica.AerofolioPuro;
import Estrutura.Material;
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

import static util.MetodosEstaticos.linspace;

public class SeccaoDeTrasicao {
    public AerofolioPuro aerofolioPuro1;
    public AerofolioPuro aerofolioPuro2;
    public Double raioInterno;
    public Double raioExterno;
    public Integer numeroDeEstacoes;
    public Double anguloAxial;
    public Double dR;
    public Material material;
    public EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao = EixoDeCoordenadas3D.eixoDeCoordenadasEixoDeRotacao;

    public List<Double> raiosPerfis;
    public List<Estacao> estacoes = new ArrayList<Estacao>();
    public List<Aerofolio> perfis = new ArrayList<Aerofolio>();;

    public SeccaoDeTrasicao(AerofolioPuro aerofolioPuro1, AerofolioPuro aerofolioPuro2, Double raioInterno, Double raioExterno, Integer numeroDeEstacoes, Double anguloAxial, Funcao2D distribuicaoCordaDimensionada, Funcao2D distribuicaoAnguloBetaDimensionada, Material material, EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao) {
        this.aerofolioPuro1 = aerofolioPuro1;
        this.aerofolioPuro2 = aerofolioPuro2;
        this.raioInterno = raioInterno;
        this.raioExterno = raioExterno;
        this.numeroDeEstacoes = numeroDeEstacoes;
        this.anguloAxial = anguloAxial;
        this.dR = (raioExterno - raioInterno)/numeroDeEstacoes;
        this.material = material;
        this.eixoDeCoordenadasDeRotacao = eixoDeCoordenadasDeRotacao;

        this.raiosPerfis = linspace(raioInterno,raioExterno,numeroDeEstacoes);

        this.perfis.add(new Aerofolio(aerofolioPuro1, distribuicaoCordaDimensionada.obtemPontoInterpolado(raioInterno).y, distribuicaoAnguloBetaDimensionada.obtemPontoInterpolado(raioInterno).y,raioInterno, anguloAxial, eixoDeCoordenadasDeRotacao));

        for (int i = 1; i < numeroDeEstacoes-2; i++) {
            AerofolioMesclado aerofolioMesclado = new AerofolioMesclado(aerofolioPuro1, aerofolioPuro2, (double) 1 - i/(numeroDeEstacoes-1));
            this.perfis.add(i, new Aerofolio(aerofolioMesclado, distribuicaoCordaDimensionada.obtemPontoInterpolado(raiosPerfis.get(i)).y, distribuicaoAnguloBetaDimensionada.obtemPontoInterpolado(raiosPerfis.get(i)).y,raiosPerfis.get(i), anguloAxial, eixoDeCoordenadasDeRotacao));
            this.estacoes.add(new Estacao(dR,raiosPerfis.get(i-1),material, this.perfis.get(i-1), this.perfis.get(i), eixoDeCoordenadasDeRotacao));
        }

        this.perfis.add(new Aerofolio(aerofolioPuro1, distribuicaoCordaDimensionada.obtemPontoInterpolado(raioExterno).y, distribuicaoAnguloBetaDimensionada.obtemPontoInterpolado(raioExterno).y,raioExterno, anguloAxial, eixoDeCoordenadasDeRotacao));
        this.estacoes.add(new Estacao(dR,raiosPerfis.get(numeroDeEstacoes-2),material, this.perfis.get(numeroDeEstacoes-2), this.perfis.get(numeroDeEstacoes-1), eixoDeCoordenadasDeRotacao));
    }

    public SeccaoDeTrasicao(AerofolioPuro aerofolioPuro1, AerofolioPuro aerofolioPuro2, Double raioInterno, Integer numeroDeEstacoes, Double anguloAxial, Double dR, Funcao2D distribuicaoCordaDimensionada, Funcao2D distribuicaoAnguloBetaDimensionada, Material material, EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao) {
        this.aerofolioPuro1 = aerofolioPuro1;
        this.aerofolioPuro2 = aerofolioPuro2;
        this.raioInterno = raioInterno;
        this.raioExterno = raioInterno + numeroDeEstacoes*dR;
        this.numeroDeEstacoes = numeroDeEstacoes;
        this.anguloAxial = anguloAxial;
        this.dR = dR;
        this.material = material;
        this.eixoDeCoordenadasDeRotacao = eixoDeCoordenadasDeRotacao;

        this.raiosPerfis = linspace(raioInterno,raioExterno,numeroDeEstacoes + 1);

        this.perfis.add(new Aerofolio(aerofolioPuro1, distribuicaoCordaDimensionada.obtemPontoInterpolado(raioInterno).y, distribuicaoAnguloBetaDimensionada.obtemPontoInterpolado(raioInterno).y,raioInterno, anguloAxial, eixoDeCoordenadasDeRotacao));

        for (int i = 1; i < numeroDeEstacoes; i++) {
            AerofolioMesclado aerofolioMesclado = new AerofolioMesclado(aerofolioPuro1, aerofolioPuro2, (double) 1 - i/(numeroDeEstacoes-1));
            this.perfis.add(i, new Aerofolio(aerofolioMesclado, distribuicaoCordaDimensionada.obtemPontoInterpolado(raiosPerfis.get(i)).y, distribuicaoAnguloBetaDimensionada.obtemPontoInterpolado(raiosPerfis.get(i)).y,raiosPerfis.get(i), anguloAxial, eixoDeCoordenadasDeRotacao));
            this.estacoes.add(new Estacao(dR,raiosPerfis.get(i-1),material, this.perfis.get(i-1), this.perfis.get(i), eixoDeCoordenadasDeRotacao));
        }

        this.perfis.add(new Aerofolio(aerofolioPuro1, distribuicaoCordaDimensionada.obtemPontoInterpolado(raioExterno).y, distribuicaoAnguloBetaDimensionada.obtemPontoInterpolado(raioExterno).y, raioExterno, anguloAxial, eixoDeCoordenadasDeRotacao));
        this.estacoes.add(new Estacao(dR,raiosPerfis.get(numeroDeEstacoes-1),material, this.perfis.get(numeroDeEstacoes-1), this.perfis.get(numeroDeEstacoes), eixoDeCoordenadasDeRotacao));
    }

    public List<Quad> getQuadsSeccaoDeTransicao(Boolean wireframeDisplayed) {
        List<Quad> quadsSeccaoDeTransicao = new ArrayList<>();
        this.estacoes.forEach(estacao -> {
            quadsSeccaoDeTransicao.addAll(estacao.getQuadsEstacao(wireframeDisplayed));
        });
        return quadsSeccaoDeTransicao;
    }

    public void plotPerfisSeccaoDeTransicao() {
        AWTChart chart = new AWTChart(Quality.Fastest);
        this.perfis.forEach(p -> chart.getScene().getGraph().add(p.geometria.getLinhaParaPlot()));
        AWTView view = chart.getAWTView();
        view.setScaleX(new Scale(-this.raioExterno/2,this.raioExterno/2));
        view.setScaleY(new Scale(-this.raioExterno/2,this.raioExterno/2));
        view.setScaleZ(new Scale(0,this.raioExterno));
        ChartLauncher.openChart(chart);
    }

    public void plotSuperficieSeccaoDeTransicao(Boolean wireframeDisplayed) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        Graph graph = chart.getScene().getGraph();
        this.getQuadsSeccaoDeTransicao(wireframeDisplayed).forEach(graph::add);
        view.setScaleX(new Scale(-this.raioExterno/2,this.raioExterno/2));
        view.setScaleY(new Scale(-this.raioExterno/2,this.raioExterno/2));
        view.setScaleZ(new Scale(0,this.raioExterno));
        ChartLauncher.openChart(chart);
    }
}

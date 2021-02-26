package VLM;

import Aerodinamica.Fluido;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Espaco.Vetor3D;
import GeometriaBase.Espaco.Vetor3DBase;
import GeometriaHelice.Estacao;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Quad;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.scene.Graph;
import org.jzy3d.plot3d.rendering.view.AWTView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static util.MetodosEstaticos.linspace;

public class EstacaoPainel {

    public Estacao estacao;
    public List<Painel> paineis = new ArrayList<Painel>();
    public Ponto3D pontoUmQuartoDaCorda;
    public Vetor3D velocidadeEscoamentoIntocadoUmQuartoDaCorda;
    public Vetor3D sustentacao;
    public Vetor3D arrasto;
    public double tracao;
    public double torque;

    public EstacaoPainel(Estacao estacao, Integer numeroDePaineisPorEstacao, Integer numeroDeFilamentosEsteira) {
        this.estacao = estacao;
        List<Double> valoresDeXParaObterCamberAerofolioInterno = linspace(this.estacao.aerofolioInterno.curvaCamber.funcao2D.pontoMinimoX().x,this.estacao.aerofolioInterno.curvaCamber.funcao2D.pontoMaximoX().x,numeroDePaineisPorEstacao+1);
        List<Double> valoresDeXParaObterCamberAerofolioExterno = linspace(this.estacao.aerofolioExterno.curvaCamber.funcao2D.pontoMinimoX().x,this.estacao.aerofolioExterno.curvaCamber.funcao2D.pontoMaximoX().x,numeroDePaineisPorEstacao+1);
        List<Ponto3D> pontosCamberPerfilInterno = valoresDeXParaObterCamberAerofolioInterno.stream().map(x -> estacao.aerofolioInterno.curvaCamber.pontoInterpoladoEmEixoDeCoordenadasPrincipal(x)).collect(Collectors.toList());
        List<Ponto3D> pontosCamberPerfilExterno = valoresDeXParaObterCamberAerofolioExterno.stream().map(x -> estacao.aerofolioExterno.curvaCamber.pontoInterpoladoEmEixoDeCoordenadasPrincipal(x)).collect(Collectors.toList());

        this.pontoUmQuartoDaCorda = pontosCamberPerfilInterno.get(0).multiplicaCoordenadasPorEscalar(3.0)
                .somaPonto3D(pontosCamberPerfilExterno.get(0).multiplicaCoordenadasPorEscalar(3.0))
                .somaPonto3D(pontosCamberPerfilExterno.get(pontosCamberPerfilExterno.size()-1))
                .somaPonto3D(pontosCamberPerfilInterno.get(pontosCamberPerfilExterno.size()-1))
                .multiplicaCoordenadasPorEscalar(1.0/8.0);

        for (int i = 0; i < numeroDePaineisPorEstacao; i++) {
            List<Ponto3D> pontosPainel = new ArrayList<Ponto3D>();
            pontosPainel.add(pontosCamberPerfilInterno.get(i));
            pontosPainel.add(pontosCamberPerfilExterno.get(i));
            pontosPainel.add(pontosCamberPerfilExterno.get(i+1));
            pontosPainel.add(pontosCamberPerfilInterno.get(i+1));

            this.paineis.add(Painel.constroiPainelHelice(pontosPainel,pontosCamberPerfilInterno.stream().skip(i+1).collect(Collectors.toList()),pontosCamberPerfilExterno.stream().skip(i+1).collect(Collectors.toList()),numeroDeFilamentosEsteira, estacao.eixoDeCoordenadasDeRotacao));
        }
    }

    public EstacaoPainel(){}

    public void calculaForca(Fluido fluido) {
        List<Vetor3D> forcaPaineis = this.paineis.stream().map(painel -> painel.calculaForca(fluido.densidade)).collect(Collectors.toList());
        Vetor3D vetorForca = forcaPaineis.stream().reduce(new Vetor3D(0.0,0.0,0.0),
                Vetor3DBase::somaVetor);
        this.tracao = vetorForca.produtoEscalar(this.estacao.eixoDeCoordenadasDeRotacao.eixoZ);
        this.torque = new Vetor3D(this.pontoUmQuartoDaCorda).produtoVetorial(vetorForca).modulo;
        this.arrasto = vetorForca.getComponenteNaDirecao(this.velocidadeEscoamentoIntocadoUmQuartoDaCorda.direcao);
        this.sustentacao = vetorForca.subtraiVetor(this.arrasto);
        this.arrasto.posicao = this.pontoUmQuartoDaCorda;
        this.sustentacao.posicao = this.pontoUmQuartoDaCorda;
    }

    public List<Quad> getQuadsPaineis(boolean wireframeDisplayed) {
        return this.paineis.stream().map(p -> {
            p.quadPlaca.setWireframeDisplayed(wireframeDisplayed);
            return p.quadPlaca;
        }).collect(Collectors.toList());
    }

    public List<LineStrip> getLineStripsVortices() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineis.forEach(p -> lineStrips.add(p.getLineStripVortice()));
        return lineStrips;
    }

    public List<Point> getPointsPontosDeControle() {
        List<Point> pontos = new ArrayList<>();
        this.paineis.forEach(p -> pontos.add(p.pontoDeControle.converteParaPoint(Color.GREEN,2.0f)));
        return  pontos;
    }

    public List<LineStrip> getLineStripsVetoresNormais() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineis.forEach(p -> lineStrips.add(p.vetorNormal.escala(50.0).getLineStripSeta(Color.BLUE,1.0f)));
        return lineStrips;
    }

    public List<LineStrip> getLineStripsVetoresForcaPaineis() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineis.forEach(painel -> lineStrips.add(painel.forca.getLineStripSeta(Color.BLACK,1.0f)));
        return lineStrips;
    }

    public List<LineStrip> getLineStripsVelocidadeLocalUmQuartoDoPainel() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineis.forEach(painel -> lineStrips.add(painel.velocidadeLocalUmQuartoDoPainel.escala(1.0/100.0).getLineStripSeta(Color.GRAY,1.0f)));
        return lineStrips;
    }

    public void plotPaineis(boolean wireframeDisplayed, boolean mostraVortices, boolean mostraVelocidadesLocais, boolean mostraForcasPaineis) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        Graph graph = chart.getScene().getGraph();
        graph.add(this.getQuadsPaineis(wireframeDisplayed));
        if (mostraVortices) {
            graph.add(this.getLineStripsVortices());
        }
        if (mostraVelocidadesLocais) {
            graph.add(this.getLineStripsVelocidadeLocalUmQuartoDoPainel());
        }
        if (mostraForcasPaineis) {
            graph.add(this.getLineStripsVetoresForcaPaineis());
        }
        view.setScaleX(new Scale(-this.estacao.dR,this.estacao.dR));
        view.setScaleY(new Scale(-this.estacao.dR,this.estacao.dR));
        view.setScaleZ(new Scale(this.estacao.distanciaRadialInterna,this.estacao.distanciaRadialInterna + this.estacao.dR));
        ChartLauncher.openChart(chart);
    }
}

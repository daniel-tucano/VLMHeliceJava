package VLM;

import GeometriaBase.Espaco.*;
import GeometriaBase.Primitivos.CurvaHelicoidal;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Quad;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.scene.Graph;
import org.jzy3d.plot3d.rendering.view.AWTView;
import org.jzy3d.plot3d.rendering.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um painel no modelo VLM
 * <p>
 * Os paineis devem ter um p
 */
public class Painel {
    public Ponto3D pontoDeControle;
    public Ponto3D pontoUmQuartoDoPainel;
    public Vetor3D velocidadeEscoamenoIntocadoUmQuartoDoPainel;
    public Vetor3D velocidadeLocalUmQuartoDoPainel;
    public Vetor3D forca;
    public List<Ponto3D> pontosDoVorticeEsquerdoNoPerfil;
    public List<Ponto3D> pontosDoVorticeDireitoNoPerfil;
    public Vetor3D vetorNormal;
    public VorticeFerradura vorticeFerradura;
    public Quad quadPlaca = new Quad();
    public List<Ponto3D> pontosPlaca;
    public EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao;

    public Painel(List<Ponto3D> pontosPlaca,
                  Direcao3D vetorNormal,
                  List<Ponto3D> pontosDoVorticeEsquerdoNoPerfil,
                  List<Ponto3D> pontosDoVorticeDireitoNoPerfil,
                  VorticeFerradura vorticeFerradura,
                  EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao) {
        //      Os Pontos dos paineis devem vir sempre na forma
        //              ________
        //            0|		|1
        //             |		|
        //            3|________|2
        this.pontosPlaca = pontosPlaca;
        this.pontosDoVorticeEsquerdoNoPerfil = pontosDoVorticeEsquerdoNoPerfil;
        this.pontosDoVorticeDireitoNoPerfil = pontosDoVorticeDireitoNoPerfil;
        this.vorticeFerradura = vorticeFerradura;
        this.pontosPlaca.forEach(p -> this.quadPlaca.add(p.converteParaPoint()));
        this.quadPlaca.setColor(Color.GRAY);
        //      realiza uma media ponderada entre os pontos, os pontos mais atrás da placa recebem um peso de 2, assim o ponto
        //      resultante sera o à 3/4 da placa, que é definido como o ponto de controle
        this.pontoDeControle = pontosPlaca.get(0)
                .somaPonto3D(pontosPlaca.get(1))
                .somaPonto3D(pontosPlaca.get(2).multiplicaCoordenadasPorEscalar(3.0))
                .somaPonto3D(pontosPlaca.get(3).multiplicaCoordenadasPorEscalar(3.0))
                .multiplicaCoordenadasPorEscalar(1.0 / 8.0);
        //      mesma estratégia usada para encontrar o ponto de controle, mas dando um peso maior para os pontos mais a frente
        //      da placa
        this.pontoUmQuartoDoPainel = pontosPlaca.get(0)
                .multiplicaCoordenadasPorEscalar(3.0)
                .somaPonto3D(pontosPlaca.get(1).multiplicaCoordenadasPorEscalar(3.0))
                .somaPonto3D(pontosPlaca.get(2))
                .somaPonto3D(pontosPlaca.get(3))
                .multiplicaCoordenadasPorEscalar(1.0 / 8.0);
        this.vetorNormal = vetorNormal.posicionaNoEspaco(this.pontoDeControle);
        this.eixoDeCoordenadasDeRotacao = eixoDeCoordenadasDeRotacao;
    }

    public static Painel constroiPainelHelice(List<Ponto3D> pontosPlaca,
                                              Direcao3D vetorNormal,
                                              List<Ponto3D> pontosCamberAerofolioInterno,
                                              List<Ponto3D> pontosCamberAerofolioExterno,
                                              Integer numeroDePontosEsteira,
                                              EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao) {
        pontosCamberAerofolioInterno.add(0, pontosPlaca.get(3).somaPonto3D(pontosPlaca.get(0).multiplicaCoordenadasPorEscalar(3.0)).multiplicaCoordenadasPorEscalar(1.0 / 4.0));
        pontosCamberAerofolioExterno.add(0, pontosPlaca.get(2).somaPonto3D(pontosPlaca.get(1).multiplicaCoordenadasPorEscalar(3.0)).multiplicaCoordenadasPorEscalar(1.0 / 4.0));
        List<Ponto3D> pontosVorticePernaEsquerda = new ArrayList<Ponto3D>(pontosCamberAerofolioInterno.subList(0, pontosCamberAerofolioInterno.size() - 1));
        List<Ponto3D> pontosVortivePernaDireita = new ArrayList<Ponto3D>(pontosCamberAerofolioExterno.subList(0, pontosCamberAerofolioInterno.size() - 1));

        CoordenadaCilindrica pontoBordoDeFugaAerofolioInterno = pontosCamberAerofolioInterno.get(pontosCamberAerofolioInterno.size() - 1)
                .descrevePontoEmNovoEixoDeCoordenadas(eixoDeCoordenadasDeRotacao)
                .converteParaCoordenadaCilindrica();
        CoordenadaCilindrica pontoBordoDeFugaAerofolioExterno = pontosCamberAerofolioExterno.get(pontosCamberAerofolioExterno.size() - 1)
                .descrevePontoEmNovoEixoDeCoordenadas(eixoDeCoordenadasDeRotacao)
                .converteParaCoordenadaCilindrica();

        pontosVorticePernaEsquerda.addAll(new CurvaHelicoidal(pontoBordoDeFugaAerofolioInterno,
                -1.0,
                false,
                numeroDePontosEsteira,
                1,
                eixoDeCoordenadasDeRotacao).descrevePontosEmEixoDeCoordenadasPrincipal().pontos);
        pontosVortivePernaDireita.addAll(new CurvaHelicoidal(pontoBordoDeFugaAerofolioExterno,
                -1.0,
                false,
                numeroDePontosEsteira,
                1,
                eixoDeCoordenadasDeRotacao).descrevePontosEmEixoDeCoordenadasPrincipal().pontos);

        return new Painel(pontosPlaca,
                vetorNormal,
                pontosCamberAerofolioInterno,
                pontosCamberAerofolioExterno,
                new VorticeFerradura(1.0, pontosVorticePernaEsquerda, pontosVortivePernaDireita),
                eixoDeCoordenadasDeRotacao);
    }

    public Double calculaInfluenciaDeOutroPainelNessePainel(Painel painel) {
        return painel.vorticeFerradura.calculaIndluenciaEmPontoEmUmaDirecao(this.pontoDeControle, this.vetorNormal.direcao);
    }

    public Vetor3D calculaVelocidadeInduzidaEmPonto(Ponto3D pontoInduzido) {
        return this.vorticeFerradura.calculaInfluenciaEmPonto(pontoInduzido).escala(this.vorticeFerradura.gama);
    }

    public Vetor3D calculaForca(double densidade) {
        Vetor3D vetorForca = this.vorticeFerradura.calculaForca(this.velocidadeLocalUmQuartoDoPainel, densidade);
        vetorForca.posicao = this.pontoUmQuartoDoPainel;
        this.forca = vetorForca;
        return vetorForca;
    }

    public void modificaEsteiraDoVortice(Double deslocamentoDaEsteiraEmUmaVolta, Integer numeroDePontosEsteira) {
        List<Ponto3D> pontosVorticePernaEsquerda = new ArrayList<Ponto3D>(this.pontosDoVorticeEsquerdoNoPerfil);
        List<Ponto3D> pontosVortivePernaDireita = new ArrayList<Ponto3D>(this.pontosDoVorticeDireitoNoPerfil);

        CoordenadaCilindrica pontoBordoDeFugaAerofolioInterno = this.pontosDoVorticeEsquerdoNoPerfil.get(this.pontosDoVorticeEsquerdoNoPerfil.size() - 1)
                .descrevePontoEmNovoEixoDeCoordenadas(eixoDeCoordenadasDeRotacao)
                .converteParaCoordenadaCilindrica();
        CoordenadaCilindrica pontoBordoDeFugaAerofolioExterno = this.pontosDoVorticeDireitoNoPerfil.get(this.pontosDoVorticeDireitoNoPerfil.size() - 1)
                .descrevePontoEmNovoEixoDeCoordenadas(eixoDeCoordenadasDeRotacao)
                .converteParaCoordenadaCilindrica();

        pontosVorticePernaEsquerda.addAll(new CurvaHelicoidal(pontoBordoDeFugaAerofolioInterno,
                -deslocamentoDaEsteiraEmUmaVolta,
                false,
                numeroDePontosEsteira,
                1,
                eixoDeCoordenadasDeRotacao).descrevePontosEmEixoDeCoordenadasPrincipal().pontos);
        pontosVortivePernaDireita.addAll(new CurvaHelicoidal(pontoBordoDeFugaAerofolioExterno,
                -deslocamentoDaEsteiraEmUmaVolta,
                false,
                numeroDePontosEsteira,
                1,
                eixoDeCoordenadasDeRotacao).descrevePontosEmEixoDeCoordenadasPrincipal().pontos);

        this.vorticeFerradura = new VorticeFerradura(1.0, pontosVorticePernaEsquerda, pontosVortivePernaDireita);
    }

    public LineStrip getLineStripVortice() {
        return this.vorticeFerradura.getLineStrip();
    }

    public void plot() {
        AWTChart chart = new AWTChart(Quality.Fastest);
        Graph graph = chart.getScene().getGraph();
        graph.add(this.quadPlaca);
        graph.add(this.getLineStripVortice());
        graph.add(this.pontoDeControle.converteParaPoint(Color.GREEN, 2.0f));
        graph.add(this.vetorNormal.getLineStripSeta(Color.BLUE, 1.0f));
        //        graph.add(this.forca.getLineStripSeta(Color.BLACK,1.0f));
        ChartLauncher.openChart(chart);
    }

    public void plot(double escala) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        Graph graph = chart.getScene().getGraph();
        AWTView view = chart.getAWTView();

        view.setScaleX(new Scale(-escala, escala));
        view.setScaleY(new Scale(-escala, escala));
        view.setScaleZ(new Scale(-escala, escala));

        graph.add(this.quadPlaca);
        graph.add(this.getLineStripVortice());
        graph.add(this.pontoDeControle.converteParaPoint(Color.GREEN, 2.0f));
        graph.add(this.vetorNormal.escala(50.0).getLineStripSeta(Color.BLUE, 1.0f));
        graph.add(this.forca.getLineStripSeta(Color.BLACK, 1.0f));

        ChartLauncher.openChart(chart);
    }

    public void plot(double escala, Ponto3D origem) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        Graph graph = chart.getScene().getGraph();
        AWTView view = chart.getAWTView();

        view.setScaleX(new Scale(-escala + origem.x, escala + origem.x));
        view.setScaleY(new Scale(-escala + origem.y, escala + origem.y));
        view.setScaleZ(new Scale(-escala + origem.z, escala + origem.z));

        graph.add(this.quadPlaca);
        graph.add(this.getLineStripVortice());
        graph.add(this.pontoDeControle.converteParaPoint(Color.GREEN, 2.0f));
        graph.add(this.vetorNormal.escala(50.0).getLineStripSeta(Color.BLUE, 1.0f));
        graph.add(this.forca.getLineStripSeta(Color.BLACK, 1.0f));

        ChartLauncher.openChart(chart);
    }
}

package VLM;

import Aerodinamica.CampoVetorialEscoamentoIntocadoHelice;
import Aerodinamica.Fluido;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Espaco.Vetor3D;
import GeometriaHelice.Helice;
import SolverAerodinamicoHelice.CondicaoDeOperacao;
import SolverAerodinamicoHelice.PontoPerformanceHelice;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HelicePainel {
    public Helice helice;
    public List<PaPainel> paineisPa = new ArrayList<>();
    public double tracao;
    public double torque;
    public Integer numeroDePaineisPorEstacao;
    public Integer numeroDePontosEsteira;

    public HelicePainel(Helice helice, Integer numeroDePaineisPorEstacao, Integer numeroDePontosEsteira) {
        this.helice = helice;
        this.numeroDePaineisPorEstacao = numeroDePaineisPorEstacao;
        this.numeroDePontosEsteira = numeroDePontosEsteira;
        this.paineisPa = helice.pas.parallelStream().map(pa -> new PaPainel(pa, numeroDePaineisPorEstacao, numeroDePontosEsteira)).collect(Collectors.toList());
    }

    public HelicePainel() {}

    public SimpleMatrix geraAIM() {
        int tamanhoAIMPa = this.helice.pas.get(0).numeroDeEstacoes*this.numeroDePaineisPorEstacao;
        int tamanhoAIM = this.helice.nPas*tamanhoAIMPa;
        final SimpleMatrix[] AIM = {new SimpleMatrix(tamanhoAIM, tamanhoAIM)};
        List<SimpleMatrix> AIMPas = IntStream.range(0,this.helice.nPas).mapToObj(iPa -> new SimpleMatrix(tamanhoAIMPa,tamanhoAIMPa)).collect(Collectors.toList());

        IntStream.range(0,this.helice.nPas).forEach(iPaInfluenciadora -> {
            AIMPas.set(iPaInfluenciadora, this.paineisPa.get(0).getInfluenciaDePaNessaPa(this.paineisPa.get(iPaInfluenciadora)));

        });

        IntStream.range(0,this.helice.nPas).forEach(iPaInfluenciada -> {
            int linhaPaInfluenciada = iPaInfluenciada*tamanhoAIMPa;
            IntStream.range(0,this.helice.nPas).forEach(iPaInfluenciadora -> {
                int colunaPaInfluenciadora;
                if (iPaInfluenciada + iPaInfluenciadora <= (this.helice.nPas - 1)) {
                    colunaPaInfluenciadora = (iPaInfluenciada + iPaInfluenciadora)*tamanhoAIMPa;
                } else  {
                    colunaPaInfluenciadora = (iPaInfluenciada + iPaInfluenciadora - this.helice.nPas)*tamanhoAIMPa;
                }
                AIM[0] = AIM[0].combine(linhaPaInfluenciada,colunaPaInfluenciadora, AIMPas.get(iPaInfluenciadora));
            });
        });

        return AIM[0];
    }

    public SimpleMatrix geraMatrizRHS(CondicaoDeOperacao condicaoDeOperacao) {
        CampoVetorialEscoamentoIntocadoHelice campoVetorialEscoamentoIntocadoHelice = new CampoVetorialEscoamentoIntocadoHelice(condicaoDeOperacao);
        int numeroDePaineisPa = this.helice.pas.get(0).numeroDeEstacoes*this.numeroDePaineisPorEstacao;
        int numeroDePaineisHelice = this.helice.nPas*numeroDePaineisPa;
        SimpleMatrix matrizRHS = new SimpleMatrix(numeroDePaineisHelice,1);
        IntStream.range(0,this.helice.nPas).forEach(iPa -> {
            IntStream.range(0,this.helice.pas.get(iPa).numeroDeEstacoes).forEach(iEstacao -> {
                IntStream.range(0,this.numeroDePaineisPorEstacao).forEach(iPainel -> {
                    Painel painelAtual = this.paineisPa.get(iPa)
                            .paineisEstacoes.get(iEstacao)
                            .paineis.get(iPainel);
                    matrizRHS.set( iPainel + iEstacao*this.numeroDePaineisPorEstacao + iPa*numeroDePaineisPa,0,
                            -painelAtual
                                    .vetorNormal
                                    .produtoEscalar(
                                            campoVetorialEscoamentoIntocadoHelice.getVelocidadeEscoamentoIntocado(
                                                    painelAtual.pontoDeControle
                                                            .setEixoDeCoordenadas(this.helice.eixoDeCoordenadasRotacao)
                                                            .converteParaCoordenadaCilindrica()
                                            )
                                                    .descrevePontoEmEixoDeCoordenadasPrincipal()
                                    ));
                });
            });
        });

        return matrizRHS;
    }

    public PontoPerformanceHelice calculaPontoPerformanceHelice(CondicaoDeOperacao condicaoDeOperacao) {
        this.setEsteira(condicaoDeOperacao);
        CampoVetorialEscoamentoIntocadoHelice campoVetorialEscoamentoIntocadoHelice = new CampoVetorialEscoamentoIntocadoHelice(condicaoDeOperacao);
        SimpleMatrix matrizGama = this.geraAIM().solve(this.geraMatrizRHS(condicaoDeOperacao));
        int numeroDePaineisPa = this.helice.pas.get(0).numeroDeEstacoes*this.numeroDePaineisPorEstacao;
        IntStream.range(0,this.helice.nPas).forEach(iPa -> {
            IntStream.range(0,this.helice.pas.get(iPa).numeroDeEstacoes).forEach(iEstacao -> {
                this
                    .paineisPa.get(iPa)
                    .paineisEstacoes.get(iEstacao)
                    .velocidadeEscoamentoIntocadoUmQuartoDaCorda = campoVetorialEscoamentoIntocadoHelice.getVelocidadeEscoamentoIntocado(this.paineisPa.get(iPa).paineisEstacoes.get(iEstacao).pontoUmQuartoDaCorda.converteParaCoordenadaCilindrica());
                IntStream.range(0,this.numeroDePaineisPorEstacao).forEach(iPainel -> {
                    Painel painelAtual = this
                        .paineisPa.get(iPa)
                        .paineisEstacoes.get(iEstacao)
                        .paineis.get(iPainel);
                    painelAtual
                        .vorticeFerradura
                        .gama = matrizGama.get(iPainel + iEstacao*this.numeroDePaineisPorEstacao + iPa*numeroDePaineisPa);
                    painelAtual
                            .velocidadeEscoamenoIntocadoUmQuartoDoPainel = campoVetorialEscoamentoIntocadoHelice.getVelocidadeEscoamentoIntocado(
                                    painelAtual.pontoUmQuartoDoPainel.converteParaCoordenadaCilindrica()
                    );
                    painelAtual
                        .velocidadeLocalUmQuartoDoPainel = this.determinaVelocidadeInduzidaEmPonto(painelAtual.pontoUmQuartoDoPainel).somaVetor(
                            this
                                .paineisPa.get(iPa)
                                .paineisEstacoes.get(iEstacao)
                                .paineis.get(iPainel)
                                .velocidadeEscoamenoIntocadoUmQuartoDoPainel
                    );
                    painelAtual.velocidadeLocalUmQuartoDoPainel.posicao = painelAtual.pontoUmQuartoDoPainel;
                });
            });
            this.paineisPa.get(iPa).calculaForca(condicaoDeOperacao.fluido);
        });
        this.tracao = this.paineisPa.stream().map(paPainel -> paPainel.tracao).reduce(Double::sum).get();
        this.torque = this.paineisPa.stream().map(paPainel -> paPainel.torque).reduce(Double::sum).get();
        return new PontoPerformanceHelice(condicaoDeOperacao, this.tracao, this.torque);
    }

    public Vetor3D determinaVelocidadeInduzidaEmPonto(Ponto3D ponto) {
        SimpleMatrix matrizVetorVelocidadeInduzidaEmPonto = new SimpleMatrix(3,1);
        IntStream.range(0,this.helice.nPas).forEach(iPa -> {
            IntStream.range(0,this.helice.pas.get(iPa).numeroDeEstacoes).forEach(iEstacao -> {
                IntStream.range(0,this.numeroDePaineisPorEstacao).forEach(iPainel -> {
                    matrizVetorVelocidadeInduzidaEmPonto
                            .plus(
                                    this
                                        .paineisPa.get(iPa)
                                        .paineisEstacoes.get(iEstacao)
                                        .paineis.get(iPainel)
                                        .calculaVelocidadeInduzidaEmPonto(ponto)
                                        .matrizVetor
                            );
                });
            });
        });
        return new Vetor3D(matrizVetorVelocidadeInduzidaEmPonto, ponto);
    }

    public void setEsteira(CondicaoDeOperacao condicaoDeOperacao) {
        Double deslocamentoDaEsteiraEmUmaVolta = (2*Math.PI/condicaoDeOperacao.velocidadeAngularRadSegundo)*condicaoDeOperacao.velocidadeAxial;
        this.paineisPa.parallelStream().forEach(pa -> {
            pa.paineisEstacoes.forEach(estacao -> {
                estacao.paineis.forEach( painel -> {
                    painel.modificaEsteiraDoVortice(deslocamentoDaEsteiraEmUmaVolta, this.numeroDePontosEsteira);
                });
            });
        });
    }

    public List<Quad> getPaineisHelice(boolean wireframeDisplayed) {
        List<Quad> paineisHelice = new ArrayList<>();
        this.paineisPa.forEach(p -> paineisHelice.addAll(p.getQuadsPa(wireframeDisplayed)));
        return paineisHelice;
    }

    public List<LineStrip> getLineStripsVortices() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineisPa.forEach(pa -> lineStrips.addAll(pa.getLineStripsVortices()));
        return lineStrips;
    }

    public List<Point> getPointsPontosDeControle() {
        List<Point> pontos = new ArrayList<>();
        this.paineisPa.forEach(pa -> pontos.addAll(pa.getPointsPontosDeControle()));
        return pontos;
    }

    public List<LineStrip> getLineStripsVetoresNormais() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineisPa.forEach(pa -> lineStrips.addAll(pa.getLineStripsVetoresNormais()));
        return lineStrips;
    }

    public List<LineStrip> getLineStripsVetoresForcaPaineis(Double escala) {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineisPa.forEach(pa -> lineStrips.addAll(pa.getLineStripsVetoresForcaPaineis(escala)));
        return lineStrips;
    }

    public List<LineStrip> getLineStripsVelocidadeLocalUmQuartoDoPainel() {
        List<LineStrip> lineStrips = new ArrayList<>();
        this.paineisPa.forEach(pa -> lineStrips.addAll(pa.getLineStripsVelocidadeLocalUmQuartoDoPainel()));
        return  lineStrips;
    }

    public AWTChart getChart(boolean wireframeDisplayed, boolean mostraVortices, boolean mostraPontosDeControle, boolean mostraVetoresNormais, boolean mostraVelocidadesLocais, boolean mostraForcasPaineis, Double escalaForca) {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        Graph graph = chart.getScene().getGraph();
        if (mostraVortices) {
            graph.add(this.getLineStripsVortices());
        }
        if (mostraPontosDeControle) {
            graph.add(this.getPointsPontosDeControle());
        }
        if (mostraVetoresNormais) {
            graph.add(this.getLineStripsVetoresNormais());
        }
        if (mostraVelocidadesLocais) {
            graph.add(this.getLineStripsVelocidadeLocalUmQuartoDoPainel());
        }
        if (mostraForcasPaineis) {
            graph.add(this.getLineStripsVetoresForcaPaineis(escalaForca));
        }
        graph.add(this.getPaineisHelice(wireframeDisplayed));
        view.setScaleX(new Scale(-this.helice.raio,this.helice.raio));
        view.setScaleY(new Scale(-this.helice.raio,this.helice.raio));
        view.setScaleZ(new Scale(-this.helice.raio,this.helice.raio));
        return chart;
    }

    public void plotPaineis(boolean wireframeDisplayed, boolean mostraVortices, boolean mostraPontosDeControle, boolean mostraVetoresNormais, boolean mostraVelocidadesLocais, boolean mostraForcasPaineis, Double escalaForca) {
        ChartLauncher.openChart(this.getChart(wireframeDisplayed, mostraVortices, mostraPontosDeControle, mostraVetoresNormais, mostraVelocidadesLocais, mostraForcasPaineis, escalaForca));
    }
}

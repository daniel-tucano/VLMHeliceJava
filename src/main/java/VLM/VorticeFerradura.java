package VLM;

import GeometriaBase.Espaco.Direcao3D;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Espaco.Vetor3D;
import org.ejml.simple.SimpleMatrix;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.scene.Graph;

import java.util.ArrayList;
import java.util.List;

public class VorticeFerradura {
    public Double gama;
    public VorticeFilamento vorticeColado;
    public List<VorticeFilamento> vorticesPernaEsquerda;
    public List<VorticeFilamento> vorticesPernaDireita;

    public VorticeFerradura(VorticeFilamento vorticeColado, List<VorticeFilamento> vorticesPernaEsquerda, List<VorticeFilamento> vorticesPernaDireita) {
        this.vorticeColado = vorticeColado;
        this.vorticesPernaEsquerda = vorticesPernaEsquerda;
        this.vorticesPernaDireita = vorticesPernaDireita;
        this.gama = vorticeColado.gama;
    }

    public VorticeFerradura( Double gama, List<Ponto3D> pontosVorticePernaEsquerda, List<Ponto3D> pontosVorticePernaDireita) {
        // ambos os pontos de vertices das pernas devem começar do ponto que compartilham com o vortice colado e partir dai
        this.gama = gama;
        this.vorticeColado = new VorticeFilamento( gama, pontosVorticePernaDireita.get(0), pontosVorticePernaEsquerda.get(0));
        this.vorticesPernaEsquerda = new ArrayList<>();
        this.vorticesPernaDireita = new ArrayList<>();
        for (int i = pontosVorticePernaEsquerda.size()-1; i > 0; i--) {
            this.vorticesPernaEsquerda.add(new VorticeFilamento( gama, pontosVorticePernaEsquerda.get(i-1), pontosVorticePernaEsquerda.get(i)));
        }
        for (int i = 0; i < pontosVorticePernaDireita.size()-1; i++) {
            this.vorticesPernaDireita.add(new VorticeFilamento( gama, pontosVorticePernaDireita.get(i+1), pontosVorticePernaDireita.get(i)));
        }
    }

    public Vetor3D calculaInfluenciaEmPonto(Ponto3D ponto) {
        Vetor3D influciaPernaEsquerda = new Vetor3D(this.vorticesPernaEsquerda
                .stream()
                .reduce( new SimpleMatrix(3,1,false,new double[] {0.0,0.0,0.0}), (matrizSoma,vortice) -> {
                    return vortice.calculaInfluenciaEmPonto(ponto).matrizVetor;
                }, SimpleMatrix::plus));

        Vetor3D influciaPernaDireita = new Vetor3D(this.vorticesPernaDireita
                .stream()
                .reduce( new SimpleMatrix(3,1,false,new double[] {0.0,0.0,0.0}), (matrizSoma,vortice) -> {
                    return vortice.calculaInfluenciaEmPonto(ponto).matrizVetor;
                }, SimpleMatrix::plus));

        Vetor3D influenciaVorticeColado = this.vorticeColado.calculaInfluenciaEmPonto(ponto);

        return influciaPernaEsquerda.somaVetor(influciaPernaDireita).somaVetor(influenciaVorticeColado);
    }

    public Double calculaIndluenciaEmPontoEmUmaDirecao(Ponto3D ponto, Direcao3D direcao) {
        return this.calculaInfluenciaEmPonto(ponto).produtoEscalar(direcao);
    }

    public Vetor3D calculaForca(Vetor3D velocidade, Double densidade) {
        return velocidade.produtoVetorial(this.vorticeColado.vetorFilamento).escala(densidade*this.gama);
    }

    public LineStrip getLineStrip() {
        LineStrip lineStrip = new LineStrip();
        this.vorticesPernaEsquerda.forEach(vpe -> lineStrip.addAll(vpe.getLineStrip()));
        lineStrip.addAll(this.vorticeColado.getLineStrip());
        this.vorticesPernaDireita.forEach(vpd -> lineStrip.addAll(vpd.getLineStrip()));
        lineStrip.setWireframeDisplayed(true);
        lineStrip.setWireframeWidth(3.0f);
        lineStrip.setWireframeColor(Color.RED);
        return lineStrip;
    }

    public void plot() {
        AWTChart chart = new AWTChart(Quality.Fastest);
        Graph graph = chart.getScene().getGraph();
        graph.add(this.getLineStrip());
        ChartLauncher.openChart(chart);
    }
}

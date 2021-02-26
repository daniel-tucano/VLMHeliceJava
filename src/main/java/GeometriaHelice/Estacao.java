package GeometriaHelice;

import Estrutura.Material;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Quad;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.scene.Graph;
import org.jzy3d.plot3d.rendering.view.AWTView;

import java.util.ArrayList;
import java.util.List;

public class Estacao {
    // Interno -> mais proximo do centro de rotação
    // Externo -> mais distante do centro de rotação
    public Double dR;
    public Double distanciaRadialInterna;
    public Material material;
    public Double volume;
    public Double massa;
    public Aerofolio aerofolioInterno;
    public Aerofolio aerofolioExterno;
    public EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao;
//    public Aerofolio aerofolioCentral;

    public Estacao(
            Double dR,
            Double distanciaRadialInterna,
            Material material,
            Aerofolio aerofolioInterno,
            Aerofolio aerofolioExterno,
            EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao
    ) {
        this.dR = dR;
        this.distanciaRadialInterna = distanciaRadialInterna;
        this.material = material;
        this.aerofolioInterno = aerofolioInterno;
        this.aerofolioExterno = aerofolioExterno;
        // Seguindo formulação do volume de tronco de pirâmide como aproximação
        this.volume = dR/3*(aerofolioInterno.geometria.geometria2D.area
                + Math.sqrt(aerofolioInterno.geometria.geometria2D.area*aerofolioExterno.geometria.geometria2D.area)
                + aerofolioExterno.geometria.geometria2D.area);
        this.massa = this.volume * this.material.densidade;
        this.eixoDeCoordenadasDeRotacao = eixoDeCoordenadasDeRotacao;
    }

    public Estacao( Estacao estacao ) {
        this.dR = estacao.dR;
        this.distanciaRadialInterna = estacao.distanciaRadialInterna;
        this.material = estacao.material;
        this.aerofolioInterno = estacao.aerofolioInterno;
        this.aerofolioExterno = estacao.aerofolioExterno;
        this.volume = estacao.volume;
        this.massa = estacao.massa;
        this.eixoDeCoordenadasDeRotacao = estacao.eixoDeCoordenadasDeRotacao;
    }

    public List<Quad> getQuadsEstacao(Boolean wireframeDisplayed) {
        List<Quad> quadsEstacao = new ArrayList<>();
        List<Point> pontosAerofolioInterno = this.aerofolioInterno.geometria.getPontosLinhaParaPlot();
        List<Point> pontosAerofolioExterno = this.aerofolioExterno.geometria.getPontosLinhaParaPlot();
        Color color = Color.GRAY;
        color.a = 0.55f;
        for (int i = 0; i < this.aerofolioInterno.geometria.getPontosLinhaParaPlot().size()-1; i++) {
            Quad face = new Quad();
            face.add(pontosAerofolioInterno.get(i));
            face.add(pontosAerofolioInterno.get(i+1));
            face.add(pontosAerofolioExterno.get(i+1));
            face.add(pontosAerofolioExterno.get(i));
            face.setColor(color);
            face.setWireframeColor(new Color(0.65f, 0.65f, 0.65f));
            face.setWireframeWidth(0.000001f);
            face.setWireframeDisplayed(wireframeDisplayed);
            quadsEstacao.add(face);
        }
        return quadsEstacao;
    }

    public void plotPerfisEstacao() {
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        chart.getScene().getGraph().add(this.aerofolioInterno.geometria.getLinhaParaPlot());
        chart.getScene().getGraph().add(this.aerofolioExterno.geometria.getLinhaParaPlot());
        view.setScaleX(new Scale(-0.01,0.01));
        view.setScaleY(new Scale(-0.01,0.01));
        view.setScaleZ(new Scale(0,distanciaRadialInterna + 0.01));
        ChartLauncher.openChart(chart);
    }

    public void plotSuperficieEstacao(Boolean wireframeDisplayed){
        AWTChart chart = new AWTChart(Quality.Fastest);
        AWTView view = chart.getAWTView();
        Graph graph = chart.getScene().getGraph();
        graph.add(this.getQuadsEstacao(wireframeDisplayed));
        view.setScaleX(new Scale(-dR,dR));
        view.setScaleY(new Scale(-dR,dR));
        view.setScaleZ(new Scale(distanciaRadialInterna,distanciaRadialInterna + dR));
        ChartLauncher.openChart(chart);
    }
}

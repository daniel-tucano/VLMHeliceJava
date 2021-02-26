package GeometriaHelice;

import GeometriaBase.Espaco.Direcoes3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Primitivos.Circunferencia;
import org.jzy3d.colors.Color;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Quad;

import java.util.ArrayList;
import java.util.List;

public class Hub {
    public EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao = EixoDeCoordenadas3D.eixoDeCoordenadasEixoDeRotacao;
    public Double raioExterno;
    public Double raioInterno;
    public Double espessura;
    public Circunferencia circunferenciaExterior;
    public Circunferencia circunferenciaInterior;

    public Hub(Double raioExterno, Double raioInterno, Double espessura, EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao) {
        this.raioExterno = raioExterno;
        this.raioInterno = raioInterno;
        this.espessura = espessura;
        this.circunferenciaExterior = new Circunferencia(raioExterno, 30);
        this.circunferenciaInterior = new Circunferencia(raioInterno, 30);
        this.eixoDeCoordenadasDeRotacao = eixoDeCoordenadasDeRotacao;
    }

    public List<Quad> getQuads(Color faceColor, Boolean wireframeDisplayed, Color wireframeColor) {
        List<Quad> quads = new ArrayList<>();

//        EixoDeCoordenadas3D eixoDeCoordenadas = new EixoDeCoordenadas3D(Direcoes3D.X, Direcoes3D.Z);

        List<Point> pontosCircunferenciaExteriorBot = circunferenciaExterior
                .setEixoDeCoordenadas(this.eixoDeCoordenadasDeRotacao.setOrigem(this.eixoDeCoordenadasDeRotacao.eixoZ.escala(-this.espessura/2).getPontoCabeca()))
                .descrevePontosEmEixoDeCoordenadasPrincipal()
                .getPontosLinhaParaPlot();
        List<Point> pontosCircunferenciaInteriorBot = circunferenciaInterior
                .setEixoDeCoordenadas(this.eixoDeCoordenadasDeRotacao.setOrigem(this.eixoDeCoordenadasDeRotacao.eixoZ.escala(-this.espessura/2).getPontoCabeca()))
                .descrevePontosEmEixoDeCoordenadasPrincipal()
                .getPontosLinhaParaPlot();
        List<Point> pontosCircunferenciaExteriorTop = circunferenciaExterior
                .setEixoDeCoordenadas(this.eixoDeCoordenadasDeRotacao.setOrigem(this.eixoDeCoordenadasDeRotacao.eixoZ.escala(this.espessura/2).getPontoCabeca()))
                .descrevePontosEmEixoDeCoordenadasPrincipal()
                .getPontosLinhaParaPlot();
        List<Point> pontosCircunferenciaInteriorTop = circunferenciaInterior
                .setEixoDeCoordenadas(this.eixoDeCoordenadasDeRotacao.setOrigem(this.eixoDeCoordenadasDeRotacao.eixoZ.escala(this.espessura/2).getPontoCabeca()))
                .descrevePontosEmEixoDeCoordenadasPrincipal()
                .getPontosLinhaParaPlot();

        for (int i = 0; i < circunferenciaExterior.numeroDePontos-1; i++) {
            Quad quadTop = new Quad();
            Quad quadBot = new Quad();
            Quad quadLateralExterno = new Quad();
            Quad quadLateralInterno = new Quad();

            quadTop.add(pontosCircunferenciaInteriorTop.get(i));
            quadTop.add(pontosCircunferenciaExteriorTop.get(i));
            quadTop.add(pontosCircunferenciaExteriorTop.get(i+1));
            quadTop.add(pontosCircunferenciaInteriorTop.get(i+1));

            quadBot.add(pontosCircunferenciaInteriorBot.get(i));
            quadBot.add(pontosCircunferenciaExteriorBot.get(i));
            quadBot.add(pontosCircunferenciaExteriorBot.get(i+1));
            quadBot.add(pontosCircunferenciaInteriorBot.get(i+1));

            quadLateralExterno.add(pontosCircunferenciaExteriorBot.get(i));
            quadLateralExterno.add(pontosCircunferenciaExteriorBot.get(i+1));
            quadLateralExterno.add(pontosCircunferenciaExteriorTop.get(i+1));
            quadLateralExterno.add(pontosCircunferenciaExteriorTop.get(i));

            quadLateralInterno.add(pontosCircunferenciaInteriorBot.get(i));
            quadLateralInterno.add(pontosCircunferenciaInteriorBot.get(i+1));
            quadLateralInterno.add(pontosCircunferenciaInteriorTop.get(i+1));
            quadLateralInterno.add(pontosCircunferenciaInteriorTop.get(i));

            quadTop.setWireframeDisplayed(wireframeDisplayed);
            quadBot.setWireframeDisplayed(wireframeDisplayed);
            quadLateralExterno.setWireframeDisplayed(wireframeDisplayed);
            quadLateralInterno.setWireframeDisplayed(wireframeDisplayed);

            quadTop.setWireframeColor(wireframeColor);
            quadBot.setWireframeColor(wireframeColor);
            quadLateralExterno.setWireframeColor(wireframeColor);
            quadLateralInterno.setWireframeColor(wireframeColor);

            quadTop.setColor(faceColor);
            quadBot.setColor(faceColor);
            quadLateralExterno.setColor(faceColor);
            quadLateralInterno.setColor(faceColor);

            quads.add(quadTop);
            quads.add(quadBot);
            quads.add(quadLateralExterno);
            quads.add(quadLateralInterno);
        }

        Quad quadTop = new Quad();
        Quad quadBot = new Quad();
        Quad quadLateralExterno = new Quad();
        Quad quadLateralInterno = new Quad();

        quadTop.add(pontosCircunferenciaInteriorTop.get(pontosCircunferenciaInteriorTop.size()-1));
        quadTop.add(pontosCircunferenciaExteriorTop.get(pontosCircunferenciaInteriorTop.size()-1));
        quadTop.add(pontosCircunferenciaExteriorTop.get(0));
        quadTop.add(pontosCircunferenciaInteriorTop.get(0));

        quadBot.add(pontosCircunferenciaInteriorBot.get(pontosCircunferenciaInteriorTop.size()-1));
        quadBot.add(pontosCircunferenciaExteriorBot.get(pontosCircunferenciaInteriorTop.size()-1));
        quadBot.add(pontosCircunferenciaExteriorBot.get(0));
        quadBot.add(pontosCircunferenciaInteriorBot.get(0));

        quadLateralExterno.add(pontosCircunferenciaExteriorBot.get(pontosCircunferenciaInteriorTop.size()-1));
        quadLateralExterno.add(pontosCircunferenciaExteriorBot.get(0));
        quadLateralExterno.add(pontosCircunferenciaExteriorTop.get(0));
        quadLateralExterno.add(pontosCircunferenciaExteriorTop.get(pontosCircunferenciaInteriorTop.size()-1));

        quadLateralInterno.add(pontosCircunferenciaInteriorBot.get(pontosCircunferenciaInteriorTop.size()-1));
        quadLateralInterno.add(pontosCircunferenciaInteriorBot.get(0));
        quadLateralInterno.add(pontosCircunferenciaInteriorTop.get(0));
        quadLateralInterno.add(pontosCircunferenciaInteriorTop.get(pontosCircunferenciaInteriorTop.size()-1));

        quadTop.setWireframeDisplayed(wireframeDisplayed);
        quadBot.setWireframeDisplayed(wireframeDisplayed);
        quadLateralExterno.setWireframeDisplayed(wireframeDisplayed);
        quadLateralInterno.setWireframeDisplayed(wireframeDisplayed);

        quadTop.setWireframeColor(wireframeColor);
        quadBot.setWireframeColor(wireframeColor);
        quadLateralExterno.setWireframeColor(wireframeColor);
        quadLateralInterno.setWireframeColor(wireframeColor);

        quadTop.setColor(faceColor);
        quadBot.setColor(faceColor);
        quadLateralExterno.setColor(faceColor);
        quadLateralInterno.setColor(faceColor);

        quads.add(quadTop);
        quads.add(quadBot);
        quads.add(quadLateralExterno);
        quads.add(quadLateralInterno);

        return quads;
    }
}

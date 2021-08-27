package GeometriaHelice;

import Aerodinamica.AerofolioBase;
import GeometriaBase.Espaco.*;
import GeometriaBase.Plano.Funcao2D;
import GeometriaBase.Plano.Geometria2D;

public class Aerofolio {
    public EixoDeCoordenadas3D eixoDeCoordenadas;
    public EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao;
    public AerofolioBase aerofolioBase;
    public Double corda;
    public Double anguloBeta;
    public Double distanciaRadial;

    public Geometria2D geometriaPlano;
    public GeometriaEmPlano3D geometria;
    public FuncaoEmPlano3D curvaCamber;


    public Aerofolio(AerofolioBase aerofolioBase, Double corda, Double anguloBeta, Double distanciaRadial, Double anguloAxial, EixoDeCoordenadas3D eixoDeCoordenadasDeRotacao) {
        this.eixoDeCoordenadasDeRotacao = eixoDeCoordenadasDeRotacao;
        this.eixoDeCoordenadas = new EixoDeCoordenadas3D(eixoDeCoordenadasDeRotacao.eixoY, eixoDeCoordenadasDeRotacao.eixoZ)
                .setOrigem(
                        eixoDeCoordenadasDeRotacao.eixoX
                                .escala(distanciaRadial)
                                .getPontoCabeca()
                )
                .rotacionaEmTornoDeEixoEmOrigemPrincipal(eixoDeCoordenadasDeRotacao.eixoX, 180 + anguloBeta)
                .rotacionaEmTornoDeEixoEmOrigemPrincipal(eixoDeCoordenadasDeRotacao.eixoZ, anguloAxial);
        this.aerofolioBase = aerofolioBase;
        this.corda = corda;
        this.anguloBeta = anguloBeta;
        this.distanciaRadial = distanciaRadial;
        this.geometriaPlano = aerofolioBase
                .geometriaIgualmenteEspacada
                .transladaParaCentroide()
                .escalaGeometria(corda)
                .multiplicaCurvaY(-1.0)
                .setEixoDeCoordenadas(this.eixoDeCoordenadas);
        this.geometria = new GeometriaEmPlano3D(
                this.geometriaPlano
        );
        this.curvaCamber = new FuncaoEmPlano3D(
                    aerofolioBase.curvaCamber
                            .transladaPontos(-aerofolioBase.geometriaIgualmenteEspacada.centroide.x,
                                            -aerofolioBase.geometriaIgualmenteEspacada.centroide.y)
                            .escala(corda)
                            .multiplicaCurvaY(-1.0)
                            .setEixoDeCoordenadas(this.eixoDeCoordenadas)
        );
    }

    public void plotAerofolio() throws Exception {
        geometria.plot();
    }
}

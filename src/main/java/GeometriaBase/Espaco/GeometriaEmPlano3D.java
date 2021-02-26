package GeometriaBase.Espaco;

import GeometriaBase.Plano.Geometria2D;

import java.util.List;
import java.util.stream.Collectors;

public class GeometriaEmPlano3D extends Curva3D {
    public Geometria2D geometria2D;
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;

    public GeometriaEmPlano3D(Geometria2D geometria2D) {
        super(descreveGeometriaEmEixoDeCoordenadasPrincipal(geometria2D));
        this.geometria2D = geometria2D;
    }

    public GeometriaEmPlano3D(EixoDeCoordenadas3D eixoDeCoordenadas,Geometria2D geometria2D) {
        super(geometria2D.descrevePontosEmNovoEixoDeCoordenadas(eixoDeCoordenadas));
        this.geometria2D = geometria2D;
        this.eixoDeCoordenadas = eixoDeCoordenadas;
    }

    static List<Ponto3D> descreveGeometriaEmEixoDeCoordenadasPrincipal(Geometria2D geometria2D) {
        return geometria2D.getPontos().parallelStream().map(p -> new Ponto3D(p.eixoDeCoordenadas,p.x,p.y,0.0).descrevePontoEmEixoDeCoordenadasPrincipal()).collect(Collectors.toList());
    }

    static Curva3D descreveGeometriaEmNovoEixoDeCoordenadas(EixoDeCoordenadas3D eixoDeCoordenadas, Geometria2D geometria2D) {
        return new Curva3D(geometria2D.getPontos().parallelStream().map(p -> new Ponto3D(p.eixoDeCoordenadas,p.x,p.y,0.0)).collect(Collectors.toList()))
                    .descrevePontosEmNovoEixoDeCoordenadas(eixoDeCoordenadas);
    }
}

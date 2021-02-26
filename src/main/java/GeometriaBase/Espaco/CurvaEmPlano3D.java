package GeometriaBase.Espaco;

import GeometriaBase.Plano.Curva2D;

import java.util.stream.Collectors;

public class CurvaEmPlano3D extends Curva3D{
    // Curva sempre estará contida no plano XY do eixo de coordenadas fornecido
    public Curva2D curva2D;
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;

    public CurvaEmPlano3D(Curva2D curva2D) {
        // pressupõe que o novo sistema de coordenadas seja o principal
        super(geraCurva3DAPartirDeCurva2DEEixo(curva2D));
        this.curva2D = curva2D;
    }

    public CurvaEmPlano3D(EixoDeCoordenadas3D eixoDeCoordenadas, Curva2D curva2D) {
        super(geraCurva3DAPartirDeCurva2DEEixo(eixoDeCoordenadas,curva2D));
        this.curva2D = curva2D;
        this.eixoDeCoordenadas = eixoDeCoordenadas;
    }

    static Curva3D geraCurva3DAPartirDeCurva2DEEixo(Curva2D curva2D) {
        return new Curva3D(curva2D.eixoDeCoordenadas, curva2D.getPontos().stream().map(p -> new Ponto3D(curva2D.eixoDeCoordenadas,p.x,p.y,0.0)).collect(Collectors.toList()))
                .descrevePontosEmEixoDeCoordenadasPrincipal() ;
    }

    static Curva3D geraCurva3DAPartirDeCurva2DEEixo(EixoDeCoordenadas3D eixoDeCoordenadas, Curva2D curva2D) {
        return new Curva3D(curva2D.eixoDeCoordenadas, curva2D.getPontos().stream().map(p -> new Ponto3D(curva2D.eixoDeCoordenadas,p.x,p.y,0.0)).collect(Collectors.toList()))
                .descrevePontosEmNovoEixoDeCoordenadas(eixoDeCoordenadas) ;
    }
}

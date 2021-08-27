package GeometriaBase.Plano;

import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Ponto3D;
import org.ejml.simple.SimpleMatrix;

public class Ponto2D {
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;
    public Double x, y;

    public Ponto2D(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Ponto2D(EixoDeCoordenadas3D eixoDeCoordenadas, Double x, Double y) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.x = x;
        this.y = y;
    }

    public Ponto2D(){
        this.x = (double) 0;
        this.y = (double) 0;
    }

    public Ponto2D subtraiPontoY(Ponto2D ponto) {
        return new Ponto2D(this.x, this.y - ponto.y);
    }

    public Ponto2D somaPontoY(Ponto2D ponto) {
        return new Ponto2D(this.x, this.y + ponto.y);
    }

    public Ponto2D somaPonto(Ponto2D ponto) { return new Ponto2D(this.x + ponto.x, this.y + ponto.y);}

    public Ponto2D subtraiPonto(Ponto2D ponto) { return new Ponto2D(this.x - ponto.x, this.y - ponto.y);}

    public Double getDistanciaDaOrigem() {
        return Math.sqrt(x*x + y*y);
    }

    public Double getDistanciaAPonto(Ponto2D ponto) {
        return this.subtraiPonto(ponto).getDistanciaDaOrigem();
    }

    /**
    * Em t = 1 o ponto retornado possui as mesmas coordenadas do ponto fornecido, em
    * t =  0 o ponto retornado possui as mesmas coordenadas da instancia que chamou o
    * método. Valores intermediários retornam coordenadas itermediárias*/
    public Ponto2D getPontoInterpolado(Ponto2D ponto, Double t) {
        return new Ponto2D(ponto.x*t + this.x*(t-1), ponto.y*t + this.y*(t-1));
    }

    public Ponto3D descrevePontoEmEixoDeCoordenadasPrincipal() {
        return this.converteEmPonto3D().descrevePontoEmEixoDeCoordenadasPrincipal();
    }

    public Ponto3D descrevePontoEmNovoEixoDeCoordenadas(EixoDeCoordenadas3D novoEixoDeCoordenadas) {
        return this.converteEmPonto3D().descrevePontoEmNovoEixoDeCoordenadas(novoEixoDeCoordenadas);
    }

    public Ponto3D converteEmPonto3D() {
        return new Ponto3D(this.eixoDeCoordenadas,this.x,this.y,0.0);
    }

    public SimpleMatrix converteParaVetorColuna() {
        return new SimpleMatrix(3,1,false,new double[] {this.x, this.y, 0.0});
    }

    public boolean equals(Ponto2D ponto) {
        return this.x.equals(ponto.x) && this.y.equals(ponto.y);
    }
}

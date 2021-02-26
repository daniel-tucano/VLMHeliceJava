package GeometriaBase.Primitivos;

import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Plano.Curva2D;
import GeometriaBase.Espaco.Curva3D;
import GeometriaBase.Espaco.Direcao3D;
import GeometriaBase.Plano.Ponto2D;

import java.util.ArrayList;
import java.util.List;

public class Circunferencia extends Curva2D {
    public Double raio;
    public Integer numeroDePontos;

    public Circunferencia(Double raio, Integer numeroDePontos) {
        super(constroiCircunferencia(raio,numeroDePontos));
        this.raio= raio;
        this.numeroDePontos = numeroDePontos;
    }
    public Circunferencia(EixoDeCoordenadas3D eixoDeCoordenadas, Double raio, Integer numeroDePontos) {
        super(eixoDeCoordenadas,constroiCircunferencia(raio,numeroDePontos));
        this.raio= raio;
        this.numeroDePontos = numeroDePontos;
    }

    static List<Ponto2D> constroiCircunferencia(Double raio, Integer numeroDePontos) {
        List<Ponto2D> pontos = new ArrayList<>();
        for (int i = 0; i < numeroDePontos; i++) {
            pontos.add(new Ponto2D(raio*Math.cos(2*Math.PI*i/numeroDePontos), raio*Math.sin(2*Math.PI*i/numeroDePontos)));
        }
        return pontos;
    }

}

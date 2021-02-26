package GeometriaBase.Primitivos;

import GeometriaBase.Espaco.CoordenadaCilindrica;
import GeometriaBase.Espaco.Curva3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Ponto3D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurvaHelicoidal extends Curva3D {
    public Integer numeroDeVoltas;
    public Integer numeroDePontosPorVolta;
    public Double raio;
    public Double zInicio;
    public Double zFinal;
    public Double anguloInicialEmGraus;
    public boolean sentidoAntiHorario;

    public CurvaHelicoidal(Double raio, Double zInicio, Double zFinal, Double anguloInicialEmGraus, boolean sentidoAntiHorario, Integer numeroDePontosPorVolta, Integer numeroDeVoltas, EixoDeCoordenadas3D eixoDeCoordenadas) {
        super(eixoDeCoordenadas, geraPontosCurvaHelicoidal(raio,zInicio,zFinal,anguloInicialEmGraus, sentidoAntiHorario, numeroDePontosPorVolta, numeroDeVoltas));
        this.raio = raio;
        this.zInicio = zInicio;
        this.zFinal = zFinal;
        this.anguloInicialEmGraus = anguloInicialEmGraus;
        this.sentidoAntiHorario = sentidoAntiHorario;
        this.numeroDePontosPorVolta = numeroDePontosPorVolta;
        this.numeroDeVoltas = numeroDeVoltas;
    }

    public CurvaHelicoidal(CoordenadaCilindrica pontoInicial, Double tamanhoDaCurvaEmZ, boolean sentidoAntiHorario, Integer numeroDePontosPorVolta, Integer numeroDeVoltas, EixoDeCoordenadas3D eixoDeCoordenadas) {
        super(eixoDeCoordenadas, geraPontosCurvaHelicoidal(pontoInicial, tamanhoDaCurvaEmZ,  sentidoAntiHorario, numeroDePontosPorVolta,  numeroDeVoltas));
        this.raio = pontoInicial.raio;
        this.zInicio = pontoInicial.z;
        this.zFinal = pontoInicial.z + tamanhoDaCurvaEmZ;
        this.anguloInicialEmGraus = pontoInicial.anguloEmZ;
        this.sentidoAntiHorario = sentidoAntiHorario;
        this.numeroDeVoltas = numeroDeVoltas;
        this.numeroDePontosPorVolta = numeroDePontosPorVolta;
    }

    static List<Ponto3D> geraPontosCurvaHelicoidal(Double raio, Double zInicio, Double zFinal, Double anguloInicialEmGraus, boolean sentidoAntiHorario,Integer numeroDePontosPorVolta, Integer numeroDeVoltas) {
        List<CoordenadaCilindrica> pontos = new ArrayList<CoordenadaCilindrica>();
        CoordenadaCilindrica pontoInicial = new CoordenadaCilindrica(raio,zInicio,anguloInicialEmGraus);
        double dZPorPonto = (zFinal - zInicio)/((numeroDePontosPorVolta - 1)*numeroDeVoltas);
        double dAnguloPonto = 360.0/(numeroDePontosPorVolta - 1)*(sentidoAntiHorario?1.0:-1.0);
        for (int nVolta = 0; nVolta < numeroDeVoltas; nVolta++) {
            for (int nPontoVolta = 0; nPontoVolta < numeroDePontosPorVolta; nPontoVolta++) {
                pontos.add(pontoInicial.tansladaPontoEmZEAngulo(dZPorPonto*nPontoVolta + nVolta*(zFinal - zInicio)/numeroDeVoltas, dAnguloPonto*nPontoVolta));
            }
        }

        return pontos.stream().map(CoordenadaCilindrica::converteParaPonto3D).collect(Collectors.toList());
    }

    static List<Ponto3D> geraPontosCurvaHelicoidal(CoordenadaCilindrica pontoInicial, Double tamanhoDaCurvaEmZ, boolean sentidoAntiHorario,Integer numeroDePontosPorVolta, Integer numeroDeVoltas) {
        List<CoordenadaCilindrica> pontos = new ArrayList<CoordenadaCilindrica>();
        double dZPorPonto = tamanhoDaCurvaEmZ/((numeroDePontosPorVolta - 1)*numeroDeVoltas);
        double dAnguloPonto = 360.0/(numeroDePontosPorVolta - 1)*(sentidoAntiHorario?1.0:-1.0);
        for (int nVolta = 0; nVolta < numeroDeVoltas; nVolta++) {
            for (int nPontoVolta = 0; nPontoVolta < numeroDePontosPorVolta; nPontoVolta++) {
                pontos.add(pontoInicial.tansladaPontoEmZEAngulo(dZPorPonto*nPontoVolta + nVolta*tamanhoDaCurvaEmZ/numeroDeVoltas, dAnguloPonto*nPontoVolta));
            }
        }

        return pontos.stream().map(CoordenadaCilindrica::converteParaPonto3D).collect(Collectors.toList());
    }

    @Override
    public void plot() {
        this.plotComEixosIguals(new Ponto3D(0.0,0.0,(this.zFinal+this.zInicio)/2), this.raio > (this.zFinal - this.zInicio) ? this.raio : (this.zFinal - this.zInicio));
    }
}

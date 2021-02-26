package GeometriaBase.Plano;

import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static util.MetodosEstaticos.linspace;

public class Funcao2D extends Curva2D {
    private final List<Ponto2D> pontosOrdemCrescente;
    private final PolynomialSplineFunction funcaoInterpoladora;

    public Funcao2D(List<Ponto2D> pontos) {
        super(pontos);
        this.pontosOrdemCrescente = super.getPontos().stream().sorted(Comparator.comparingDouble(p -> p.x)).collect(Collectors.toList());
        this.funcaoInterpoladora = new LinearInterpolator()
                .interpolate(this.pontosOrdemCrescente.stream().mapToDouble(p -> p.x).toArray(), this.pontosOrdemCrescente.stream().mapToDouble(p -> p.y).toArray());
    }

    public Funcao2D(EixoDeCoordenadas3D eixoDeCoordenadas, List<Ponto2D> pontos) {
        super(eixoDeCoordenadas,pontos);
        this.pontosOrdemCrescente = super.getPontos().stream().sorted(Comparator.comparingDouble(p -> p.x)).collect(Collectors.toList());
        this.funcaoInterpoladora = new LinearInterpolator()
                .interpolate(this.pontosOrdemCrescente.stream().mapToDouble(p -> p.x).toArray(), this.pontosOrdemCrescente.stream().mapToDouble(p -> p.y).toArray());
    }

    public Funcao2D(List<Double> pontosX, List<Double> pontosY) {
        super(pontosX, pontosY);
        this.pontosOrdemCrescente = super.getPontos().stream().sorted(Comparator.comparingDouble(p -> p.x)).collect(Collectors.toList());
        this.funcaoInterpoladora = new LinearInterpolator()
                .interpolate(this.pontosOrdemCrescente.stream().mapToDouble(p -> p.x).toArray(), this.pontosOrdemCrescente.stream().mapToDouble(p -> p.y).toArray());
    }

    public Funcao2D(EixoDeCoordenadas3D eixoDeCoordenadas, List<Double> pontosX, List<Double> pontosY) {
        super(eixoDeCoordenadas, pontosX, pontosY);
        this.pontosOrdemCrescente = super.getPontos().stream().sorted(Comparator.comparingDouble(p -> p.x)).collect(Collectors.toList());
        this.funcaoInterpoladora = new LinearInterpolator()
                .interpolate(this.pontosOrdemCrescente.stream().mapToDouble(p -> p.x).toArray(), this.pontosOrdemCrescente.stream().mapToDouble(p -> p.y).toArray());
    }

    public Funcao2D(Curva2D curva) {
        super(curva);
        this.pontosOrdemCrescente = super.getPontos().stream().sorted(Comparator.comparingDouble(p -> p.x)).collect(Collectors.toList());
        this.funcaoInterpoladora = new LinearInterpolator()
                .interpolate(this.pontosOrdemCrescente.stream().mapToDouble(p -> p.x).toArray(), this.pontosOrdemCrescente.stream().mapToDouble(p -> p.y).toArray());
    }

    public List<Ponto2D> getPontosOrdemCrescente() {return pontosOrdemCrescente;}

    public Funcao2D geraFuncaoIgualmenteEspacada( Integer nPontos ) {

        List<Double> novoPontosX =  linspace(this.getPontosX().get(0), this.getPontosX().get(this.getPontosX().size() - 1), nPontos);
        List<Double> novoPontosY = novoPontosX.stream().map(this.funcaoInterpoladora::value).collect(Collectors.toList());
        return new Funcao2D(novoPontosX, novoPontosY);
    }

    public Ponto2D obtemPontoInterpolado(Double x) {
        return new Ponto2D(this.eixoDeCoordenadas,x, this.funcaoInterpoladora.value(x));
    }

    public Funcao2D subtraiCurvaY(Curva2D curva2D) {
        return new Funcao2D(IntStream
                            .range(0, this.getPontos().size()-1)
                            .mapToObj(i -> this.getPontos().get(i).subtraiPontoY(curva2D.getPontos().get(i)))
                            .collect(Collectors.toList()));
    }

    public Funcao2D somaCurvaY(Curva2D curva2D) {
        return new Funcao2D(IntStream
                            .range(0, this.getPontos().size()-1)
                            .mapToObj(i -> this.getPontos().get(i).somaPontoY(curva2D.getPontos().get(i)))
                            .collect(Collectors.toList()));
    }

    @Override
    public Funcao2D multiplicaCurvaY(Double multiplicador) {
        return new Funcao2D(this.getPontosX(), this.getPontosY().stream().map(y -> y*multiplicador).collect(Collectors.toList()));
    }

    @Override
    public Funcao2D multiplicaCurvaX(Double multiplicador) {
        return new Funcao2D(this.getPontosX().stream().map(x -> multiplicador*x).collect(Collectors.toList()), this.getPontosY());
    }

    @Override
    public Funcao2D divideCurvaY(Double divisor) {
        return new Funcao2D(this.getPontosX(), this.getPontosY().stream().map(y -> y/divisor).collect(Collectors.toList()));
    }

    public Funcao2D escala(Double fatorDeEscala) {
        return new Funcao2D(this.getPontos().stream().map(p -> new Ponto2D(p.x*fatorDeEscala, p.y*fatorDeEscala)).collect(Collectors.toList()));
    }

    public Funcao2D escalaEmX(Double fatorDeEscala) {
        return new Funcao2D(this.getPontos().stream().map(p -> new Ponto2D(p.x*fatorDeEscala, p.y)).collect(Collectors.toList()));
    }

    @Override
    public Funcao2D setEixoDeCoordenadas(EixoDeCoordenadas3D eixoDeCoordenadas) {
        return new Funcao2D(eixoDeCoordenadas, this.getPontos());
    }

    @Override
    public Funcao2D transladaPontos(Double translacaoEmX, Double translacaoEmY) {
        return new Funcao2D(this.getPontosX().stream().map(x -> x + translacaoEmX).collect(Collectors.toList()), this.getPontosY().stream().map(y -> y + translacaoEmY).collect(Collectors.toList()));
    }

}

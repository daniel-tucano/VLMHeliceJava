package util;

import GeometriaBase.Espaco.Vetor3DBase;
import GeometriaBase.Plano.Funcao2D;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class MetodosEstaticos {

    public static List<Double> linspace(double start, double stop, int n)
    {
        List<Double> result = new ArrayList<Double>();

        double step = (stop-start)/(n-1);

        for(int i = 0; i <= n-2; i++)
        {
            result.add(start + (i * step));
        }
        result.add(stop);

        return result;
    }

    public static long factorial(int n) {
        return LongStream.rangeClosed(1,n).reduce(1, (long x, long y) -> x * y);
    }

    public static SimpleMatrix geraMatrizDeRotacao(Vetor3DBase vetorEixoDeRotacao, Double anguloDeRotacaoEmRadianos) {
        // seno de theta
        Double sT = Math.sin(anguloDeRotacaoEmRadianos);
        // cosseno de theta
        double cT = Math.cos(anguloDeRotacaoEmRadianos);
        Double a = vetorEixoDeRotacao.getComponenteX().modulo;
        Double b = vetorEixoDeRotacao.getComponenteY().modulo;
        Double c = vetorEixoDeRotacao.getComponenteZ().modulo;
        double [][] matrizDouble = {{cT + (1 - cT)*a*a, (1 - cT)*a*b + sT*c, (1 - cT)*a*c - sT*b},
                                    {(1 - cT)*b*a - sT*c, cT + (1 - cT)*b*b, (1 - cT)*b*c + sT*a},
                                    {(1 - cT)*c*a + sT*b, (1 - cT)*c*b - sT*a, cT + (1 - cT)*c*c}};
        return new SimpleMatrix(matrizDouble);
    }

    public static Funcao2D criaFuncaoAPartirDeCoeficientesPolinomioEPontosX(double [] coeficientes, List<Double> pontosX) {
        PolynomialFunction funcaoPolinomio = new PolynomialFunction(coeficientes);
        List<Double> pontosY = pontosX.stream().map(funcaoPolinomio::value).collect(Collectors.toList());
        return new Funcao2D(pontosX, pontosY);
    }
}

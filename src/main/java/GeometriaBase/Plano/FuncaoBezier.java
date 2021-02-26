package GeometriaBase.Plano;

import GeometriaBase.Plano.Funcao2D;
import GeometriaBase.Plano.Ponto2D;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static util.MetodosEstaticos.factorial;
import static util.MetodosEstaticos.linspace;

public class FuncaoBezier extends Funcao2D {

    public List<Ponto2D> pontosDeControle;
    public List<Double> pontosDeParametrizacao;

    static List<Ponto2D> criaPontosBezier(List<Ponto2D> pontosDeControle, List<Double> pontosDeParametrizacao) {
        int n = pontosDeControle.size() - 1;
        List<Integer> i = IntStream.rangeClosed(0,n).boxed().collect(Collectors.toList());;
        List<Long> binomio = i.stream().map(integer -> factorial(n)/(factorial(integer)*factorial(n - integer))).collect(Collectors.toList());
        return pontosDeParametrizacao.stream().map( t -> {
            double x_t = (double) 0;
            double y_t = (double) 0;
            for (int j = 0; j < i.size(); j++) {
                x_t += binomio.get(j)*Math.pow(t,j)*(Math.pow(1-t, n-j)*pontosDeControle.get(j).x);
                y_t += binomio.get(j)*Math.pow(t,j)*(Math.pow(1-t, n-j)*pontosDeControle.get(j).y);
            }
            return new Ponto2D(x_t,y_t);
        }).collect(Collectors.toList());
    }

    static List<Ponto2D> criaPontosBezier(List<Ponto2D> pontosDeControle) {
        List<Double> pontosDeParametrizacao = linspace(0,1,100);
        int n = pontosDeControle.size() - 1;
        List<Integer> i = IntStream.rangeClosed(0,n).boxed().collect(Collectors.toList());;
        List<Long> binomio = i.stream().map(integer -> factorial(n)/(factorial(integer)*factorial(n - integer))).collect(Collectors.toList());
        return pontosDeParametrizacao.stream().map( t -> {
            double x_t = (double) 0;
            double y_t = (double) 0;
            for (int j = 0; j < i.size(); j++) {
                x_t += binomio.get(j)*Math.pow(t,j)*(Math.pow(1-t, n-j)*pontosDeControle.get(j).x);
                y_t += binomio.get(j)*Math.pow(t,j)*(Math.pow(1-t, n-j)*pontosDeControle.get(j).y);
            }
            return new Ponto2D(x_t,y_t);
        }).collect(Collectors.toList());
    }

    public FuncaoBezier(List<Ponto2D> pontosDeControle, List<Double> pontosDeParametrizacao) {
        super(criaPontosBezier(pontosDeControle,pontosDeParametrizacao));
        this.pontosDeControle = pontosDeControle;
        this.pontosDeParametrizacao = pontosDeParametrizacao;
    }

    public FuncaoBezier(List<Ponto2D> pontosDeControle) {
        super(criaPontosBezier(pontosDeControle));
        this.pontosDeControle = pontosDeControle;
    }

}

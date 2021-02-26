package GeometriaBase.Espaco;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.List;
import java.util.stream.Collectors;


/**
* Essa classe representa uma função vetorial que interpola vetores entre o vetor inicial
* e o vetor final de forma que cada vetor interpolado esteja associado a um numero de 0 a 1
* para isso é importante a curva formada pela ponta desses vetores quando a cauda desses esteja
* na origem forme uma curva que possuam componentes na direção formada pela subtração do primeiro
* vetor pelo ultimo vetor monotonamente crescentes
*/
public class FuncaoVetorialParametrizada {
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;
    public List<Vetor3DBase> vetoresBase;
    public List<Double> pontosVariavelDeParametrizacaoT;
    public PolynomialSplineFunction funcaoComponenteXParametrizada;
    public PolynomialSplineFunction funcaoComponenteYParametrizada;
    public PolynomialSplineFunction funcaoComponenteZParametrizada;

    public FuncaoVetorialParametrizada(List<Vetor3DBase> vetores) {
        this.vetoresBase = vetores;
        Vetor3D vetorDistanciaComecoFim = vetores.get(vetores.size()-1).subtraiVetor(vetores.get(0));
        this.pontosVariavelDeParametrizacaoT = vetores.stream().map(vet -> vet.produtoEscalar(vetorDistanciaComecoFim.direcao)/vetorDistanciaComecoFim.modulo).collect(Collectors.toList());
        this.funcaoComponenteXParametrizada = new LinearInterpolator().interpolate(this.pontosVariavelDeParametrizacaoT.stream().mapToDouble(t -> t).toArray(), vetores.stream().mapToDouble(Vetor3DBase::getModuloComponenteX).toArray());
        this.funcaoComponenteYParametrizada = new LinearInterpolator().interpolate(this.pontosVariavelDeParametrizacaoT.stream().mapToDouble(t -> t).toArray(), vetores.stream().mapToDouble(Vetor3DBase::getModuloComponenteY).toArray());
        this.funcaoComponenteZParametrizada = new LinearInterpolator().interpolate(this.pontosVariavelDeParametrizacaoT.stream().mapToDouble(t -> t).toArray(), vetores.stream().mapToDouble(Vetor3DBase::getModuloComponenteZ).toArray());
    }

    public FuncaoVetorialParametrizada(List<Vetor3DBase> vetores, List<Double> pontosVariavelDeParametrizacaoT) {
        this.vetoresBase = vetores;
        this.pontosVariavelDeParametrizacaoT = pontosVariavelDeParametrizacaoT;
        this.funcaoComponenteXParametrizada = new LinearInterpolator().interpolate(this.pontosVariavelDeParametrizacaoT.stream().mapToDouble(t -> t).toArray(), vetores.stream().mapToDouble(Vetor3DBase::getModuloComponenteX).toArray());
        this.funcaoComponenteYParametrizada = new LinearInterpolator().interpolate(this.pontosVariavelDeParametrizacaoT.stream().mapToDouble(t -> t).toArray(), vetores.stream().mapToDouble(Vetor3DBase::getModuloComponenteY).toArray());
        this.funcaoComponenteZParametrizada = new LinearInterpolator().interpolate(this.pontosVariavelDeParametrizacaoT.stream().mapToDouble(t -> t).toArray(), vetores.stream().mapToDouble(Vetor3DBase::getModuloComponenteZ).toArray());
    }

    public Vetor3D getVetorInterpolado(double valorDe0A1) {
        return new Vetor3D( this.funcaoComponenteXParametrizada.value(valorDe0A1),
                            this.funcaoComponenteYParametrizada.value(valorDe0A1),
                            this.funcaoComponenteZParametrizada.value(valorDe0A1));
    }
}

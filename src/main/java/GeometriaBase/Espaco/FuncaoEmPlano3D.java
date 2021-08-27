package GeometriaBase.Espaco;

import GeometriaBase.Plano.Curva2D;
import GeometriaBase.Plano.Funcao2D;
import GeometriaBase.Plano.Ponto2D;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.List;
import java.util.stream.Collectors;

public class FuncaoEmPlano3D extends Curva3D {
    public final Funcao2D funcao2D;
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;

    public FuncaoEmPlano3D(Funcao2D funcao2D) {
        super(descreveFuncaoEmeixoDeCoordenadasPrincipal(funcao2D));
        this.funcao2D = funcao2D;
    }

    static List<Ponto3D> descreveFuncaoEmeixoDeCoordenadasPrincipal(Funcao2D funcao2D) {
        return funcao2D.getPontos().parallelStream().map(p -> new Ponto3D(funcao2D.eixoDeCoordenadas,p.x,p.y,0.0).descrevePontoEmEixoDeCoordenadasPrincipal()).collect(Collectors.toList());
    }

    public Ponto3D pontoInterpoladoEmEixoDeCoordenadasPrincipal(Double xEmCoordenadasDoPlano) {
        Ponto2D pontoInterpolado = this.funcao2D.obtemPontoInterpolado(xEmCoordenadasDoPlano);
        return pontoInterpolado.descrevePontoEmEixoDeCoordenadasPrincipal();
    }

    public Vetor3D getDirecaoNormalEmEixoDeCoordenadasPrincipal(Double xEmCoordenadasDoPlano) {
        return this.funcao2D.getDirecaoNormal(xEmCoordenadasDoPlano).descreveVetorEmEixoDeCoordenadasPrincipal();
    }
}

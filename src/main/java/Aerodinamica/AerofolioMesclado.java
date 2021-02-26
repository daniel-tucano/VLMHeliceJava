package Aerodinamica;

public class AerofolioMesclado extends AerofolioBase {
    public AerofolioPuro aerofolioPuro1;
    public AerofolioPuro aerofolioPuro2;
    public Double porcentagemAerofolio1;
    public Double porcentagemAerofolio2;

    public AerofolioMesclado(AerofolioPuro aerofolioPuro1, AerofolioPuro aerofolioPuro2, Double porcentagemAerofolio1) {
        super(
                aerofolioPuro1.intradorso
                        .geraFuncaoIgualmenteEspacada(30)
                        .multiplicaCurvaY(porcentagemAerofolio1)
                        .somaCurvaY(aerofolioPuro2.intradorso.multiplicaCurvaY(1 - porcentagemAerofolio1)),
                aerofolioPuro1.extradorso
                        .geraFuncaoIgualmenteEspacada(30)
                        .multiplicaCurvaY(porcentagemAerofolio1)
                        .somaCurvaY(aerofolioPuro2.extradorso.multiplicaCurvaY(1 - porcentagemAerofolio1))

        );
        this.aerofolioPuro1 = aerofolioPuro1;
        this.aerofolioPuro2 = aerofolioPuro2;
        this.porcentagemAerofolio1 = porcentagemAerofolio1;
        this.porcentagemAerofolio2 = 1 - porcentagemAerofolio1;
    }

    @Override
    public void plotAerofolio() {
        geometriaIgualmenteEspacada.plot(this.porcentagemAerofolio1 + "% " + this.aerofolioPuro1.nome + " " + this.porcentagemAerofolio2 + "% " + this.aerofolioPuro2.nome,
                (double) 0, (double) 1, -0.5, 0.5);
    }

    @Override
    public PontoPolar achaPontoPolarMaisProximo(Double mach, Double reynolds, Double alpha) {
        PontoPolar pontoPolarAerofolioPuro1 = this.aerofolioPuro1.achaPontoPolarMaisProximo(mach, reynolds, alpha);
        PontoPolar pontoPolarAerofolioPuro2 = this.aerofolioPuro2.achaPontoPolarMaisProximo(mach, reynolds, alpha);
        return pontoPolarAerofolioPuro1.pontoPolarMesclado(pontoPolarAerofolioPuro1,pontoPolarAerofolioPuro2,this.porcentagemAerofolio1,this.porcentagemAerofolio2);
    }

}

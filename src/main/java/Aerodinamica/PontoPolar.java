package Aerodinamica;

public class PontoPolar {
    public Double alpha;
    public Double cl;
    public Double cd;
    public Double cm;

    public PontoPolar(Double alpha, Double cl, Double cd, Double cm) {
        this.alpha = alpha;
        this.cl = cl;
        this.cd = cd;
        this.cm = cm;
    }

    public PontoPolar(PontoPolar pontoPolar) {
        this.alpha = pontoPolar.alpha;
        this.cl = pontoPolar.cl;
        this.cd = pontoPolar.cd;
        this.cm = pontoPolar.cm;
    }

    public PontoPolar multiplicaValoresPorEscalar(Double multiplicador) {
        return new PontoPolar(this.alpha, this.cl * multiplicador, this.cd * multiplicador, this.cm * multiplicador);
    }

    public PontoPolar somaPontoPolar(PontoPolar pontoPolar) {
        return new PontoPolar(this.alpha, this.cl + pontoPolar.cl, this.cd + pontoPolar.cd, this.cm + pontoPolar.cm);
    }

    public PontoPolar pontoPolarMesclado(PontoPolar pontoPolar1, PontoPolar pontoPolar2, Double porcentagemAerofolioPuro1, Double porcentagemAerofolioPuro2) {
        return pontoPolar1.multiplicaValoresPorEscalar(porcentagemAerofolioPuro1).somaPontoPolar(pontoPolar2.multiplicaValoresPorEscalar(porcentagemAerofolioPuro2));
    }

    public PontoPolar() {}
}

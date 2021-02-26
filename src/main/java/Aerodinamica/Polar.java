package Aerodinamica;

import GeometriaBase.Plano.Funcao2D;
import util.RunJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Polar implements Cloneable {
    public Double mach;
    public Double reynolds;
    public List<PontoPolar> pontosPolar;
    public Funcao2D clAlpha;
    public Funcao2D cdAlpha;
    public Funcao2D cmAlpha;
    public PropriedadesPolar propriedadesPolar;

    public Polar(
            Double mach,
            Double reynolds,
            List<PontoPolar> pontosPolar,
            PropriedadesPolar propriedadesPolar
    ) {
        this.mach = mach;
        this.reynolds = reynolds;
        this.pontosPolar = pontosPolar;
        this.propriedadesPolar = propriedadesPolar;
        geraFuncoesPolar();
    }

    public Polar() {}

    public Polar(RunJSON runJSON) {
        this.mach = runJSON.mach;
        this.reynolds = runJSON.reynolds;
        this.pontosPolar = new ArrayList<PontoPolar>();
        this.propriedadesPolar = new PropriedadesPolar(runJSON.polarProperties);

        for (int i = 0; i < runJSON.polar.alpha.size(); i++) {
            this.pontosPolar.add( new PontoPolar(runJSON.polar.alpha.get(i), runJSON.polar.cl.get(i), runJSON.polar.cd.get(i), runJSON.polar.cm.get(i)) );
        }
        geraFuncoesPolar();
    }

    private void geraFuncoesPolar() {
        this.clAlpha = new Funcao2D(this.pontosPolar.stream().map(p -> p.alpha).collect(Collectors.toList()), this.pontosPolar.stream().map(p -> p.cl).collect(Collectors.toList()));
        this.cdAlpha = new Funcao2D(this.pontosPolar.stream().map(p -> p.alpha).collect(Collectors.toList()), this.pontosPolar.stream().map(p -> p.cd).collect(Collectors.toList()));
        this.cmAlpha = new Funcao2D(this.pontosPolar.stream().map(p -> p.alpha).collect(Collectors.toList()), this.pontosPolar.stream().map(p -> p.cm).collect(Collectors.toList()));
    }

    public PontoPolar achaPontoPolarAlpha(Double alpha) {
        return new PontoPolar(alpha, clAlpha.obtemPontoInterpolado(alpha).y, cdAlpha.obtemPontoInterpolado(alpha).y, cmAlpha.obtemPontoInterpolado(alpha).y);
    }
}

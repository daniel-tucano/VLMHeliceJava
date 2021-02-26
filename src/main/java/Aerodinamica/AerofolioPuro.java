package Aerodinamica;

import GeometriaBase.Plano.Funcao2D;
import GeometriaBase.Plano.Ponto2D;
import util.AirfoilJSON;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AerofolioPuro extends AerofolioBase {
    public String nome;
    public List<Polar> polars;

    public AerofolioPuro(
            String nome,
            Funcao2D intradorso,
            Funcao2D extradorso,
            List<Polar> polars
    ) {
        super(
                intradorso,
                extradorso
        );
        this.nome = nome;
        this.polars = polars;
    }

    public AerofolioPuro(AirfoilJSON airfoilJSON) {
        super(
                constroiIntradorso(airfoilJSON),
                constroiExtradorso(airfoilJSON)
        );
        this.nome = airfoilJSON.name;
        this.polars = airfoilJSON.runJSONS.stream().map(Polar::new).collect(Collectors.toList());
    }

    static Funcao2D constroiExtradorso(AirfoilJSON airfoilJSON) {
        List<Ponto2D> pontosExtradorso = new ArrayList<Ponto2D>();
        for (int i = 0; i < airfoilJSON.geometrie.x.size(); i++) {
            if(airfoilJSON.geometrie.side.get(i).equals("Top")) {
                pontosExtradorso.add(new Ponto2D(airfoilJSON.geometrie.x.get(i), airfoilJSON.geometrie.y.get(i)));
            }
        }
        return new Funcao2D(pontosExtradorso);
    }

    static Funcao2D constroiIntradorso(AirfoilJSON airfoilJSON) {
        List<Ponto2D> pontosIntradorso = new ArrayList<Ponto2D>();
        for (int i = 0; i < airfoilJSON.geometrie.x.size(); i++) {
            if(airfoilJSON.geometrie.side.get(i).equals("Bottom")) {
                pontosIntradorso.add(new Ponto2D(airfoilJSON.geometrie.x.get(i), airfoilJSON.geometrie.y.get(i)));
            }
        }
        return new Funcao2D(pontosIntradorso);
    }

    @Override
    public void plotAerofolio() {
        geometriaIgualmenteEspacada.plot(this.nome, (double) 0, (double) 1, -0.5, 0.5);
    }

    @Override
    public PontoPolar achaPontoPolarMaisProximo(Double mach, Double reynolds, Double alpha) {
        Double machMaisProximo = this.polars.stream().map(p -> p.mach).min(Comparator.comparing(pMach -> Math.abs( pMach - mach ))).get();
        Polar polarComReynoldsEMachMaisProximos = this.polars.stream().filter(p -> p.mach.equals(machMaisProximo)).min(Comparator.comparing(p -> Math.abs(p.reynolds - reynolds))).get();
        return polarComReynoldsEMachMaisProximos.achaPontoPolarAlpha(alpha);
    }
}

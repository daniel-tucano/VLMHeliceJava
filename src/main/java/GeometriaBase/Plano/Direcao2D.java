package GeometriaBase.Plano;

public class Direcao2D extends Vetor2D{
    public Direcao2D(Ponto2D ponto) {
        super(new Ponto2D(ponto.x/ponto.getDistanciaDaOrigem(), ponto.y/ponto.getDistanciaDaOrigem()));
    }
}

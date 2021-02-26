package VLM;

import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Espaco.Vetor3D;
import org.jzy3d.colors.Color;
import org.jzy3d.plot3d.primitives.LineStrip;

public class VorticeFilamento {
    public Double gama;
    public Vetor3D ponta;
    public Vetor3D cauda;
    public Vetor3D vetorFilamento;

    public VorticeFilamento(Double gama, Vetor3D ponta, Vetor3D cauda) {
        this.gama = gama;
        this.ponta = ponta;
        this.cauda = cauda;
        this.vetorFilamento = new Vetor3D(ponta,cauda);
    }

    public VorticeFilamento(Double gama, Ponto3D ponta, Ponto3D cauda) {
        this.gama = gama;
        this.ponta = new Vetor3D(ponta);
        this.cauda = new Vetor3D(cauda);
        this.vetorFilamento = this.ponta.subtraiVetor(this.cauda);
        this.vetorFilamento.posicao = cauda;
    }

    public Vetor3D calculaInfluenciaEmPonto(Ponto3D ponto) {
        Vetor3D vetorPonto = new Vetor3D(ponto);
        Vetor3D vetorDistanciaPontaFilamentoAPonto = vetorPonto.subtraiVetor(this.ponta);
        Vetor3D vetorDistanciaCaudaFilamentoAPonto = vetorPonto.subtraiVetor(this.cauda);
        double denominador = (vetorDistanciaPontaFilamentoAPonto.modulo*vetorDistanciaCaudaFilamentoAPonto.modulo
                + vetorDistanciaPontaFilamentoAPonto.produtoEscalar(vetorDistanciaCaudaFilamentoAPonto))
                * 4*Math.PI;

        return denominador >= 10.0e-6 ? vetorDistanciaCaudaFilamentoAPonto.produtoVetorial(vetorDistanciaPontaFilamentoAPonto).escala((1/vetorDistanciaPontaFilamentoAPonto.modulo + 1/vetorDistanciaCaudaFilamentoAPonto.modulo)/denominador) : new Vetor3D(0.0,0.0,0.0);
    }

    public LineStrip getLineStrip() {
        return this.vetorFilamento.getLineStripSeta(Color.RED, 1.0f);
    }
}

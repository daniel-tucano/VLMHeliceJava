package Aerodinamica;

import GeometriaBase.Espaco.Vetor3D;
import SolverAerodinamicoHelice.CondicaoDeOperacao;
import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;

public class CampoVetorialEscoamentoIntocadoHeliceTest extends TestCase {

    public CondicaoDeOperacao condicaoDeOperacao = new CondicaoDeOperacao(10.0,12000.0,Fluidos.AR,0.28);
    public CampoVetorialEscoamentoIntocadoHelice campoVetorialEscoamentoIntocadoHelice = new CampoVetorialEscoamentoIntocadoHelice(condicaoDeOperacao);

    public void testGetVelocidadeEscoamentoIntocado() {
        Vetor3D vetorDistanciaACentroDeRotacao = new Vetor3D(0.14,0.14,0.12);
        Vetor3D vetorVelocidadeEscoamento = campoVetorialEscoamentoIntocadoHelice.getVelocidadeEscoamentoIntocado(vetorDistanciaACentroDeRotacao.getPontoCabeca().converteParaCoordenadaCilindrica());
        Assert.assertTrue(vetorVelocidadeEscoamento.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{Math.PI*2*0.14*12000.0/60.0},{-Math.PI*2*0.14*12000.0/60.0},{-10.0}}),10.0e-7));
    }
}
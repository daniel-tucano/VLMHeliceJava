package Aerodinamica;

import GeometriaBase.Espaco.Vetor3D;
import SolverAerodinamicoHelice.CondicaoDeOperacao;
import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;

public class CampoVetorialEscoamentoIntocadoHeliceTest extends TestCase {

    public CondicaoDeOperacao condicaoDeOperacao = new CondicaoDeOperacao(10.0, 12000.0, Fluidos.AR, 0.28);
    public CampoVetorialEscoamentoIntocadoHelice campoVetorialEscoamentoIntocadoHelice =
            new CampoVetorialEscoamentoIntocadoHelice(
                    condicaoDeOperacao);

    public void testGetVelocidadeEscoamentoIntocado() throws InterruptedException {
        // ---------------------------------------- Teste para vetor na diagonal --------------------------------------
        Vetor3D vetorDistanciaACentroDeRotacao1 = new Vetor3D(0.14, 0.14, 0.12);

        Vetor3D vetorVelocidadeEscoamento1 = campoVetorialEscoamentoIntocadoHelice.getVelocidadeEscoamentoIntocado(
                vetorDistanciaACentroDeRotacao1.getPontoCabeca().converteParaCoordenadaCilindrica());

        Assert.assertTrue(vetorVelocidadeEscoamento1.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{Math.PI * 2 * 0.14 * 12000.0 / 60.0},
                        {-Math.PI * 2 * 0.14 * 12000.0 / 60.0},
                        {-10.0}}),
                10.0e-7));

        // ---------------------------------------- Teste para vetor no eixo X ----------------------------------------
        Vetor3D vetorDistanciaACentroDeRotacao2 = new Vetor3D(0.14, 0.0, 0.12);

        Vetor3D vetorVelocidadeEscoamento2 = campoVetorialEscoamentoIntocadoHelice.getVelocidadeEscoamentoIntocado(
                vetorDistanciaACentroDeRotacao2.getPontoCabeca().converteParaCoordenadaCilindrica());

        Assert.assertTrue(vetorVelocidadeEscoamento2.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{0.0},
                        {-Math.PI * 2 * 0.14 * 12000.0 / 60.0},
                        {-10.0}}),
                10.0e-7));

        // ---------------------------------------- Teste para vetor no eixo Y ----------------------------------------
        Vetor3D vetorDistanciaACentroDeRotacao3 = new Vetor3D(0.0, 0.14, 0.12);

        Vetor3D vetorVelocidadeEscoamento3 = campoVetorialEscoamentoIntocadoHelice.getVelocidadeEscoamentoIntocado(
                vetorDistanciaACentroDeRotacao3.getPontoCabeca().converteParaCoordenadaCilindrica());

        Assert.assertTrue(vetorVelocidadeEscoamento3.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{Math.PI * 2 * 0.14 * 12000.0 / 60.0},
                        {0.0},
                        {-10.0}}),
                10.0e-7));

    }
}
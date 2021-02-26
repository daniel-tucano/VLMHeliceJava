package GeometriaBase.Espaco;

import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;

public class Vetor3DBaseTest extends TestCase {

    public Vetor3D vetor3D1 = new Vetor3D(3.0,0.0,0.0);
    public Vetor3D vetor3D2 = new Vetor3D(0.0,2.0,0.0);
    public Vetor3D vetor3D3 = new Vetor3D(1.5,2.1,3.4);
    public Vetor3D vetor3D4 = new Vetor3D(1.0,1.0,1.0);
    public Vetor3D vetor3D5 = new Vetor3D(-1.0,-1.0,-1.0);
    public EixoDeCoordenadas3D eixoDeCoordenadas = new EixoDeCoordenadas3D(new Direcao3D(0.0,1.0,0.0), new Direcao3D(0.0,0.0,1.0));

    public void testProdutoVetorial() {
        Vetor3D produtoVec1_2 = vetor3D1.produtoVetorial(vetor3D2);
        Vetor3D produtoVec2_1 = vetor3D2.produtoVetorial(vetor3D1);
        Vetor3D produtoVec4_5 = vetor3D4.produtoVetorial(vetor3D5);
        Assert.assertTrue(produtoVec1_2.direcao.matrizVetor.isIdentical(Direcoes3D.Z.matrizVetor,10.0e-7));
        Assert.assertEquals(produtoVec1_2.modulo,6.0,10.0e-7);
        Assert.assertTrue(produtoVec2_1.direcao.matrizVetor.isIdentical(Direcoes3D.Z.matrizVetor.scale(-1.0),10.0e-7));
        Assert.assertEquals(produtoVec2_1.modulo,6.0,10.0e-7);
        Assert.assertEquals(produtoVec4_5.modulo,0.0,10.0e-7);
    }

    public void testProdutoEscalar() {
        double produtoEsc1_2 = vetor3D1.produtoEscalar(vetor3D2);
        double produtoEsc3_4 = vetor3D3.produtoEscalar(vetor3D4);
        double produtoEsc4_3 = vetor3D4.produtoEscalar(vetor3D3);
        Assert.assertEquals(produtoEsc1_2,0.0,10.0e-7);
        Assert.assertEquals(produtoEsc3_4,1.5+2.1+3.4,10.0e-7);
        Assert.assertEquals(produtoEsc4_3,1.5+2.1+3.4,10.0e-7);
    }

    public void testAnguloEntreVetores() {
        double angulo1_2 = vetor3D1.anguloEntreVetores(vetor3D2);
        double angulo4_5 = vetor3D4.anguloEntreVetores(vetor3D5);
        Assert.assertEquals(angulo1_2,90.0,10.0e-7);
        Assert.assertEquals(angulo4_5,180.0,10.0e-7);
    }

    public void testSubtraiVetor() {
        Vetor3D vetorSub3_4 = vetor3D3.subtraiVetor(vetor3D4);
        Assert.assertTrue(vetorSub3_4.matrizVetor.isIdentical(new SimpleMatrix(new double[][] {{0.5},{1.1},{2.4}}),10.0e-7));
    }

    public void testSomaVetor() {
        Vetor3D vetorSoma3_4 = vetor3D3.somaVetor(vetor3D4);
        Assert.assertTrue(vetorSoma3_4.matrizVetor.isIdentical(new SimpleMatrix(new double[][] {{2.5},{3.1},{4.4}}),10.0e-7));
    }

    public void testDescrevePontoEmEixoDeCoordenadasPrincipal() {
        Vetor3D vetorEmEixoDeCoordenadasArbitrario = new Vetor3D(eixoDeCoordenadas, new SimpleMatrix(new double[][]{{0.0},{0.0},{1.5}}));
        Vetor3D vetorEmEixoDeCoordenadsPrincipal = vetorEmEixoDeCoordenadasArbitrario.descrevePontoEmEixoDeCoordenadasPrincipal();

        Assert.assertTrue(vetorEmEixoDeCoordenadsPrincipal.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{1.5},{0.0},{0.0}}),10.0e-7));
    }
}
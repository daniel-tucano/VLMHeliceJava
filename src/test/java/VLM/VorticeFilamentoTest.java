package VLM;

import GeometriaBase.Espaco.Direcoes3D;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Espaco.Vetor3D;
import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;

public class VorticeFilamentoTest extends TestCase {

    public VorticeFilamento vorticeFilamento1 = new VorticeFilamento(1.0,new Ponto3D(1.0,0.0,0.0), new Ponto3D(0.0,0.0,0.0));
    public VorticeFilamento vorticeFilamento2 = new VorticeFilamento(1.0,new Ponto3D(0.0,0.0,0.0), new Ponto3D(1.0,0.0,0.0));
    public VorticeFilamento vorticeFilamento3 = new VorticeFilamento(1.0,new Ponto3D(0.0,1.0,0.0), new Ponto3D(1.0,0.0,0.0));
    public Ponto3D pontoInfluenciado1 = new Ponto3D(0.0,0.0,0.0);
    public Ponto3D pontoInfluenciado2 = new Ponto3D(0.0,1.0,0.0);
    public Ponto3D pontoInfluenciado3 = new Ponto3D(3.0,0.0,0.0);

    public void testCalculaInfluenciaEmPonto() {
        Vetor3D vetorInfluenciaVortice1EmPonto2 = vorticeFilamento1.calculaInfluenciaEmPonto(pontoInfluenciado2);
        Assert.assertTrue(vetorInfluenciaVortice1EmPonto2.direcao.matrizVetor.isIdentical(Direcoes3D.Z.matrizVetor,10.0e-4));
        Assert.assertTrue(vetorInfluenciaVortice1EmPonto2.matrizVetor.isIdentical(new SimpleMatrix(new double[][] {{0.0},{0.0},{(Math.sqrt(2.0)+1)/(4*Math.PI*(Math.sqrt(2.0)+2))}}),10.0e-4));

        Vetor3D vetorInfluenciaVortice2EmPonto2 = vorticeFilamento2.calculaInfluenciaEmPonto(pontoInfluenciado2);
        Assert.assertTrue(vetorInfluenciaVortice2EmPonto2.direcao.matrizVetor.isIdentical(Direcoes3D.Z.matrizVetor.scale(-1.0),10.0e-4));
        Assert.assertTrue(vetorInfluenciaVortice2EmPonto2.matrizVetor.isIdentical(new SimpleMatrix(new double[][] {{0.0},{0.0},{-(Math.sqrt(2.0)+1)/(4*Math.PI*(Math.sqrt(2.0)+2))}}),10.0e-4));

        Vetor3D vetorInfluenciaVortice3EmPonto1 = vorticeFilamento3.calculaInfluenciaEmPonto(pontoInfluenciado1);
        Assert.assertTrue(vetorInfluenciaVortice3EmPonto1.direcao.matrizVetor.isIdentical(Direcoes3D.Z.matrizVetor,10.0e-4));
        Assert.assertTrue(vetorInfluenciaVortice3EmPonto1.matrizVetor.isIdentical(new SimpleMatrix(new double[][] {{0.0},{0.0},{1.0/(2*Math.PI)}}),10.0e-4));

        Vetor3D vetorInfluenciaVortice1emPonto3 = vorticeFilamento1.calculaInfluenciaEmPonto(pontoInfluenciado3);
        Assert.assertTrue(vetorInfluenciaVortice1emPonto3.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{0.0}, {0.0}, {0.0}}),10.0e-7));
    }
}
package VLM;

import GeometriaBase.Espaco.Direcoes3D;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Espaco.Vetor3D;
import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;

import java.util.Arrays;

public class VorticeFerraduraTest extends TestCase {

    public VorticeFerradura vorticeFerradura1 = new VorticeFerradura(1.0, Arrays.asList(new Ponto3D(1.0,2.0,0.0), new Ponto3D(1.0,1.0,0.0)), Arrays.asList(new Ponto3D(2.0,2.0,0.0), new Ponto3D(2.0,1.0,0.0)));
    public Ponto3D pontoInfluenciado1 = new Ponto3D(1.5,1.5,0.0);

    public void testCalculaInfluenciaEmPonto() {
        Vetor3D vetorInfluenciaVorticeFerradura1EmPonto1 = vorticeFerradura1.calculaInfluenciaEmPonto(pontoInfluenciado1);

        Assert.assertTrue(vetorInfluenciaVorticeFerradura1EmPonto1.direcao.matrizVetor.isIdentical(Direcoes3D.Z.matrizVetor.scale(-1.0),10.0e-4));
        Assert.assertTrue(vetorInfluenciaVorticeFerradura1EmPonto1.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{0.0},{0.0},{-3*Math.sqrt(2)/(2*Math.PI)}}),10.0e-5));
    }

    public void testCalculaIndluenciaEmPontoEmUmaDirecao() {
        double influenciaVorticeFerradura1EmPonto1EmDirecaoX = vorticeFerradura1.calculaIndluenciaEmPontoEmUmaDirecao(pontoInfluenciado1,Direcoes3D.X);
        double influenciaVorticeFerradura1EmPonto1EmDirecaoY = vorticeFerradura1.calculaIndluenciaEmPontoEmUmaDirecao(pontoInfluenciado1,Direcoes3D.Y);
        double influenciaVorticeFerradura1EmPonto1EmDirecaoZ = vorticeFerradura1.calculaIndluenciaEmPontoEmUmaDirecao(pontoInfluenciado1,Direcoes3D.Z);

        Assert.assertEquals(0.0, influenciaVorticeFerradura1EmPonto1EmDirecaoX, 10.0e-4);
        Assert.assertEquals(0.0, influenciaVorticeFerradura1EmPonto1EmDirecaoY, 10.0e-4);
        Assert.assertEquals(-3*Math.sqrt(2)/(2*Math.PI), influenciaVorticeFerradura1EmPonto1EmDirecaoZ, 10.0e-4);
    }

    public void testCalculaForca() {
    }
}
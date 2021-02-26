package GeometriaBase.Espaco;

import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class Direcao3DTest {

    public Direcao3D direcao3D = new Direcao3D(1.0,1.0,1.0);

    @Test
    public void getDirecaoPerpendicular() {
        Assert.assertEquals(0.0 ,direcao3D.produtoEscalar(direcao3D.getDirecaoPerpendicular()), 10.0e-4);
    }

    @Test
    public void escala() {
        Vetor3D direcaoEscalada = direcao3D.escala(2.0);
        Assert.assertEquals(2.0, direcaoEscalada.modulo,10.0e-4);
        Assert.assertTrue(direcaoEscalada.posicao.equals( new Ponto3D(0.0,0.0,0.0)));
        Assert.assertTrue(direcaoEscalada.matrizVetor.isIdentical(new SimpleMatrix(new double[][] {{2/Math.sqrt(3)},{2/Math.sqrt(3)},{2/Math.sqrt(3)}}), 10.0e-4));
        Assert.assertTrue(direcao3D.matrizVetor.isIdentical(direcaoEscalada.direcao.matrizVetor,10.0e-4));
    }

    @Test
    public void posicionaNoEspaco() {
        Vetor3D direcaoPosicionada = direcao3D.posicionaNoEspaco(new Ponto3D(1.0,0.0,0.0));
        Assert.assertTrue(direcaoPosicionada.posicao.equals(new Ponto3D(1.0,0.0,0.0)));
        Assert.assertTrue(direcaoPosicionada.direcao.matrizVetor.isIdentical(direcao3D.matrizVetor,10.0e-4));
    }
}
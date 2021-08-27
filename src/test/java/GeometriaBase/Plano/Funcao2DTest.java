package GeometriaBase.Plano;

import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class Funcao2DTest extends TestCase {

    Funcao2D reta = new Funcao2D(Arrays.asList(new Ponto2D(0.0,0.0), new Ponto2D(2.0,2.0), new Ponto2D(1.0,1.0)));

    public void testGetPontosOrdemCrescente() {
        List<Ponto2D> pontosOrdemCrescrente = reta.getPontosOrdemCrescente();
        Assert.assertArrayEquals(pontosOrdemCrescrente.stream().mapToDouble(p -> p.x).toArray(), new double[] {0.0,1.0,2.0},10e-8);
        Assert.assertArrayEquals(pontosOrdemCrescrente.stream().mapToDouble(p -> p.y).toArray(), new double[] {0.0,1.0,2.0},10e-8);
    }

    public void testGeraFuncaoIgualmenteEspacada() {
        Funcao2D funcaoIgualmenteEspacada = reta.geraFuncaoIgualmenteEspacada(5);
        Assert.assertArrayEquals(funcaoIgualmenteEspacada.getPontosX().stream().mapToDouble(X -> X).toArray(), new double[] {0.0,0.5,1.0,1.5,2.0},10e-8);
        Assert.assertArrayEquals(funcaoIgualmenteEspacada.getPontosY().stream().mapToDouble(Y -> Y).toArray(), new double[] {0.0,0.5,1.0,1.5,2.0},10e-8);
    }

    public void testObtemPontoInterpolado() {
        Ponto2D pontoInterpolado = reta.obtemPontoInterpolado(0.25);
        Assert.assertTrue(pontoInterpolado.equals( new Ponto2D(0.25,0.25)));
    }

    public void testGetDirecaoTangente() {
        Direcao2D direcaoTangente = reta.getDirecaoTangente(0.25);
        Assert.assertTrue(direcaoTangente.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{1/Math.sqrt(2)}, {1/Math.sqrt(2)}, {0.0}}), 10e-7));
    }

    public void testGetDirecaoNormal() {
        Direcao2D direcaoNormal = reta.getDirecaoNormal(0.25);
        Assert.assertTrue(direcaoNormal.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{-1/Math.sqrt(2)}, {1/Math.sqrt(2)}, {0.0}}), 10e-7));
    }

    public void testSubtraiCurvaY() {
    }

    public void testSomaCurvaY() {
    }

    public void testMultiplicaCurvaY() {
    }

    public void testMultiplicaCurvaX() {
    }

    public void testDivideCurvaY() {
    }

    public void testEscala() {
    }

    public void testEscalaEmX() {
    }

    public void testSetEixoDeCoordenadas() {
    }

    public void testTransladaPontos() {
    }
}
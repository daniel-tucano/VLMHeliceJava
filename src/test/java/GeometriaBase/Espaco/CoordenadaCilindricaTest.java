package GeometriaBase.Espaco;

import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;

public class CoordenadaCilindricaTest extends TestCase {

    public CoordenadaCilindrica coordenadaCilindrica1 = new CoordenadaCilindrica(12.0,2.0,45.0);

    public void testConverteParaPonto3D() {
    }

    public void testConverteParaVetor3D() {
    }

    public void testTansladaPontoEmZEAngulo() {
    }

    public void testGetDirecaoTangenteAntiHorario() {
        Direcao3D vetorTangenteACoordenadaCilindrica1 = coordenadaCilindrica1.getDirecaoTangenteAntiHorario();

        Assert.assertTrue(vetorTangenteACoordenadaCilindrica1.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{-Math.sqrt(2.0)/2.0},{Math.sqrt(2.0)/2},{0.0}}),10.0e-7));
    }
}
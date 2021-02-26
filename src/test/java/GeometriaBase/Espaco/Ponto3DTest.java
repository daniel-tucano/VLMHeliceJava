package GeometriaBase.Espaco;

import junit.framework.TestCase;
import org.junit.Assert;

public class Ponto3DTest extends TestCase {

    public Ponto3D ponto1 = new Ponto3D(0.14,0.14,2.0);

    public void testGetDistanciaDaOrigem() {
    }

    public void testSomaPonto3D() {
    }

    public void testSubtraiPonto3D() {
    }

    public void testMultiplicaCoordenadasPorEscalar() {
    }

    public void testRotacionaPontoEmTornoDeEixo() {
    }

    public void testRotacionaPontoUtilizandoMatrizDeRotacao() {
    }

    public void testDescrevePontoEmEixoDeCoordenadasPrincipal() {
    }

    public void testDescrevePontoEmNovoEixoDeCoordenadas() {
    }

    public void testConverteParaVetorColuna() {
    }

    public void testSetEixoDeCoordenadas() {
    }

    public void testConverteParaCoordenadaCilindrica() {
        CoordenadaCilindrica coordenadaCilindricaPonto1 = ponto1.converteParaCoordenadaCilindrica();

        Assert.assertEquals(coordenadaCilindricaPonto1.z, 2.0,10.0e-7);
        Assert.assertEquals(coordenadaCilindricaPonto1.raio, 0.19798989873223333,10.0e-7);
        Assert.assertEquals(coordenadaCilindricaPonto1.anguloEmZ,45.0,10.0e-7);
    }
}
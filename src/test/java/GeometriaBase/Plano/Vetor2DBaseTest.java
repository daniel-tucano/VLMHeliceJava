package GeometriaBase.Plano;

import GeometriaBase.Espaco.Direcoes3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Vetor3D;
import junit.framework.TestCase;
import org.junit.Assert;

public class Vetor2DBaseTest extends TestCase {

    EixoDeCoordenadas3D eixoDeCoordenadas = new EixoDeCoordenadas3D(Direcoes3D.X, Direcoes3D.Z);

    Direcao2D direcao = new Direcao2D(eixoDeCoordenadas,1.0,1.0);

    public void testGetComponenteX() {
    }

    public void testGetComponenteY() {
    }

    public void testGetModuloComponenteX() {
    }

    public void testGetModuloComponenteY() {
    }

    public void testGetComponenteNaDirecao() {
    }

    public void testProdutoVetorial() {
    }

    public void testTestProdutoVetorial() {
    }

    public void testProdutoEscalar() {
    }

    public void testAnguloEntreVetores() {
    }

    public void testDescreveVetorEmEixoDeCoordenadasPrincipal() {
        Vetor3D direcaoEmEixoDeCoordenadasPrincipal = direcao.descreveVetorEmEixoDeCoordenadasPrincipal();
        Assert.assertEquals(1.0, direcaoEmEixoDeCoordenadasPrincipal.modulo,10e-8);
        Assert.assertEquals(1/Math.sqrt(2), direcaoEmEixoDeCoordenadasPrincipal.getModuloComponenteX(), 10e-8);
        Assert.assertEquals(1/Math.sqrt(2), direcaoEmEixoDeCoordenadasPrincipal.getModuloComponenteZ(), 10e-8);
        Assert.assertEquals(0.0, direcaoEmEixoDeCoordenadasPrincipal.getModuloComponenteY(), 10e-8);
    }

    public void testSubtraiVetor() {
    }

    public void testTestSubtraiVetor() {
    }

    public void testSomaVetor() {
    }

    public void testTestSomaVetor() {
    }
}
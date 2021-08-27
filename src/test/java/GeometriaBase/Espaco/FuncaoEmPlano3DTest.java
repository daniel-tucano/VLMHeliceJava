package GeometriaBase.Espaco;

import GeometriaBase.Plano.Funcao2D;
import GeometriaBase.Plano.Ponto2D;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class FuncaoEmPlano3DTest {

    private Funcao2D funcao2DEmEixoDeCoordenadasPadrao = new Funcao2D(Arrays.asList(new Ponto2D(0.0, 0.0),
                                                                                    new Ponto2D(1.0, 1.0),
                                                                                    new Ponto2D(2.0, 0.0)));

    private FuncaoEmPlano3D funcaoEmPlano3DEmEixoDeCoordenadasPadrao = new FuncaoEmPlano3D(funcao2DEmEixoDeCoordenadasPadrao);

    private Funcao2D funcao2DEmEixoDeCoordenadasDirefente = new Funcao2D(EixoDeCoordenadas3D.eixoDeCoordenadasEixoDeRotacao, Arrays.asList(new Ponto2D(0.0, 0.0),
                                                                                    new Ponto2D(1.0, 1.0),
                                                                                    new Ponto2D(2.0, 0.0)));

    private FuncaoEmPlano3D funcaoEmPlano3DEmEixoDeCoordenadasDiferente = new FuncaoEmPlano3D(funcao2DEmEixoDeCoordenadasDirefente);

    @Test
    public void testDescreveFuncaoEmeixoDeCoordenadasPrincipal() {
    }

    @Test
    public void testPontoInterpoladoEmEixoDeCoordenadasPrincipal() {
    }

    @Test
    public void deveCalcularOsVetoresNormaisCorretamenteComEixoDeCoordenadasPadrao() {
        Vetor3D vetorNormalCalculado1 = funcaoEmPlano3DEmEixoDeCoordenadasPadrao.getDirecaoNormalEmEixoDeCoordenadasPrincipal(0.5);
        Vetor3D vetorNormalEsperado1 = new Vetor3D(-Math.sqrt(2.0)/2.0, Math.sqrt(2.0)/2.0, 0.0);

        Assert.assertEquals(vetorNormalEsperado1.getModuloComponenteX(), vetorNormalCalculado1.getModuloComponenteX(), 10e-8);
        Assert.assertEquals(vetorNormalEsperado1.getModuloComponenteY(), vetorNormalCalculado1.getModuloComponenteY(), 10e-8);
        Assert.assertEquals(vetorNormalEsperado1.getModuloComponenteZ(), vetorNormalCalculado1.getModuloComponenteZ(), 10e-8);

        Vetor3D vetorNormalCalculado2 = funcaoEmPlano3DEmEixoDeCoordenadasPadrao.getDirecaoNormalEmEixoDeCoordenadasPrincipal(1.5);
        Vetor3D vetorNormalEsperado2 = new Vetor3D(Math.sqrt(2.0)/2.0, Math.sqrt(2.0)/2.0, 0.0);

        Assert.assertEquals(vetorNormalEsperado2.getModuloComponenteX(), vetorNormalCalculado2.getModuloComponenteX(), 10e-8);
        Assert.assertEquals(vetorNormalEsperado2.getModuloComponenteY(), vetorNormalCalculado2.getModuloComponenteY(), 10e-8);
        Assert.assertEquals(vetorNormalEsperado2.getModuloComponenteZ(), vetorNormalCalculado2.getModuloComponenteZ(), 10e-8);
    }

    @Test
    public void deveCalcularOsVetoresNormaisCorretamente() throws InterruptedException {
        Vetor3D vetorNormalCalculado1 = funcaoEmPlano3DEmEixoDeCoordenadasDiferente.getDirecaoNormalEmEixoDeCoordenadasPrincipal(0.5);
        Vetor3D vetorNormalEsperado1 = new Vetor3D(Math.sqrt(2.0)/2.0, 0.0, -Math.sqrt(2.0)/2.0);

        Assert.assertEquals(vetorNormalEsperado1.getModuloComponenteX(), vetorNormalCalculado1.getModuloComponenteX(), 10e-8);
        Assert.assertEquals(vetorNormalEsperado1.getModuloComponenteY(), vetorNormalCalculado1.getModuloComponenteY(), 10e-8);
        Assert.assertEquals(vetorNormalEsperado1.getModuloComponenteZ(), vetorNormalCalculado1.getModuloComponenteZ(), 10e-8);

        Vetor3D vetorNormalCalculado2 = funcaoEmPlano3DEmEixoDeCoordenadasDiferente.getDirecaoNormalEmEixoDeCoordenadasPrincipal(1.5);
        Vetor3D vetorNormalEsperado2 = new Vetor3D(Math.sqrt(2.0)/2.0, 0.0, Math.sqrt(2.0)/2.0);

        Assert.assertEquals(vetorNormalEsperado2.getModuloComponenteX(), vetorNormalCalculado2.getModuloComponenteX(), 10e-8);
        Assert.assertEquals(vetorNormalEsperado2.getModuloComponenteY(), vetorNormalCalculado2.getModuloComponenteY(), 10e-8);
        Assert.assertEquals(vetorNormalEsperado2.getModuloComponenteZ(), vetorNormalCalculado2.getModuloComponenteZ(), 10e-8);
    }
}
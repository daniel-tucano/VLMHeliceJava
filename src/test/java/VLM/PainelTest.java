package VLM;

import GeometriaBase.Espaco.Curva3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Espaco.Vetor3D;
import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PainelTest extends TestCase {

    public Curva3D pontosPlaca1 = new Curva3D(Arrays.asList(new Ponto3D(3.0,6.0,0.0), new Ponto3D(6.0,6.0,0.0), new Ponto3D(6.0,3.0,0.0), new Ponto3D(3.0,3.0,0.0)));
    public Ponto3D translacaoPlaca1ParaPlaca2 = new Ponto3D(5.0, 5.0, 0.0);
    public Curva3D pontosVorticePernaEsquerdaPlaca1 = new Curva3D(Arrays.asList( new Ponto3D(3.0,5.25, 0.0), new Ponto3D(3.0, 1.0,0.0)));
    public Curva3D pontosVorticePernaDiretaPlaca1 = pontosVorticePernaEsquerdaPlaca1.transladaPontos(new Ponto3D(3.0,0.0,0.0));
    public Painel painel1 = new Painel(pontosPlaca1.pontos, null, null, new VorticeFerradura(5.0, pontosVorticePernaEsquerdaPlaca1.pontos, pontosVorticePernaDiretaPlaca1.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
    public Painel painel2 = new Painel(pontosPlaca1.transladaPontos(translacaoPlaca1ParaPlaca2).pontos, null, null, new VorticeFerradura(7.0, pontosVorticePernaEsquerdaPlaca1.transladaPontos(translacaoPlaca1ParaPlaca2).pontos, pontosVorticePernaDiretaPlaca1.transladaPontos(translacaoPlaca1ParaPlaca2).pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);

    public void testCalculaInfluenciaDeOutroPainelNessePainel() {
        double influenciaPainel2EmPainel1 = painel1.calculaInfluenciaDeOutroPainelNessePainel(painel2);
        double influenciaPainel1EmPaneil1 = painel1.calculaInfluenciaDeOutroPainelNessePainel(painel1);
        Assert.assertEquals(0.007723868946910299 -0.002852631556774087 -0.004652172909052894,influenciaPainel2EmPainel1,10.0e-7);
        Assert.assertEquals(-0.24320052183813012,influenciaPainel1EmPaneil1, 10.0e-7);
    }

    public void testCalculaVelocidadeInduzidaEmPonto() {
        Vetor3D velocidadeInduzidaPainel1Ponto14DoPainel1 = painel1.calculaVelocidadeInduzidaEmPonto(painel1.pontoUmQuartoDoPainel);
        Vetor3D velocidadeInduzidaPainel2Ponto14DoPainel2 = painel2.calculaVelocidadeInduzidaEmPonto(painel2.pontoUmQuartoDoPainel);

        Assert.assertTrue(velocidadeInduzidaPainel1Ponto14DoPainel1.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{0.0},{0.0},{-0.500271909591}}),10.0e-7));
        Assert.assertTrue(velocidadeInduzidaPainel2Ponto14DoPainel2.matrizVetor.isIdentical(new SimpleMatrix(new double[][]{{0.0},{0.0},{-0.700380673428}}),10.0e-7));
    }
                                                                                                                                                                                                                                                                                                            
    public void testModificaEsteiraDoVortice() {
    }
}
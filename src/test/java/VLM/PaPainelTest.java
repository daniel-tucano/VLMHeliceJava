package VLM;

import GeometriaBase.Espaco.Curva3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaHelice.Pa;
import junit.framework.TestCase;
import org.ejml.simple.SimpleMatrix;
import org.junit.Assert;

import java.io.IOException;
import java.util.Arrays;

public class PaPainelTest extends TestCase {

    public Curva3D pontosPlaca1 = new Curva3D(Arrays.asList(new Ponto3D(0.0,0.0,0.0), new Ponto3D(0.0,4.0,0.0), new Ponto3D(4.0,4.0,0.0), new Ponto3D(4.0,0.0,0.0)));
    public Ponto3D translacaoPlaca1ParaPlaca2Estacao1 = new Ponto3D(4.0, 0.0, 0.0);
    public Ponto3D translacaoPlaca1ParaPlaca3Estacao2 = new Ponto3D(0.0, 4.0, 0.0);
    public Ponto3D translacaoPlaca1ParaPlaca4Estacao2 = new Ponto3D(4.0, 4.0, 0.0);
    public Curva3D pontosVorticePernaEsquerdaPlaca1 = new Curva3D(Arrays.asList( new Ponto3D(1.0,0.0, 0.0), new Ponto3D(6.0, 0.0,0.0)));
    public Curva3D pontosVorticePernaDiretaPlaca1 = pontosVorticePernaEsquerdaPlaca1.transladaPontos(new Ponto3D(0.0,4.0,0.0));

    public Painel painel1Estacao1 = new Painel(pontosPlaca1.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca1.pontos, pontosVorticePernaDiretaPlaca1.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
    public Painel painel2Estacao1 = new Painel(pontosPlaca1.transladaPontos(translacaoPlaca1ParaPlaca2Estacao1).pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca1.transladaPontos(translacaoPlaca1ParaPlaca2Estacao1).pontos, pontosVorticePernaDiretaPlaca1.transladaPontos(translacaoPlaca1ParaPlaca2Estacao1).pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
    public Painel painel1Estacao2 = new Painel(pontosPlaca1.transladaPontos(translacaoPlaca1ParaPlaca3Estacao2).pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca1.transladaPontos(translacaoPlaca1ParaPlaca3Estacao2).pontos, pontosVorticePernaDiretaPlaca1.transladaPontos(translacaoPlaca1ParaPlaca3Estacao2).pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
    public Painel painel2Estacao2 = new Painel(pontosPlaca1.transladaPontos(translacaoPlaca1ParaPlaca4Estacao2).pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca1.transladaPontos(translacaoPlaca1ParaPlaca4Estacao2).pontos, pontosVorticePernaDiretaPlaca1.transladaPontos(translacaoPlaca1ParaPlaca4Estacao2).pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);

    public EstacaoPainel estacaoPainel1 = new EstacaoPainel();
    public EstacaoPainel estacaoPainel2 = new EstacaoPainel();

    public PaPainel paPainel = new PaPainel();

    public void testGetInfluenciaDePaNessaPa() throws IOException {
        estacaoPainel1.paineis.add(painel1Estacao1);
        estacaoPainel1.paineis.add(painel2Estacao1);
        estacaoPainel2.paineis.add(painel1Estacao2);
        estacaoPainel2.paineis.add(painel2Estacao2);

        Pa pa = new Pa();
        pa.numeroDeEstacoes = 2;

        paPainel.paineisEstacoes.add(estacaoPainel1);
        paPainel.paineisEstacoes.add(estacaoPainel2);
        paPainel.pa = pa;
        paPainel.numeroDePaineisPorEstacao = 2;

        double influenciaP1E1P1E1 = painel1Estacao1.calculaInfluenciaDeOutroPainelNessePainel(painel1Estacao1);
        double influenciaP1E1P2E1 = painel1Estacao1.calculaInfluenciaDeOutroPainelNessePainel(painel2Estacao1);
        double influenciaP1E1P1E2 = painel1Estacao1.calculaInfluenciaDeOutroPainelNessePainel(painel1Estacao2);
        double influenciaP1E1P2E2 = painel1Estacao1.calculaInfluenciaDeOutroPainelNessePainel(painel2Estacao2);

        double influenciaP2E1P1E1 = painel2Estacao1.calculaInfluenciaDeOutroPainelNessePainel(painel1Estacao1);
        double influenciaP2E1P2E1 = painel2Estacao1.calculaInfluenciaDeOutroPainelNessePainel(painel2Estacao1);
        double influenciaP2E1P1E2 = painel2Estacao1.calculaInfluenciaDeOutroPainelNessePainel(painel1Estacao2);
        double influenciaP2E1P2E2 = painel2Estacao1.calculaInfluenciaDeOutroPainelNessePainel(painel2Estacao2);

        double influenciaP1E2P1E1 = painel1Estacao2.calculaInfluenciaDeOutroPainelNessePainel(painel1Estacao1);
        double influenciaP1E2P2E1 = painel1Estacao2.calculaInfluenciaDeOutroPainelNessePainel(painel2Estacao1);
        double influenciaP1E2P1E2 = painel1Estacao2.calculaInfluenciaDeOutroPainelNessePainel(painel1Estacao2);
        double influenciaP1E2P2E2 = painel1Estacao2.calculaInfluenciaDeOutroPainelNessePainel(painel2Estacao2);

        double influenciaP2E2P1E1 = painel2Estacao2.calculaInfluenciaDeOutroPainelNessePainel(painel1Estacao1);
        double influenciaP2E2P2E1 = painel2Estacao2.calculaInfluenciaDeOutroPainelNessePainel(painel2Estacao1);
        double influenciaP2E2P1E2 = painel2Estacao2.calculaInfluenciaDeOutroPainelNessePainel(painel1Estacao2);
        double influenciaP2E2P2E2 = painel2Estacao2.calculaInfluenciaDeOutroPainelNessePainel(painel2Estacao2);

        SimpleMatrix matrizInfluenciaPaEsperado = new SimpleMatrix(new double[][]{  {influenciaP1E1P1E1, influenciaP1E1P2E1, influenciaP1E1P1E2, influenciaP1E1P2E2},
                                                                                    {influenciaP2E1P1E1, influenciaP2E1P2E1, influenciaP2E1P1E2, influenciaP2E1P2E2},
                                                                                    {influenciaP1E2P1E1, influenciaP1E2P2E1, influenciaP1E2P1E2, influenciaP1E2P2E2},
                                                                                    {influenciaP2E2P1E1, influenciaP2E2P2E1, influenciaP2E2P1E2, influenciaP2E2P2E2}  });

        SimpleMatrix matrizInfluenciaPaCalculado = paPainel.getInfluenciaDePaNessaPa(paPainel);

        Assert.assertTrue(matrizInfluenciaPaEsperado.isIdentical(matrizInfluenciaPaCalculado, 10.0e-7));
    }
}
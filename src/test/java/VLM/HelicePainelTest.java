//package VLM;
//
//import Aerodinamica.Fluidos;
//import GeometriaBase.Espaco.Curva3D;
//import GeometriaBase.Espaco.EixoDeCoordenadas3D;
//import GeometriaBase.Espaco.Ponto3D;
//import GeometriaHelice.Helice;
//import GeometriaHelice.Pa;
//import SolverAerodinamicoHelice.CondicaoDeOperacao;
//import junit.framework.TestCase;
//import org.ejml.simple.SimpleMatrix;
//import org.junit.Assert;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class HelicePainelTest extends TestCase {
//
//    public Pa pa = setupPa();
//    public Helice helice = setupHelice(this.pa);
//
//    // -------------------------------- Pontos que formam os vórtices ferradura da Pa 1 --------------------------------
//    public Curva3D pontosPlaca1Pa1 = new Curva3D(Arrays.asList(new Ponto3D(-4.0, 1.0, 0.0), new Ponto3D(-4.0, 5.0, 1.0), new Ponto3D(0.0, 5.0, 0.0), new Ponto3D(0.0, 1.0, 0.0)));
//    public Curva3D pontosVorticePernaEsquerdaPlaca1Pa1 = new Curva3D(Arrays.asList(new Ponto3D(-3.0, 1.0, 0.0), new Ponto3D(2.0, 1.0, 0.0)));
//    public Curva3D pontosVorticePernaDiretaPlaca1Pa1 = new Curva3D(Arrays.asList(new Ponto3D(-3.0, 5.0, 0.75), new Ponto3D(2.0, 5.0, -0.5)));
//    public Curva3D pontosPlaca2Pa1 = new Curva3D(Arrays.asList(new Ponto3D(0.0, 1.0, 0.0), new Ponto3D(0.0, 5.0, 0.0), new Ponto3D(4.0, 5.0, -1.0), new Ponto3D(4.0, 1.0, 0.0)));
//    public Curva3D pontosVorticePernaEsquerdaPlaca2Pa1 = new Curva3D(Arrays.asList(new Ponto3D(1.0, 1.0, 0.0), new Ponto3D(6.0, 1.0, 0.0)));
//    public Curva3D pontosVorticePernaDiretaPlaca2Pa1 = new Curva3D(Arrays.asList(new Ponto3D(1.0, 5.0, -0.25), new Ponto3D(6.0, 5.0, -1.5)));
//    public Curva3D pontosPlaca3Pa1 = new Curva3D(Arrays.asList(new Ponto3D(-4.0, 5.0, 1.0), new Ponto3D(-4.0, 9.0, 2.0), new Ponto3D(0.0, 9.0, 0.0), new Ponto3D(0.0, 5.0, 0.0)));
//    public Curva3D pontosVorticePernaEsquerdaPlaca3Pa1 = new Curva3D(Arrays.asList(new Ponto3D(-3.0, 5.0, 0.75), new Ponto3D(2.0, 5.0, -0.5)));
//    public Curva3D pontosVorticePernaDiretaPlaca3Pa1 = new Curva3D(Arrays.asList(new Ponto3D(-3.0, 9.0, 1.5), new Ponto3D(2.0, 9.0, -1.0)));
//    public Curva3D pontosPlaca4Pa1 = new Curva3D(Arrays.asList(new Ponto3D(0.0, 5.0, 0.0), new Ponto3D(0.0, 9.0, 0.0), new Ponto3D(4.0, 9.0, -2.0), new Ponto3D(4.0, 5.0, -1.0)));
//    public Curva3D pontosVorticePernaEsquerdaPlaca4Pa1 = new Curva3D(Arrays.asList(new Ponto3D(1.0, 5.0, -0.25), new Ponto3D(6.0, 5.0, -1.5)));
//    public Curva3D pontosVorticePernaDiretaPlaca4Pa1 = new Curva3D(Arrays.asList(new Ponto3D(1.0, 9.0, -0.5), new Ponto3D(6.0, 9.0, -3.0)));
//
//    // -------------------------------- Painéis que contem os vórtices ferradura da Pa 1 -------------------------------
//    public Painel painel1Estacao1Pa1 = new Painel(pontosPlaca1Pa1.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca1Pa1.pontos, pontosVorticePernaDiretaPlaca1Pa1.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//    public Painel painel2Estacao1Pa1 = new Painel(pontosPlaca2Pa1.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca2Pa1.pontos, pontosVorticePernaDiretaPlaca2Pa1.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//    public Painel painel1Estacao2Pa1 = new Painel(pontosPlaca3Pa1.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca3Pa1.pontos, pontosVorticePernaDiretaPlaca3Pa1.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//    public Painel painel2Estacao2Pa1 = new Painel(pontosPlaca4Pa1.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca4Pa1.pontos, pontosVorticePernaDiretaPlaca4Pa1.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//
//    // -------------------------------- Estações que contem os painéis ferradura da Pa 1 -------------------------------
//    public EstacaoPainel estacaoPainel1Pa1 = setupEstacaoPainel(Arrays.asList(painel1Estacao1Pa1, painel2Estacao1Pa1));
//    public EstacaoPainel estacaoPainel2Pa1 = setupEstacaoPainel(Arrays.asList(painel1Estacao2Pa1, painel2Estacao2Pa1));
//
//    public PaPainel paPainel1 = setupPaPainel(Arrays.asList(estacaoPainel1Pa1, estacaoPainel2Pa1), pa);
//
//    // -------------------------------- Pontos que formam os vórtices ferradura da Pa 2 --------------------------------
//    public Curva3D pontosPlaca1Pa2 = new Curva3D(Arrays.asList(new Ponto3D(4.0, -1.0, 0.0), new Ponto3D(4.0, -5.0, 1.0), new Ponto3D(0.0, -5.0, 0.0), new Ponto3D(0.0, -1.0, 0.0)));
//    public Curva3D pontosVorticePernaEsquerdaPlaca1Pa2 = new Curva3D(Arrays.asList(new Ponto3D(3.0, -1.0, 0.0), new Ponto3D(-2.0, -1.0, 0.0)));
//    public Curva3D pontosVorticePernaDiretaPlaca1Pa2 = new Curva3D(Arrays.asList(new Ponto3D(3.0, -5.0, 0.75), new Ponto3D(-2.0, -5.0, -0.5)));
//    public Curva3D pontosPlaca2Pa2 = new Curva3D(Arrays.asList(new Ponto3D(0.0, -1.0, 0.0), new Ponto3D(0.0, -5.0, 0.0), new Ponto3D(-4.0, -5.0, -1.0), new Ponto3D(-4.0, -1.0, 0.0)));
//    public Curva3D pontosVorticePernaEsquerdaPlaca2Pa2 = new Curva3D(Arrays.asList(new Ponto3D(-1.0, -1.0, 0.0), new Ponto3D(-6.0, -1.0, 0.0)));
//    public Curva3D pontosVorticePernaDiretaPlaca2Pa2 = new Curva3D(Arrays.asList(new Ponto3D(-1.0, -5.0, -0.25), new Ponto3D(-6.0, -5.0, -1.5)));
//    public Curva3D pontosPlaca3Pa2 = new Curva3D(Arrays.asList(new Ponto3D(4.0, -5.0, 1.0), new Ponto3D(4.0, -9.0, 2.0), new Ponto3D(0.0, -9.0, 0.0), new Ponto3D(0.0, -5.0, 0.0)));
//    public Curva3D pontosVorticePernaEsquerdaPlaca3Pa2 = new Curva3D(Arrays.asList(new Ponto3D(3.0, -5.0, 0.75), new Ponto3D(-2.0, -5.0, -0.5)));
//    public Curva3D pontosVorticePernaDiretaPlaca3Pa2 = new Curva3D(Arrays.asList(new Ponto3D(3.0, -9.0, 1.5), new Ponto3D(-2.0, -9.0, -1.0)));
//    public Curva3D pontosPlaca4Pa2 = new Curva3D(Arrays.asList(new Ponto3D(0.0, -5.0, 0.0), new Ponto3D(0.0, -9.0, 0.0), new Ponto3D(-4.0, -9.0, -2.0), new Ponto3D(-4.0, -5.0, -1.0)));
//    public Curva3D pontosVorticePernaEsquerdaPlaca4Pa2 = new Curva3D(Arrays.asList(new Ponto3D(-1.0, -5.0, -0.25), new Ponto3D(-6.0, -5.0, -1.5)));
//    public Curva3D pontosVorticePernaDiretaPlaca4Pa2 = new Curva3D(Arrays.asList(new Ponto3D(-1.0, -9.0, -0.5), new Ponto3D(-6.0, -9.0, -3.0)));
//
//    // -------------------------------- Painéis que contem os vórtices ferradura da Pa 2 -------------------------------
//    public Painel painel1Estacao1Pa2 = new Painel(pontosPlaca1Pa2.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca1Pa2.pontos, pontosVorticePernaDiretaPlaca1Pa2.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//    public Painel painel2Estacao1Pa2 = new Painel(pontosPlaca2Pa2.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca2Pa2.pontos, pontosVorticePernaDiretaPlaca2Pa2.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//    public Painel painel1Estacao2Pa2 = new Painel(pontosPlaca3Pa2.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca3Pa2.pontos, pontosVorticePernaDiretaPlaca3Pa2.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//    public Painel painel2Estacao2Pa2 = new Painel(pontosPlaca4Pa2.pontos, null, null, new VorticeFerradura(1.0, pontosVorticePernaEsquerdaPlaca4Pa2.pontos, pontosVorticePernaDiretaPlaca4Pa2.pontos), EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//
//    // -------------------------------- Estações que contem os painéis ferradura da Pa 2 -------------------------------
//    public EstacaoPainel estacaoPainel1Pa2 = setupEstacaoPainel(Arrays.asList(painel1Estacao1Pa2, painel2Estacao1Pa2));
//    public EstacaoPainel estacaoPainel2Pa2 = setupEstacaoPainel(Arrays.asList(painel1Estacao2Pa2, painel2Estacao2Pa2));
//
//    public PaPainel paPainel2 = setupPaPainel(Arrays.asList(estacaoPainel1Pa2, estacaoPainel2Pa2), pa);
//
//    public HelicePainel helicePainel = setupHelicePainel(Arrays.asList(paPainel1, paPainel2), helice);
//
//    /**
//     * Retorna uma pá basica de 2 estações e raio 11.0
//     */
//    public static Pa setupPa() {
//        Pa pa = new Pa();
//        pa.numeroDeEstacoes = 2;
//        pa.raio = 11.0;
//        return pa;
//    }
//
//    /**
//     * Retorna uma hélice basica com 2 pás e raio 11.0 a partir da pá fornecida
//     *
//     * @param pa
//     * @return
//     */
//    public static Helice setupHelice(Pa pa) {
//        Helice helice = new Helice();
//        helice.nPas = 2;
//        helice.pas.add(pa);
//        helice.pas.add(pa);
//        helice.raio = 11.0;
//
//        return helice;
//    }
//
//    /**
//     * retorna uma estação de painéis com os valores da lista de painéis fornecida
//     *
//     * @param paineis
//     * @return
//     */
//    public static EstacaoPainel setupEstacaoPainel(List<Painel> paineis) {
//        EstacaoPainel estacaoPainel = new EstacaoPainel();
//        estacaoPainel.paineis.addAll(paineis);
//        return estacaoPainel;
//    }
//
//    /**
//     * Retorna uma pá de paineis a partir da lista de estações de paineis e da pá fornecida
//     *
//     * @param estacoesPainel
//     * @param pa
//     * @return
//     */
//    public static PaPainel setupPaPainel(List<EstacaoPainel> estacoesPainel, Pa pa) {
//        PaPainel paPainel = new PaPainel();
//        paPainel.paineisEstacoes.addAll(estacoesPainel);
//        paPainel.pa = pa;
//        paPainel.numeroDePaineisPorEstacao = 2;
//        return paPainel;
//    }
//
//    /**
//     * Retorna uma hélice de paineis a partir da lista de pas de paineis e da hélice fornecida
//     *
//     * @param pasPainel
//     * @param helice
//     * @return
//     */
//    public static HelicePainel setupHelicePainel(List<PaPainel> pasPainel, Helice helice) {
//        HelicePainel helicePainel = new HelicePainel();
//        helicePainel.paineisPa.addAll(pasPainel);
//        helicePainel.helice = helice;
//        helicePainel.numeroDePaineisPorEstacao = 2;
//        return helicePainel;
//    }
//
//    public void testGeraAIM() {
//        SimpleMatrix matrizInfluenciaPa1Pa1 = paPainel1.getInfluenciaDePaNessaPa(paPainel1);
//        SimpleMatrix matrizInfluenciaPa1Pa2 = paPainel1.getInfluenciaDePaNessaPa(paPainel2);
//        SimpleMatrix matrizInfluenciaPa2Pa1 = paPainel2.getInfluenciaDePaNessaPa(paPainel1);
//        SimpleMatrix matrizInfluenciaPa2Pa2 = paPainel2.getInfluenciaDePaNessaPa(paPainel2);
//
//        SimpleMatrix matrizInfluenciaEsperada = new SimpleMatrix(8, 8);
//        matrizInfluenciaEsperada = matrizInfluenciaEsperada.combine(0, 0, matrizInfluenciaPa1Pa1);
//        matrizInfluenciaEsperada = matrizInfluenciaEsperada.combine(0, 4, matrizInfluenciaPa1Pa2);
//        matrizInfluenciaEsperada = matrizInfluenciaEsperada.combine(4, 0, matrizInfluenciaPa2Pa1);
//        matrizInfluenciaEsperada = matrizInfluenciaEsperada.combine(4, 4, matrizInfluenciaPa2Pa2);
//
//        SimpleMatrix matrizInfluenciaCalculada = helicePainel.geraAIM();
//
//        Assert.assertTrue(matrizInfluenciaEsperada.isIdentical(matrizInfluenciaCalculada, 10e-7));
//    }
//
//    public void testGeraMatrizRHS() {
//        CondicaoDeOperacao condicaoDeOperacao = new CondicaoDeOperacao(10.0, 1200.0, Fluidos.AR, 4.0);
//        SimpleMatrix matrizRHSCalculada = this.helicePainel.geraMatrizRHS(condicaoDeOperacao);
//        SimpleMatrix matrizRHSEsperada = new SimpleMatrix(new double[][]{
//                {10.0},
//                {(120 * Math.PI + 40) / Math.sqrt(17)},
//                {(40 - 280 * Math.PI) / Math.sqrt(17)},
//                {20 * Math.sqrt(2) / 3 - 80 * Math.sqrt(2) * Math.PI / 3},
//
//                {10.0},
//                {(120 * Math.PI + 40) / Math.sqrt(17)},
//                {(40 - 280 * Math.PI) / Math.sqrt(17)},
//                {20 * Math.sqrt(2) / 3 - 80 * Math.sqrt(2) * Math.PI / 3},
//        });
//
//        Assert.assertTrue(matrizRHSEsperada.isIdentical(matrizRHSCalculada, 10.0e-7));
//    }
//
//    public void testCalculaPontoPerformanceHelice() {
//
//    }
//
//    public void testDeterminaVelocidadeInduzidaEmPonto() {
//
//    }
//
//    public void testSetEsteira() {
//    }
//}
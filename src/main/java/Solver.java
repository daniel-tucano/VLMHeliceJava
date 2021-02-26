

import Aerodinamica.AerofolioPuro;
import Aerodinamica.Fluidos;
import Estrutura.Materiais;
import GeometriaBase.Espaco.Direcoes3D;
import GeometriaBase.Espaco.EixoDeCoordenadas3D;
import GeometriaBase.Espaco.Ponto3D;
import GeometriaBase.Espaco.Vetor3D;
import GeometriaBase.Plano.Ponto2D;
import GeometriaBase.Primitivos.CurvaHelicoidal;
import GeometriaHelice.Helice;

import SolverAerodinamicoHelice.CondicaoDeOperacao;
import SolverAerodinamicoHelice.PontoPerformanceHelice;
import VLM.HelicePainel;
import org.ejml.simple.SimpleMatrix;
import org.jzy3d.colors.Color;
import util.AirfoilJSON;

import java.util.Arrays;
import java.util.List;

public class Solver {
    public static void main(String[] args) throws Exception {
        double fator = 1.4;
        List<Ponto2D> pontosDistribuicaoDeCordaNormalizada = Arrays.asList(new Ponto2D(0.05,0.15*fator), new Ponto2D(0.15,0.16*fator), new Ponto2D(0.30,0.162*fator), new Ponto2D(0.45, 0.14*fator), new Ponto2D(0.7, 0.12*fator), new Ponto2D(0.9,0.07*fator), new Ponto2D(1.0, 0.012*fator));
        List<Ponto2D> pontosDistribuicaoDeAnguloBetaNormalizada = Arrays.asList(new Ponto2D(0.05,3.0), new Ponto2D(0.15,10.0), new Ponto2D(0.30,35.0), new Ponto2D(0.45, 20.0), new Ponto2D(0.7, 10.0), new Ponto2D(0.9,8.0), new Ponto2D(1.0, 7.0));
        List<AerofolioPuro> aerofoliosPuros = Arrays.asList(new AerofolioPuro(AirfoilJSON.parseAirfoilJsonUrl(130, 0.0, "Xfoil")), new AerofolioPuro(AirfoilJSON.parseAirfoilJsonUrl(190, 0.0, "Xfoil")), new AerofolioPuro(AirfoilJSON.parseAirfoilJsonUrl(188, 0.0, "Xfoil")));
        Integer numeroDeEstacoes = 25;
        Integer numeroDePas = 2;
        List<Integer> indicesDosPerfisDeAerofolioPuro = Arrays.asList(0,15, 25);
        Double raio = 0.12;
        Double raioHub = 0.008;
//        aerofoliosPuros.get(0).plotAerofolio();
        Helice heliceTeste = new Helice(numeroDePas, raio, raioHub, numeroDeEstacoes, aerofoliosPuros, indicesDosPerfisDeAerofolioPuro, pontosDistribuicaoDeCordaNormalizada, pontosDistribuicaoDeAnguloBetaNormalizada, Materiais.MADEIRA_MDF, EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal);
//        heliceTeste.plotPerfisHelice();
//        heliceTeste.pas.get(0).seccoesDeTrasicao.get(0).perfis.get(0).aerofolioTransladadoEscaladoERotacionado.plot("teste",-0.01,0.01,-0.01,0.01);
//        heliceTeste.plotSuperficieHelice(true,true);

//        heliceTeste.pas.get(0).seccoesDeTrasicao.get(0).perfis.get(5).curvaCamber.plot(-raio,raio,-raio,raio,-raio,raio);

        HelicePainel helicePainel = new HelicePainel(heliceTeste, 2, 20);
        CondicaoDeOperacao condicaoDeOperacao = new CondicaoDeOperacao(10.0,12000.0, Fluidos.AR, raio*2);
        PontoPerformanceHelice pontoPerformanceHelice = helicePainel.calculaPontoPerformanceHelice(condicaoDeOperacao);
        helicePainel.plotPaineis(true,false,false,false, true, false);
//        helicePainel.paineisPa.get(0).plotTracaoEstacao();
        System.out.println(pontoPerformanceHelice.tracao);
        System.out.println(pontoPerformanceHelice.torque);
//        SimpleMatrix AIM = helicePainel.geraAIM();
    }

}

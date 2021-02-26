package Aerodinamica;

import GeometriaBase.Espaco.CoordenadaCilindrica;
import GeometriaBase.Espaco.Vetor3D;
import SolverAerodinamicoHelice.CondicaoDeOperacao;

public class CampoVetorialEscoamentoIntocadoHelice {

    public CondicaoDeOperacao condicaoDeOperacao;

    public CampoVetorialEscoamentoIntocadoHelice(CondicaoDeOperacao condicaoDeOperacao) {
        this.condicaoDeOperacao = condicaoDeOperacao;
    }

    public Vetor3D getVelocidadeEscoamentoIntocado(CoordenadaCilindrica coordenadaCilindrica) {
        return coordenadaCilindrica
                .getDirecaoTangenteHorario()
                .escala(this.condicaoDeOperacao.velocidadeAngularRadSegundo*coordenadaCilindrica.raio)
                .setComponenteZ(-this.condicaoDeOperacao.velocidadeAxial);
    }
}

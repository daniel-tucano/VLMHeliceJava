package SolverAerodinamicoHelice;

public class PontoPerformanceHelice {
    public CondicaoDeOperacao condicaoDeOperacao;
    public Double tracao;
    public Double torque;
    public Double cT;
    public Double cQ;
    public Double cP;
    public Double eficiencia;

    public PontoPerformanceHelice(CondicaoDeOperacao condicaoDeOperacao, Double tracao, Double torque) {
        this.condicaoDeOperacao = condicaoDeOperacao;
        this.tracao = tracao;
        this.torque = torque;
        this.cT = tracao/(condicaoDeOperacao.fluido.densidade*Math.pow(condicaoDeOperacao.velocidadeAngularHz,2)*Math.pow(condicaoDeOperacao.getDiametro(),4));
        this.cQ = torque/(condicaoDeOperacao.fluido.densidade*Math.pow(condicaoDeOperacao.velocidadeAngularHz,3)*Math.pow(condicaoDeOperacao.getDiametro(),5));
        this.cP = 2*Math.PI*this.cQ;
        this.eficiencia = this.cT*condicaoDeOperacao.razaoDeAvanco/this.cP;
    }
}

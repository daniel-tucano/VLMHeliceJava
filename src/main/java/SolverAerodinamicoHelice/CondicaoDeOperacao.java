package SolverAerodinamicoHelice;

import Aerodinamica.Fluido;

public class CondicaoDeOperacao {
    public Double velocidadeAxial;
    public Double velocidadeAngularRadSegundo;
    public Double velocidadeAngularHz;
    public Double velocidadeAngularRPM;
    public Double razaoDeAvanco;
    public Fluido fluido;

    private final Double diametro;

    public Double getDiametro() {
        return diametro;
    }

    public CondicaoDeOperacao(Double velocidadeAxial, Double velocidadeAngularRPM, Fluido fluido, Double diametro) {
        this.velocidadeAxial = velocidadeAxial;
        this.velocidadeAngularRPM = velocidadeAngularRPM;
        this.velocidadeAngularHz = velocidadeAngularRPM/60;
        this.velocidadeAngularRadSegundo = velocidadeAngularRPM/60*2*Math.PI;
        this.razaoDeAvanco = velocidadeAxial/(this.velocidadeAngularHz*diametro);
        this.fluido = fluido;

        this.diametro = diametro;
    }
}

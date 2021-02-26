package Aerodinamica;

public class Fluido {
    public Double densidade;
    public Double viscosidadeCinematica;
    public Double viscosidadeDinamica;
    public Double velocidadeDoSom;

    public Fluido(Double densidade, Double viscosidadeCinematica, Double velociadeDoSom) {
        this.densidade = densidade;
        this.viscosidadeDinamica = viscosidadeCinematica;
        this.viscosidadeCinematica = viscosidadeCinematica*densidade;
        this.velocidadeDoSom = velocidadeDoSom;
    }
}

package GeometriaBase.Plano;

import GeometriaBase.Espaco.EixoDeCoordenadas3D;

import java.util.List;
import java.util.stream.Collectors;

public class Geometria2D extends Curva2D {
    public Double area;
    public Ponto2D centroide;

    public Geometria2D( List<Ponto2D> pontos ) {
        super(pontos);
        calculaArea();
        calculaCentroide();
    }

    public Geometria2D(EixoDeCoordenadas3D eixoDeCoordenadas, List<Ponto2D> pontos ) {
        super(eixoDeCoordenadas,pontos);
        calculaArea();
        calculaCentroide();
    }

    public Geometria2D( List<Double> pontosX, List<Double> pontosY ) {
        super(pontosX,pontosY);
        calculaArea();
        calculaCentroide();
    }

    public Geometria2D(EixoDeCoordenadas3D eixoDeCoordenadas, List<Double> pontosX, List<Double> pontosY ) {
        super(eixoDeCoordenadas,pontosX,pontosY);
        calculaArea();
        calculaCentroide();
    }

    public Geometria2D( Curva2D curva ){
        super(curva);
        calculaArea();
        calculaCentroide();
    }

    private void calculaArea() {
        final int n = this.getPontos().size();
        this.area = (double) 0;
        for (int i = 1; i < n; i++) {
            this.area += (this.getPontos().get(i-1).x + this.getPontos().get(i).x) *
                         (this.getPontos().get(i-1).y - this.getPontos().get(i).y);
        }
        this.area += (this.getPontos().get(n-1).x + this.getPontos().get(0).x) *
                (this.getPontos().get(n-1).y - this.getPontos().get(0).y);
        this.area = Math.abs(this.area)/2;
    }

    private void calculaCentroide() {
        Double centroideX = (double) 0;
        Double centroideY = (double) 0;

        for (Ponto2D ponto : this.getPontos()) {
            centroideX += ponto.x;
            centroideY += ponto.y;
        }

        this.centroide = new Ponto2D(centroideX/this.getPontos().size(), centroideY/this.getPontos().size());
    }

    @Override
    public Geometria2D transladaPontos(Double translacaoEmX, Double translacaoEmY) {
        return new Geometria2D(this.getPontosX().stream().map(x -> x + translacaoEmX).collect(Collectors.toList()), this.getPontosY().stream().map(y -> y + translacaoEmY).collect(Collectors.toList()));
    }

    @Override
    public Geometria2D rotacionaPontosPelaOrigem(Double grausRotacionadosAntiHorario) {
        double radianosRotacionadosAntiHorario = Math.toRadians(grausRotacionadosAntiHorario);
        return new Geometria2D(this.getPontos().stream()
                .map(p -> new Ponto2D(p.x*Math.cos(radianosRotacionadosAntiHorario)-p.y*Math.sin(radianosRotacionadosAntiHorario), p.x*Math.sin(radianosRotacionadosAntiHorario) + p.y*Math.cos(radianosRotacionadosAntiHorario)))
                .collect(Collectors.toList()));
    }

    public Geometria2D transladaParaCentroide() {
        return new Geometria2D(this.transladaPontos(-this.centroide.x, -this.centroide.y));
    }

    public Geometria2D escalaGeometria(Double multiplicador) {
        return new Geometria2D(this.getPontos().stream()
                .map(p -> new Ponto2D(p.x * multiplicador, p.y * multiplicador))
                .collect(Collectors.toList()));
    }

    @Override
    public Geometria2D multiplicaCurvaX(Double multiplicador) {
        return new Geometria2D(this.getPontosX().stream().map(x -> multiplicador*x).collect(Collectors.toList()), this.getPontosY());
    }

    @Override
    public Geometria2D multiplicaCurvaY(Double multiplicador) {
        return new Geometria2D(this.getPontosX(), this.getPontosY().stream().map(y -> multiplicador*y).collect(Collectors.toList()));
    }

    @Override
    public Geometria2D setEixoDeCoordenadas(EixoDeCoordenadas3D eixoDeCoordenadas) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        return new Geometria2D(eixoDeCoordenadas,this.getPontos());
    }

    public Geometria2D rotacionaPontosPeloCentroide(Double grausRotacionadosAntiHorario) {
        double radianosRotacionadosAntiHorario = Math.toRadians(grausRotacionadosAntiHorario);
        Geometria2D geometriaComOrigemEmCentroide = this.transladaPontos(-this.centroide.x, -this.centroide.y);
        return geometriaComOrigemEmCentroide.rotacionaPontosPelaOrigem(radianosRotacionadosAntiHorario).transladaPontos(this.centroide.x, this.centroide.y);
    }
}

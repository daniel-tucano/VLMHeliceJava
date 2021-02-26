package GeometriaBase.Espaco;

public class CoordenadaCilindrica {
    public EixoDeCoordenadas3D eixoDeCoordenadas = EixoDeCoordenadas3D.eixoDeCoordenadasPrincipal;
    public Double raio;
    public Double z;
    public Double anguloEmZ;

    public CoordenadaCilindrica(Double raio, Double z, Double anguloEmZ) {
        this.raio = raio;
        this.z = z;
        this.anguloEmZ = anguloEmZ;
    }

    public CoordenadaCilindrica(EixoDeCoordenadas3D eixoDeCoordenadas, Double raio, Double z, Double anguloEmZ) {
        this.eixoDeCoordenadas = eixoDeCoordenadas;
        this.raio = raio;
        this.z = z;
        this.anguloEmZ = anguloEmZ;
    }

    public Ponto3D converteParaPonto3D() {
        return new Ponto3D(this.eixoDeCoordenadas, this.raio*Math.cos(this.anguloEmZ*Math.PI/180), this.raio*Math.sin(this.anguloEmZ*Math.PI/180), this.z);
    }

    public Vetor3D converteParaVetor3D() {
        return new Vetor3D(this.converteParaPonto3D());
    }

    public CoordenadaCilindrica tansladaPontoEmZEAngulo(Double z, Double anguloEmZ) {
        return new CoordenadaCilindrica(this.eixoDeCoordenadas, this.raio, this.z + z, this.anguloEmZ + anguloEmZ);
    }

    public Direcao3D getDirecaoTangenteAntiHorario() {
        return Direcoes3D.Z.produtoVetorial(this.converteParaVetor3D().setComponenteZ(0.0)).direcao;
    }

    public Direcao3D getDirecaoTangenteHorario() {
        return this.converteParaVetor3D().setComponenteZ(0.0).produtoVetorial(Direcoes3D.Z).direcao;
    }
}

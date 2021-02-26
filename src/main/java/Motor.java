import GeometriaBase.Plano.Funcao2D;

public class Motor {

    public String nome;
    public Funcao2D curvaDeTorque;

    public Motor(String nome, Funcao2D curvaDeTorque) {
        this.nome = nome;
        this.curvaDeTorque = curvaDeTorque;
    }
}

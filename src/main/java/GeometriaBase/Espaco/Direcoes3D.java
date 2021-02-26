package GeometriaBase.Espaco;

import org.ejml.simple.SimpleMatrix;

public class Direcoes3D {
    public static final Direcao3D X = new Direcao3D(new SimpleMatrix(3,1,false,new double[] {1.0,0.0,0.0}));
    public static final Direcao3D Y = new Direcao3D(new SimpleMatrix(3,1,false,new double[] {0.0,1.0,0.0}));
    public static final Direcao3D Z = new Direcao3D(new SimpleMatrix(3,1,false,new double[] {0.0,0.0,1.0}));
}

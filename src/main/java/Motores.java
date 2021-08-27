import static util.MetodosEstaticos.criaFuncaoAPartirDeCoeficientesPolinomioEPontosX;
import static util.MetodosEstaticos.linspace;

public class Motores {
    public static final Motor MAGNUM_61 = new Motor("magnum .61",
                                                    criaFuncaoAPartirDeCoeficientesPolinomioEPontosX(new double[]{-0.296942877066229, 0.000238562278198207, -1.27792995419068e-08},
                                                                                                     linspace(0, 17000, 1700)));
}

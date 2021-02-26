package Aerodinamica;

import GeometriaBase.Plano.Funcao2D;
import GeometriaBase.Plano.Geometria2D;
import GeometriaBase.Plano.Ponto2D;

public abstract class AerofolioBase {
     public Geometria2D geometriaOriginal;
     public Geometria2D geometriaIgualmenteEspacada;
     public Funcao2D intradorso;
     public Funcao2D extradorso;
     public Funcao2D intradorsoIgualmenteEspacado;
     public Funcao2D extradorsoIgualmenteEspacado;
     public Funcao2D curvaCamber;
     public Funcao2D curvaThickness;
     public Double camberMax;
     public Double xCamberMax;
     public Double thicknessMax;
     public Double xThicknessMax;

     public AerofolioBase(
             Funcao2D intradorso,
             Funcao2D extradorso
     ) {
          this.geometriaOriginal = new Geometria2D(extradorso.concat(intradorso));
          this.intradorso = intradorso;
          this.extradorso = extradorso;
          this.intradorsoIgualmenteEspacado = intradorso.geraFuncaoIgualmenteEspacada(150);
          this.extradorsoIgualmenteEspacado = extradorso.geraFuncaoIgualmenteEspacada(150);
          this.geometriaIgualmenteEspacada = new Geometria2D(extradorsoIgualmenteEspacado.concat(intradorsoIgualmenteEspacado));
          this.curvaCamber = new Funcao2D(extradorsoIgualmenteEspacado.somaCurvaY(intradorsoIgualmenteEspacado).divideCurvaY(2.0));
          this.curvaThickness = new Funcao2D(extradorsoIgualmenteEspacado.subtraiCurvaY(intradorsoIgualmenteEspacado));
          Ponto2D pontoMaxCamber = this.curvaCamber.pontoMaximoY();
          Ponto2D pontoMaxThickness = this.curvaThickness.pontoMaximoY();
          this.thicknessMax = pontoMaxThickness.y;
          this.xThicknessMax = pontoMaxThickness.x;
          this.camberMax = pontoMaxCamber.y;
          this.xCamberMax = pontoMaxCamber.x;
     }

     abstract void plotAerofolio();

     abstract PontoPolar achaPontoPolarMaisProximo(Double mach, Double reynolds, Double alpha);
}

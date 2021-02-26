package Aerodinamica;

import util.PolarPropertiesJSON;

public class PropriedadesPolar {
    public Double clMax;
    public Double cl0;
    public Double clAlpha;
    public Double cdMin;
    public Double cdMax;
    public Double clCdMax;
    public Double cm0;
    public Double alphaStall;
    public Double alpha0Cl;
    public Double alphaClCdMax;

    public PropriedadesPolar(
            Double clMax,
            Double cl0,
            Double clAlpha,
            Double cdMin,
            Double cdMax,
            Double clCdMax,
            Double cm0,
            Double alphaStall,
            Double alpha0Cl,
            Double alphaClCdMax
    ) {
         this.clMax = clMax;
         this.cl0 = cl0;
         this.clAlpha = clAlpha;
         this.cdMin = cdMin;
         this.cdMax = cdMax;
         this.clCdMax = clCdMax;
         this.cm0 = cm0;
         this.alphaStall = alphaStall;
         this.alpha0Cl = alpha0Cl;
         this.alphaClCdMax = alphaClCdMax;
    }

    public PropriedadesPolar(PolarPropertiesJSON polarPropertiesJSON) {
        this.clMax = polarPropertiesJSON.clMax;
        this.cl0 = polarPropertiesJSON.cl0;
        this.clAlpha = polarPropertiesJSON.clAlpha;
        this.cdMin = polarPropertiesJSON.cdMin;
        this.cdMax = polarPropertiesJSON.cdMax;
        this.clCdMax = polarPropertiesJSON.clCdMax;
        this.cm0 = polarPropertiesJSON.cm0;
        this.alphaStall = polarPropertiesJSON.alphaStall;
        this.alpha0Cl = polarPropertiesJSON.alpha0Cl;
        this.alphaClCdMax = polarPropertiesJSON.alphaClCdMax;
    }

    public PropriedadesPolar(PropriedadesPolar propriedadesPolar0) {
        this.clMax = propriedadesPolar0.clMax;
        this.cl0 = propriedadesPolar0.cl0;
        this.clAlpha = propriedadesPolar0.clAlpha;
        this.cdMin = propriedadesPolar0.cdMin;
        this.cdMax = propriedadesPolar0.cdMax;
        this.clCdMax = propriedadesPolar0.clCdMax;
        this.cm0 = propriedadesPolar0.cm0;
        this.alphaStall = propriedadesPolar0.alphaStall;
        this.alpha0Cl = propriedadesPolar0.alpha0Cl;
        this.alphaClCdMax = propriedadesPolar0.alphaClCdMax;
    }

    public PropriedadesPolar(){}

}

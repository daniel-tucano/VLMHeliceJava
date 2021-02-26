package util;

public class PolarPropertiesJSON {
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

    public PolarPropertiesJSON(
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

    public PolarPropertiesJSON(){}
}

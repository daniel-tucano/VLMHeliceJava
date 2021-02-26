package util;

import java.util.List;

public class PolarJSON {
    public List<Double> alpha;
    public List<Double> cl;
    public List<Double> cd;
    public List<Double> cm;

    public PolarJSON(
        List<Double> alpha,
        List<String> side,
        List<Double> cl,
        List<Double> cd,
        List<Double> cm
    ) {
        this.alpha = alpha;
        this.cl = cl;
        this.cd = cd;
        this.cm = cm;
    }

    public PolarJSON() {}
}

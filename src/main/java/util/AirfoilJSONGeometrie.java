package util;

import java.util.List;

public class AirfoilJSONGeometrie {
    public List<String> side;
    public List<Double> x, y;

    AirfoilJSONGeometrie(List<String> side, List<Double> x, List<Double> y) {
        this.side = side;
        this.x = x;
        this.y = y;
    }

    AirfoilJSONGeometrie(){}
}

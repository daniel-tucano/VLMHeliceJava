package util;

import Aerodinamica.Polar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AirfoilJSON {
    @JsonProperty("name")
    public String name;
    @JsonProperty("xCamber")
    public Double xCamber;
    @JsonProperty("camber")
    public Double camber;
    @JsonProperty("xThickness")
    public Double xThickness;
    @JsonProperty("thickness")
    public Double thickness;
    @JsonProperty("geometrie")
    public AirfoilJSONGeometrie geometrie;
    @JsonProperty("runs")
    public AirfoilJSONRuns runs;
    @JsonIgnore
    public List<RunJSON> runJSONS;

    AirfoilJSON(String name, Double xCamber, Double camber, Double xThickness, Double thickness, AirfoilJSONGeometrie geometrie, AirfoilJSONRuns runs) {
        this.name = name;
        this.xCamber = xCamber;
        this.camber = camber;
        this.xThickness = xThickness;
        this.thickness = thickness;
        this.geometrie = geometrie;
        this.runs = runs;
        this.runJSONS = new ArrayList<RunJSON>();
    }

    AirfoilJSON() {
        this.runJSONS = new ArrayList<RunJSON>();
    }

    public static AirfoilJSON parseAirfoilJsonFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(new File(filePath), AirfoilJSON.class);

    }

    public static AirfoilJSON parseAirfoilJsonUrl(Integer airfoilId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        AirfoilJSON airfoilJSON = mapper.readValue(new URL("https://backend.aero-db.com/airfoils/"+airfoilId), AirfoilJSON.class);
        for (Integer runId: airfoilJSON.runs.runIDs) {
            airfoilJSON.runJSONS.add( RunJSON.parseRunJsonUrl(runId) );
        }
        return airfoilJSON;
    }

    public static AirfoilJSON parseAirfoilJsonUrl(Integer airfoilId, Double maxMach, String source) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        AirfoilJSON airfoilJSON = mapper.readValue(new URL("https://backend.aero-db.com/airfoils/"+airfoilId), AirfoilJSON.class);
        airfoilJSON.runJSONS.addAll( RunJSON.parseRunJsonUrl(airfoilId,source,maxMach) );
        return airfoilJSON;
    }
}
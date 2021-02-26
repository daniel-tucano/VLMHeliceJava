package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RunJSON {
    public PolarJSON polar;
    public PolarPropertiesJSON polarProperties;
    public Double reynolds;
    public Double mach;

    public RunJSON(
        PolarJSON polar,
        PolarPropertiesJSON polarProperties,
        Double reynolds,
        Double mach
    ) {
        this.mach = mach;
        this.reynolds = reynolds;
        this.polar = polar;
        this.polarProperties = polarProperties;
    }

    public RunJSON() {}

    public static RunJSON parseRunJsonUrl(int runId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(new URL("https://backend.aero-db.com/runs/"+runId), RunJSON.class);
    }

    public static List<RunJSON> parseRunJsonUrl(Integer airfoilId, String source, Double maxMach) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        PaginatedEntity<RunJSON> runsPage = mapper.readValue(new URL(String.format("https://backend.aero-db.com/runs?$filter=airfoilID eq %d and substringof('%s',source) and mach le %s&estimatedDocumentCount=false", airfoilId, source, maxMach).replace(" ","%20")), new TypeReference<PaginatedEntity<RunJSON>>() {});
        List<RunJSON> runResponse = new ArrayList<RunJSON>(runsPage.docs);
        while (runsPage.hasNext) {
            runsPage = mapper.readValue(new URL(String.format("https://backend.aero-db.com/runs?$filter=airfoilID eq %d and substringof('%s',source) and mach le %s&estimatedDocumentCount=false&page=%d", airfoilId, source, maxMach,runsPage.nextPage).replace(" ","%20")), new TypeReference<PaginatedEntity<RunJSON>>() {});
            runResponse.addAll(runsPage.docs);
        };
        return runResponse;
    }
}

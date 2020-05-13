package be.howest.ti.alhambra.logic;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildingFactory {

    public List<Building> getAllBuildings() {
        try (InputStream input = BuildingFactory.class.getResourceAsStream("/buildings.json")) {
            return new ArrayList<>(Arrays.asList(Json.decodeValue(Buffer.buffer(input.readAllBytes()),
                            Building[].class))
            );
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Didn't find the buildings");
            return Collections.emptyList();
        }
    }
}

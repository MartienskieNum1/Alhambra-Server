package be.howest.ti.alhambra.logic;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildingFactory {
    private static List<Building> allBuildings = null;

    private BuildingFactory() {}

    public static List<Building> getAllBuildings() {
        if (allBuildings == null) {
            allBuildings = loadFromFile();
        }

        return Collections.unmodifiableList(allBuildings);

    }

    private static List<Building> loadFromFile() {
        try (InputStream input = BuildingFactory.class.getResourceAsStream("/buildings.json")) {
            return Arrays.asList(Json.decodeValue(Buffer.buffer(input.readAllBytes()),
                            Building[].class)
            );
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Didn't find the buildings");
            return Collections.emptyList();
        }
    }

}

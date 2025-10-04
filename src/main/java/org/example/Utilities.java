package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import org.apache.commons.lang3.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class Utilities {

    public static String readDataFromJsonFile(String filename) throws IOException {
        byte[] jsonData=Files.readAllBytes(Paths.get("src/test/resources/body/postProduct.json"));
        return new String(jsonData);
    }

    public static String name(){
        return randomAlphabetic(4) + " " + randomAlphabetic(4);
    }
}

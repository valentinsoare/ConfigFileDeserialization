package io.moviesondemand.projects;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class ExecApp {
    private static final Path gameCFG =
            Path.of("/home/vsoare/Documents/learningJava/AdvancedJavaTopicsJavaReflectionMasterClass/src/main/java/io/learningreflections/Section4FieldModificationAndArraysCreation/game.cfg");

    public static void main(String[] args) {
        Optional<GameConfig> configObject = ReadingConfigFileDeserialization.createConfigObject(GameConfig.class, gameCFG);
        System.out.printf("%n%s", configObject.get());

        int[] c = ReadingConfigFileDeserialization.concat(int.class, 1, 2, 3, new int[]{4, 5, 6}, 7);
        System.out.printf("%n%s", Arrays.toString(c));

    }
}

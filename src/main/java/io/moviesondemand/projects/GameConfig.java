package io.moviesondemand.projects;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class GameConfig {
    private int releaseYear;
    private String gameName;
    private double price;
    private String[] characterNames;

    public GameConfig() {}

    @Override
    public String toString() {
        return "GameConfig{" +
                "releaseYear=" + releaseYear +
                ", gameName='" + gameName + '\'' +
                ", price=" + price +
                ", characterNames=" + Arrays.toString(characterNames) + '}';
    }
}

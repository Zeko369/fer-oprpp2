package hr.fer.oprpp2;

import java.util.List;

public class ColorsConfig {

    public static List<Color> colors = List.of(
            new Color("WHITE", "#FFFFFF", "#000000"),
            new Color("RED", "#FF0000", "#FFFFFF"),
            new Color("GREEN", "#00FF00", "#000000"),
            new Color("CYAN", "#00FFFF", "#000000"),
            new Color("DARK", "#0F0F0F", "#FFFFFF")
    );

    public static Color getColor(String colorName) {
        return colors.stream()
                .filter(color -> color.name().equals(colorName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Color " + colorName + " not found"));
    }
}

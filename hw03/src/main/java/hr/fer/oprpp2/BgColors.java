package hr.fer.oprpp2;

import java.util.List;

public class BgColors {
    private record Color(String name, String hex) {
    }

    public static List<Color> colors = List.of(
            new Color("WHITE", "#FFFFFF"),
            new Color("RED", "#FF0000"),
            new Color("GREEN", "#00FF00"),
            new Color("CYAN", "#00FFFF")
    );

    public static boolean isColor(String colorName) {
        return colors.stream().anyMatch(color -> color.name.equals(colorName));
    }
}

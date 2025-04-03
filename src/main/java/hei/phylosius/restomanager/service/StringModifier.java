package service;

public class StringModifier {
    public static String pascalCaseToSnakeCase(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        // Parcourir chaque caractère
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            // Si c'est une majuscule (sauf pour le premier caractère), ajouter "_"
            if (Character.isUpperCase(c) && i > 0) {
                result.append("_");
            }

            // Ajouter le caractère en minuscule
            result.append(Character.toLowerCase(c));
        }

        return result.toString();
    }
}

import java.io.*;
import java.util.*;

public class SmartRecipeAndCook {

    static class Recipe {
        String name;
        List<String> ingredients;

        Recipe(String name, List<String> ingredients) {
            this.name = name;
            this.ingredients = ingredients;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Recipe file path
        String filePath = "RecipeFiles/Recipe.txt";
        List<Recipe> recipes = loadRecipes(filePath);

        if (recipes.isEmpty()) {
            System.out.println("No recipes found in " + filePath);
            return;
        }

        System.out.println("Enter your available ingredients (comma separated): ");
        String input = scanner.nextLine().toLowerCase();
        Set<String> availableIngredients = new HashSet<>(Arrays.asList(input.split("\\s*,\\s*")));

        // Store matching results
        Map<Recipe, Double> matches = new HashMap<>();
        Map<Recipe, List<String>> missingMap = new HashMap<>();
        Map<Recipe, List<String>> extraMap = new HashMap<>();

        for (Recipe r : recipes) {
            int matchedCount = 0;
            List<String> missing = new ArrayList<>();

            for (String ing : r.ingredients) {
                if (availableIngredients.contains(ing)) {
                    matchedCount++;
                } else {
                    missing.add(ing);
                }
            }

            double matchPercentage = (double) matchedCount / r.ingredients.size() * 100;

            if (matchPercentage >= 20) { // Only keep recipes with ≥50% match
                matches.put(r, matchPercentage);

                // Missing ingredients for specific recipe
                missingMap.put(r, missing);

                // Extra ingredients: those provided but not needed
                List<String> extra = new ArrayList<>();
                for (String ing : availableIngredients) {
                    if (!r.ingredients.contains(ing)) {
                        extra.add(ing);
                    }
                }
                extraMap.put(r, extra);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No recipes match 50% or more of your ingredients.");
            return;
        }

        // Sort recipes by match percentage (desc)
        List<Map.Entry<Recipe, Double>> sortedRecipes = new ArrayList<>(matches.entrySet());
        sortedRecipes.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        System.out.println("\nTop Recipe Suggestions (≥20% match, max 10 recipe):");
        int count = 0;
        for (Map.Entry<Recipe, Double> entry : sortedRecipes) {
            if (count >= 10) break; // Show at most 10 recipes
            Recipe r = entry.getKey();
            double percent = entry.getValue();
            List<String> missing = missingMap.get(r);
            List<String> extra = extraMap.get(r);

            System.out.printf("- %s (%.0f%% ingredients available)\n", r.name, percent);

            if (!missing.isEmpty()) {
                System.out.println("  Missing ingredients (add these): " + String.join(", ", missing));
            }
            if (!extra.isEmpty()) {
                System.out.println("  Extra ingredients (not needed): " + String.join(", ", extra));
            }
            count++;
        }
    }

    private static List<Recipe> loadRecipes(String fileName) {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String name = null;
            List<String> ingredients = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Recipe:")) {
                    name = line.substring(7).trim();
                } else if (line.startsWith("Ingredients:")) {
                    String ing = line.substring(12).trim().toLowerCase();
                    ingredients = Arrays.asList(ing.split("\\s*,\\s*"));
                    if (name != null && ingredients != null) {
                        recipes.add(new Recipe(name, ingredients));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return recipes;
    }
}

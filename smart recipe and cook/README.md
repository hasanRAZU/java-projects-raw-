🍲 Smart Recipe Recommender (Java)

A Java-based console application that recommends Bangladeshi recipes based on the ingredients you have.
It uses a recipe database stored in a text file and suggests what dishes you can make, along with missing and extra ingredients.

✨ Features

	📂 Reads recipes from a simple text file (RecipeFiles/Recipe.txt).

	🥘 Suggests up to 10 best recipes ranked by ingredient match.

	✅ Shows only recipes where 50% or more ingredients match.

	📝 Displays:

		Missing ingredients (what you need to add).

		Extra ingredients (what you provided but are not required).

📂 Project Structure

SmartRecipeRecommender/
│
├── SmartRecipeRecommenderFinal.java   # Main program
├── README.md                          # Project documentation
└── RecipeFiles/
    └── Recipe.txt                     # Recipe database

⚙️ Setup & Run
1. Clone or Download the Project
git clone https://github.com/yourusername/SmartRecipeRecommender.git
cd SmartRecipeRecommender

2. Compile the Java File
javac SmartRecipeRecommenderFinal.java

3. Run the Program
java SmartRecipeRecommenderFinal

🗂 Recipe File Format

The recipes are stored in RecipeFiles/Recipe.txt.
Each recipe has a name and a list of ingredients, written like this:

Recipe: Daal
Ingredients: lentils, onion, garlic, turmeric, salt, oil

Recipe: Bhuna Khichuri
Ingredients: rice, lentils, onion, garlic, ginger, cumin, turmeric, salt, chili, oil


👉 You can add as many recipes as you like (100+ Bangladeshi recipes recommended).

🖥 Example Run
Enter your available ingredients (comma separated): 
onion, oil, bread, beans

Top Recipe Suggestions (≥50% match, max 10):
- Daal (67% ingredients available)
  Missing ingredients (add these): lentils, garlic, turmeric, salt
  Extra ingredients (not needed): bread, beans

🚀 Future Improvements

GUI version with search and filter.

Machine learning–based ingredient substitution.

Support for multiple cuisines beyond Bangladeshi food.

👨‍💻 Author

Developed by Monjurul Hasan
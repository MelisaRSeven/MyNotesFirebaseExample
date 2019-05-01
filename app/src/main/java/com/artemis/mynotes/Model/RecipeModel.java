package com.artemis.mynotes.Model;

public class RecipeModel {
    private String recipeName, ingredients, howToDo;

    public RecipeModel(String recipeName, String ingredients, String howToDo) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.howToDo = howToDo;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getHowToDo() {
        return howToDo;
    }

    public void setHowToDo(String howToDo) {
        this.howToDo = howToDo;
    }
}

package kz.nikitka.diploma.project.dip.service;

import kz.nikitka.diploma.project.dip.model.Ingredient;
import kz.nikitka.diploma.project.dip.model.Permission;
import kz.nikitka.diploma.project.dip.model.Recipe;
import kz.nikitka.diploma.project.dip.model.User;
import kz.nikitka.diploma.project.dip.repository.IngredientRepository;
import kz.nikitka.diploma.project.dip.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public Recipe addRecipe(Recipe recipe){
        return   recipeRepository.save(recipe);
    }

    public List<Recipe> getRecipes(){
        return recipeRepository.findAll();
    }


    public Recipe getRecipe(Long id){
        return recipeRepository.findById(id).orElseThrow();
    }



    public List<Ingredient> getIngredients(){
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredient(Long ingredientId){
        return ingredientRepository.findById(ingredientId).orElseThrow();
    }

    public void deleteRecipe(Long id){
        recipeRepository.deleteById(id);

    }


    public void assignIngredient(Long recipeId,Long ingredientId){
        Recipe recipe = getRecipe(recipeId);
        Ingredient ingredient = getIngredient(ingredientId);
        recipe.getIngredients().add(ingredient);
        recipeRepository.save(recipe);

    }

    public void unassignIngredient(Long recipeId,Long ingredientId){
        Recipe recipe = getRecipe(recipeId);
        Ingredient ingredient = getIngredient(ingredientId);
        recipe.getIngredients().remove(ingredient);
        recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Recipe recipe){
        return recipeRepository.save(recipe);

    }



}
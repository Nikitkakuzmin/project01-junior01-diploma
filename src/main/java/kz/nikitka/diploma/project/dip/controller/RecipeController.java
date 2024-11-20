package kz.nikitka.diploma.project.dip.controller;

import jakarta.annotation.Resource;
import kz.nikitka.diploma.project.dip.model.Ingredient;
import kz.nikitka.diploma.project.dip.model.Recipe;
import kz.nikitka.diploma.project.dip.service.AccountService;
import kz.nikitka.diploma.project.dip.service.FileUploadService;
import kz.nikitka.diploma.project.dip.service.RecipeService;
import kz.nikitka.diploma.project.dip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecipeController {
    private final UserService userService;
    private final AccountService accountService;
    private final RecipeService recipeService;
    private final FileUploadService fileUploadService;

    @GetMapping(value = "/recipe-details")
    public String recipe(Model model) {
        return "recipe-details";
    }

    @GetMapping(value = "/add-recipe")
    public String addRecipe(Model model) {
        return "add-recipe";
    }

    @GetMapping(value = "/add-ingredient")
    public String addIngredient(Model model) {
        return "add-ingredient";
    }

    @PostMapping(value = "/add-recipe")
    public String addRecipe(Recipe recipe) {
        recipeService.addRecipe(recipe);
        return "redirect:/";
        //return "redirect:/recipe-details/" + recipe.getId();
    }
    @GetMapping(value = "/recipe-details/{id}")
    public String recipeDetails(@PathVariable(name = "id") Long id, Model model) {
        Recipe recipe = recipeService.getRecipe(id);
        model.addAttribute("recipe", recipe);

        List<Ingredient> ingredients = recipeService.getIngredients();
        ingredients.removeAll(recipe.getIngredients());
        model.addAttribute("ingredients", ingredients);
        return "recipe-details";
    }

    @PostMapping(value = "/assign-ingredient")
    public String assignIngredient(@RequestParam(name = "ingredient_id") Long ingredientId,
                                   @RequestParam(name = "recipe_id") Long recipeId) {
        recipeService.assignIngredient(recipeId, ingredientId);
        return "redirect:/recipe-details/" + recipeId;
    }

    @PostMapping(value = "/unassign-ingredient")
    public String unAssignIngredient(@RequestParam(name = "ingredient_id") Long ingredientId,
                                     @RequestParam(name = "recipe_id") Long recipeId) {
        recipeService.unassignIngredient(recipeId, ingredientId);
        return "redirect:/recipe-details/" + recipeId;
    }

    @PostMapping(value = "/delete-recipe")
    public String deleteRecipe(@RequestParam(name = "id") Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/moder";

    }
    @GetMapping(value = "/recipe-cook/{id}")
    public String recipeCook(@PathVariable(name = "id") Long id, Model model) {
        Recipe recipe = recipeService.getRecipe(id);
        model.addAttribute("recipe", recipe);

        List<Ingredient> ingredients = recipeService.getIngredients();
        ingredients.removeAll(recipe.getIngredients());
        model.addAttribute("ingredients", ingredients);
        return "recipe-cook";
    }

    @PostMapping(value = "/save-ingredient")
    public String saveRecipe(Recipe recipe) {
        return "redirect:/";
    }

    @PostMapping(value = "/update-recipe")
    public String updateRecipe(Recipe recipe) {
        recipeService.updateRecipe(recipe);
        return "redirect:/moder";
    }

    @PostMapping(value = "/upload-image/{id}")
    public String uploadImage(Recipe recipe,
                              @RequestParam(name = "image")MultipartFile pic,
                              @RequestParam(name = "recipe_id") Long id){
        fileUploadService.uploadImage(pic, id);
        return "redirect:/recipe-details/" + recipe.getId();
    }

    @GetMapping(value = "/view-image/{url}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable(name = "url") String url,
                                         @RequestParam(name = "recipe_id") Long id) throws IOException {
        return fileUploadService.getImage(url,id);
    }


}

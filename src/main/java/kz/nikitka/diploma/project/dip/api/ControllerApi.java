package kz.nikitka.diploma.project.dip.api;

import kz.nikitka.diploma.project.dip.model.Recipe;
import kz.nikitka.diploma.project.dip.model.User;
import kz.nikitka.diploma.project.dip.service.AccountService;
import kz.nikitka.diploma.project.dip.service.FileUploadService;
import kz.nikitka.diploma.project.dip.service.RecipeService;
import kz.nikitka.diploma.project.dip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ControllerApi {

    private final UserService userService;
    private final AccountService accountService;
    private final RecipeService recipeService;
    private final FileUploadService fileUploadService;


    @GetMapping(value = "/getrecipes")
    public List<Recipe> getRecipes() {
        return recipeService.getRecipes();
    }

    @GetMapping(value = "/getrecipe/{id}")
    public Recipe getRecipe(@PathVariable(value = "id") Long id) {
        return recipeService.getRecipe(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/register")
    public String registerUser(@RequestBody( required = false) String email,
                               @RequestBody( required = false)  String password,
                               @RequestBody( required = false)  String fullName) {
        accountService.registerUser(email, password,password,fullName);


        return "/email";
    }
}

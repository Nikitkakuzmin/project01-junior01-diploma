package kz.nikitka.diploma.project.dip.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import kz.nikitka.diploma.project.dip.model.Ingredient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RecipeDto {

    private String recipeName;

    private String recipeDescription;

    private String recipeImage;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Ingredient> RecipeIngredients;
}

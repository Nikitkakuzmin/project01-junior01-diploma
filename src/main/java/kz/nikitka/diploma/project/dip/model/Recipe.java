package kz.nikitka.diploma.project.dip.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity  {

    private String name;

    private String description;

    private String image;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Ingredient> ingredients;


}

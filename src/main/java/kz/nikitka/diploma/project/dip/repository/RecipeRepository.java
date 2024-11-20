package kz.nikitka.diploma.project.dip.repository;

import jakarta.transaction.Transactional;
import kz.nikitka.diploma.project.dip.model.Recipe;
import kz.nikitka.diploma.project.dip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface RecipeRepository extends JpaRepository<Recipe,Long> {

}
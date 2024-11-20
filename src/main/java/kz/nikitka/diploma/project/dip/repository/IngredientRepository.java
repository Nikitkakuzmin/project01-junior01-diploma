package kz.nikitka.diploma.project.dip.repository;

import jakarta.transaction.Transactional;
import kz.nikitka.diploma.project.dip.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface IngredientRepository extends JpaRepository <Ingredient,Long> {
}
package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dao.PriceDAO;
import hei.phylosius.restomanager.dto.IngredientCreateDTO;
import hei.phylosius.restomanager.dto.IngredientDTO;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.Price;
import hei.phylosius.restomanager.model.Unit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Component
public class IngredientMapper {

    private PriceDAO priceDAO;

    public IngredientDTO toDTO(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getPrice().getValue(),
                ingredient.getModificationDate());
    }

    public Ingredient toEntity(ResultSet resultSet) {
        try {
            Ingredient ingredient = new Ingredient();

            ingredient.setId(resultSet.getString("id"));
            ingredient.setName(resultSet.getString("name"));
            ingredient.setModificationDate(resultSet.getTimestamp("modification_date").toLocalDateTime());
            ingredient.setPrice(priceDAO.getLatestByIngredientID(ingredient.getId()));
            ingredient.setUnit(Unit.valueOf(resultSet.getString("unit")));

            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ingredient toEntity(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();

        ingredient.setId(ingredientDTO.getId() != null ? ingredientDTO.getId() : UUID.randomUUID().toString());
        ingredient.setName(ingredientDTO.getName());
        ingredient.setPrice(new Price(ingredientDTO.getUnitPrice(), LocalDateTime.now()));
        ingredient.setModificationDate(LocalDateTime.now());

        return ingredient;
    }

    public Ingredient toEntity(IngredientCreateDTO ingredientCreateDTO) {
        Ingredient ingredient = new Ingredient();

        ingredient.setId(UUID.randomUUID().toString());
        ingredient.setName(ingredientCreateDTO.getName());
        ingredient.setModificationDate(LocalDateTime.now());

        return ingredient;
    }
}

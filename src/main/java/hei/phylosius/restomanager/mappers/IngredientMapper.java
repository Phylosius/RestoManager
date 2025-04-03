package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dao.PriceDAO;
import hei.phylosius.restomanager.dto.IngredientDTO;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.Unit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}

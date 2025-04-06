package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.Repository.StockMovementDAO;
import hei.phylosius.restomanager.dto.IngredientCreateRest;
import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.dto.IngredientRestDetailled;
import hei.phylosius.restomanager.dto.IngredientUpdateRest;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.Price;
import hei.phylosius.restomanager.model.StockMovement;
import hei.phylosius.restomanager.model.Unit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class IngredientMapper {

    private final StockMovementMapper stockMovementMapper;
    private PriceDAO priceDAO;
    private StockMovementDAO stockMovementDAO;

    public IngredientRestDetailled toDTODetailled(Ingredient ingredient) {
        IngredientRestDetailled dto = new IngredientRestDetailled();
        String id = ingredient.getId();

        dto.setId(id);
        dto.setName(ingredient.getName());
        dto.setAvalaibleQuantity(ingredient.getAvailableQuantity());
        dto.setCurrentPrice(ingredient.getRecentPrice().getValue());
        dto.setPriceHistory(priceDAO.getAllByIngredientID(id));
        dto.setMovementHistory(
                stockMovementMapper.toDTOs(stockMovementDAO.getAllByIngredientID(id))
        );

        return dto;
    }

    public IngredientRest toDTO(Ingredient ingredient) {
        return new IngredientRest(
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

    public Ingredient toEntity(IngredientRest ingredientRest) {
        Ingredient ingredient = new Ingredient();

        ingredient.setId(ingredientRest.getId() != null ? ingredientRest.getId() : UUID.randomUUID().toString());
        ingredient.setName(ingredientRest.getName());
        ingredient.setPrice(new Price(ingredientRest.getUnitPrice(), LocalDateTime.now()));
        ingredient.setUnit(Unit.U);
        ingredient.setModificationDate(LocalDateTime.now());

        return ingredient;
    }

    public Ingredient toEntity(IngredientUpdateRest ingredientUpdateRest) {
        Ingredient ingredient = new Ingredient();

        priceDAO.saveAllByIngredientId(ingredientUpdateRest.getId(), ingredientUpdateRest.getPrices());

        ingredient.setId(ingredientUpdateRest.getId());
        ingredient.setName(ingredientUpdateRest.getName());
        ingredient.setModificationDate(LocalDateTime.now());
        ingredient.setUnit(Unit.U);

        List<StockMovement> moves = ingredientUpdateRest.getStockMovements().stream().map(s -> stockMovementMapper.toEntity(ingredient, s)).toList();
        moves.forEach(stockMovementDAO::save);

        return ingredient;
    }

    public Ingredient toEntity(IngredientCreateRest ingredientCreateRest) {
        Ingredient ingredient = new Ingredient();

        ingredient.setId(UUID.randomUUID().toString());
        ingredient.setName(ingredientCreateRest.getName());
        ingredient.setModificationDate(LocalDateTime.now());
        ingredient.setUnit(Unit.U);

        return ingredient;
    }
}

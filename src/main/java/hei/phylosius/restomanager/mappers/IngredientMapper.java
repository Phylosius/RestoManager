package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.Repository.StockMovementDAO;
import hei.phylosius.restomanager.dto.*;
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
    private final PriceMapper priceMapper;
    private PriceDAO priceDAO;
    private StockMovementDAO stockMovementDAO;

    public IngredientRest toDTO(Ingredient ingredient) {
        IngredientRest dto = new IngredientRest();
        Integer id = Integer.valueOf(ingredient.getId());

        dto.setId(id);
        dto.setName(ingredient.getName());
        dto.setAvailableQuantity(ingredient.getAvailableQuantity());
        dto.setActualPrice(ingredient.getRecentPrice().getValue());
        dto.setPrices(
                priceMapper.toDTOs(id, priceDAO.getAllByIngredientID(id.toString()))
        );
        dto.setStockMovements(
                stockMovementMapper.toDTOs(stockMovementDAO.getAllByIngredientID(id.toString()))
        );

        return dto;
    }

    public List<IngredientRest> toDTOs(List<Ingredient> ingredients) {
        return ingredients.stream().map(this::toDTO).toList();
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

        ingredient.setId(ingredientRest.getId() != null ? ingredientRest.getId().toString() : UUID.randomUUID().toString());
        ingredient.setName(ingredientRest.getName());
        ingredient.setPrice(new Price(ingredientRest.getActualPrice(), LocalDateTime.now()));
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

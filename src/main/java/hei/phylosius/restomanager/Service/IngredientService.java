package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.IngredientDAO;
import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.Repository.StockMovementDAO;
import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.dto.PriceRest;
import hei.phylosius.restomanager.dto.StockMovementRest;
import hei.phylosius.restomanager.mappers.IngredientMapper;
import hei.phylosius.restomanager.mappers.PriceMapper;
import hei.phylosius.restomanager.mappers.StockMovementMapper;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class IngredientService {

    private final IngredientDAO ingredientDAO;
    private final IngredientMapper ingredientMapper;
    private final PriceDAO priceDAO;
    private final PriceMapper priceMapper;
    private final StockMovementDAO stockMovementDAO;
    private final StockMovementMapper stockMovementMapper;

    public Boolean isExist(Integer id) {
        return ingredientDAO.isExist(id.toString());
    }

    public IngredientRest getOneIngredient(Integer id) {
        Ingredient ingredient = ingredientDAO.getById(id.toString());
        return ingredientMapper.toDTO(ingredient);
    }

    public List<IngredientRest> getIngredients(Integer page, Integer pageSize){

        if (page != null && pageSize != null) {
            return ingredientMapper.toDTOs(ingredientDAO.getAll(page, pageSize));
        } else {
            return ingredientMapper.toDTOs(ingredientDAO.getAll());
        }
    }

    public void savePrices(Integer id, List<PriceRest> prices) {
        priceDAO.saveAllByIngredientId(
                id.toString(),
                priceMapper.toEntities(id, prices));
    }


    public void addStockMovements(Integer id, List<StockMovementRest> movements) {
        stockMovementDAO.saveAll(
                stockMovementMapper.toEntities(
                        ingredientDAO.getById(id.toString()),
                        movements
                )
        );
    }
}

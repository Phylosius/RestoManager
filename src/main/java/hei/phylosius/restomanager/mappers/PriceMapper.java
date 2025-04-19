package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.PriceRest;
import hei.phylosius.restomanager.model.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Component
public class PriceMapper {

    public List<PriceRest> toDTOs(Integer ingredientId, List<Price> prices) {
        return  prices.stream().map(price -> toDTO(ingredientId, price)).toList();
    }

    public PriceRest toDTO(Integer ingredientId, Price price) {
        return new PriceRest(
                String.format("%s.%s", ingredientId.toString(), price.getDate().toString()).hashCode(),
                price.getValue(),
                price.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
}

package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.PriceRest;
import hei.phylosius.restomanager.model.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Component
public class PriceMapper {

    public List<Price> toEntities(List<PriceRest> DTOs) {
        return DTOs.stream().map(this::toEntity).toList();
    }

    public Price toEntity(PriceRest dto) { // don't use the id in the dto 😲
        Price price = new Price();

        price.setDate(LocalDateTime.parse(dto.getDateValue()));
        price.setValue(dto.getPrice());

        return price;
    }

    public List<PriceRest> toDTOs(List<Price> prices) {
        return prices.stream().map(this::toDTO).toList();
    }

    public PriceRest toDTO(Price price) {
        PriceRest dto = new PriceRest();


        dto.setDateValue(
                price.getDate()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        dto.setPrice(price.getValue());
        dto.setId(
                // the id of the DTO is equal to the timestamp since epoch 🤔 (find why in ::toEntity)
                (int) Instant.now().toEpochMilli()
        );

        return dto;
    }
}

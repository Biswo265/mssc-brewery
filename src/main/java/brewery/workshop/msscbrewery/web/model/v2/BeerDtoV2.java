package brewery.workshop.msscbrewery.web.model.v2;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDtoV2 {
    private UUID beerId;
    private String beerName;
    private BeerStyleEnum beerStyle;
    private Long upc;
}

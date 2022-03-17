package brewery.workshop.msscbrewery.web.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {
    private UUID beerId;
    private String beerName;
    private String beerStyle;
    private Long upc;
}

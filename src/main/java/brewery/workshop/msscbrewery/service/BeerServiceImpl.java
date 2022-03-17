package brewery.workshop.msscbrewery.service;

import brewery.workshop.msscbrewery.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().beerId(beerId).beerName("ClassBerg").beerStyle("Party").build();
    }
}

package brewery.workshop.msscbrewery.service;

import brewery.workshop.msscbrewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);
}

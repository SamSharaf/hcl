package com.apple.hcl4.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import com.apple.hcl4.domain.Country;

public class CountryRepository extends AbstractRepository<Country, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CountryRepository() {
        super(Country.class);
    }

}

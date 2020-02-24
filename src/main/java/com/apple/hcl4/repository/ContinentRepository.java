package com.apple.hcl4.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import com.apple.hcl4.domain.Continent;

public class ContinentRepository extends AbstractRepository<Continent, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContinentRepository() {
        super(Continent.class);
    }

}

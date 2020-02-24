package com.apple.hcl4.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * @author samsharaf
 */
@Entity
@Table(name = "continent", schema = "hcl")
@Cacheable
public class Continent implements Serializable {

    private static final long serialVersionUID = -1010416464116052776L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", table = "continent", unique = true, nullable = false, updatable = false)
    private Long id;

    @Basic(optional = false, fetch = FetchType.LAZY)
    @Column(name = "continent_name", table = "continent", unique = true, nullable = false, updatable = false)
    private String continentName;

    @OneToMany(mappedBy = "countryContinent", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<@NotNull(message = "A continent may not contain null country") Country> countriesSet;

    @Version
    @Column(name = "continent_version", table = "continent")
    private long continentVersion = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public Set<Country> getCountriesSet() {
        if (countriesSet == null) {
            countriesSet = new HashSet<>();
        }
        return Collections.unmodifiableSet(countriesSet);
    }

    public void addCountriesSet(Country countriesSet) {
        getCountriesSet().add(countriesSet);
        countriesSet.setCountryContinent(this);
    }

    public void removeCountriesSet(Country countriesSet) {
        getCountriesSet().remove(countriesSet);
        countriesSet.setCountryContinent(null);
    }

    public long getContinentVersion() {
        return continentVersion;
    }

    public void setContinentVersion(long continentVersion) {
        this.continentVersion = continentVersion;
    }

}
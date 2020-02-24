package com.apple.hcl4.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author samsharaf
 */
@Entity
@Table(name = "country", schema = "hcl")
//@IdClass(CountriesPK.class)
@Cacheable
public class Country implements Serializable {

    private static final long serialVersionUID = 577903537076738324L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", table = "country", unique = true, nullable = false, updatable = false)
    private Long id;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "country_name", table = "country", unique = true, nullable = false, updatable = false)
    private String countryName;

    @Basic(optional = false, fetch = FetchType.LAZY)
    @Column(name = "country_flag", table = "country", unique = true, nullable = false, length = 2)
    @NotEmpty(message = "Country flag can not be empty field")
    @Size(min = 2, max = 2, message = "Country flag consists of exactly 2 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Country flag consists of alphabets only")
    private String countryFlag;

//    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REFRESH)
//    @JoinTable(name = "country", schema = "hcl")
    @JoinTable(name = "continent", schema = "hcl")
    @NotNull(message = "A country must belong to a continent")
    private Continent countryContinent;

    @Version
    @Column(name = "country_version", table = "country")
    private long countryVersion = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public Continent getCountryContinent() {
        return countryContinent;
    }

    public void setCountryContinent(Continent countryContinent) {
        this.countryContinent = countryContinent;
    }

    public long getCountryVersion() {
        return countryVersion;
    }

    public void setCountryVersion(long countryVersion) {
        this.countryVersion = countryVersion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final Country other = (Country) obj;
        if (!java.util.Objects.equals(this.getCountryFlag(), other.getCountryFlag())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.getCountryFlag());
        return hash;
    }

}
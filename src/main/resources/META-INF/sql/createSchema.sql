/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  samsharaf
 * Created: Feb 24, 2020
 */
CREATE SCHEMA hcl;
-- CREATE TABLE hcl.continent (id BIGINT IDENTITY NOT NULL UNIQUE, continent_name VARCHAR(255) NOT NULL UNIQUE, continent_version BIGINT, PRIMARY KEY (id));
-- CREATE TABLE hcl.country (id BIGINT IDENTITY NOT NULL UNIQUE, country_flag VARCHAR(2) NOT NULL UNIQUE, country_name VARCHAR(255) NOT NULL UNIQUE, country_version BIGINT, Country_id BIGINT NOT NULL, countryContinent_id BIGINT NOT NULL, PRIMARY KEY (id, Country_id, countryContinent_id));
-- ALTER TABLE hcl.country ADD CONSTRAINT FK_country_Country_id FOREIGN KEY (Country_id) REFERENCES hcl.country (id);
-- ALTER TABLE hcl.country ADD CONSTRAINT FK_country_countryContinent_id FOREIGN KEY (countryContinent_id) REFERENCES hcl.continent (id);
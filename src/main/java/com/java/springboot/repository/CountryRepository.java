package com.java.springboot.repository;

import com.java.springboot.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer>, JpaRepository<Country, Integer> {

    @Modifying
    @Query("delete from Country c where c.name = ?1")
    void deleteByCountryName(String name);

    List<Country> findByNameIgnoreCase(String name);
}

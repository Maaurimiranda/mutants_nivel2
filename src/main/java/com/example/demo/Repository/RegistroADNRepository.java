package com.example.demo.Repository;

import com.example.demo.Entities.RegistroADN;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroADNRepository extends BaseRepository<RegistroADN, Long>{

    // Cuenta el numero de mutantes
    @Query("SELECT COUNT(r) FROM RegistroADN r WHERE r.isMutant = true")
    Long countMutants();

    // Cuenta el numero de no mutantes
    @Query("SELECT COUNT(r) FROM RegistroADN r WHERE r.isMutant = false")
    Long countNonMutants();
}

package com.example.vidaplus.infrastructure.medic;

import com.example.vidaplus.infrastructure.medic.entity.MedicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface MedicRepository extends JpaRepository<MedicEntity, Long> {

    Optional<MedicEntity> findByCrm(String crm);

    Optional<MedicEntity> findByEmail(String email);

}

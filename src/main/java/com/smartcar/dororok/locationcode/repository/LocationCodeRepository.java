package com.smartcar.dororok.locationcode.repository;

import com.smartcar.dororok.locationcode.domain.LocationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationCodeRepository extends JpaRepository<LocationCode, Long> {

    Optional<LocationCode> findByAreaNameAndSigunguName(String areaName, String sigunguName);

}

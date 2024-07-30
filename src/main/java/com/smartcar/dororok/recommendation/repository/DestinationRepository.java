package com.smartcar.dororok.recommendation.repository;

import com.smartcar.dororok.recommendation.domain.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
}

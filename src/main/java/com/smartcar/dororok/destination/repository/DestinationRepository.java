package com.smartcar.dororok.destination.repository;

import com.smartcar.dororok.destination.domain.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}

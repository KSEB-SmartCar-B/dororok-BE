package com.smartcar.dororok.destination.repository;

import com.smartcar.dororok.destination.domain.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    @Modifying
    @Query("DELETE FROM Destination d WHERE d.createdDate < :cutoffDate")
    void deleteOlderThan(LocalDateTime cutoffDate);
}

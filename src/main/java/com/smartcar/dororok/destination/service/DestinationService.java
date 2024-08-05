package com.smartcar.dororok.destination.service;

import com.smartcar.dororok.destination.domain.AgeRange;
import com.smartcar.dororok.destination.domain.entity.Destination;
import com.smartcar.dororok.destination.repository.DestinationRepository;
import com.smartcar.dororok.global.auth.utils.SecurityUtils;
import com.smartcar.dororok.location.dto.LocationInfoDto;
import com.smartcar.dororok.location.service.LocationService;
import com.smartcar.dororok.member.domain.entitiy.Gender;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationService {

    private final LocationService locationService;
    private final DestinationRepository destinationRepository;
    private final MemberRepository memberRepository;

    public void postDestination(String lat, String lng) {
        Member member = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        LocationInfoDto locationInfoDto = locationService.getAddressFromCoordinates(lat, lng);
        Gender gender = member.getGender();
        AgeRange ageRange = getAgeRange(member.getBirthday());

        Destination destination = Destination.builder()
                .region1depthName(locationInfoDto.getRegion1depthName())
                .region2depthName(locationInfoDto.getRegion2depthName())
                .region3depthName(locationInfoDto.getRegion3depthName())
                .gender(gender)
                .ageRange(ageRange)
                .build();

        destinationRepository.save(destination);

    }

    public AgeRange getAgeRange(LocalDate birthDate) {
        int age = calculateAge(birthDate, LocalDate.now());
        return AgeRange.fromAge(age);
    }

    private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            throw new IllegalArgumentException("Birth date or current date cannot be null");
        }
    }
}

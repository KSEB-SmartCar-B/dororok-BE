package com.smartcar.dororok.destination.domain;

public enum AgeRange {
    TEENS("10대"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES("50대"),
    SIXTIES("60대"),
    SEVENTIES("70대"),
    EIGHTIES("80대"),
    NINETIES("90대"),
    HUNDREDS("100+");

    private final String ageRange;

    AgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public static AgeRange fromAge(int age) {
        if (age >= 10 && age <= 19) {
            return TEENS;
        } else if (age >= 20 && age <= 29) {
            return TWENTIES;
        } else if (age >= 30 && age <= 39) {
            return THIRTIES;
        } else if (age >= 40 && age <= 49) {
            return FORTIES;
        } else if (age >= 50 && age <= 59) {
            return FIFTIES;
        } else if (age >= 60 && age <= 69) {
            return SIXTIES;
        } else if (age >= 70 && age <= 79) {
            return SEVENTIES;
        } else if (age >= 80 && age <= 89) {
            return EIGHTIES;
        } else if (age >= 90 && age <= 99) {
            return NINETIES;
        } else if (age >= 100) {
            return HUNDREDS;
        } else {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
    }

}

package com.smartcar.dororok.recommendation.domain.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteAccountRes {
    private String res;

    public DeleteAccountRes() {
    }

    public DeleteAccountRes(String res) {
        this.res = res;
    }

}

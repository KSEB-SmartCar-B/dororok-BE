package com.smartcar.dororok.global.auth.utils;


import com.smartcar.dororok.global.exception.CustomException;
import com.smartcar.dororok.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return authentication.getName();
    }
}

package org.example.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.exception.NTTAppBaseException;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OwnerRoleEnum {
    ADMIN("ADMIN"),
    OWNER("OWNER");

    private final String role;

    @JsonValue
    public String getRole() {
        return role;
    }

    @JsonCreator
    public static OwnerRoleEnum fromValue(String value) {
        return Arrays.stream(OwnerRoleEnum.values())
                .filter(userRoleEnum -> userRoleEnum.role.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new NTTAppBaseException("OwnerRoleEnum value not found: " + value));
    }
}

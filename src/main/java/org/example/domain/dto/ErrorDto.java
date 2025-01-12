package org.example.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ErrorDto {

    private String message;
    private List<AdditionalDetailsDto> additionalDetails = new ArrayList<>();

    @Builder
    public ErrorDto(String message, List<AdditionalDetailsDto> additionalDetails) {
        this.message = message;
        this.additionalDetails = (additionalDetails != null) ? additionalDetails : new ArrayList<>();
    }
}


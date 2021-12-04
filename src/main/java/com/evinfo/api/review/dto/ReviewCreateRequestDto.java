package com.evinfo.api.review.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewCreateRequestDto {
    @NotBlank
    private String content;
    @NotNull
    private Double star;
    @NotBlank
    private String stationId;
}

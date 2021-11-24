package com.evinfo.api.map.dto.kakao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoMapDto {
    private List<KakaoMapResponseDto> documents;
    private KakaoMapMetaDataDto meta;
}

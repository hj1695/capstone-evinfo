package com.evinfo.utils;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.ChargerType;

import java.util.Arrays;
import java.util.List;

public class ChargerGenerator {
    private static final String 충전기_정류소_이름 = "충전기 정류소 이름";
    private static final String 충전기_정류소_ID_1 = "충전기 정류소 ID 1";
    private static final String 충전기_충전기_ID_1 = "충전기 충전기 ID 1";
    private static final String 충전기_정류소_ID_2 = "충전기 정류소 ID 2";
    private static final String 충전기_충전기_ID_2 = "충전기 충전기 ID 2";
    private static final ChargerType 충전기_충전_타입 = ChargerType.TYPE01;
    private static final String 충전기_주소 = "주소 예시";
    private static final String 충전기_세부_주소 = "주소 세부설명 예시";
    private static final String 충전기_사용시간 = "사용시간 예시";
    private static final Double 충전기_위도_1 = 1.0;
    private static final Double 충전기_위도_2 = 2.0;
    private static final Double 충전기_경도_1 = 10.0;
    private static final Double 충전기_경도_2 = 20.0;
    private static final String 충전기_전화번호 = "010-1234-5678";
    private static final ChargerStat 충전기_상태 = ChargerStat.CHECKING;

    private static final Charger charger1, charger2;

    static {
        charger1 = Charger.builder()
                .stationName(충전기_정류소_이름)
                .stationId(충전기_정류소_ID_1)
                .chargerId(충전기_충전기_ID_1)
                .chargerType(충전기_충전_타입)
                .address(충전기_주소)
                .location(충전기_세부_주소)
                .useTime(충전기_사용시간)
                .latitude(충전기_위도_1)
                .longitude(충전기_경도_1)
                .callNumber(충전기_전화번호)
                .chargerStat(충전기_상태)
                .build();
        charger2 = Charger.builder()
                .stationName(충전기_정류소_이름)
                .stationId(충전기_정류소_ID_2)
                .chargerId(충전기_충전기_ID_2)
                .chargerType(충전기_충전_타입)
                .address(충전기_주소)
                .location(충전기_세부_주소)
                .useTime(충전기_사용시간)
                .latitude(충전기_위도_2)
                .longitude(충전기_경도_2)
                .callNumber(충전기_전화번호)
                .chargerStat(충전기_상태)
                .build();
    }

    public static Charger getCharger() {
        return charger1;
    }

    public static List<Charger> getChargers() {
        return Arrays.asList(charger1, charger2);
    }

    public static List<ChargerClientResponseDto> getClientChargers() {
        return Arrays.asList(
                ChargerClientResponseDto.builder()
                        .statNm(charger1.getStationName())
                        .statId(charger1.getStationId())
                        .chgerId(charger1.getChargerId())
                        .chgerType(charger1.getChargerType().getKey())
                        .addr(charger1.getAddress())
                        .location(charger1.getLocation())
                        .useTime(charger1.getUseTime())
                        .lat(charger1.getLatitude())
                        .lng(charger1.getLongitude())
                        .busiCall(charger1.getCallNumber())
                        .stat(charger1.getChargerStat().getKey())
                        .build(),
                ChargerClientResponseDto.builder()
                        .statNm(charger2.getStationName())
                        .statId(charger2.getStationId())
                        .chgerId(charger2.getChargerId())
                        .chgerType(charger2.getChargerType().getKey())
                        .addr(charger2.getAddress())
                        .location(charger2.getLocation())
                        .useTime(charger2.getUseTime())
                        .lat(charger2.getLatitude())
                        .lng(charger2.getLongitude())
                        .busiCall(charger2.getCallNumber())
                        .stat(charger2.getChargerStat().getKey())
                        .build());
    }
}

package com.evinfo.utils;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.ChargerType;

import java.util.Arrays;
import java.util.List;

public class ChargerGenerator {
    private static final Long 충전기_ID_1 = 1L;
    private static final Long 충전기_ID_2 = 2L;
    private static final String 충전기_정류소_이름 = "충전기 정류소 이름";
    private static final String 충전기_정류소_ID = "충전기 정류소 ID";
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
                .id(충전기_ID_1)
                .stationName(충전기_정류소_이름)
                .stationId(충전기_정류소_ID)
                .chargerType(충전기_충전_타입)
                .address(충전기_주소)
                .location(충전기_세부_주소)
                .useTime(충전기_사용시간)
                .lat(충전기_위도_1)
                .lng(충전기_경도_1)
                .callNumber(충전기_전화번호)
                .chargerStat(충전기_상태)
                .build();
        charger2 = Charger.builder()
                .id(충전기_ID_1)
                .stationName(충전기_정류소_이름)
                .stationId(충전기_정류소_ID)
                .chargerType(충전기_충전_타입)
                .address(충전기_주소)
                .location(충전기_세부_주소)
                .useTime(충전기_사용시간)
                .lat(충전기_위도_2)
                .lng(충전기_경도_2)
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
                        .chgerType(charger1.getChargerType().getKey())
                        .addr(charger1.getAddress())
                        .location(charger1.getLocation())
                        .useTime(charger1.getUseTime())
                        .lat(charger1.getLat())
                        .lng(charger1.getLng())
                        .busiCall(charger1.getCallNumber())
                        .stat(charger1.getChargerStat().getKey())
                        .build(),
                ChargerClientResponseDto.builder()
                        .statNm(charger2.getStationName())
                        .statId(charger2.getStationId())
                        .chgerType(charger2.getChargerType().getKey())
                        .addr(charger2.getAddress())
                        .location(charger2.getLocation())
                        .useTime(charger2.getUseTime())
                        .lat(charger2.getLat())
                        .lng(charger2.getLng())
                        .busiCall(charger2.getCallNumber())
                        .stat(charger2.getChargerStat().getKey())
                        .build());
    }
}

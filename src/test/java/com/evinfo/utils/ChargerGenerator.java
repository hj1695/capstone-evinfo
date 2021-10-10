package com.evinfo.utils;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.ChargerType;
import com.evinfo.domain.charger.Station;

import java.util.Arrays;
import java.util.List;

public class ChargerGenerator {
    private static final String 충전소_이름_1 = "충전기 충전소 이름 1";
    private static final String 충전소_이름_2 = "충전기 충전소 이름 2";
    private static final String 충전소_ID_1 = "충전기 충전소 ID 1";
    private static final String 충전소_ID_2 = "충전기 충전소 ID 2";
    private static final String 충전기_ID_1 = "충전기 충전기 ID 1";
    private static final String 충전기_ID_2 = "충전기 충전기 ID 2";
    private static final ChargerType 충전기_충전_타입 = ChargerType.TYPE01;
    private static final String 충전소_주소 = "주소 예시";
    private static final String 충전소_세부_주소 = "주소 세부설명 예시";
    private static final String 충전소_사용시간 = "사용시간 예시";
    private static final Double 충전소_위도_1 = 1.0;
    private static final Double 충전소_위도_2 = 2.0;
    private static final Double 충전소_경도_1 = 10.0;
    private static final Double 충전소_경도_2 = 20.0;
    private static final String 충전소_전화번호 = "010-1234-5678";
    private static final ChargerStat 충전기_상태 = ChargerStat.CHECKING;

    private static final Charger charger1, charger2;
    private static final Station station1, station2;

    static {
        station1 = Station.builder()
                .stationName(충전소_이름_1)
                .stationId(충전소_ID_1)
                .address(충전소_주소)
                .location(충전소_세부_주소)
                .useTime(충전소_사용시간)
                .latitude(충전소_위도_1)
                .longitude(충전소_경도_1)
                .callNumber(충전소_전화번호)
                .build();
        station2 = Station.builder()
                .stationName(충전소_이름_2)
                .stationId(충전소_ID_2)
                .address(충전소_주소)
                .location(충전소_세부_주소)
                .useTime(충전소_사용시간)
                .latitude(충전소_위도_2)
                .longitude(충전소_경도_2)
                .callNumber(충전소_전화번호)
                .build();
        charger1 = Charger.builder()
                .station(station1)
                .chargerId(충전기_ID_1)
                .chargerType(충전기_충전_타입)
                .chargerStat(충전기_상태)
                .build();
        charger2 = Charger.builder()
                .station(station2)
                .chargerId(충전기_ID_2)
                .chargerType(충전기_충전_타입)
                .chargerStat(충전기_상태)
                .build();
        station1.addCharger(charger1);
        station2.addCharger(charger2);
    }

    public static Charger getCharger() {
        return charger1;
    }

    public static List<Charger> getChargers() {
        return Arrays.asList(charger1, charger2);
    }

    public static List<Station> getStations() {
        return Arrays.asList(station1, station2);
    }

    public static List<ChargerClientResponseDto> getClientChargers() {
        return Arrays.asList(
                ChargerClientResponseDto.builder()
                        .statNm(station1.getStationName())
                        .statId(station1.getStationId())
                        .chgerId(charger1.getChargerId())
                        .chgerType(charger1.getChargerType().getKey())
                        .addr(station1.getAddress())
                        .location(station1.getLocation())
                        .useTime(station1.getUseTime())
                        .lat(station1.getLatitude())
                        .lng(station1.getLongitude())
                        .busiCall(station1.getCallNumber())
                        .stat(charger1.getChargerStat().getKey())
                        .build(),
                ChargerClientResponseDto.builder()
                        .statNm(station2.getStationName())
                        .statId(station2.getStationId())
                        .chgerId(charger2.getChargerId())
                        .chgerType(charger2.getChargerType().getKey())
                        .addr(station2.getAddress())
                        .location(station2.getLocation())
                        .useTime(station2.getUseTime())
                        .lat(station2.getLatitude())
                        .lng(station2.getLongitude())
                        .busiCall(station2.getCallNumber())
                        .stat(charger2.getChargerStat().getKey())
                        .build());
    }
}

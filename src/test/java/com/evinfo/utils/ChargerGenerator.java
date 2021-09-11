package com.evinfo.utils;

import com.evinfo.domain.charger.domain.Charger;
import com.evinfo.domain.charger.domain.ChargerStat;
import com.evinfo.domain.charger.domain.ChargerType;

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
    private static final Double 충전기_위도 = 123.45;
    private static final Double 충전기_경도 = 456.78;
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
                .lat(충전기_위도)
                .lng(충전기_경도)
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
                .lat(충전기_위도)
                .lng(충전기_경도)
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
}

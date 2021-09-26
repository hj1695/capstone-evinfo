package com.evinfo.api.charger.dto.client;

import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.ChargerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChargerClientResponseDto {
    // TODO: 2021/09/10 일단 필요한 값 다 넣었음. 추후 제거하기
    private String statNm;
    private String statId;
    private String chgerId;
    private Long chgerType;
    private String addr;
    private String location;
    private Double lat;
    private Double lng;
    private String useTime;
    private String busiId;
    private String bnm;
    private String busiNm;
    private String busiCall;
    private Long stat;
    private String statUpdDt;
    private String lastTsdt;
    private String lastTedt;
    private String nowTsdt;
    private String powerType;
    private Long output;
    private String method;
    private Long zcode;
    private String parkingFree;
    private String note;
    private String limitYn;
    private String limitDetail;
    private String delYn;
    private String delDetail;

    public Charger getCharger() {
        return Charger.builder()
                .stationName(this.statNm)
                .stationId(this.statId)
                .chargerId(chgerId)
                .chargerType(ChargerType.valueOf(this.chgerType))
                .address(this.addr)
                .location(this.location)
                .useTime(this.useTime)
                .lat(this.lat)
                .lng(this.lng)
                .callNumber(this.busiCall)
                .chargerStat(ChargerStat.valueOf(this.stat))
                .build();
    }
}
package com.evinfo.api.charger.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "header")
@XmlAccessorType(XmlAccessType.FIELD)
public class EvinfoHeaderResponseDto {
    private Long resultCode;
    private String resultMsg;
    private Long totalCount;
    private Long pageNo;
    private Long numOfRows;
}

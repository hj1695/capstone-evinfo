package com.evinfo.api.charger.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "response")
public class EvinfoResponseDto {
    @XmlElement(name = "header")
    private EvinfoHeaderResponseDto header;

    @XmlElement(name = "body")
    private EvinfoBodyResponseDto body;
}

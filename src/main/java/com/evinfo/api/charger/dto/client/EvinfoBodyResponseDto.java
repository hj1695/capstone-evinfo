package com.evinfo.api.charger.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "body")
public class EvinfoBodyResponseDto {
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<ChargerClientResponseDto> items;
}

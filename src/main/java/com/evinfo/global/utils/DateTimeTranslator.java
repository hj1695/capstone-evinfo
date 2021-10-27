package com.evinfo.global.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * DateTimeTranslator 는 문자열로 된 날짜-시간값을 LocalDateTime 으로 변환한다.
 * 이 과정에서 유효하지 않은 값은 LOCAL_DATE_TIME_UNDER_BOUND 를 반환하도록 수행한다.
 *
 * @author Geonhee Lee
 */
public class DateTimeTranslator {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final LocalDateTime LOCAL_DATE_TIME_UNDER_BOUND = LocalDateTime.parse("20000101000000", formatter);

    private DateTimeTranslator() {
    }

    public static LocalDateTime exchangeLocalDateTime(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            return LOCAL_DATE_TIME_UNDER_BOUND;
        }

        var dateTime = LocalDateTime.parse(value, formatter);
        if (dateTime.isAfter(LocalDateTime.now()) || dateTime.isBefore(LOCAL_DATE_TIME_UNDER_BOUND)) {
            return LOCAL_DATE_TIME_UNDER_BOUND;
        }

        return dateTime;
    }
}

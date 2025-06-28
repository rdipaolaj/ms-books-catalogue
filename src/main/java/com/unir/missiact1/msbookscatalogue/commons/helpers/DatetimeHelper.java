package com.unir.missiact1.msbookscatalogue.commons.helpers;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Helper para obtener la fecha/hora actual en la zona de Lima (Perú).
 */
public final class DatetimeHelper {

    private static final String TIME_ZONE = "America/Lima";

    private DatetimeHelper() { /* no-instanciable */ }

    /**
     * @return fecha/hora actual en la zona horaria configurada.
     */
    public static ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.of(TIME_ZONE));
    }

    /**
     * @return fecha/hora actual en UTC.
     */
    public static ZonedDateTime nowUtc() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }
}
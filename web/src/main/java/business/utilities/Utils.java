package business.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * La clase Utils proporciona métodos útiles para el manejo de fechas y conversiones.
 */
public class Utils {
    /**
     * Convierte una cadena de fecha en formato "dd/MM/yyyy" a un objeto Date.
     *
     * @param dateString La cadena de fecha a convertir.
     * @return Un objeto Date que representa la fecha.
     */
    public static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Convierte un objeto Date en una cadena de fecha en formato "dd/MM/yyyy".
     *
     * @param date El objeto Date a convertir.
     * @return Una cadena de fecha en formato "dd/MM/yyyy".
     */
    public static String getStringDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    /**
     * Calcula la diferencia en días entre dos fechas.
     *
     * @param zero La fecha de inicio.
     * @param date La fecha de fin.
     * @return La diferencia en días entre las dos fechas.
     */
    public static long daysBetween(Date zero, Date date) {
        long daysBetween = ChronoUnit.DAYS.between(zero.toInstant(), date.toInstant());
        boolean isBefore = zero.getTime() >= date.getTime();
        return Math.abs(daysBetween) * (isBefore ? 1 : -1);
    }

    /**
     * Calcula la diferencia en años entre dos fechas.
     *
     * @param zero La fecha de inicio.
     * @param date La fecha de fin.
     * @return La diferencia en años entre las dos fechas.
     */
    public static long yearsBetween(Date zero, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(zero);
        int startYear = calendar.get(Calendar.YEAR);

        calendar.setTime(date);
        int endYear = calendar.get(Calendar.YEAR);

        return Math.abs(endYear - startYear);
    }
    
    /**
     * Devuelve la fecha actual.
     *
     * @return La fecha actual.
     */
    public static Date getCurrentDate() {
        return new Date();
    }
    
    public static Date parseDateBrowserFormat(String dateString) {
	    try {
	        return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
	    } catch (ParseException e) {
	        return null;
	    }
	}
}

package basicbike.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util for date format used in the application.
 */
public class DateUtil {
    private static final String dateFormat = "dd/MM/yyyy HH:mm:ss";

    /**
     * Get date format as string
     * @return String format
     */
    public static String getDateFormat() {
        return dateFormat;
    }

    public static Date parseDate(String stringDate) throws ParseException {
        return new SimpleDateFormat(dateFormat).parse(stringDate);
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat(dateFormat).format(date);
    }
}

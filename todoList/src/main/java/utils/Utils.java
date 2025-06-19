package utils;

import java.sql.Date;
import java.time.LocalDate;

public class Utils {
    public static LocalDate getUtilDate(Date sqlDate){
        return sqlDate.toLocalDate();
    }

    public static Date getSQLDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

}

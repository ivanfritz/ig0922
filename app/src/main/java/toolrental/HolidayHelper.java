package toolrental;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalAdjusters.firstInMonth;
import java.util.HashMap;

/**
 *
 * @author ivang
 */
public class HolidayHelper {

    private final DateTimeFormatter ymdFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final HashMap<String, String> holidays = new HashMap<>();
    private int holidayYear = 0;

    private void setHolidays(int year) {
        //Check if year is something reasonable. May work fine but normally
        //will not be anything before/after these years
        if (year < 1900 || year > 2999) {
            throw new IllegalArgumentException("Please pass in a year that is >= 1900 and <= 2999 ");
        }

        //If year is same as last year used, do not recalculate holidays.
        if (year != holidayYear) {
            holidayYear = year;
            calculateHolidays();
        }
    }
    
    /*
    If holiday lands on a weekend it will add or subract a day to
    move it to closest buisiness day.
    
    Ex: Lands Sat then we subtract day so it's on Friday
     */
    private LocalDate fixHolidayLandsWeekend(LocalDate holidayDT) {
        switch (holidayDT.getDayOfWeek().toString()) {
            case "SATURDAY":
                holidayDT = holidayDT.minusDays(1);
                break;
            case "SUNDAY":
                holidayDT = holidayDT.plusDays(1);
                break;
        }

        return holidayDT;
    }

    private void storeKeyedHoliday(String key, LocalDate date) {
        String ymd;

        if (holidays.containsKey(key)) {
            throw new IllegalArgumentException("Found duplicate key entry for the following holiday '" + key + "'");
        }

        ymd = date.format(ymdFormat);
        holidays.put(key, ymd);
    }

    private void calculateHolidays() {
        LocalDate holidayDT;
        
        //Clear holidays
        holidays.clear();

        //Set 4th of July (Independence) Day";
        holidayDT = LocalDate.of(this.holidayYear, Month.JULY, 4);
        holidayDT = fixHolidayLandsWeekend(holidayDT);
        storeKeyedHoliday("independence", holidayDT);

        //Set Labor Day";
        holidayDT = LocalDate.of(this.holidayYear, Month.SEPTEMBER, 1);
        holidayDT = holidayDT.with(firstInMonth(DayOfWeek.MONDAY));
        storeKeyedHoliday("labor", holidayDT);
    }

    public boolean isDateHoliday(LocalDate checkDate) {
        setHolidays(checkDate.getYear());
        String date = checkDate.format(ymdFormat);
        return holidays.values().contains(date);
    }

    //Returns keyed holidays so can view values for each
    public HashMap<String, String> getHolidays(int year) {
        setHolidays(year);
        return holidays;
    }
}

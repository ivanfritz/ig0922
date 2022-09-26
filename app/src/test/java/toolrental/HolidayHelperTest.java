package toolrental;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ivang
 */
public class HolidayHelperTest {
    
 
    /**
     * Asserts base condition of 4th July being on a weekday and makes
     * sure we recognize it correctly
     */
    @Test
    public void testIsDateHoliday() {
        LocalDate checkDate = LocalDate.of(2022, Month.JULY, 4);
        HolidayHelper instance = new HolidayHelper();
        boolean expResult = true;
        boolean result = instance.isDateHoliday(checkDate);
        assertEquals(expResult, result);
    }
 
    /**
     * Asserts edge case of 4th July being on a weekend so it is observed on different day
     */
    @Test
    public void testIsDateHolidayLandsWeekend() {
        LocalDate checkDate = LocalDate.of(2021, Month.JULY, 4);
        HolidayHelper instance = new HolidayHelper();
        boolean expResult = false;
        boolean result = instance.isDateHoliday(checkDate);
        assertEquals(expResult, result);
    }

    /**
     * Asserts we get back the correct observed holidays/days for a respective year.
     */
    @Test
    public void testGetHolidaysSingleYear() {
        int year = 2022;
        HolidayHelper instance = new HolidayHelper();
        HashMap<String, String> expResult = new HashMap<>();
        expResult.put("independence", "20220704");
        expResult.put("labor", "20220905");
        HashMap<String, String> result = instance.getHolidays(year);
        assertEquals(expResult, result);
    }
    
    /**
     * Asserts we get back the correct observed holidays/days for multiple years.
     * Holiday object should clear itself and only contain holidays for respective year.
     */
    @Test
    public void testGetHolidaysMulipleYears() {
        HashMap<String, String> result;
        HolidayHelper instance = new HolidayHelper();
        
        //Expected for 2022
        HashMap<String, String> expResult2022 = new HashMap<>();
        expResult2022.put("independence", "20220704");
        expResult2022.put("labor", "20220905");
        
        //Expected for 2015
        HashMap<String, String> expResult2015 = new HashMap<>();
        expResult2015.put("independence", "20150703");
        expResult2015.put("labor", "20150907");
        
        result = instance.getHolidays(2022);
        assertEquals(expResult2022, result);
        
        result = instance.getHolidays(2015);
        assertEquals(expResult2015, result);
        
        //Double check of for 2022 again
        result = instance.getHolidays(2022);
        assertEquals(expResult2022, result);
    }
    
    /**
     * Asserts we get back the correct observed holidays/days if we get holidays for same year again with same object
     * Holiday object should not recalculate and just return holidays it has.
     */
    @Test
    public void testGetHolidaysMulipleSameYear() {
        HashMap<String, String> result;
        HolidayHelper instance = new HolidayHelper();
        
        //Expected for 2022
        HashMap<String, String> expResult2022 = new HashMap<>();
        expResult2022.put("independence", "20220704");
        expResult2022.put("labor", "20220905");
        
        result = instance.getHolidays(2022);
        assertEquals(expResult2022, result);
        
        //Double check of for 2022 again
        result = instance.getHolidays(2022);
        assertEquals(expResult2022, result);
    }
}
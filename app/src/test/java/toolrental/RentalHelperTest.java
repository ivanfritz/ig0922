package toolrental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ivang
 */
public class RentalHelperTest {
    
    /**
     * Base test for getBillableDays. Asserts the base condition of counting days
     * is correct if we charge for all. Validates that we do not include checkout date in count of days.
     */
    @Test
    public void testGetBillableDays() {
        
        //A Friday
        LocalDate checkoutDate = LocalDate.of(2022, Month.SEPTEMBER, 9 );
        int rentalDays = 8;
        boolean chargeWeekdays = true;
        boolean chargeWeekends = true;
        boolean chargeHolidays = true;
        RentalHelper instance = new RentalHelper();
        int expResult = 8;
        int result = instance.getBillableDays(checkoutDate, rentalDays, chargeWeekdays, chargeWeekends, chargeHolidays);
        assertEquals(expResult, result);
    }
    
    /**
     * Same as testGetBillableDays but we do not charge for weekdays.
     */
    @Test
    public void testGetBillableDaysNoWeekdayCharge() {
        
        //A Friday
        LocalDate checkoutDate = LocalDate.of(2022, Month.SEPTEMBER, 9 );
        int rentalDays = 8;
        boolean chargeWeekdays = false;
        boolean chargeWeekends = true;
        boolean chargeHolidays = true;
        RentalHelper instance = new RentalHelper();
        int expResult = 3;
        int result = instance.getBillableDays(checkoutDate, rentalDays, chargeWeekdays, chargeWeekends, chargeHolidays);
        assertEquals(expResult, result);
    }
    
    /**
     * Same as testGetBillableDays but we do not charge for weekends.
     */
    @Test
    public void testGetBillableDaysNoWeekendCharge() {
        
        //A Friday
        LocalDate checkoutDate = LocalDate.of(2022, Month.SEPTEMBER, 9 );
        int rentalDays = 8;
        boolean chargeWeekdays = true;
        boolean chargeWeekends = false;
        boolean chargeHolidays = true;
        RentalHelper instance = new RentalHelper();
        int expResult = 5;
        int result = instance.getBillableDays(checkoutDate, rentalDays, chargeWeekdays, chargeWeekends, chargeHolidays);
        assertEquals(expResult, result);
    }
    
     /**
     * Asserts if chargeHolidays if false that we do not charge if rental day
     * lands on a known holiday. In this case it's labor day on 5th.
     */
    @Test
    public void testGetBillableDaysNoHolidayCharge() {
        
        //A Friday
        //Labor day lands on 5th
        LocalDate checkoutDate = LocalDate.of(2022, Month.SEPTEMBER, 2 );
        int rentalDays = 8;
        boolean chargeWeekdays = true;
        boolean chargeWeekends = true;
        boolean chargeHolidays = false;
        RentalHelper instance = new RentalHelper();
        int expResult = 7;
        int result = instance.getBillableDays(checkoutDate, rentalDays, chargeWeekdays, chargeWeekends, chargeHolidays);
        assertEquals(expResult, result);
    }
    
    /**
     * Asserts if all charge flags are off that we do not have any billable days found
     * as all days loop on will fall into one of these cases.
     */
    @Test
    public void testGetBillableDaysAllFlagsFalse() {
        
        //A Friday
        //Labor day lands on 5th
        LocalDate checkoutDate = LocalDate.of(2022, Month.SEPTEMBER, 2 );
        int rentalDays = 8;
        boolean chargeWeekdays = false;
        boolean chargeWeekends = false;
        boolean chargeHolidays = false;
        RentalHelper instance = new RentalHelper();
        int expResult = 0;
        int result = instance.getBillableDays(checkoutDate, rentalDays, chargeWeekdays, chargeWeekends, chargeHolidays);
        assertEquals(expResult, result);
    }

    /**
     * Asserts getPrediscountCharge returns arg1 * arg2 and that value is correct
     */
    @Test
    public void testGetPrediscountCharge() {
        String costPerDay = "2.99";
        int billableDays = 7;
        RentalHelper instance = new RentalHelper();
        BigDecimal expResult = new BigDecimal("20.93");
        BigDecimal result = instance.getPrediscountCharge(costPerDay, billableDays);
        assertEquals(expResult, result);
        
        //Round them so we can assert
        result = result.setScale(2, RoundingMode.HALF_UP);
        expResult = expResult.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expResult.setScale(2), result.setScale(2));
    }

    /**
     * Test of getTotalDiscount method, of class RentalHelper.
     * Asserts we are able to get expected discount amount if we have a discount
     */
    @Test
    public void testGetTotalDiscount() {
        BigDecimal totalCharge = new BigDecimal(25.29);
        int discount = 10;
        RentalHelper instance = new RentalHelper();
        BigDecimal expResult = new BigDecimal(2.53);
        BigDecimal result = instance.getTotalDiscount(totalCharge, discount);
        
        //Round them so we can assert
        result = result.setScale(2, RoundingMode.HALF_UP);
        expResult = expResult.setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expResult.setScale(2), result.setScale(2));
    }

    /**
     * Asserts if or discount has a decimal we round up so we get expected value
     * In this case we get back 1.495 which rounds up to 1.50
     */
    @Test
    public void testGetTotalDiscountRoundsHalfUp() {
        BigDecimal totalCharge = new BigDecimal(2.99);
        int discount = 50;
        RentalHelper instance = new RentalHelper();
        BigDecimal expResult = new BigDecimal(1.50);
        BigDecimal result = instance.getTotalDiscount(totalCharge, discount);
        
        //Round them so we can assert
        result = result.setScale(2, RoundingMode.HALF_UP);
        expResult = expResult.setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expResult.setScale(2), result.setScale(2));
    }
    
    
    /**
     * Test of getTotalDiscount method, of class RentalHelper.
     * Asserts we get back 0 if we do not have a discount.
     */
    @Test
    public void testGetTotalDiscountNoDiscount() {
        BigDecimal totalCharge = new BigDecimal(25.20);
        int discount = 0;
        RentalHelper instance = new RentalHelper();
        BigDecimal expResult = new BigDecimal(0);
        BigDecimal result = instance.getTotalDiscount(totalCharge, discount);
        
        //Round them so we can assert
        result = result.setScale(2, RoundingMode.HALF_UP);
        expResult = expResult.setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expResult.setScale(2), result.setScale(2));
    }

    /**
     * Test of getMoneyString method, of class RentalHelper.
     * Asserts we take care of rounding and return a prefixed dollar sign
     */
    @Test
    public void testGetMoneyStringwithString() {
        String amount = "2.90";
        RentalHelper instance = new RentalHelper();
        String expResult = "$2.90";
        String result = instance.getMoneyString(amount);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getMoneyString method, of class RentalHelper.
     * Asserts we take care of rounding and return a prefixed dollar sign
     */
    @Test
    public void testGetMoneyStringwithEmptyString() {
        String amount = "";
        RentalHelper instance = new RentalHelper();
        String expResult = "$0.00";
        String result = instance.getMoneyString(amount);
        assertEquals(expResult, result);
    }
    
        /**
     * Test of getMoneyString method, of class RentalHelper.
     * Asserts we take care of rounding and return a prefixed dollar sign
     */
    @Test
    public void testGetMoneyStringwithNonNumericString() {
        String amount = "t3sting";
        RentalHelper instance = new RentalHelper();
        String expResult = "$0.00";
        String result = instance.getMoneyString(amount);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMoneyString method, of class RentalHelper.
     * Asserts we take care of rounding and return a prefixed dollar sign
     */
    @Test
    public void testGetMoneyStringwithBigDecimal() {
        BigDecimal amount = new BigDecimal(2.899);
        RentalHelper instance = new RentalHelper();
        String expResult = "$2.90";
        String result = instance.getMoneyString(amount);
        assertEquals(expResult, result);
    }

   /**
     * Test of getMoneyString method, of class RentalHelper.
     * Asserts we take care of rounding and return a prefixed dollar sign
     */
    @Test
    public void testGetMoneyStringwithZeroBigDecimal() {
        BigDecimal amount = new BigDecimal(0);
        RentalHelper instance = new RentalHelper();
        String expResult = "$0.00";
        String result = instance.getMoneyString(amount);
        assertEquals(expResult, result);
    }
}
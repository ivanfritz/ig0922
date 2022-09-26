package toolrental;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ivang
 */
public class AppTest {

    /**
     * Asserts we get an exception if discount is over 100.
     *  PDF: Test 1
     */
    @Test
    public void testDiscountOverLimit() {

        String expectedError = "Discount of '101' is not allowed. It must be between 0 and 100 inclusive";
        String actualError = "";
        try {
            RentalDetails rental = new RentalDetails("JAKR", "9/3/15", 5, 101);
            rental.checkoutRental();
            fail("Did not throw exception for discount over 100%");
        } catch (IllegalArgumentException ex) {
            actualError = ex.getMessage().strip();
        }
        assertEquals(expectedError, actualError);
    }

    /**
     * Asserts we get expected rental values if rental occurs during 4th of July
     * Holiday will be observed on 3rd(Friday) and we charge for Sat and Sunday
     * We charge for weekdays and weekends. Just no charge if it's an observed holiday
     *  PDF: Test 2
     */
    @Test
    public void testNoHolidayChargeLadder() {
        HashMap expected = new HashMap<>();
        expected.put("code", "LADW");
        expected.put("type", "Ladder");
        expected.put("brand", "Werner");
        expected.put("rentalDays", "3");
        expected.put("checkoutDate", "7/2/20");
        expected.put("dueDate", "7/5/20");
        expected.put("dailyCharge", "$1.99");
        expected.put("billableDays", "2");
        expected.put("preDiscountCharge", "$3.98");
        expected.put("discountPercent", "10%");
        expected.put("totalDiscount", "$0.40");
        expected.put("finalCharge", "$3.58");

        RentalDetails rental = new RentalDetails("LADW", "7/2/20", 3, 10);
        rental.checkoutRental();
        assertEquals(expected, rental.checkedOutTool);
    }

    /**
     * Asserts we charge correctly for a chainsaw if we have weekdays, weekend, and a holiday
     * 4th of July lands on Sat so observed Friday
     * We charge for all days except weekends.
     *  PDF: Test 3
     */
    @Test
    public void testNoChargeChainsawWeekendsOnly() {
        HashMap expected = new HashMap<>();
        expected.put("code", "CHNS");
        expected.put("type", "Chainsaw");
        expected.put("brand", "Stihl");
        expected.put("rentalDays", "5");
        expected.put("checkoutDate", "7/2/15");
        expected.put("dueDate", "7/7/15");
        expected.put("dailyCharge", "$1.49");
        expected.put("billableDays", "3");
        expected.put("preDiscountCharge", "$4.47");
        expected.put("discountPercent", "25%");
        expected.put("totalDiscount", "$1.12");
        expected.put("finalCharge", "$3.35");

        RentalDetails rental = new RentalDetails("CHNS", "7/2/15", 5, 25);
        rental.checkoutRental();
        assertEquals(expected, rental.checkedOutTool);
    }

    /**
     * Asserts we get expected rental values if we have a jackhammer rental
     * with weekdays, weekend, and a holiday
     * We should only charge for weekdays that aren't holidays
     *  PDF: Test 4
     */
    @Test
    public void testRentJackHammerOnlyChargeWeekdays() {
        HashMap expected = new HashMap<>();
        expected.put("code", "JAKD");
        expected.put("type", "Jackhammer");
        expected.put("brand", "DeWalt");
        expected.put("rentalDays", "6");
        expected.put("checkoutDate", "9/3/15");
        expected.put("dueDate", "9/9/15");
        expected.put("dailyCharge", "$2.99");
        expected.put("billableDays", "3");
        expected.put("preDiscountCharge", "$8.97");
        expected.put("discountPercent", "0%");
        expected.put("totalDiscount", "$0.00");
        expected.put("finalCharge", "$8.97");

        RentalDetails rental = new RentalDetails("JAKD", "9/3/15", 6, 0);
        rental.checkoutRental();
        assertEquals(expected, rental.checkedOutTool);
    }

    /**
     * Asserts we get expected rental values if we have a jackhammer rental
     * with weekdays, weekend, and a holiday
     * 4th of July is observed on 3rd of July (Friday)
     * We will only bill for weekdays and should not bill for 1 weekday as it is an observed holiday
     *  PDF: Test 5
     */
    @Test
    public void testRentJackhammerHolidayTwoWeekends() {
        HashMap expected = new HashMap<>();
        expected.put("code", "JAKR");
        expected.put("type", "Jackhammer");
        expected.put("brand", "Ridgid");
        expected.put("rentalDays", "9");
        expected.put("checkoutDate", "7/2/15");
        expected.put("dueDate", "7/11/15");
        expected.put("dailyCharge", "$2.99");
        expected.put("billableDays", "5");
        expected.put("preDiscountCharge", "$14.95");
        expected.put("discountPercent", "0%");
        expected.put("totalDiscount", "$0.00");
        expected.put("finalCharge", "$14.95");

        RentalDetails rental = new RentalDetails("JAKR", "7/2/15", 9, 0);
        rental.checkoutRental();
        assertEquals(expected, rental.checkedOutTool);
    }

    /**
     * Asserts we get expected rental values if we have a jackhammer rental
     * with weekdays, weekend, and a holiday
     * 4th of July is observed on 3rd of July (Friday)
     * We should only bill for weekdays and should not bill for 1 weekday as it is an observed holiday.
     * Also validates discount is calculated and applied correctly.
     *  PDF: Test 6
     */
    @Test
    public void testRentJackhammerOneWeekendWithDiscount() {
        HashMap expected = new HashMap<>();
        expected.put("code", "JAKR");
        expected.put("type", "Jackhammer");
        expected.put("brand", "Ridgid");
        expected.put("rentalDays", "4");
        expected.put("checkoutDate", "7/2/20");
        expected.put("dueDate", "7/6/20");
        expected.put("dailyCharge", "$2.99");
        expected.put("billableDays", "1");
        expected.put("preDiscountCharge", "$2.99");
        expected.put("discountPercent", "50%");
        expected.put("totalDiscount", "$1.50");
        expected.put("finalCharge", "$1.50");

        RentalDetails rental = new RentalDetails("JAKR", "7/2/20", 4, 50);
        rental.checkoutRental();
        assertEquals(expected, rental.checkedOutTool);
    }
}
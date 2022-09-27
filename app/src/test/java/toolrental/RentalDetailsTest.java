package toolrental;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ivang
 */
public class RentalDetailsTest {

    /**
     * Asserts we get an error if we pass in an unknown tool code
     */
    @Test
    public void testInvalidToolCode() {

        String expectedError = "Encountered an unknown tool code, please correct your entry or update the available tool codes";
        String actualError = "";
        try {
            RentalDetails rental = new RentalDetails("fake", "9/3/15", 5, 0);
            rental.checkoutRental();
            fail("Did not throw exception for discount over 100%");
        } catch (IllegalArgumentException ex) {
            actualError = ex.getMessage().strip();
        }
        assertEquals(expectedError, actualError);
    }

    /**
     * Asserts case does not matter when comparing tool codes
     */
    @Test
    public void testCanProcessToolCodeCaseInsensitive() {

        String expectedToolCode = "CHNS";
        RentalDetails rental = new RentalDetails("cHns", "9/3/15", 5, 0);
        rental.checkoutRental();
        assertEquals(expectedToolCode, rental.checkedOutTool.get("code"));
    }

    /**
     * Asserts we remove beginning and trailing spaces from tool code
     */
    @Test
    public void testCanProcessToolCodeLeadingEndingSpace() {

        String expectedToolCode = "CHNS";
        RentalDetails rental = new RentalDetails(" CHNS ", "9/3/15", 5, 0);
        rental.checkoutRental();
        assertEquals(expectedToolCode, rental.checkedOutTool.get("code"));
    }

    /**
     * Asserts we get an error if we pass 0 for rental days.
     */
    @Test
    public void testZeroRentalDays() {

        String expectedError = "Rental days of '0' is unallowed. It must be > 0";
        String actualError = "";
        try {
            RentalDetails rental = new RentalDetails("JAKR", "9/3/15", 0, 25);
            rental.checkoutRental();
            fail("Did not throw exception for discount over 100%");
        } catch (IllegalArgumentException ex) {
            actualError = ex.getMessage().strip();
        }
        assertEquals(expectedError, actualError, "reee");
    }

    /**
     * Asserts we get an error if we pass a negative number for rental days.
     */
    @Test
    public void testNegativeRentalDays() {

        String expectedError = "Rental days of '-3' is unallowed. It must be > 0";
        String actualError = "";
        try {
            RentalDetails rental = new RentalDetails("JAKR", "9/3/15", -3, 25);
            rental.checkoutRental();
            fail("Did not throw exception for discount over 100%");
        } catch (IllegalArgumentException ex) {
            actualError = ex.getMessage().strip();
        }
        assertEquals(expectedError, actualError, "reee");
    }

    /**
     * Asserts we get an error if we pass in a negative discount.
     */
    @Test
    public void testNegativeDiscount() {

        String expectedError = "Discount of '-26' is not allowed. It must be between 0 and 100 inclusive";
        String actualError = "";
        try {
            RentalDetails rental = new RentalDetails("JAKR", "9/3/15", 5, -26);
            rental.checkoutRental();
            fail("Did not throw exception for discount over 100%");
        } catch (IllegalArgumentException ex) {
            actualError = ex.getMessage().strip();
        }
        assertEquals(expectedError, actualError, "reee");
    }

    /**
     * Asserts we get an error if checkout date is an empty string
     */
    @Test
    public void testEmptyCheckoutDate() {

        String expectedError = "Got an unexpected checkout date of '' please use dates in format of mm/dd/yy";
        String actualError = "";
        try {
            RentalDetails rental = new RentalDetails("JAKR", "", 5, 0);
            rental.checkoutRental();
            fail("Did not throw exception for checkout date being an empty string");
        } catch (IllegalArgumentException ex) {
            actualError = ex.getMessage().strip();
        }
        assertEquals(expectedError, actualError, "reee");
    }

    /**
     * Asserts we get an error if checkout date passed in is not formatted
     * correctly
     */
    @Test
    public void testInvalidCheckoutDateFormat() {

        String expectedError = "Got an unexpected checkout date of '9-25-22' please use dates in format of mm/dd/yy";
        String actualError = "";
        try {
            RentalDetails rental = new RentalDetails("JAKR", "9-25-22", 5, 0);
            rental.checkoutRental();
            fail("Did not throw exception for checkout date being an empty string");
        } catch (IllegalArgumentException ex) {
            actualError = ex.getMessage().strip();
        }
        assertEquals(expectedError, actualError, "reee");
    }

}

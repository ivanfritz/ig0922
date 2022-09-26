package toolrental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

/**
 *
 * @author ivang
 */
public class RentalDetails {

    String toolCode;
    LocalDate checkoutDate;
    LocalDate dueDate;
    int rentalDays;
    int discount;
    BigDecimal preDiscountCharge;
    BigDecimal totalDiscount;
    BigDecimal finalCharge;
    int billableDays;
    Tool rentedTool;
    DateTimeFormatter rentalDateFormat;
    RentalHelper rentalHelper;
    HashMap<String,String> checkedOutTool = new HashMap<>();

    public RentalDetails(String toolCode, String checkout, int rentalDays, int discount) {
        this.rentalDays = rentalDays;
        this.toolCode = toolCode;
        this.discount = discount;

        this.rentalHelper = new RentalHelper();
        this.rentalDateFormat = rentalHelper.getRentalDateFormat();
        this.rentedTool = new Tool(this.toolCode);

        validateRentalDetails(checkout);
        processRental();
    }

    private void validateRentalDetails(String checkout) {
        //Make sure discount is valid
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount of '" + discount + "' is not allowed. It must be between 0 and 100 inclusive");
        }

        if (rentalDays <= 0) {
            throw new IllegalArgumentException("Rental days of '" + rentalDays + "' is unallowed. It must be > 0");
        }

        //Make sure passed in a valid rental date
        try {
            checkoutDate = LocalDate.parse(checkout, rentalDateFormat);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Got an unexpected checkout date of '" + checkout + "' please use dates in format of M/d/yy");
        }
    }
    
    private void processRental() {
        billableDays = rentalHelper.getBillableDays(checkoutDate, rentalDays, rentedTool.isChargedWeekdays(), rentedTool.isChargedWeekends(), rentedTool.isChargedHolidays());
        dueDate = rentalHelper.getDueDate();
        preDiscountCharge = rentalHelper.getPrediscountCharge(rentedTool.getDailyCharge(), billableDays);
        totalDiscount =  rentalHelper.getTotalDiscount(preDiscountCharge, discount);
        finalCharge = preDiscountCharge.subtract(totalDiscount);
    }

    public void checkoutRental() {
        //Storing a value in a map for now so we can assert the exact value we output to user
        checkedOutTool.put("code", rentedTool.getToolCode());
        checkedOutTool.put("type", rentedTool.getToolType());
        checkedOutTool.put("brand", rentedTool.getToolBrand());
        checkedOutTool.put("rentalDays", String.valueOf(rentalDays));
        checkedOutTool.put("checkoutDate", rentalHelper.formatOutputDate(checkoutDate));
        checkedOutTool.put("dueDate", rentalHelper.formatOutputDate(dueDate));
        checkedOutTool.put("dailyCharge", rentalHelper.getMoneyString(rentedTool.getDailyCharge()));
        checkedOutTool.put("billableDays", String.valueOf(billableDays));
        checkedOutTool.put("preDiscountCharge", rentalHelper.getMoneyString(preDiscountCharge));
        checkedOutTool.put("discountPercent", discount + "%");
        checkedOutTool.put("totalDiscount", rentalHelper.getMoneyString(totalDiscount));
        checkedOutTool.put("finalCharge", rentalHelper.getMoneyString(finalCharge));

        System.out.println("Tool Code: " + checkedOutTool.get("code"));
        System.out.println("Tool Type: " + checkedOutTool.get("type"));
        System.out.println("Tool Brand: " + checkedOutTool.get("brand"));
        System.out.println("Rental Days: " + checkedOutTool.get("rentalDays"));
        System.out.println("Checkout Date: " + checkedOutTool.get("checkoutDate"));
        System.out.println("Due Date: " + checkedOutTool.get("dueDate"));
        System.out.println("Daily Rental Charge: " + checkedOutTool.get("dailyCharge"));
        System.out.println("Charge Days: " + checkedOutTool.get("billableDays"));
        System.out.println("Pre-discount Charge: " + checkedOutTool.get("preDiscountCharge"));
        System.out.println("Discount Percent: " + checkedOutTool.get("discountPercent"));
        System.out.println("Discount Amount: " + checkedOutTool.get("totalDiscount"));
        System.out.println("Final Charge: " + checkedOutTool.get("finalCharge"));
    }
}

package toolrental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ivang
 */
public class RentalHelper {

    private final HolidayHelper holidayUtil = new HolidayHelper();
    private LocalDate dueDate;
    private final DateTimeFormatter rentalDateFormat = DateTimeFormatter.ofPattern("M/d/yy");

    private boolean isDateWeekend(LocalDate checkDate) {
        switch (checkDate.getDayOfWeek().toString()) {
            case "SATURDAY":
                return true;
            case "SUNDAY":
                return true;
            default:
                return false;
        }
    }

    //Calcuate the number of billable rental days. According to specs
    //we do not charge for the initial checkout date.
    public int getBillableDays(LocalDate startDate, int rentalDays, boolean chargeWeekdays, boolean chargeWeekends, boolean chargeHolidays) {
        int billableDays = 0;

        //Piggybacking to avoid having to set dueData again
        dueDate = startDate.plusDays(rentalDays);

        //According to specs we do not include the intiial checkout date as one of our billable days
        //So increment startDate by 1 day so next day is our initial billable date.
        startDate = startDate.plusDays(1);

        for (LocalDate iterDate = startDate; iterDate.isBefore(dueDate.plusDays(1)); iterDate = iterDate.plusDays(1)) {
            if (!chargeWeekdays && !isDateWeekend(iterDate)) {
                continue;
            } else if (!chargeWeekends && isDateWeekend(iterDate)) {
                continue;
            } else if (!chargeHolidays && holidayUtil.isDateHoliday(iterDate)) {
                continue;
            }

            billableDays++;
        }

        return billableDays;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getPrediscountCharge(String costPerDay, int billableDays) {
        BigDecimal cost = new BigDecimal(costPerDay);
        return cost.multiply(new BigDecimal(billableDays));
    }

    public BigDecimal getTotalDiscount(BigDecimal totalCharge, int discount) {
        if (discount == 0) {
            return new BigDecimal(0);
        }

        BigDecimal resultant = totalCharge.multiply(new BigDecimal(discount)).divide(new BigDecimal(100));
        return resultant;
    }

    public String getMoneyString(String amount) {
        try {
            Double.valueOf(amount);
        } catch (NumberFormatException ex) {
            amount = "0.00";
        }
        return "$" + amount;
    }

    public String getMoneyString(BigDecimal amount) {
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        return "$" + amount.toPlainString();
    }

    public DateTimeFormatter getRentalDateFormat() {
        return rentalDateFormat;
    }

    public String formatOutputDate(LocalDate date) {
        return date.format(rentalDateFormat);
    }
}

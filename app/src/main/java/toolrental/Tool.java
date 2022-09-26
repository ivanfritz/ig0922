package toolrental;

/**
 *
 * @author ivang
 */
public class Tool {

    private final String toolCode;
    private String toolType;
    private String toolBrand;
    private boolean chargeHolidays;
    private boolean chargeWeekends;
    private boolean chargeWeekdays;
    private String dailyCharge;

    public Tool(String toolCode) {
        this.toolCode = toolCode.toUpperCase().strip();
        setToolType();
        setToolCharges();
    }
    
    private void setToolType() {
        switch (this.toolCode) {
            case "CHNS":
                this.toolType = "Chainsaw";
                this.toolBrand = "Stihl";
                break;
            case "LADW":
                this.toolType = "Ladder";
                this.toolBrand = "Werner";
                break;
            case "JAKD":
                this.toolType = "Jackhammer";
                this.toolBrand = "DeWalt";
                break;
            case "JAKR":
                this.toolType = "Jackhammer";
                this.toolBrand = "Ridgid";
                break;
            default:
                throw new IllegalArgumentException("Encountered an unknown tool code, please correct your entry or update the available tool codes");
        }
    }

    //Note: dailyCharge will be stored as a whole number. We will divide by 100
    //before presenting result to avoid rounding issues and to make calc easier.
    //Ex: 199/100 = $1.99
    private void setToolCharges() {
        switch (this.toolType.toUpperCase()) {
            case "LADDER":
                this.chargeWeekdays = true;
                this.chargeWeekends = true;
                this.chargeHolidays = false;
                this.dailyCharge = "1.99";
                break;
            case "CHAINSAW":
                this.chargeWeekdays = true;
                this.chargeWeekends = false;
                this.chargeHolidays = true;
                this.dailyCharge = "1.49";
                break;
            case "JACKHAMMER":
                this.chargeWeekdays = true;
                this.chargeWeekends = false;
                this.chargeHolidays = false;
                this.dailyCharge = "2.99";
                break;
            default:
                //Should not reach this 
                throw new IllegalArgumentException("Encountered an unknown tool, please correct your entry or update available tool types");
        }
    }

    public boolean isChargedHolidays() {
        return chargeHolidays;
    }

    public boolean isChargedWeekends() {
        return chargeWeekends;
    }

    public boolean isChargedWeekdays() {
        return chargeWeekdays;
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public String getDailyCharge() {
        return dailyCharge;
    }

}
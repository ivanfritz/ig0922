package toolrental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) throws IOException {
        String toolCode, checkoutDate;
        int rentalDays, discount;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter tool code:");
        toolCode = br.readLine().strip();
        
        System.out.println("Enter Checkout Date as M/d/yy:");
        checkoutDate = br.readLine().strip();
        
        try {
            System.out.println("Enter the number of rental days:");
            rentalDays = Integer.parseInt(br.readLine());

            System.out.println("Enter the discount percent (number only):");
            discount = Integer.parseInt(br.readLine());
        } catch(NumberFormatException ex) {
            throw new IllegalArgumentException("I expected a valid whole number but got an unexpected value instead" );
        }
        RentalDetails a = new RentalDetails(toolCode, checkoutDate, rentalDays, discount);
        a.checkoutRental();
    }
}

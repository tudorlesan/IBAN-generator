
import util.BBANFormatException;
import util.CountryCodes;
import util.IBANGenerator;
import util.InvalidCountryCodeException;

public class Solution {

    public static void main(String[] args){


        IBANGenerator generator = IBANGenerator.getInstance();

        try {
            System.out.println(generator.generateIBAN(CountryCodes.GERMANY.code()));
            System.out.println(generator.generateIBAN(CountryCodes.AUSTRIA.code()));
            System.out.println(generator.generateIBAN(CountryCodes.NETHERLANDS.code()));
        } catch (InvalidCountryCodeException | BBANFormatException e) {
            e.getMessage();
        }
    }
}

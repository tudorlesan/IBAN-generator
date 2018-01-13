package util;

import java.util.HashMap;
import java.util.Map;


public class IBANValidator {

    private static final int COUNTRY_CODE_LENGTH = 2;
    private static final int CHECK_DIGITS_LENGTH = 2;
    private Map<String, String> bbanFormats = new HashMap<>();

    public IBANValidator() {
        initializeBBANs();
    }

    private void initializeBBANs() {
        bbanFormats.put(CountryCodes.GERMANY.code(), "18 n");
        bbanFormats.put(CountryCodes.AUSTRIA.code(), "16 n");
        bbanFormats.put(CountryCodes.NETHERLANDS.code(), "4 a,10 n");
    }

    public boolean validateIBAN(String iban) throws BBANFormatException {
        String countryCode = iban.substring(0, 2);

        if (!validateCountryCode(countryCode)) {
            return false;
        }

        //check length
        int expectedLength = 0;
        String[] groups = bbanFormats.get(countryCode).split(",");
        for (String group : groups) {

            try {
                expectedLength += Integer.valueOf(group.split(" ")[0]);
            } catch (NumberFormatException ex) {
                throw new BBANFormatException();
            }
        }

        return iban.length() == expectedLength + COUNTRY_CODE_LENGTH + CHECK_DIGITS_LENGTH;
    }

    public boolean validateCountryCode(String countryCode) {
        return CountryCodes.contains(countryCode);
    }


    public String getBBANFormat(String countryCode) {
        return bbanFormats.get(countryCode);
    }
}

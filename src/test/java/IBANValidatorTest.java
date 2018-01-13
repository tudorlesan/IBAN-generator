import org.junit.jupiter.api.Test;
import util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IBANValidatorTest {
    public static final String RANDOM_IBAN_1 = "DE89370400440532013000";
    public static final String RANDOM_INVALID_IBAN_1 = "DE8937040044053201300";

    @Test
    public void testValidateValidGermanyIBAN() throws BBANFormatException, InvalidCountryCodeException {
        IBANValidator validator = new IBANValidator();
        assertEquals(true, validator.validateIBAN(RANDOM_IBAN_1));
    }
    @Test
    public void testValidateInvalidGermanyIBAN() throws BBANFormatException, InvalidCountryCodeException {
        IBANValidator validator = new IBANValidator();
        assertEquals(false, validator.validateIBAN(RANDOM_INVALID_IBAN_1));
    }
}

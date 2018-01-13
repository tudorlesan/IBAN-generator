import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import util.BBANFormatException;
import util.CountryCodes;
import util.IBANGenerator;
import util.InvalidCountryCodeException;


public class IBANGeneratorTest {

    static final int GERMANY_IBAN_LENGTH = 22;
    static final int AUSTRIA_IBAN_LENGTH = 20;
    static final int NETHERLANDS_IBAN_LENGTH = 18;

    @Test
    public void testGenerateValidGermanyIBAN() throws BBANFormatException, InvalidCountryCodeException {
        IBANGenerator generator = IBANGenerator.getInstance();
        assertEquals(GERMANY_IBAN_LENGTH, generator.generateIBAN(CountryCodes.GERMANY.code()).length());
    }
    @Test
    public void testGenerateValidAustriaBAN() throws BBANFormatException, InvalidCountryCodeException {
        IBANGenerator generator = IBANGenerator.getInstance();
        assertEquals(AUSTRIA_IBAN_LENGTH, generator.generateIBAN(CountryCodes.AUSTRIA.code()).length());
    }
    @Test
    public void testGenerateValidNetherlandsIBAN() throws BBANFormatException, InvalidCountryCodeException {
        IBANGenerator generator = IBANGenerator.getInstance();
        assertEquals(NETHERLANDS_IBAN_LENGTH, generator.generateIBAN(CountryCodes.NETHERLANDS.code()).length());
    }
    @Test
    public void testGenerateInvalidCountryCode() throws BBANFormatException, InvalidCountryCodeException {
        IBANGenerator generator = IBANGenerator.getInstance();
        assertThrows(InvalidCountryCodeException.class, ()-> {generator.generateIBAN("DD");});
    }


}

import org.junit.jupiter.api.Test;
import repository.IBANStorer;
import repository.InMemoryIBANStorer;
import util.BBANFormatException;
import util.InvalidCountryCodeException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IBANStorerTest {

    public static final String RANDOM_IBAN_1 = "DE89370400440532013000";
    public static final String RANDOM_IBAN_2 = "DE89370400440532013001";

    @Test
    public void testSaveUniqueIBAN() throws BBANFormatException, InvalidCountryCodeException {
        IBANStorer store = new InMemoryIBANStorer();
        assertEquals(true, store.saveIBAN(RANDOM_IBAN_1));
        assertEquals(true, store.saveIBAN(RANDOM_IBAN_2));
    }
    @Test
    public void testSaveDuplicateIBAN() throws BBANFormatException, InvalidCountryCodeException {
        IBANStorer store = new InMemoryIBANStorer();
        assertEquals(true, store.saveIBAN(RANDOM_IBAN_1));
        assertEquals(false, store.saveIBAN(RANDOM_IBAN_1));
    }
    @Test
    public void testContainsIBAN() throws BBANFormatException, InvalidCountryCodeException {
        IBANStorer store = new InMemoryIBANStorer();
        store.saveIBAN(RANDOM_IBAN_1);
        assertEquals(true, store.containsIBAN(RANDOM_IBAN_1));
    }

}

package repository;

import java.util.HashSet;
import java.util.Set;

public class InMemoryIBANStorer implements IBANStorer {
    private Set<String> ibanSet;

    public InMemoryIBANStorer() {
        this.ibanSet = new HashSet<>();
    }

    public boolean saveIBAN(String IBAN) {
        if (ibanSet.contains(IBAN))
            return false;
        ibanSet.add(IBAN);
        return true;
    }

    public boolean containsIBAN(String IBAN) {
        return ibanSet.contains(IBAN);
    }
}

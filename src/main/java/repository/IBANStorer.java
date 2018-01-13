package repository;

public interface IBANStorer {
    boolean saveIBAN(String IBAN);
    boolean containsIBAN(String IBAN);
}

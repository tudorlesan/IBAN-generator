package util;

import repository.IBANStorer;
import repository.InMemoryIBANStorer;

import java.util.Random;

public class IBANGenerator {
    private static final String NUMBERS = "0123456789";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    //private static final String COMBINED = "abcdefghijklmnopqrstuwxyz" + ALPHANUMERIC;     NOT USED FOR AUSTRIA, GERMANY, NETHERLANDS

    private static IBANGenerator instance = new IBANGenerator();

    private IBANStorer ibanStorer;
    private IBANValidator ibanValidator;

    private IBANGenerator() {
        this.ibanStorer = new InMemoryIBANStorer();
        this.ibanValidator = new IBANValidator();
    }

    public static IBANGenerator getInstance() {
        return instance;
    }

    //public void setIbanStorer(IBANStorer ibanStorer) {  //in case we want to change the implementation of the storer
    //    this.ibanStorer = ibanStorer;
    //}


    public synchronized String generateIBAN(String countryCode) throws InvalidCountryCodeException, BBANFormatException {

        if (!ibanValidator.validateCountryCode(countryCode)) {
            throw new InvalidCountryCodeException();
        }

        String bbanFormat = ibanValidator.getBBANFormat(countryCode);
        String newBBAN = generateNewBBAN(bbanFormat);
        String checkDigits = generateCheckDigits(countryCode, newBBAN);

        String IBAN = countryCode + checkDigits + newBBAN;

        //obtain vaild IBAN
        while (!ibanValidator.validateIBAN(IBAN) || !ibanStorer.saveIBAN(IBAN)) {

            newBBAN = generateNewBBAN(bbanFormat);
            checkDigits = generateCheckDigits(countryCode, newBBAN);
            IBAN = countryCode + checkDigits + newBBAN;

        }

        return IBAN;
    }

    private String generateCheckDigits(String countryCode, String newBBAN) {  //based on algorithm explained on Wikipedia.
        String IBAN = newBBAN + countryCode + "00";

        char[] IBANChars = IBAN.toUpperCase().toCharArray();
        char[] result = new char[IBAN.length() * 2];

        int i = 0; //iterates IBAN char array
        int j = 0; //iterates result array
        while (IBANChars[i] == '0') { //remove trailing zeroes
            ++i;
        }

        for (; i < IBANChars.length; ++i) {
            if (Character.isLetter(IBANChars[i])) { //expand chars into code
                Integer charToInt = IBANChars[i] - 'A' + 10; // A->10, B->11..Z->35
                result[j] = ("" + (charToInt / 10)).charAt(0); //(char) (charToInt/10) would obtain unicode chars

                ++j;
                result[j] = ("" + (charToInt / 10)).charAt(0);
            } else {
                result[j] = IBANChars[i];
            }
            ++j;
        }


        String resultString = String.valueOf(result).substring(0, j); //remove unwanted characters

        //perform piece-wise modulo operation
        StringBuilder checkDigits = pieceWiseModulo(resultString);

        if (checkDigits.toString().length() == 1) { //make sure it has 2 digits
            return "0" + checkDigits;
        }
        return checkDigits.toString();
    }

    private StringBuilder pieceWiseModulo(String resultString) {

        StringBuilder modulo = new StringBuilder(String.valueOf(Long.valueOf(resultString.substring(0, 9)) % 97)); //first group
        int i = 9;
        int j = 16; //index of second group

        while (resultString.substring(i).length() >= 7) {
            modulo.append(resultString.substring(i, j));
            modulo = new StringBuilder(String.valueOf(Long.valueOf(modulo.toString()) % 97));
            if (modulo.length() == 1) {
                modulo.insert(0, 1);
            }
            i += 7;
            j += 7;
        }
        modulo.append(resultString.substring(i));
        modulo = new StringBuilder(String.valueOf(Long.valueOf(modulo.toString()) % 97));
        return modulo;
    }

    private String generateNewBBAN(String bbanFormat) throws BBANFormatException {

        String[] groups = bbanFormat.split(",");
        StringBuilder result = new StringBuilder();

        for (String group : groups) {
            int length;
            char characterSetCode;

            try {
                length = Integer.valueOf(group.split(" ")[0]);
                characterSetCode = group.split(" ")[1].charAt(0);
            } catch (NumberFormatException ex) {
                throw new BBANFormatException();
            }

            if (characterSetCode == 'n') {
                result.append(generateRandomString(length, NUMBERS));
            } else if (characterSetCode == 'a') {
                result.append(generateRandomString(length, ALPHANUMERIC));
            } //else if (characterSetCode == 'c'){

            //}
            else {
                throw new BBANFormatException();
            }

        }
        return result.toString();

    }

    private String generateRandomString(int length, String possibleCharacters) {

        StringBuilder randomStringBuilder = new StringBuilder();
        Random rnd = new Random();
        while (randomStringBuilder.length() < length) {
            int index = (int) (rnd.nextFloat() * possibleCharacters.length());
            randomStringBuilder.append(possibleCharacters.charAt(index));
        }

        return randomStringBuilder.toString();

    }
}

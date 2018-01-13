package util;

public enum CountryCodes {
    GERMANY("DE"),
    AUSTRIA("AT"),
    NETHERLANDS("NL");

    private String code;

    CountryCodes(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static boolean contains(String countryCode) {

        for (CountryCodes c : CountryCodes.values()) {
            if (c.code().equals(countryCode)) {
                return true;
            }
        }

        return false;
    }
}

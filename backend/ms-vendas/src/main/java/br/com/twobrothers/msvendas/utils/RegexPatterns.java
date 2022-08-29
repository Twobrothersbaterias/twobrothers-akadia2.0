package br.com.twobrothers.msvendas.utils;

public class RegexPatterns {

    RegexPatterns() {}

    public static final String QUANTIDADE_REGEX = "[1-9][0-9]{0,2}";

    public static final String DATE_REGEX =
            "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]" +
                    "|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579]" +
                    "[26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(" +
                    "?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String EMAIL_REGEX_PATTERN =
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)" +
                    "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    public static final String POSTAL_CODE_REGEX_PATTERN = "[0-9]{5}-[0-9]{3}";
    public static final String STREET_NUMBER_REGEX_PATTERN = "[0-9]{1,5}";
    public static final String CPF_REGEX_PATTERN = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}";
    public static final String CNPJ_REGEX_PATTERN = "[0-9]{2}.[0-9]{3}.[0-9]{3}/000[1-9]-[0-9]{2}";
    public static final String PHONE_REGEX_PATTERN = "^\\([1-9]{2}\\)[9]{0,1}[6-9]{1}[0-9]{3}\\-[0-9]{4}$";

}

package me.senior.coding.desafio.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ReserveUtils {
    private static final Pattern CPF_PATTERN = Pattern.compile("^[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9]{2}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\(?\\d{2}\\)?[\\s-]?[\\s9]?\\d{4}-?\\d{4}$");

    public static Boolean isCpf(String cpf){
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }
        Matcher matcher = CPF_PATTERN.matcher(cpf);
        return matcher.matches();
    }
    public static Boolean isPhone(String phone){
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }
    public static String returnCpfMask(String cpf){
        cpf = cpf.replace(".", "").replace("-", "");
        return String.format("%s.%s.%s-%s", cpf.substring(0, 3), cpf.substring(3, 6),
                cpf.substring(6, 9), cpf.substring(9));
    }

}

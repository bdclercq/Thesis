package net.democritus.valuetype.basic;

import java.math.BigInteger;

public class IBANValidation {

  public static class IBANResponse {
    private String errorMessage;

    public static IBANResponse error(String errorMessage) {
      return new IBANResponse(errorMessage);
    }

    public static IBANResponse success() {
      return new IBANResponse(null);
    }

    private IBANResponse(String errorMessage) {
      this.errorMessage = errorMessage;
    }

    public boolean isValid() {
      return errorMessage == null;
    }

    public String getErrorMessage() {
      if (errorMessage == null) {
        return "no error";
      }
      return errorMessage;
    }
  }

  public static final BigInteger DIVIDER = new BigInteger("97");

  public static IBANResponse validateIBAN(String in) {
    String noSpaces = stripSpaces(in);
    if (noSpaces.length() <= 4) {
      return IBANResponse.error("IBAN Number too short: " +in);
    }

    if (!hasValidIBANCharacters(noSpaces)) {
      return IBANResponse.error("IBAN Number contains invalid charcters: " + in);
    }

    String countryWithCheck = noSpaces.substring(0, 4);
    String number = noSpaces.substring(4);

    String rearranged = number + countryWithCheck;

    String digits = toDigits(rearranged);
    BigInteger bigInteger = new BigInteger(digits);
    BigInteger remainder = bigInteger.mod(DIVIDER);

    if (remainder.equals(BigInteger.ONE)) {
      return IBANResponse.success();
    } else {
      return IBANResponse.error("Checksum is not correct");
    }
  }

  public static boolean hasValidIBANCharacters(String noSpaces) {
    for (char c : noSpaces.toCharArray()) {
      if (!(isAsciiLetter(c) || Character.isDigit(c))) {
        return false;
      }
    }
    return true;
  }

  public static String stripSpaces(String withSpaces) {
    return withSpaces.replaceAll(" ", "");
  }

  public static String toDigits(String in) {
    StringBuilder builder = new StringBuilder();
    for (char c : in.toCharArray()) {
      if (Character.isDigit(c)) {
        builder.append(c);
      } else if (isAsciiLetter(c)){
        builder.append(charToDigits(c));
      }
    }
    return builder.toString();
  }

  public static char[] charToDigits(char c) {
    int offset = Character.toLowerCase((c)) - 'a' + 10;
    return new char[] {(char) ('0' + offset / 10), (char) ('0' + offset % 10)};
  }

  public static boolean isAsciiLetter(char c) {
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
  }
}

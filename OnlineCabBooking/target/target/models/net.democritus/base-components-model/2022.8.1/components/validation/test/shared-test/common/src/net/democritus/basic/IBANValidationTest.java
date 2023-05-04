package net.democritus.basic;

import net.democritus.valuetype.basic.IBANValidation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Based on algorithm on wikipedia: https://en.wikipedia.org/wiki/International_Bank_Account_Number
 */
public class IBANValidationTest {
  public static final String SAMPLE_IBAN = "GB82 WEST 1234 5698 7654 32";

  @Test
  public void stripSpaces() {
    String in = SAMPLE_IBAN;

    String out = IBANValidation.stripSpaces(in);

    assertEquals("GB82WEST12345698765432", out);
  }

  @Test
  public void hasValidIBANCharacters() throws Exception {
    assertTrue(IBANValidation.hasValidIBANCharacters("1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));
    assertFalse(IBANValidation.hasValidIBANCharacters("\t"));
    assertFalse(IBANValidation.hasValidIBANCharacters(" "));
  }

  @Test
  public void isValidIBAN() {
    assertTrue(IBANValidation.validateIBAN(SAMPLE_IBAN).isValid());
    assertTrue(IBANValidation.validateIBAN(SAMPLE_IBAN.toLowerCase()).isValid());
  }

  @Test
  public void checkErrorMessages() {
    assertEquals("IBAN Number too short: GB82", IBANValidation.validateIBAN("GB82").getErrorMessage());
    assertEquals("Checksum is not correct", IBANValidation.validateIBAN(SAMPLE_IBAN + '1').getErrorMessage());
    assertEquals("IBAN Number contains invalid charcters: 1234@", IBANValidation.validateIBAN("1234@").getErrorMessage());
  }

  @Test
  public void charToDigits() {
    assertEquals("10", new String(IBANValidation.charToDigits('a')));
    assertEquals("10", new String (IBANValidation.charToDigits('A')));
    assertEquals("35", new String(IBANValidation.charToDigits('z')));
    assertEquals("35", new String (IBANValidation.charToDigits('Z')));
  }
}
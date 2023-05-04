package net.democritus.basic;

import static org.junit.Assert.assertTrue;

import net.democritus.valuetype.basic.RijksRegisterNummer;
import net.democritus.valuetype.basic.RijksRegisterNummerValidator;
import org.junit.Test;

public class RijksRegisterNummerValidatorTest {

	@Test
	public void testValidateErrorNonNumeric() {
		RijksRegisterNummerValidator rrnValidator = new RijksRegisterNummerValidator();
		RijksRegisterNummer rrn = new RijksRegisterNummer("A2020290081");
		assertTrue(rrnValidator.validate(rrn).isError());
	}

	@Test
	public void testValidateErrorIncorrectNumberOfDigits() {
		RijksRegisterNummerValidator rrnValidator = new RijksRegisterNummerValidator();
		RijksRegisterNummer rrn = new RijksRegisterNummer("212020290081");
		assertTrue(rrnValidator.validate(rrn).isError());
	}

	@Test
	public void testValidateCorrect() {
		RijksRegisterNummerValidator rrnValidator = new RijksRegisterNummerValidator();

		// empty rrn
		RijksRegisterNummer rrn = new RijksRegisterNummer("");
		assertTrue(rrnValidator.validate(rrn).isValid());

		// rrn before 2000
		rrn = new RijksRegisterNummer("72020290081");
		assertTrue(rrnValidator.validate(rrn).isValid());

		// rrn after 2000
		rrn = new RijksRegisterNummer("00012556777");
		assertTrue(rrnValidator.validate(rrn).isValid());
	}

	@Test
	public void testValidateErrorInDigit() {
		RijksRegisterNummerValidator rrnValidator = new RijksRegisterNummerValidator();
		RijksRegisterNummer rrn = new RijksRegisterNummer("72010290081");
		assertTrue(rrnValidator.validate(rrn).isError());
	}

	@Test
	public void testValidate_null() {
		RijksRegisterNummerValidator rrnValidator = new RijksRegisterNummerValidator();
		assertTrue(rrnValidator.validate(null).isError());
	}

	@Test
	public void testValidate_empty() {
		RijksRegisterNummerValidator rrnValidator = new RijksRegisterNummerValidator();
		RijksRegisterNummer rrn = new RijksRegisterNummer(null);
		assertTrue(rrnValidator.validate(rrn).isValid());
	}
}

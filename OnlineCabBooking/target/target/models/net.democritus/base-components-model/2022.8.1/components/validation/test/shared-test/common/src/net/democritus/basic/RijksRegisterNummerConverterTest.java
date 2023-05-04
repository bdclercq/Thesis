package net.democritus.basic;

import static org.junit.Assert.assertEquals;

import net.democritus.valuetype.basic.RijksRegisterNummer;
import net.democritus.valuetype.basic.RijksRegisterNummerConverter;
import org.junit.Test;

public class RijksRegisterNummerConverterTest {

	@Test
	public void test_fromString_success() {
		RijksRegisterNummerConverter rrnConverter = new RijksRegisterNummerConverter();

		assertEquals(new RijksRegisterNummer(""), rrnConverter.fromString("").getValue());
		assertEquals(new RijksRegisterNummer("72020290081"), rrnConverter.fromString("72020290081").getValue());
		assertEquals(new RijksRegisterNummer("00012556777"), rrnConverter.fromString("00012556777").getValue());

	}

	@Test
	public void test_fromString_null() {
		RijksRegisterNummerConverter rrnConverter = new RijksRegisterNummerConverter();
		assertEquals(new RijksRegisterNummer(""), rrnConverter.fromString(null).getValue());
	}

}

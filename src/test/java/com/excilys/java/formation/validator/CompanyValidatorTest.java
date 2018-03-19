package com.excilys.java.formation.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class CompanyValidatorTest {

	@Test
	void GetLongIdTest() throws ValidatorException {
		CompanyValidator validator = CompanyValidator.INSTANCE;
		assertNull(validator.getLongId(""));
		assertNull(validator.getLongId("Null"));
		assertNull(validator.getLongId("null"));
		assertNull(validator.getLongId("NULL"));
		Executable getId = () -> {validator.getLongId("t");};
		assertThrows(ValidatorException.class, getId);
		getId = () -> {validator.getLongId("10.");};
		assertThrows(ValidatorException.class, getId);
		assertEquals(validator.getLongId("1"), new Long(1L));
		assertEquals(validator.getLongId("100"), new Long(100L));
	}


}

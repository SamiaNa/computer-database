package com.excilys.java.formation.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.excilys.java.formation.model.service.CompanyValidator;
import com.excilys.java.formation.model.service.ValidatorException;

public class CompanyValidatorTest {

	@Test
	void testGetLongId() throws ValidatorException {
		CompanyValidator validator = CompanyValidator.getValidator();
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

	@Test 
	void testCheckCompanyId() throws ValidatorException {
		CompanyValidator validator = CompanyValidator.getValidator();
		Executable checkId = () -> {validator.checkCompanyId("");};
		assertThrows(ValidatorException.class, checkId);
		checkId = () -> {validator.checkCompanyId("-2");};
		assertThrows(ValidatorException.class, checkId);		
	}
	
	@Test
	void testCheckCompanyIdOrNull() throws ValidatorException, ClassNotFoundException, SQLException {
		CompanyValidator validator = CompanyValidator.getValidator();
		Executable checkId = () -> {validator.checkCompanyId("");};
		assertThrows(ValidatorException.class, checkId);
		checkId = () -> {validator.checkCompanyId("-2");};
		assertThrows(ValidatorException.class, checkId);
		assertNull(validator.checkCompanyIdOrNull(""));
		assertNull(validator.checkCompanyIdOrNull("Null"));
	}
}

package com.excilys.java.formation.test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.sql.SQLException;
import java.time.LocalDate;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.Validator;
import com.excilys.java.formation.validator.ValidatorException;

class ComputerValidatorTest {

	@Test
	void testCheckName() throws ValidatorException  {
		ComputerValidator validator = ComputerValidator.INSTANCE;
	  
		Executable checkName = () -> {validator.checkName("");};
		assertThrows(ValidatorException.class, checkName);
	    
		checkName = () -> {validator.checkName("null");};
		assertThrows(ValidatorException.class, checkName);
	    
		checkName = () -> {validator.checkName("NULL");};
		assertThrows(ValidatorException.class, checkName);
		
		validator.checkName("name");
		validator.checkName("computer");
	}
	
	@Test
	void testGetDate() throws ValidatorException, ParseException {
		ComputerValidator validator = ComputerValidator.INSTANCE;
		assertNull(validator.getDate(""));
		assertNull(validator.getDate("Null"));
		assertNull(validator.getDate("NULL"));
		assertNull(validator.getDate("null"));
	
		Executable getDate = () -> {validator.getDate("2001");};
		assertThrows(ValidatorException.class, getDate);
	
		getDate = () -> {validator.getDate("abcd20");};
	    assertThrows(ValidatorException.class, getDate);
	    
	    getDate = () -> {validator.getDate("01/02/2010");};
	    assertThrows(ValidatorException.class, getDate);
	    
	    getDate = () -> {validator.getDate("01-10-2010");};
	    assertThrows(ValidatorException.class, getDate);
	    
	    getDate = () -> {validator.getDate("1900-30-10");};
	    assertThrows(ValidatorException.class, getDate);
	    
	    getDate = () -> {validator.getDate("1900-10-60");};
	    assertThrows(ValidatorException.class, getDate);
	}
	
	@Test
	void testCheckDates () throws ValidatorException {
		ComputerValidator validator = ComputerValidator.INSTANCE;
		LocalDate d1 = null;
		LocalDate d2 = null;
		validator.checkDates(d1, d2);
		d1 = LocalDate.parse("2010-12-30");
		validator.checkDates(d1, d2);
		validator.checkDates(d2, d1);
		d2 = LocalDate.parse("2015-01-13");
		validator.checkDates(d1, d2);
		LocalDate d3 = LocalDate.parse("2015-02-12");
		LocalDate d4 = LocalDate.parse("2012-12-25");
		Executable checkDate = () -> {validator.checkDates(d3, d4);};
		assertThrows(ValidatorException.class, checkDate);
	}
	
	@Test 
	void getLongPrimId() throws ValidatorException {
		assertEquals(Validator.getLongPrimId("1"), 1L);
		assertEquals(Validator.getLongPrimId("200"), 200L);
		Executable getId = () -> {Validator.getLongPrimId("");};
		assertThrows(ValidatorException.class, getId);
		getId = () -> {Validator.getLongPrimId("null");};
		assertThrows(ValidatorException.class, getId);
		getId = () -> {Validator.getLongPrimId("abc");};
		assertThrows(ValidatorException.class, getId);
		getId = () -> {Validator.getLongPrimId("10.1");};
		assertThrows(ValidatorException.class, getId);
		
	}
	
	@Test
	void testCheckComputerId() throws ValidatorException, ClassNotFoundException, SQLException {
		ComputerValidator validator = ComputerValidator.INSTANCE;
		Executable checkId = () -> {validator.checkComputerId("");};
		assertThrows(ValidatorException.class, checkId);
		checkId = () -> {validator.checkComputerId("10a");};
		assertThrows(ValidatorException.class, checkId);
		checkId = () -> {validator.checkComputerId("0.1");};
		assertThrows(ValidatorException.class, checkId);
	}
	

}

package com.excilys.java.formation.test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import com.excilys.java.formation.model.service.ComputerValidator;
import com.excilys.java.formation.model.service.ValidatorException;

class ComputerValidatorTest {

	@Test
	void testCheckName() throws ValidatorException  {
		ComputerValidator validator = ComputerValidator.getValidator();
	  
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
		ComputerValidator validator = ComputerValidator.getValidator();
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
		ComputerValidator validator = ComputerValidator.getValidator();
		Date d1 = null;
		Date d2 = null;
		validator.checkDates(d1, d2);
		d1 = Date.valueOf("2010-12-30");
		validator.checkDates(d1, d2);
		validator.checkDates(d2, d1);
		d2 = Date.valueOf("2015-01-13");
		validator.checkDates(d1, d2);
		Date d3 = Date.valueOf("2015-02-12");
		Date d4 = Date.valueOf("2012-12-25");
		Executable checkDate = () -> {validator.checkDates(d3, d4);};
		assertThrows(ValidatorException.class, checkDate);
	}
	
	@Test 
	void getLongPrimId() throws ValidatorException {
		ComputerValidator validator = ComputerValidator.getValidator();
		assertEquals(validator.getLongPrimId("1"), 1L);
		assertEquals(validator.getLongPrimId("200"), 200L);
		Executable getId = () -> {validator.getLongPrimId("");};
		assertThrows(ValidatorException.class, getId);
		getId = () -> {validator.getLongPrimId("null");};
		assertThrows(ValidatorException.class, getId);
		getId = () -> {validator.getLongPrimId("abc");};
		assertThrows(ValidatorException.class, getId);
		getId = () -> {validator.getLongPrimId("10.1");};
		assertThrows(ValidatorException.class, getId);
		
	}
	
	@Test
	void testCheckComputerId() throws ValidatorException, ClassNotFoundException, SQLException {
		ComputerValidator validator = ComputerValidator.getValidator();
		Executable checkId = () -> {validator.checkComputerId("");};
		assertThrows(ValidatorException.class, checkId);
		checkId = () -> {validator.checkComputerId("10a");};
		assertThrows(ValidatorException.class, checkId);
		checkId = () -> {validator.checkComputerId("0.1");};
		assertThrows(ValidatorException.class, checkId);
	}
	

}

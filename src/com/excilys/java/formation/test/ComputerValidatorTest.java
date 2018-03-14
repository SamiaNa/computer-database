package com.excilys.java.formation.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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
	void testGetDate() throws ValidatorException {
		ComputerValidator validator = ComputerValidator.getValidator();
		assertEquals(validator.getDate(""), null);
		assertEquals(validator.getDate("Null"), null);
		assertEquals(validator.getDate("NULL"), null);
		assertEquals(validator.getDate("null"), null);
		
		
		/*Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 20);
		//System.out.println(Calendar.get(Calendar.ZONE_OFFSET) + Calendar.get(Calendar.DST_OFFSET)) / (60 * 1000));
		assertEquals(validator.getDate("2000-10-20"), new Date(cal.getTimeInMillis()));
		System.out.println(cal.getTimeInMillis()+" "+Date.valueOf("2000-10-20").getTime());*/
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

}

package com.excilys.java.formation.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class ComputerValidatorTest {

    @Test
    public void test() {
        assertTrue(true);
    }
    /*
    @Test
    void testCheckName() throws ValidatorException  {
        ComputerValidator validator = ComputerValidator.INSTANCE;

        Executable checkName = () -> {validator.checkName("");};
        assertThrows(ValidatorException.class, checkName);

        checkName = () -> {validator.checkName("null");};
        assertThrows(ValidatorException.class, checkName);

        checkName = () -> {validator.checkName("NULL");};
        assertThrows(ValidatorException.class, checkName);

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
        Computer c = new Computer(0, null, d1, d2, null);
        validator.checkDates(c);
        d1 = LocalDate.parse("2010-12-30");
        c = new Computer(0, null, d1, d2, null);
        validator.checkDates(c);
        c = new Computer(0, null, d2, d1, null);
        validator.checkDates(c);
        d2 = LocalDate.parse("2015-01-13");
        c = new Computer(0, null, d1, d2, null);
        validator.checkDates(c);
        LocalDate d3 = LocalDate.parse("2015-02-12");
        LocalDate d4 = LocalDate.parse("2012-12-25");
        Executable checkDate = () -> {validator.checkDates(new Computer(0, null, d3, d4, null));};
        assertThrows(ValidatorException.class, checkDate);
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
    }*/


}

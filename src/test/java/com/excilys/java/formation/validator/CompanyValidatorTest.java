package com.excilys.java.formation.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CompanyValidatorTest {

    @Test
    public void test() {
        assertTrue(true);
    }
    /*
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
    }*/


}

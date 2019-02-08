package edu.ncsu.csc.itrust.unit.model.childbirth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;
import edu.ncsu.csc.itrust.model.childbirth.Childbirth;
import edu.ncsu.csc.itrust.model.childbirth.ChildbirthMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

public class ChildbirthMySQLTest {
	ChildbirthMySQL sql;
	Childbirth cb;
	ChildInChildbirth cbc;
	ArrayList<ChildInChildbirth> children;
     @Before
     public void setUp() throws DBException, FileNotFoundException, IOException, SQLException {
    	 TestDataGenerator gen = new TestDataGenerator();
    	 gen.patient1();
    	 sql = new ChildbirthMySQL(ConverterDAO.getDataSource());
    	 cb = new Childbirth();
    	 
    	cbc = new ChildInChildbirth();
    	cbc.setGender("Male");
    	cbc.setDate(LocalDateTime.now());
    	cb.setDate(LocalDateTime.now());
    	cb.setMethod("Vaginal Delivery");
    	children = new ArrayList<ChildInChildbirth>();
    	children.add(cbc);
    	cb.setChildbirthchildren(children);
    	
    	
     }
     
     @Test
     public void testAddError() {
    	 try { //Method should throw exception
 			sql.add(cb);
 			Assert.fail();
 		} catch (Exception e) {
 			//yay
 		} 
     }
     
     @Test
     public void setChildbirthChildren() {
    	 cb.setChildbirthchildren(children);
    	 Assert.assertTrue(children.equals(cb.getChildbirthchildren()));
     }
     
     @Test
     public void testAddReturnGeneratedId() throws DBException {
    	 long id = sql.addReturnGeneratedId(cbc);
    	 Assert.assertTrue(id >= 0);
     }
     
     @Test
     public void testAddPrevPreg() {
    	 try {
			sql.addPreviousPreg(cb, 1L);
		} catch (DBException e) {
			Assert.fail();
		}
     }
}

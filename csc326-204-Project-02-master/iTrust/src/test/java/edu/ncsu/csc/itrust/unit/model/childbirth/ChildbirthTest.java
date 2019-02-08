package edu.ncsu.csc.itrust.unit.model.childbirth;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;
import edu.ncsu.csc.itrust.model.childbirth.Childbirth;

public class ChildbirthTest {
	Childbirth c;
	LocalDateTime time;
    @Before
    public void setUp() {
    	c = new Childbirth();
    	ArrayList<ChildInChildbirth> children = new ArrayList<ChildInChildbirth>();
    	ChildInChildbirth child = new ChildInChildbirth();
    	child.setApproximate(false);
    	time = LocalDateTime.now();
    	child.setDate(time);
    	child.setChildID(14L);
    	child.setGender("Male");
    	child.setMid(1534L);
    	children = new ArrayList<ChildInChildbirth>();
    	children.add(child);
    	c.setChildbirthchildren(children);
    	c.setEpiduralAnaesthesia(1.2);
    	c.setHoursInLabor(2);
    	c.setMagnesiumSulfate(3.4);
    	c.setMethod("Vaginal Delivery");
    	c.setNitrousOxide(0.7);
    	c.setPatientMID(1L);
    	c.setPethidine(7.8);
    	c.setPitocin(1.3);
    	c.setRhimmuneglobulin(8.1);
    	c.setDate(time);
    	c.setVisitID(1L);
    	c.setChildrenIDs("1534");

    }
    
    @Test
    public void testGetters() {
    	Assert.assertTrue(c.getEpiduralAnaesthesia().equals(1.2));
    	Assert.assertTrue(c.getChildrenIDs().equals("1534"));
    	Assert.assertTrue(c.getMethod().equals("Vaginal Delivery"));
    	Assert.assertTrue(c.getDate().equals(time));
    	Assert.assertTrue(c.getHoursInLabor().equals(2));
    	Assert.assertTrue(c.getMagnesiumSulfate().equals(3.4));
    	Assert.assertTrue(c.getNitrousOxide().equals(0.7));
    	Assert.assertTrue(c.getPethidine().equals(7.8));
    	Assert.assertTrue(c.getPatientMID().equals(1L));
    	Assert.assertTrue(c.getPitocin().equals(1.3));
    	Assert.assertTrue(c.getRhimmuneglobulin().equals(8.1));
    	Assert.assertTrue(c.getVisitID().equals(1L));
    	ArrayList<ChildInChildbirth> cic = c.getChildbirthchildren();
    	ChildInChildbirth child = cic.get(0);
    	Assert.assertTrue(child.getGender().equals("Male"));
    	Assert.assertEquals(child.getChildID(), 14L);
    	Assert.assertTrue(child.getDate().equals(time));
    	Assert.assertEquals(child.getMid(), 1534L);
    	Assert.assertFalse(child.isApproximate());
    }
}

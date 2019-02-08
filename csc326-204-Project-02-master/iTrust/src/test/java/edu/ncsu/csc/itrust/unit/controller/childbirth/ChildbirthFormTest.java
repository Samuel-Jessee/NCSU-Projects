package edu.ncsu.csc.itrust.unit.controller.childbirth;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.controller.childbirth.ChildbirthForm;
import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;

public class ChildbirthFormTest {
    private ChildbirthForm form;
    private ArrayList<ChildInChildbirth> children;
    private LocalDateTime time;
    @Before
    public void setUp() {
    	form = new ChildbirthForm();
    	form.setChildrenMIDs("1534");
    	ChildInChildbirth child = new ChildInChildbirth();
    	child.setApproximate(false);
    	time = LocalDateTime.now();
    	child.setDate(time);
    	child.setChildID(14L);
    	child.setGender("Male");
    	child.setMid(1534L);
    	children = new ArrayList<ChildInChildbirth>();
    	children.add(child);
    	form.setChildren(children);
    	form.setEpiduralAnaesthesia(1.2);
    	form.setHoursInLabor(2);
    	form.setMagnesiumSulfate(3.4);
    	form.setMethod("Vaginal Delivery");
    	form.setNitrousOxide(0.7);
    	form.setPatientMID(1L);
    	form.setPethidine(7.8);
    	form.setPitocin(1.3);
    	form.setPrefMethod("Vaginal Delivery");
    	form.setRhimmuneglobulin(8.1);
    	form.setDate(time);
    	form.setVisitID(1L);

    }
    
    @Test
    public void testGetters(){
    	Assert.assertTrue(form.getEpiduralAnaesthesia().equals(1.2));
    	Assert.assertTrue(form.getChildrenMIDs().equals("1534"));
    	Assert.assertTrue(form.getMethod().equals("Vaginal Delivery"));
    	Assert.assertTrue(form.getPrefMethod().equals("Vaginal Delivery"));
    	Assert.assertTrue(form.getDate().equals(time));
    	Assert.assertTrue(form.getHoursInLabor().equals(2));
    	Assert.assertTrue(form.getMagnesiumSulfate().equals(3.4));
    	Assert.assertTrue(form.getNitrousOxide().equals(0.7));
    	Assert.assertTrue(form.getPethidine().equals(7.8));
    	Assert.assertTrue(form.getPatientMID().equals(1L));
    	Assert.assertTrue(form.getPitocin().equals(1.3));
    	Assert.assertTrue(form.getRhimmuneglobulin().equals(8.1));
    	Assert.assertTrue(form.getVisitID().equals(1L));
    	ArrayList<ChildInChildbirth> cic = form.getChildren();
    	ChildInChildbirth child = cic.get(0);
    	Assert.assertTrue(child.getGender().equals("Male"));
    	Assert.assertEquals(child.getChildID(), 14L);
    	Assert.assertTrue(child.getDate().equals(time));
    	Assert.assertEquals(child.getMid(), 1534L);
    	Assert.assertFalse(child.isApproximate());

    }
    @Test
    public void testAddChild() {
    	form.addChild();
    	children = form.getChildren();
    	Assert.assertNotNull(children.get(1));
    }
    
    @Test
    public void testInit() {
    	
    	form.init();
    	try {
    	    form.submit();
    	} catch (NullPointerException e) {
    		//nothing
    	}
    }
}

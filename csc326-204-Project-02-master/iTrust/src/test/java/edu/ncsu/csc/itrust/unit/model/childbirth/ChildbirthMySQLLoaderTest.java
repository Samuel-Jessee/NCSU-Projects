package edu.ncsu.csc.itrust.unit.model.childbirth;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;

import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.childbirth.ChildInChildbirth;
import edu.ncsu.csc.itrust.model.childbirth.Childbirth;
import edu.ncsu.csc.itrust.model.childbirth.ChildbirthSQLLoader;

public class ChildbirthMySQLLoaderTest {
    @Test
    public void testChildbirthMySQLLoader() throws SQLException {
    	ChildbirthSQLLoader loader = new ChildbirthSQLLoader();
    	DataSource ds = ConverterDAO.getDataSource();
    	Connection conn = ds.getConnection();
    	PreparedStatement ps = null;
    	Childbirth testCb = new Childbirth();
        testCb.setPatientMID(1L);
        testCb.setDate(LocalDateTime.now());
        testCb.setHoursInLabor(1);
        testCb.setChildrenIDs("1534");
    	ChildInChildbirth child = new ChildInChildbirth();
    	child.setApproximate(false);
    	LocalDateTime time = LocalDateTime.now();
    	child.setDate(time);
    	child.setChildID(14L);
    	child.setGender("Male");
    	child.setMid(1534L);
    	ArrayList<ChildInChildbirth> children = new ArrayList<ChildInChildbirth>();
    	children.add(child);
    	testCb.setChildbirthchildren(children);
    	testCb.setEpiduralAnaesthesia(1.2);
    	testCb.setHoursInLabor(2);
    	testCb.setMagnesiumSulfate(3.4);
    	testCb.setMethod("Vaginal Delivery");
    	testCb.setNitrousOxide(0.7);
    	testCb.setPatientMID(1L);
    	testCb.setPethidine(7.8);
    	testCb.setPitocin(1.3);
    	testCb.setRhimmuneglobulin(8.1);
    	testCb.setDate(time);
    	testCb.setVisitID(1L);
    	loader.loadParameters(conn, ps, testCb, true);
    	loader.loadParameters(conn, ps, child, false);
    }
}

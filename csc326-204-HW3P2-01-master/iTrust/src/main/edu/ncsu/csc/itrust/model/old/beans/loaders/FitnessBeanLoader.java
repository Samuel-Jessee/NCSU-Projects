package edu.ncsu.csc.itrust.model.old.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.old.beans.FitnessBean;

/**
 * This loader class is made to turn the ResultSet into usable information in
 * the form of FitnessBeans. It implements the BeanLoader interface.
 * 
 * This class was inspired by MessageBeanLoader.
 * 
 * @author Louis Le
 *
 */
public class FitnessBeanLoader implements BeanLoader<FitnessBean> {

	@Override
	public List<FitnessBean> loadList(ResultSet rs) throws SQLException {
		List<FitnessBean> list = new ArrayList<FitnessBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	@Override
	public FitnessBean loadSingle(ResultSet rs) throws SQLException {
		FitnessBean fitness = new FitnessBean();

		fitness.setId(rs.getLong("fitness_id"));
		fitness.setPatient(rs.getLong("patient_id"));
		fitness.setDate(rs.getTimestamp(("fitness_date")));
		fitness.setCalBurned(rs.getInt("calories_burned"));
		fitness.setSteps(rs.getInt("steps"));
		fitness.setDist(rs.getDouble("distance"));
		fitness.setFloors(rs.getInt("floors"));
		fitness.setMinSed(rs.getInt("minutes_sedentary"));
		fitness.setMinLight(rs.getInt("minutes_light"));
		fitness.setMinFair(rs.getInt("minutes_fair"));
		fitness.setMinVery(rs.getInt("minutes_very"));
		fitness.setActCal(rs.getInt("active_calories"));
		return fitness;
	}

	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, FitnessBean fitnessBean) throws SQLException {
		ps.setLong(1, fitnessBean.getPatient());
		ps.setTimestamp(2, fitnessBean.getDate());
		ps.setInt(3, fitnessBean.getCalBurned());
		ps.setInt(4, fitnessBean.getSteps());
		ps.setDouble(5, fitnessBean.getDist());
		ps.setInt(6, fitnessBean.getFloors());
		ps.setInt(7, fitnessBean.getMinSed());
		ps.setInt(8, fitnessBean.getMinLight());
		ps.setInt(9, fitnessBean.getMinFair());
		ps.setInt(10, fitnessBean.getMinVery());
		ps.setInt(11, fitnessBean.getActCal());

		return ps;
	}

}

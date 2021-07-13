package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.DepartmentDAO;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class DepartmentDAOJDBC implements DepartmentDAO {

	private Connection conn;

	public DepartmentDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dp) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO Department\r\n " + "(Name, Id) " + "VALUES " + "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, dp.getName());
			st.setInt(2, dp.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dp.setId(id);
				}
			} else {
				throw new DbException("Erro! Nenhuma linha alterada");
			}
		}

		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatment(st);
		}
	}

	@Override
	public void update(Department dp) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("UPDATE Department SET Name = ? WHERE Id = ?");

			st.setString(1, dp.getName());
			st.setInt(2, dp.getId());

			st.executeUpdate();
		}

		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatment(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("DELETE FROM Department WHERE Id = ?");

			st.setInt(1, id);
			st.executeUpdate();

		}

		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatment(st);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM Department WHERE Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {

				Department dep = new Department();
				dep.setName(rs.getString("Name"));
				dep.setId(rs.getInt("Id"));
				return dep;
			}

			return null;

		}

		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatment(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM Department ORDER BY Name");

			rs = st.executeQuery();

			List<Department> list = new ArrayList<>();

			while (rs.next()) {

				Department dep = new Department();

				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));
				list.add(dep);
			}

			return list;
		}

		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatment(st);
			DB.closeResultSet(rs);
		}
	}

}

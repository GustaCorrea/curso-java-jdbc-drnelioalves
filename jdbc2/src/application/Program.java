package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import db.DB;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		
		PreparedStatement ps = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			conn = DB.getConnection();
			
			System.out.println("Inserting datas in the database");
			
			ps = conn.prepareStatement(
					"insert into seller" 
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "values"
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
					);
			
			ps.setString(1, "Gustavo Correa");
			ps.setString(2, "gustavo@gmail.com");
			ps.setDate(3, new java.sql.Date(sdf.parse("18/10/2006").getTime()));
			ps.setDouble(4, 3000.0);
			ps.setInt(5, 4);
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0 ) {
				ResultSet rs = ps.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done, Id = " + id);
				}
			}else {
				System.out.println("No row affected.");
			}

			
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ParseException e) {
		    e.printStackTrace();
		}finally {
			DB.closePreparedStatement(ps);
			DB.closeConection();	
		}
		
	}

}

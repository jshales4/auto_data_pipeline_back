package com.autolocations.auto_location;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {
	
	private static Connection getConnection()
			throws URISyntaxException, SQLException {
		String dbUrl = System.getenv("HEROKU_POSTGRESQL_BROWN_JDBC_URL");
		return DriverManager.getConnection(dbUrl);
	}

public static boolean adduser(AppUser u) throws URISyntaxException, SQLException {
	try (Connection conn = getConnection();
			PreparedStatement pstmt_1 = conn.prepareStatement(
					"Select count(*) as count from users where email = ?;");
			PreparedStatement pstmt_2 = conn.prepareStatement(
					"Insert into users(email, password_hash) values (?, ?);"))

	{pstmt_1.setString(1, u.getEmail());
		ResultSet rs = pstmt_1.executeQuery();
		rs.next();
		if (rs.getInt("count") >= 1) {
			return false; }
			else {pstmt_2.setString(1,u.getEmail());
			pstmt_2.setString(2, u.getPassword_hash());
				rs = pstmt_2.executeQuery();
				return true;
		}

	}

}
}

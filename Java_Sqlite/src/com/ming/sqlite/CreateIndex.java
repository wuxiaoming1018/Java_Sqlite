package com.ming.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ming.util.SqliteUtil;

public class CreateIndex {
	private String sql1 = "create index lac_ci_index on bs_ydlt_dx4g(lac,ci)";
	private String sql2 = "create index mnc_lac_ci_index on bs_dx3g(mnc,lac,ci)";
	private Connection connection;
	private PreparedStatement mPreparedStatement1,mPreparedStatement2;
	public CreateIndex() {
		connection = SqliteUtil.getConnection();
		try {
			connection.setAutoCommit(false);
			mPreparedStatement1 = connection.prepareStatement(sql1);
			mPreparedStatement2 = connection.prepareStatement(sql2);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void index(){		
		try {
			mPreparedStatement1.executeBatch();
			mPreparedStatement2.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

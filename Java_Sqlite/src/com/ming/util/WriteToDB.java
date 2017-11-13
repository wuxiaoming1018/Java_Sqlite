package com.ming.util;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class WriteToDB {

	public static void write(Connection connection,
			PreparedStatement mPreparedStatement, String[] spliteStrings,
			long count, long index) {
		if (spliteStrings == null || spliteStrings.length == 0
				|| spliteStrings[0].contains("mcc")) {
			return;
		}
		try {
			mPreparedStatement.setInt(1, Integer.parseInt(spliteStrings[0]));
			mPreparedStatement.setInt(2, Integer.parseInt(spliteStrings[1]));
			mPreparedStatement.setInt(3, Integer.parseInt(spliteStrings[2]));
			mPreparedStatement.setInt(4, Integer.parseInt(spliteStrings[3]));
			mPreparedStatement.setFloat(5, Float.parseFloat(spliteStrings[4]));
			mPreparedStatement.setFloat(6, Float.parseFloat(spliteStrings[5]));
			mPreparedStatement.setInt(7, Integer.parseInt(spliteStrings[6]));
			mPreparedStatement.setInt(8, Integer.parseInt(spliteStrings[7]));
			mPreparedStatement.setString(9, delete(spliteStrings[9]));
			mPreparedStatement.setString(10, delete(spliteStrings[10]));
			mPreparedStatement.setString(11, delete(spliteStrings[11]));
			mPreparedStatement.setString(12, delete(spliteStrings[12]));
			mPreparedStatement.setString(13, delete(spliteStrings[13]));
			mPreparedStatement.addBatch();
			connection.commit();
			if (count % 10000 == 0) {// 读取一万次插入一次
				mPreparedStatement.executeBatch();
				connection.commit();
				mPreparedStatement.clearBatch();
			}
			if (index % 1000 == 0) {
				mPreparedStatement.executeBatch();
				connection.commit();
				mPreparedStatement.clearBatch();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static String delete(String result) {
		if (result == null || result.isEmpty()) {
			result = "��";
		}
		if (result.contains(";")) {
			result = result.replace(";", "");
		}
		if (result.equals("[]")) {
			result = "��";
		}
		return result;

	}

}

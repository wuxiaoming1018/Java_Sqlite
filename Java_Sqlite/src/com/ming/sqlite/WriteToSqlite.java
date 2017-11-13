package com.ming.sqlite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ming.util.SqliteUtil;
import com.ming.util.WriteToDB;

public class WriteToSqlite {

	private PreparedStatement mPreparedStatement1,mPreparedStatement2;
	private long count = 1, index=1, html=1;
	private Connection connection;
	private String sql1 = "insert into bs_dx3g values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String sql2 = "insert into bs_ydlt_dx4g values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public WriteToSqlite() {
		connection = SqliteUtil.getConnection();
		try {
			connection.setAutoCommit(false);
			mPreparedStatement1 = connection.prepareStatement(sql1);
			mPreparedStatement2 = connection.prepareStatement(sql2);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void readData(String filePath) {
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader reader = new InputStreamReader(
						new FileInputStream(file), "UTF-8");
				BufferedReader buReader = new BufferedReader(reader);
				String lineTextString = null;
				String[] spliteStrings;
				long startTime = System.currentTimeMillis();
				buReader.readLine();//去掉第一行
				int code;
				while ((lineTextString = buReader.readLine()) != null) {
					spliteStrings = lineTextString.split("\\s+");
					code = Integer.parseInt(spliteStrings[1]);
					if(code==0||code==1||code==11){
						WriteToDB.write(connection, mPreparedStatement2, spliteStrings, count,index);
						count++;
					}else{
						WriteToDB.write(connection, mPreparedStatement1, spliteStrings, count,index);
						index++;
					}
					System.out.println(html + "---" + lineTextString);
					html++;
				}
				reader.close();
				buReader.close();
				if(mPreparedStatement1!=null){
					mPreparedStatement1.executeBatch();
				}
				if(mPreparedStatement2!=null){
					mPreparedStatement2.executeBatch();
				}
				connection.commit();
				SqliteUtil.close(connection, mPreparedStatement1, null);
				SqliteUtil.close(connection, mPreparedStatement2, null);
				long endTime = System.currentTimeMillis();
				long minute = (endTime - startTime) / 1000 / 60;
				long second = (endTime - startTime) / 1000 % 60;
				System.out.println("读取文件总耗时:" + minute + "分" + second + "秒");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

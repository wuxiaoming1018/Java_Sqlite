package com.ming.sqlite;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.csvreader.CsvReader;
import com.ming.util.SqliteUtil;
import com.ming.util.WriteToDB;

public class OpenCSVFile {
	
	private PreparedStatement mPreparedStatement1,mPreparedStatement2;
	private long count = 1,index=1,html=1;
	private Connection connection;
	private String sql1 = "insert into bs_dx3g values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String sql2 = "insert into bs_ydlt_dx4g values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	
	public OpenCSVFile(){
		connection = SqliteUtil.getConnection();
		try {
			connection.setAutoCommit(false);
			mPreparedStatement1 = connection.prepareStatement(sql1);
			mPreparedStatement2 = connection.prepareStatement(sql2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void read(String filePath) {
		File file = new File(filePath);
		String[] results;
		if (file.isFile() && file.exists()) {
			try {
				CsvReader reader = new CsvReader(filePath, ',',
						Charset.forName("utf-8"));
				reader.readHeaders();
				long startTime = System.currentTimeMillis();
				int code;
				while (reader.readRecord()) {
					results = reader.getRawRecord().split(",");
					code = Integer.parseInt(results[1]);
					if(code==0||code==1||code==11){
						WriteToDB.write(connection, mPreparedStatement2, results, count,index);
						count++;
					}else{
						WriteToDB.write(connection, mPreparedStatement1, results, count,index);
						index++;
					}					
					System.out.println(html + "---" + reader.getRawRecord());
					html++;
				}
				reader.close();
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
				System.out.println("读取文件总耗时:" + minute + "分," + second + "秒");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println(filePath + "文件路径不存在");
		}
	}
}

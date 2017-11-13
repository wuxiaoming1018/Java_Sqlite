package com.ming.sqlite;



public class OpenSqliteDB {
			
	public static void main(String[] args) {
//		txtToSqlite();
//		csvToSqlite();
		CreateIndex index = new CreateIndex();
		index.index();
	}

	private static void csvToSqlite() {
		OpenCSVFile openCSVFile = new OpenCSVFile();
		openCSVFile.read("D:\\123.csv");
	}

	private static void txtToSqlite() {
		WriteToSqlite writeToSqlite = new WriteToSqlite();
		writeToSqlite.readData("D:\\aaa.txt");
	}
	
}

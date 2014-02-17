package com.bonsai.DBF;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The main class that supplies iterators and appenders. 
 * @author galisha
 * @since 1.00
 */
public class DbfEngine {

	private DbfEngine() {

	}

	public static DbfIterator getReader(String dbfFileName, String enc) {
		File dbfFile = new File(dbfFileName);
		return getReader(dbfFile, enc);
	}

	public static DbfIterator getReader(String path, String dbfFileName,
			String enc) {
		File dbfFile = new File(path, dbfFileName);
		return getReader(dbfFile, enc);
	}

	public static DbfIterator getReader(File dbfFile, String enc) {
		DbfIterator dbfIterator = new DbfIterator(dbfFile, enc);
		return dbfIterator;
	}

	public static DbfIterator getReader(InputStream dbfStream, String enc) {
		DbfIterator dbfIterator = new DbfIterator(dbfStream, enc);
		return dbfIterator;
	}

	public static DbfAppender getWriter(String path, String dbfFileName,
			DbfCodePages dbfCodePage) {
		File dbfFile = new File(path, dbfFileName);
		return getWriter(dbfFile, dbfCodePage);
	}

	public static DbfAppender getWriter(String dbfFileName,
			DbfCodePages dbfCodePage) {
		File dbfFile = new File(dbfFileName);
		return getWriter(dbfFile, dbfCodePage);
	}

	public static DbfAppender getWriter(File dbfFile, DbfCodePages dbfCodePage) {
		if (dbfFile.exists()) {
			throw new DbfEngineException(DbfConstants.EXCP_DBF_EXISTS);
		}
		DbfAppender dbfAppender = new DbfAppender(dbfFile, dbfCodePage);
		return dbfAppender;
	}

	public static DbfAppender getWriter(OutputStream dbfStream,
			DbfCodePages dbfCodePage) {
		DbfAppender dbfAppender = new DbfAppender(dbfStream, dbfCodePage);
		return dbfAppender;
	}

}

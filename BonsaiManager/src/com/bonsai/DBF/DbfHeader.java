package com.bonsai.DBF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains dbf header definition. 
 * @author galisha
 * @since 1.00
 */
public class DbfHeader {

	private int currentOffset = 1;
	private int firstRecordPosition = 0;
	private int countRecords = 0;
	private int typeDbf = -1;
	private int lengthRecord = 0;

	private Map<String, DbfColumn> columns = new HashMap<String, DbfColumn>();
	private String codePage = null;
	private String defaultCodePage = DbfConstants.DEFAULT_CODE_PAGE;
	private InputStream dbfStream = null;
	private List<DbfColumn> orderedColumns = new ArrayList<DbfColumn>();

	protected DbfHeader(File dbfFile, String enc) {
		// Open for reading
		codePageHandling(enc);
		parseDbfHeader(dbfFile);
	}

	protected DbfHeader(InputStream is, String enc) {
		// Open for reading
		dbfStream = is;
		codePageHandling(enc);
		parseDbfHeader();
	}

	protected DbfHeader() {
		// Create for append
	}

	private void codePageHandling(String enc) {
		if (enc != null) {
			defaultCodePage = enc;
		}
	}

	protected List<DbfColumn> getOrderedColumnList() {
		return orderedColumns;
	}

	protected void addColumn(DbfColumn dbfColumn) {
		if (columns.get(dbfColumn.getColumnName()) != null) {
			throw new DbfEngineException(DbfConstants.EXCP_COLUMN_EXISTED
					+ dbfColumn.getColumnName());
		}
		columns.put(dbfColumn.getColumnName(), dbfColumn);
		orderedColumns.add(dbfColumn);
	}

	protected DbfColumn getColumn(String name) {
		return columns.get(name);
	}

	private void parseDbfHeader(File dbf) {
		try {
			dbfStream = new FileInputStream(dbf);
			parseDbfHeader();
		} catch (FileNotFoundException e) {
			throw new DbfEngineException(DbfConstants.EXCP_IO_ERROR, e);
		}
	}

	private void parseDbfHeader() {
		try {
			byte[] hdr = new byte[32];
			int cnt = dbfStream.read(hdr);
			if (cnt != DbfConstants.DBF_HEADER_LENGTH) {
				dbfStream.close();
				throw new DbfEngineException(DbfConstants.EXCP_HEADER_INF);
			}
			typeDbf = hdr[0] & 0xff;
			// Skip Last update (YYMMDD)
			// Read count of records
			countRecords = hdr[4] & 0xff;
			countRecords += (hdr[5] & 0xff) << 8;
			countRecords += (hdr[6] & 0xff) << 16;
			countRecords += (hdr[7] & 0xff) << 24;
			// Read first record position
			firstRecordPosition = hdr[8] & 0xff;
			firstRecordPosition += (hdr[9] & 0xff) << 8;
			// Read length
			lengthRecord = hdr[10] & 0xff;
			lengthRecord += (hdr[11] & 0xff) << 8;
			// Code page mark
			int cp = hdr[29];
			// System.out.println("cp "+cp);
			if (cp == 0) {
				codePage = defaultCodePage;
			} else {
				DbfCodePages dcp = DbfCodePages.Cp1251.getByDbfCode(cp);
				if (dcp == null) {
					throw new DbfEngineException(DbfConstants.EXCP_CODE_PAGE);
				} else {
					codePage = dcp.getCharsetName();
				}
			}
			// Fields
			byte[] fld = new byte[32];
			boolean next = true;
			do {
				// cnt = dbfStream.read(fld);
				int flagByte = dbfStream.read();
				// if (cnt < 32)
				// next = false;
				// if (fld[0] == 0x0D)
				if (flagByte == 0x0D)
					next = false;
				if (next) {
					fld[0] = (byte) flagByte;
					dbfStream.read(fld, 1, 31);
					DbfColumn dbfColumn = new DbfColumn(this);
					dbfColumn.parse(fld);
					columns.put(dbfColumn.getColumnName(), dbfColumn);
					orderedColumns.add(dbfColumn);
				}
			} while (next);
		} catch (Exception e) {
			throw new DbfEngineException(DbfConstants.EXCP_IO_ERROR, e);
		}
	}

	protected int getCurrentOffset() {
		return currentOffset;
	}

	protected void setCurrentOffset(int currentOffset) {
		this.currentOffset = currentOffset;
	}

	protected int getFirstRecordPosition() {
		return firstRecordPosition;
	}

	protected void setFirstRecordPosition(int firstRecordPosition) {
		this.firstRecordPosition = firstRecordPosition;
	}

	protected int getCountRecords() {
		return countRecords;
	}

	protected void setCountRecords(int countRecords) {
		this.countRecords = countRecords;
	}

	protected String getCodePage() {
		return codePage;
	}

	protected int getTypeDbf() {
		if (typeDbf == -1) {
			// TODO check for memo field
			/*
			 * for (DbfColumn dc : orderedColumns){ }
			 */
			typeDbf = DbfType.FoxBASE_dBASE_III_PLUS_without_memo;
		}
		return typeDbf;
	}

	protected void setTypeDbf(int typeDbf) {
		this.typeDbf = typeDbf;
	}

	protected int getLengthRecord() {
		if (lengthRecord == 0) {
			for (DbfColumn dc : orderedColumns) {
				lengthRecord += dc.getDbfColumnPosition().getColumnLength();
			}
			lengthRecord++; // 1 byte for deleted flag
		}
		return lengthRecord;
	}

	protected InputStream getDbfStream() {
		return dbfStream;
	}

}

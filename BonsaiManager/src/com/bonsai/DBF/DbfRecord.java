package com.bonsai.DBF;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The class <code>DbfRecord</code> represents xBase record.
 * 
 * @author galisha
 * @since 1.00
 */
public class DbfRecord {

	private byte[] record = null;
	private DbfHeader dbfHeader = null;
	private Map<String, DbfColumnPosition> mapColumnPos = new HashMap<String, DbfColumnPosition>();
	private Calendar cal = null;
	private int currentRecord = 1;
	private static final byte EMPTY_SYMBOL_SPACE = 0x20;
	private static final byte EMPTY_SYMBOL_ZERO = 0x0;

	protected DbfRecord(byte[] record, DbfHeader dbfHeader) {
		this.record = record;
		this.dbfHeader = dbfHeader;
		cal = Calendar.getInstance();
	}

	/**
	 * Retrieves the value of the designated column in the current record of
	 * this <code>DbfRecord</code> object as a <code>String</code> in the Java programming
	 * language.
	 * 
	 * @param colName
	 *            xBase field name
	 * @return string object
	 * @since 1.00
	 */
	public String getString(String colName) {
		String ret = null;
		DbfColumnPosition dbfColumnPos = definePosition(colName, "C");
		ret = handleField(dbfColumnPos);
		if (ret != null && ret.trim().length() == 0) {
			ret = null;
		}
		return ret;
	}

	private String handleField(DbfColumnPosition dbfColumnPos) {
		String ret = null;
		int indexLast = dbfColumnPos.getOffset()
				+ dbfColumnPos.getColumnLength() - 1;
		do {
			if (record[indexLast] == EMPTY_SYMBOL_SPACE
					|| record[indexLast] == EMPTY_SYMBOL_ZERO) {
				indexLast--;
			} else {
				break;
			}
		} while (indexLast > dbfColumnPos.getOffset());

		if (indexLast >= dbfColumnPos.getOffset()) {
			try {
				ret = new String(record, dbfColumnPos.getOffset(), indexLast
						- dbfColumnPos.getOffset() + 1, dbfHeader.getCodePage());
			} catch (UnsupportedEncodingException e) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_CP);
			}
		}
		return ret;
	}

	/**
	 * Retrieves the value of the designated column in the current record of
	 * this <code>DbfRecord</code> object as a <code>int</code> in the Java programming
	 * language.
	 * 
	 * @param colName
	 *            xBase field name
	 * @return int value
	 * @since 1.00
	 */
	public int getInt(String colName) {
		int ret = 0;
		DbfColumnPosition dbfColumnPos = definePosition(colName, "N");
		String str = handleField(dbfColumnPos);
		try {
			if (str != null) {
				str = str.trim();
				if (str.length() > 0) {
					ret = Integer.parseInt(str);
				}
			}
		} catch (NumberFormatException e) {
			throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NUM + ": "
					+ str + DbfConstants.EXCP_CURR_REC_INFO+String.valueOf(this.currentRecord));
		}
		return ret;
	}

	/**
	 * Retrieves the value of the designated column in the current record of
	 * this <code>DbfRecord</code> object as a <code>short</code> in the Java programming
	 * language.
	 * 
	 * @param colName
	 *            xBase field name
	 * @return short value
	 * @since 1.00
	 */
	public short getShort(String colName) {
		short ret = 0;
		DbfColumnPosition dbfColumnPos = definePosition(colName, "N");
		String str = handleField(dbfColumnPos);
		try {
			if (str != null) {
				str = str.trim();
				if (str.length() > 0) {
					ret = Short.parseShort(str);
				}
			}
		} catch (NumberFormatException e) {
			throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NUM + ": "
					+ str + DbfConstants.EXCP_CURR_REC_INFO+String.valueOf(this.currentRecord));
		}
		return ret;
	}

	/**
	 * Retrieves the value of the designated column in the current record of
	 * this <code>DbfRecord</code> object as a <code>boolean</code> in the Java programming
	 * language.
	 * 
	 * @param colName
	 *            xBase field name
	 * @return boolean value
	 * @since 1.00
	 */
	public boolean getBoolean(String colName) {
		boolean flag = false;
		DbfColumnPosition dbfColumnPos = definePosition(colName, "L");
		if (record[dbfColumnPos.getOffset()] == DbfConstants.LOGICAL_TRUE) {
			flag = true;
		}
		return flag;
	}

	/**
	 * Retrieves the value of the designated column in the current record of
	 * this <code>DbfRecord</code> object as a <code>BigDecimal</code> in the Java programming
	 * language.
	 * 
	 * @param colName
	 *            xBase field name
	 * @return BigDecimal object
	 * @since 1.00
	 */
	public BigDecimal getBigDecimal(String colName) {
		BigDecimal bdl = null;
		DbfColumnPosition dbfColumnPos = definePosition(colName, "N");
		String str = handleField(dbfColumnPos);
		if (str != null) {
			try {
				str = str.trim();
				if (str.length() > 0) {
					bdl = new BigDecimal(str);
				}
			} catch (NumberFormatException e) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NUM
						+ ": " + str + DbfConstants.EXCP_CURR_REC_INFO+String.valueOf(this.currentRecord));
			}
		}
		return bdl;
	}

	/**
	 * Retrieves the value of the designated column in the current record of
	 * this <code>DbfRecord</code> object as a <code>float</code> in the Java programming
	 * language.
	 * 
	 * @param colName
	 *            xBase field name
	 * @return float value
	 * @since 1.00
	 */
	public float getFloat(String colName) {
		float ret = 0;
		DbfColumnPosition dbfColumnPos = definePosition(colName, "F");
		String str = handleField(dbfColumnPos);
		if (str != null) {
			try {
				str = str.trim();
				if (str.length() > 0) {
					ret = Float.parseFloat(str);
				}				
			} catch (NumberFormatException e) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NUM
						+ ": " + str + DbfConstants.EXCP_CURR_REC_INFO+String.valueOf(this.currentRecord));
			}
		}
		return ret;
	}

	/**
	 * Retrieves the value of the designated column in the current record of
	 * this <code>DbfRecord</code> object as a <code>Date</code> in the Java programming
	 * language.
	 * 
	 * @param colName
	 *            xBase field name
	 * @return Date object
	 * @since 1.00
	 */
	public Date getDate(String colName) {
		Date date = null;
		DbfColumnPosition dbfColumnPos = definePosition(colName, "D");
		String str = handleField(dbfColumnPos);
		if (str != null) {
			str = str.trim();
			if (str.length() == 0) {
				;
			}else if (str.trim().length() != 8) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_DT + ": "
						+ str + DbfConstants.EXCP_CURR_REC_INFO+String.valueOf(this.currentRecord));
			} else {
				String year = new String(record, dbfColumnPos.getOffset(), 4);
				String mm = new String(record, dbfColumnPos.getOffset() + 4, 2);
				String dd = new String(record, dbfColumnPos.getOffset() + 6, 2);
				try {
					int iYear = Integer.parseInt(year);
					int iMm = Integer.parseInt(mm);
					int iDd = Integer.parseInt(dd);
					cal.clear();
					cal.set(iYear, iMm - 1, iDd);
					date = cal.getTime();
				} catch (NumberFormatException e) {
					throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NUM
							+ ": " + str + DbfConstants.EXCP_CURR_REC_INFO+String.valueOf(this.currentRecord));
				}
			}
		}
		return date;
	}

	/**
	 * Returns <code>true</code> is dbf record marked as deleted.
	 * @return <code>true</code> is dbf record marked as deleted.
	 * @since 1.00
	 */
	public boolean isDeleted() {
		return record[0] == DbfConstants.DELETED_MARKER;
	}

	private DbfColumnPosition definePosition(String colName, String reqType) {
		DbfColumnPosition dbfColumnPos = mapColumnPos
				.get(colName.toUpperCase());
		if (dbfColumnPos == null) {
			DbfColumn dbfColumn = dbfHeader.getColumn(colName.toUpperCase());
			if (dbfColumn == null) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NOEXISTS
						+ colName);
			}

			if (!reqType.equals(dbfColumn.getOriginalType())) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_COL_TYPE
						+ colName);
			}
			dbfColumnPos = dbfColumn.getDbfColumnPosition();

			mapColumnPos.put(colName.toUpperCase(), dbfColumnPos);
		}
		return dbfColumnPos;
	}

	protected void setCurrentRecord(int currentRecord) {
		this.currentRecord = currentRecord;
	}

}

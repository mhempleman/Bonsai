package com.bonsai.DBF;

/**
 * The class <code>DbfColumn</code> is responsible for xBase field definition.
 * @author galisha
 * @since 1.00
 */
public class DbfColumn {

	private String originalType = null;
	private String columnName = null;

	private DbfHeader dbfHeader = null;
	private DbfColumnPosition dbfColumnPosition = null;
	private DbfColumnTypes dbfColumnType = null;

	protected DbfColumn(DbfHeader dbfHeader) {
		this.dbfHeader = dbfHeader;
	}

	/**
	 * Constructs a newly allocated <code>DbfColumn</code> object that represents xBase field. 
	 * @param columnName
	 * @param dbfColumnType enumeration for column type
	 * @param width column width
	 * @param dec column scale ( in other words amount point after comma )
	 * @since 1.00
	 */
	public DbfColumn(String columnName, DbfColumnTypes dbfColumnType,
			int width, int dec) {
		if (columnName == null || columnName.trim().length() == 0) {
			throw new DbfEngineException(DbfConstants.EXCP_COLUMN_EMPTY);
		}
		if (columnName.trim().length() > DbfConstants.DBF_COLUMN_NAME_LENGTH) {
			throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NAMELEN);
		}
		if (dbfColumnType == null) {
			throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NOTYPE);
		}
		this.columnName = columnName.trim().toUpperCase();
		this.dbfColumnType = dbfColumnType;

		if (dbfColumnType.compareTo(DbfColumnTypes.Date) == 0) {
			dbfColumnPosition = new DbfColumnPosition(8, 0);
		} else if (dbfColumnType.compareTo(DbfColumnTypes.Logical) == 0) {
			dbfColumnPosition = new DbfColumnPosition(1, 0);
		} else if (dbfColumnType.compareTo(DbfColumnTypes.Character) == 0) {
			if (dec > 0) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NODEC);
			}
			if (width <= 0 || width > DbfConstants.DBF_COLUMN_CHAR_MAX_LEN) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_CHARLEN);
			}
			dbfColumnPosition = new DbfColumnPosition(width, 0);
		} else if (dbfColumnType.compareTo(DbfColumnTypes.Numeric) == 0
				|| dbfColumnType.compareTo(DbfColumnTypes.Float) == 0) {
			if (width <= 0 || width > DbfConstants.DBF_COLUMN_NUM_MAX_LEN) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NUMLEN);
			}
			if (dec < 0 && (width - dec) < 2) {
				throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NUMDECLEN);
			}
			dbfColumnPosition = new DbfColumnPosition(width, dec);
		}
	}

	protected DbfColumnTypes getDbfColumnType() {
		return dbfColumnType;
	}

	protected void parse(byte[] recField) {
		originalType = new String(recField, 11, 1);
		
		int offset = 0;
		
/*		Foxpro makes bad this field after dbf modify
  		int offset = recField[12] & 0xff;
		offset += (recField[13] & 0xff) << 8;
		offset += (recField[14] & 0xff) << 16;
		offset += (recField[15] & 0xff) << 24;
*/
		int columnLength = recField[16] & 0xff;

		// 16.03.2008 workaround for such dbf where not found offset
		if (offset == 0) {
			offset = dbfHeader.getCurrentOffset();
		}
		dbfHeader.setCurrentOffset(offset + columnLength);

		int columnDotAmount = recField[17] & 0xff;
		for (int i = 0; i <= 10; i++) {
			if (recField[i] == 0) {
				columnName = new String(recField, 0, i);
				break;
			}
		}
		dbfColumnPosition = new DbfColumnPosition(offset, columnLength,
				columnDotAmount);

	}

	protected String getColumnName() {
		return columnName;
	}

	protected void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	protected DbfColumnPosition getDbfColumnPosition() {
		return dbfColumnPosition;
	}

	protected void setDbfColumnPosition(DbfColumnPosition dbfColumnPosition) {
		this.dbfColumnPosition = dbfColumnPosition;
	}

	protected String getOriginalType() {
		return originalType;
	}

}

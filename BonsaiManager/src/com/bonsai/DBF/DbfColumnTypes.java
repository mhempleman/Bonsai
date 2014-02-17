package com.bonsai.DBF;

/**
 * Enumeration of the supported field types
 * @author galisha
 * @since 1.00
 */
public enum DbfColumnTypes {
	Character("C"), Numeric("N"), Float("F"), Date("D"), Logical("L");
	// Memo

	private String columnType = null;

	DbfColumnTypes(String columnType) {
		this.columnType = columnType;
	}

	protected String getColumnType() {
		return columnType;
	}

}

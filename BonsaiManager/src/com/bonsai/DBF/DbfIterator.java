package com.bonsai.DBF;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * An iterator over xBase file. Reads all file from first record to last record.
 * 
 * @author galisha
 * @since 1.00
 */
public class DbfIterator {

	private InputStream dbfStream = null;
	private int currentRecord = 1;
	private int countRecord = -1;
	private DbfRecord dbfRecord = null;
	private byte[] record = null;
	int lengthRecord = 0;

	protected DbfIterator(File dbfFile, String enc) {
		DbfHeader dbfHeader = new DbfHeader(dbfFile, enc);
		handleDbfHeader(dbfHeader);
		this.dbfStream = dbfHeader.getDbfStream();
	}

	protected DbfIterator(InputStream is, String enc) {
		this.dbfStream = is;
		DbfHeader dbfHeader = new DbfHeader(is, enc);
		handleDbfHeader(dbfHeader);
	}

	private void handleDbfHeader(DbfHeader dbfHeader) {
		countRecord = dbfHeader.getCountRecords();
		lengthRecord = dbfHeader.getLengthRecord();

		if (lengthRecord > 0) {
			record = new byte[lengthRecord];
		} else {
			throw new DbfEngineException(DbfConstants.EXCP_HEADER_LEN);
		}

		dbfRecord = new DbfRecord(record, dbfHeader);
	}

	/**
	 * Returns <code>true</code> if the iteration has more records
	 * 
	 * @return <code>true</code> if the iteration has more records
	 * @since 1.00
	 */
	public boolean hasMoreRecords() {
		boolean ok = false;
		if (currentRecord <= this.countRecord) {
			ok = true;
		}
		return ok;
	}

	/**
	 * Returns the next record in the iteration.
	 * 
	 * @return returns the next record in the iteration.
	 * @since 1.00
	 */
	public DbfRecord nextRecord() {

		if (hasMoreRecords() == false) {
			return null;
		} else {
			dbfRecord.setCurrentRecord(currentRecord);
			try {
				int cnt = dbfStream.read(record);
				if (cnt == -1 || cnt < lengthRecord) {
					dbfStream.close();
					throw new DbfEngineException(DbfConstants.EXCP_REC_UNEXP
							+ currentRecord);
				}
				currentRecord++;
				if (currentRecord > this.countRecord) {
					dbfStream.close();
				}
			} catch (IOException e) {
				throw new DbfEngineException(DbfConstants.EXCP_IO_ERROR);
			}
			return dbfRecord;
		}
	}

	/**
	 * The method has to be invoked in case when not all records were read.
	 */
	public void closeIterator() {
		if (dbfStream != null) {
			try {
				dbfStream.close();
			} catch (IOException e) {
			}
		}
	}
}

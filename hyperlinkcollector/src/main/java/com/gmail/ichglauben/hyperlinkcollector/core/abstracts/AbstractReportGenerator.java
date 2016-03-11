package com.gmail.ichglauben.hyperlinkcollector.core.abstracts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.gmail.ichglauben.hyperlinkcollector.core.utils.constants.GlobalConstants;
import com.gmail.ichglauben.hyperlinkcollector.core.utils.validators.PathValidator;

public abstract class AbstractReportGenerator {

	public AbstractReportGenerator() {
		super();
	}

	public static boolean generateReport(File file, List<String> data, boolean overWrite) {
		try {
			if (overWrite) {
				overwrite(file, data);
			} else if (!overWrite) {
				append(file, data);
			}
			return PathValidator.pathExists(file);
		} catch (IOException ioe) {
			return false;
		}
	}
	
	/**
	 * Static method that will append to the contents of the file. If the file
	 * does not exist, this method will create it in the program's directory.
	 * 
	 * @param file
	 *            - the file onto which the data will be written
	 * @param data
	 *            - The data to write
	 * @throws java.io.IOException
	 *             - Throws IOException
	 */
	private static void append(File file, List<String> data) throws IOException {
		if (null != file) {
			if (!PathValidator.pathExists(file))
				file = new File(GlobalConstants.USRDIR + file.toPath().getFileName() + ".txt");

			FileWriter writer = new FileWriter(file, true);
			BufferedWriter buffer = new BufferedWriter(writer);

			for (String str : data) {
				buffer.append(str + GlobalConstants.LINESEPARATOR);
			}

			String results;
			switch (data.size()) {
			case 1:
				results = " link";
				break;

			default:
				results = " total links";
				break;
			}

			buffer.append(data.size() + results);

			buffer.close();
		}
	}

	/**
	 * Static method that will over-write the contents of the file. If the file
	 * does not exist, this method will create it in the program's directory.
	 * 
	 * @param file
	 *            - the file onto which the data will be written
	 * @param data
	 *            - the data to write
	 * @throws java.io.IOException
	 *             - Throws IOException
	 */
	private static void overwrite(File file, List<String> data) throws IOException {
		if (null != file) {
			if (!PathValidator.pathExists(file))
				file = new File(GlobalConstants.USRDIR + file.toPath().getFileName() + ".txt");

			FileWriter writer = new FileWriter(file);
			BufferedWriter buffer = new BufferedWriter(writer);

			for (String str : data) {
				buffer.write(str + GlobalConstants.LINESEPARATOR);
			}

			String results;
			switch (data.size()) {
			case 1:
				results = " link";
				break;

			default:
				results = " total links";
				break;
			}

			buffer.append(data.size() + results);

			buffer.close();
		}
	}
	
	/**
	 * Static method that will over-write the contents of the file. If the file
	 * does not exist, this method will create it in the program's directory.
	 * 
	 * @param file
	 *            - the file onto which the data will be written
	 * @param data
	 *            - the data to write
	 * @throws java.io.IOException
	 *             - Throws IOException
	 */
	public static void summary(File file, String data) throws IOException {
		if (null != file) {
			if (!PathValidator.pathExists(file))
				file = new File(GlobalConstants.USRDIR + file.toPath().getFileName() + ".txt");

			FileWriter writer = new FileWriter(file);
			BufferedWriter buffer = new BufferedWriter(writer);
			buffer.append("Url: " + data);
			buffer.close();
		}
	}


	public String toString() {
		return "Abstract Report Generator";
	}
}

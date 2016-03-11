package com.gmail.ichglauben.hyperlinkcollector.core.utils.validators;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Singleton class that contains methods that will verify the existence of a file or directory.
 * @see java.io
 * @see java.nio.file.Path
 * @see java.nio.file.Paths*/
public class PathValidator {
	private static PathValidator pathValidator = new PathValidator();

	/**
	 * Private constructor
	 */
	private PathValidator() {
		super();
	}

	/**
	 * Returns a new instance of this class. 
	 * @return PathValidator
	 */
	public static PathValidator getInstance() {
		return pathValidator;
	}

	/**
	 * Verifies whether or not the file exists. 
	 * @param file_path The file to verify
	 * @return boolean Returns true if file exists
	 */
	public static boolean pathExists(String file_path) {
		Path path = null;
		if (null != file_path) {
			if (null != (path = Paths.get(file_path))) {
				boolean exists = (path.toFile().exists() && path.toFile().isFile());
				path = null;
				return exists;
			}
		}
		return false;
	}

	/**
	 * Verifies whether or not the file exists. 
	 * @param file_path The file to verify
	 * @return boolean Returns true if file exists
	 */
	public static boolean pathExists(Path file_path) {
		Path path = null;
		if (null != file_path) {
			if (null != (path = Paths.get(file_path.toFile().getAbsolutePath().toString()))) {
				boolean exists = (path.toFile().exists() && path.toFile().isFile());
				path = null;
				return exists;
			}
		}
		return false;
	}

	/**
	 * Verifies whether or not the file exists.
	 * @param file The file to verify
	 * @return boolean Returns true if file exists
	 */
	public static boolean pathExists(File file) {
		if (null != file) {
			return (file.exists() && file.isFile());
		}
		return false;
	}

	public static boolean dirExists(String directory_path) {
		Path path = null;
		if (null != directory_path) {
			if (null != (path = Paths.get(directory_path))) {
				boolean exists = (path.toFile().exists() && path.toFile().isDirectory());
				path = null;
				return exists;
			}
		}
		return false;
	}
}

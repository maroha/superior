package ee.ut.math.tvt.lihtne.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 
 * @author Kevin Nemerzitski
 *
 */
public class Util {
	
	/**
	 * Checks whether application was run as jar or not
	 * and returns inputstream accordingly
	 * @param fileName - file path from base dir.
	 * @return
	 */
	public static InputStream getFileInputStream(String fileName){
		if(ClassLoader.getSystemResource("") != null){
			try {
				//from eclipse
				return new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			//from jar
			return ClassLoader.getSystemResourceAsStream(fileName);
		}
		
	}

}

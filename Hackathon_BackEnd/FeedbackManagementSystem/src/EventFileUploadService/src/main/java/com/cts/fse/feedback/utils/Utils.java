package com.cts.fse.feedback.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;


public class Utils {
	/**
	* Parses an excel file into a list of beans.
	*
	* @param <T> the type of the bean
	* @param xlsFile the excel data file to parse
	* @param jxlsConfigFile the jxls config file describing how to map rows to beans
	* @return the list of beans or an empty list there are none
	* @throws Exception if there is a problem parsing the file
	*/
	public static <T> List<T> parseExcelFileToBeans(final FileInputStream xlsFile,
	                                                final String jxlsConfigFileName) {
	  final List<T> result = new ArrayList<>();
	  try {
	  ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	  File jxlsConfigFile = new File(classLoader.getResource(jxlsConfigFileName).getFile());
	  final XLSReader xlsReader = ReaderBuilder.buildFromXML(jxlsConfigFile);
	  final Map<String, Object> beans = new HashMap();
	  beans.put("result", result);
	  InputStream inputStream = new BufferedInputStream(xlsFile);
	  xlsReader.read(inputStream, beans);
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
	  
	  System.out.println(result);
	  return result;
	}
	
}

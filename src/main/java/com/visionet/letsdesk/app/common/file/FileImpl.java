package com.visionet.letsdesk.app.common.file;


import com.visionet.letsdesk.app.common.constant.ContentTypes;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.security.password.PwdGenerator;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.modules.string.StringUtil;
import com.visionet.letsdesk.app.common.modules.time.Time;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import org.apache.jackrabbit.extractor.HTMLTextExtractor;
import org.apache.jackrabbit.extractor.MsExcelTextExtractor;
import org.apache.jackrabbit.extractor.TextExtractor;
import org.apache.jackrabbit.extractor.XMLTextExtractor;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.io.File;
import java.util.*;


/**
 *
 * @author visionet
 */
public class FileImpl implements com.visionet.letsdesk.app.common.file.File {
	
	private static Logger _log = LoggerFactory.getLogger(FileImpl.class);
	
	public FileImpl() {
		Class<?>[] textExtractorClasses = new Class[] {
			HTMLTextExtractor.class, MsExcelTextExtractor.class,
//			MsPowerPointTextExtractor.class, MsWordTextExtractor.class,
//			OpenOfficeTextExtractor.class, PdfTextExtractor.class,
//			PlainTextExtractor.class, RTFTextExtractor.class,
			XMLTextExtractor.class
		};

		for (Class<?> textExtractorClass : textExtractorClasses) {
			try {
				TextExtractor textExtractor =
					(TextExtractor)textExtractorClass.newInstance();

				String[] contentTypes = textExtractor.getContentTypes();

				for (String contentType : contentTypes) {
					_textExtractors.put(contentType, textExtractor);
				}
			}
			catch (Exception e) {
				_log.error("", e);
			}
		}
	}

	public static FileImpl getInstance() {
		return _instance;
	}


	public byte[] getBytes(File file) throws IOException {
		if ((file == null) || !file.exists()) {
			return null;
		}
        FileInputStream is = null;
        byte[] bytes = null;
        try {
            is = new FileInputStream(file);
            bytes = getBytes(is, (int)file.length());
        }catch (Exception e){
            throw e;
        }finally {
            if (is!=null){
                is.close();
            }
        }
		return bytes;
	}

	public byte[] getBytes(InputStream is) throws IOException {
		return getBytes(is, -1);
	}

	public String getExtension(String fileName) {
		if (fileName == null) {
			return null;
		}

		int pos = fileName.lastIndexOf(StringPool.PERIOD);

		if (pos > 0) {
			return fileName.substring(pos + 1, fileName.length()).toLowerCase();
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getPath(String fullFileName) {
		int pos = fullFileName.lastIndexOf(StringPool.SLASH);

		if (pos == -1) {
			pos = fullFileName.lastIndexOf(StringPool.BACK_SLASH);
		}

		String shortFileName = fullFileName.substring(0, pos);

		if (Validator.isNull(shortFileName)) {
			return StringPool.SLASH;
		}

		return shortFileName;
	}

	public String getShortFileName(String fullFileName) {
		int pos = fullFileName.lastIndexOf(StringPool.SLASH);

		if (pos == -1) {
			pos = fullFileName.lastIndexOf(StringPool.BACK_SLASH);
		}

		String shortFileName =
			fullFileName.substring(pos + 1, fullFileName.length());

		return shortFileName;
	}

//	public boolean isAscii(File file) throws IOException {
//		boolean ascii = true;
//
//		nsDetector detector = new nsDetector(nsPSMDetector.ALL);
//
//		InputStream inputStream = new FileInputStream(file);
//
//		byte[] buffer = new byte[1024];
//
//		int len = 0;
//
//		while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
//
//			if (ascii) {
//				ascii = detector.isAscii(buffer, len);
//
//				if (!ascii) {
//					break;
//				}
//			}
//		}
//
//		detector.DataEnd();
//
//		inputStream.close();
//
//		return ascii;
//	}

	public String[] listDirs(String fileName) {
		return listDirs(new File(fileName));
	}

	public String[] listDirs(File file) {
		List<String> dirs = new ArrayList<String>();

		File[] fileArray = file.listFiles();

		for (int i = 0; (fileArray != null) && (i < fileArray.length); i++) {
			if (fileArray[i].isDirectory()) {
				dirs.add(fileArray[i].getName());
			}
		}

		return dirs.toArray(new String[dirs.size()]);
	}

	public String[] listFiles(String fileName) {
		if (Validator.isNull(fileName)) {
			return new String[0];
		}

		return listFiles(new File(fileName));
	}

	public String[] listFiles(File file) {
		List<String> files = new ArrayList<String>();

		File[] fileArray = file.listFiles();

		for (int i = 0; (fileArray != null) && (i < fileArray.length); i++) {
			if (fileArray[i].isFile()) {
				files.add(fileArray[i].getName());
			}
		}

		return files.toArray(new String[files.size()]);
	}

	public void mkdirs(String pathName) {
		File file = new File(pathName);

		file.mkdirs();
	}

	public boolean move(String sourceFileName, String destinationFileName) {
		return move(new File(sourceFileName), new File(destinationFileName));
	}

	public boolean move(File source, File destination) {
		if (!source.exists()) {
			return false;
		}

		destination.delete();

		return source.renameTo(destination);
	}

	public String read(String fileName) throws IOException {
		return read(new File(fileName));
	}

	public String read(File file) throws IOException {
		return read(file, false);
	}
	
	public String read(File file, int offset, int length)throws IOException{
		FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[length];

            fis.skip(offset);

            fis.read(bytes, 0, length);
            return new String(bytes, StringPool.UTF8);
        }catch (Exception e){
            throw e;
        }finally {
            if(fis!=null){
                fis.close();
            }
        }





	}
	
	public byte[] read(InputStream is, int offset, int length)throws IOException{
		//FileInputStream fis = new FileInputStream(file);

		byte[] bytes = new byte[length];
		
		is.skip(offset);

		is.read(bytes, 0, length);

		is.close();

		return bytes;
	}

	public String read(File file, boolean raw) throws IOException {
		FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];

            fis.read(bytes);

            String s = new String(bytes, StringPool.UTF8);

            if (raw) {
                return s;
            }
            else {
                return StringUtil.replace(
                        s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if (fis!=null){
                fis.close();
            }
        }

	}

	public String replaceSeparator(String fileName) {
		return StringUtil.replace(
                fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	public File[] sortFiles(File[] files) {
		if (files == null) {
			return null;
		}

		Arrays.sort(files, new FileComparator());

		List<File> directoryList = new ArrayList<File>();
		List<File> fileList = new ArrayList<File>();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				directoryList.add(files[i]);
			}
			else {
				fileList.add(files[i]);
			}
		}

		directoryList.addAll(fileList);

		return directoryList.toArray(new File[directoryList.size()]);
	}

	public String stripExtension(String fileName) {
		if (fileName == null) {
			return null;
		}

		String ext = getExtension(fileName);

		if (ext.length() > 0) {
			return fileName.substring(0, fileName.length() - ext.length() - 1);
		}
		else {
			return fileName;
		}
	}

	public Properties toProperties(FileInputStream fis) {
		Properties props = new Properties();

		try {
			props.load(fis);
		}
		catch (IOException ioe) {
		}

		return props;
	}

	public Properties toProperties(String fileName) {
		try {
			return toProperties(new FileInputStream(fileName));
		}
		catch (IOException ioe) {
			return new Properties();
		}
	}

	
	public void copyDirectory(String sourceDirName, String destinationDirName) {
		// TODO Auto-generated method stub
		copyDirectory(new File(sourceDirName), new File(destinationDirName));
	}


	
	public void copyDirectory(File source, File destination) {
		// TODO Auto-generated method stub
		if (source.exists() && source.isDirectory()) {
			if (!destination.exists()) {
				destination.mkdirs();
			}

			File[] fileArray = source.listFiles();

			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isDirectory()) {
					copyDirectory(
						fileArray[i],
						new File(destination.getPath() + File.separator
							+ fileArray[i].getName()));
				}
				else {
					copyFile(
						fileArray[i],
						new File(destination.getPath() + File.separator
							+ fileArray[i].getName()));
				}
			}
		}
	}


	
	public void copyFile(String source, String destination) {
		// TODO Auto-generated method stub
		copyFile(source, destination, false);
	}


	
	public void copyFile(String source, String destination, boolean lazy) {
		// TODO Auto-generated method stub
		copyFile(new File(source), new File(destination), lazy);
	}


	
	public void copyFile(File source, File destination) {
		// TODO Auto-generated method stub
		copyFile(source, destination, false);
	}


	
	public void copyFile(File source, File destination, boolean lazy) {
		// TODO Auto-generated method stub
		if (!source.exists()) {
			return;
		}

		if (lazy) {
			String oldContent = null;

			try {
				oldContent = read(source);
			}
			catch (Exception e) {
				return;
			}

			String newContent = null;

			try {
				newContent = read(destination);
			}
			catch (Exception e) {
			}

			if ((oldContent == null) || !oldContent.equals(newContent)) {
				copyFile(source, destination, false);
			}
		}
		else {
			if ((destination.getParentFile() != null) &&
				(!destination.getParentFile().exists())) {

				destination.getParentFile().mkdirs();
			}

            FileInputStream fi = null;
            FileOutputStream fo = null;
			try {
                fi = new FileInputStream(source);
                fo = new FileOutputStream(destination);

				StreamUtil.transfer(fi, fo);
			}catch (IOException ioe) {
				_log.error(ioe.getMessage());
			}finally {
                if(fi!=null){
                    try {
                        fi.close();
                    }catch (Exception e){

                    }
                }
                if(fo!=null){
                    try {
                        fo.close();
                    }catch (Exception e){

                    }
                }
            }
        }
	}


	
	public File createTempFile() {
		// TODO Auto-generated method stub
		return createTempFile(null);
	}


	
	public File createTempFile(String extension) {
		// TODO Auto-generated method stub
		return new File(createTempFileName(extension));
	}


	
	public String createTempFileName() {
		// TODO Auto-generated method stub
		return createTempFileName(null);
	}


	
	public String createTempFileName(String extension) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();

		sb.append(PropsUtil.getProperty(SysConstants.TMP_DIR));
		sb.append(StringPool.SLASH);
		sb.append(Time.getTimestamp());
		sb.append(PwdGenerator.getPassword(PwdGenerator.KEY2, 8));

		if (Validator.isNotNull(extension)) {
			sb.append(StringPool.PERIOD);
			sb.append(extension);
		}

		return sb.toString();
	}


	
	public String decodeSafeFileName(String fileName) {
		// TODO Auto-generated method stub
		return StringUtil.replace(
                fileName, _SAFE_FILE_NAME_2, _SAFE_FILE_NAME_1);
	}


	
	public boolean delete(String file) {
		// TODO Auto-generated method stub
		return delete(new File(file));
	}


	
	public boolean delete(File file) {
		// TODO Auto-generated method stub
		if ((file != null) && file.exists()) {
			return file.delete();
		}
		else {
			return false;
		}
	}


	
	public void deltree(String directory) {
		// TODO Auto-generated method stub
		deltree(new File(directory));
	}


	
	public void deltree(File directory) {
		// TODO Auto-generated method stub
		if (directory.exists() && directory.isDirectory()) {
			File[] fileArray = directory.listFiles();

			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isDirectory()) {
					deltree(fileArray[i]);
				}
				else {
					fileArray[i].delete();
				}
			}

			directory.delete();
		}
	}


	
	public String encodeSafeFileName(String fileName) {
		// TODO Auto-generated method stub
		return StringUtil.replace(
                fileName, _SAFE_FILE_NAME_1, _SAFE_FILE_NAME_2);
	}


	
	public boolean exists(String fileName) {
		// TODO Auto-generated method stub
		return exists(new File(fileName));
	}


	
	public boolean exists(File file) {
		// TODO Auto-generated method stub
		return file.exists();
	}

	
	public String getAbsolutePath(File file) {
		// TODO Auto-generated method stub
		return StringUtil.replace(
                file.getAbsolutePath(), StringPool.BACK_SLASH, StringPool.SLASH);
	}


	
	public byte[] getBytes(InputStream is, int bufferSize) throws IOException {
		// TODO Auto-generated method stub

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(
                is, unsyncByteArrayOutputStream, bufferSize);

		return unsyncByteArrayOutputStream.toByteArray();
	}


	
	public List<String> toList(Reader reader) {
		// TODO Auto-generated method stub

		List<String> list = new ArrayList<String>();

		try {
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(reader);

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				list.add(line);
			}

			unsyncBufferedReader.close();
		}
		catch (IOException ioe) {
		}

		return list;
	}


	
	public List<String> toList(String fileName) {
		// TODO Auto-generated method stub
		try {
			return toList(new FileReader(fileName));
		}
		catch (IOException ioe) {
			return new ArrayList<String>();
		}
	}


	
	public void write(String fileName, String s) throws IOException {
		// TODO Auto-generated method stub
		write(new File(fileName), s);
	}


	
	public void write(String fileName, String s, boolean lazy)
			throws IOException {
		// TODO Auto-generated method stub
		write(new File(fileName), s, lazy);
	}


	
	public void write(String fileName, String s, boolean lazy, boolean append)
			throws IOException {
		// TODO Auto-generated method stub
		write(new File(fileName), s, lazy, append);
	}


	
	public void write(String pathName, String fileName, String s)
			throws IOException {
		// TODO Auto-generated method stub
		write(new File(pathName, fileName), s);
	}


	
	public void write(String pathName, String fileName, String s, boolean lazy)
			throws IOException {
		// TODO Auto-generated method stub
		write(new File(pathName, fileName), s, lazy);
	}


	
	public void write(String pathName, String fileName, String s, boolean lazy,
			boolean append) throws IOException {
		// TODO Auto-generated method stub
		write(new File(pathName, fileName), s, lazy, append);
	}


	
	public void write(File file, String s) throws IOException {
		// TODO Auto-generated method stub
		write(file, s, false);
	}


	
	public void write(File file, String s, boolean lazy) throws IOException {
		// TODO Auto-generated method stub
		write(file, s, lazy, false);
	}


	
	public void write(File file, String s, boolean lazy, boolean append)
			throws IOException {
		// TODO Auto-generated method stub

		if (file.getParent() != null) {
			mkdirs(file.getParent());
		}

		if (lazy && file.exists()) {
			String content = read(file);

			if (content.equals(s)) {
				return;
			}
		}

		Writer writer = new OutputStreamWriter(
			new FileOutputStream(file, append), StringPool.UTF8);

		writer.write(s);

		writer.close();
	}


	
	public void write(String fileName, byte[] bytes) throws IOException {
		// TODO Auto-generated method stub
		write(new File(fileName), bytes);
	}


	
	public void write(File file, byte[] bytes) throws IOException {
		// TODO Auto-generated method stub
		write(file, bytes, 0, bytes.length);
	}


	
	public void write(File file, byte[] bytes, int offset, int length)
			throws IOException {
		// TODO Auto-generated method stub
		if (file.getParent() != null) {
			mkdirs(file.getParent());
		}
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes, offset, length);
        }catch (Exception e){
            throw e;
        }finally {
            if (fos!=null){
                fos.close();
            }
        }

	}
	
	public String getContentType(String fileName){		
		return _mimeTypes.getContentType(fileName);
	}

	
	public String extractText(InputStream is, String fileName) {
		// TODO Auto-generated method stub
		String text = null;

		try {
			if (!is.markSupported()) {
				is = new BufferedInputStream(is);
			}

			String contentType = getContentType(fileName);

			TextExtractor textExtractor = _textExtractors.get(contentType);

			if (textExtractor != null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Using text extractor " +
							textExtractor.getClass().getName());
				}

				StringBuilder sb = new StringBuilder();

				Reader reader = null;
				
				reader = textExtractor.extractText(
					is, contentType, null);

				try{
					char[] buffer = new char[1024];

					int result = -1;

					while ((result = reader.read(buffer)) != -1) {
						sb.append(buffer, 0, result);
					}
				}
				finally {
					try {
						reader.close();
					}
					catch (IOException ioe) {
					}
				}

				text = sb.toString();
			}
			else {
				if (contentType.equals(ContentTypes.APPLICATION_ZIP) ||
					contentType.startsWith(
						"application/vnd.openxmlformats-officedocument.")) {

					try {
						POITextExtractor poiTextExtractor = ExtractorFactory.createExtractor(is);

						text = poiTextExtractor.getText();
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn("", e);
						}
					}
				}

				if ((text == null) && _log.isInfoEnabled()) {
					_log.info("No text extractor found for " + fileName);
				}
			}
		}
		catch (Exception e) {
			_log.error("", e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Extractor returned text:\n\n" + text);
		}

		if (text == null) {
			text = StringPool.BLANK;
		}
		
		return text;
	}


	
	public void write(String fileName, InputStream is) throws IOException {
		// TODO Auto-generated method stub
		write(new File(fileName), is);
	}


	
	public void write(File file, InputStream is) throws IOException {
		// TODO Auto-generated method stub
		if (file.getParent() != null) {
			mkdirs(file.getParent());
		}

		StreamUtil.transfer(is, new FileOutputStream(file));
	}
	
	public void write(File file, InputStream is, int off)
			throws IOException {
		// TODO Auto-generated method stub
		if (file.getParent() != null) {
			file.getParentFile().mkdirs();
		}

		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
		
		randomAccessFile.seek(off);
		
		byte[] bytes = new byte[1024];

		int value = -1;

		while ((value = is.read(bytes, 0, 1024)) != -1) {
			randomAccessFile.write(bytes, 0 , value);
		}
		randomAccessFile.close();
		is.close();
	}

	private static final String[] _SAFE_FILE_NAME_1 = {
		StringPool.AMPERSAND, StringPool.CLOSE_PARENTHESIS,
		StringPool.OPEN_PARENTHESIS, StringPool.SEMICOLON
	};

	private static final String[] _SAFE_FILE_NAME_2 = {
		"_AMP_", "_CP_", "_OP_", "_SEM_"
	};
	
	private Map<String, TextExtractor> _textExtractors = new HashMap<String, TextExtractor>();
	
	private static FileImpl _instance = new FileImpl();
	private static MimetypesFileTypeMap _mimeTypes = new MimetypesFileTypeMap();
	
}
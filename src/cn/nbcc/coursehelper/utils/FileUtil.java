package cn.nbcc.coursehelper.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class FileUtil {
	
	private static final String COURSE_KLASS = "�༶:";
	private static final String COURSE_TEACHER = "��ʦ:";
	private static final String COURSE_NAME = "����:";
	private static final String COURSE_ID = "�κ�:";
	// ��������û����ݵ�Ŀ¼
	public static final String DATA_FOLDER = "D:\\datas" + File.separator;
	// ��ž���ĳ���˻����õ�properties�ļ�
	public static final String CONFIG_FILE = "config.properties";
	public static final String ASSESSMENT_FILE = "��ĩ����";
	public static final String ATTANDENCE_FILE = "���ڱ�";
	public static final String PRACTICAL_FILE = "����������˱�";
	public static final String EXERCISE_FILE = "��ҵ������˱�";
	
	//�ļ���ʽ����
	public static final String SEPERATOR_TAG = "_";
	public static String SEMESTER = DateUtils.getCurMonth()>=6?"01":"02";
	public static int SEMESTER_YEAR = DateUtils.getCurMonth()<=8?DateUtils.getCurYear()-1:DateUtils.getCurYear();
	
	public static String DEFAULT_FILE_HD = SEMESTER_YEAR+SEMESTER+SEPERATOR_TAG;
	
	
	
	public static  boolean genFileNames(Path savePath,ArrayList<String> fileNameList) {
		if (fileNameList.size()<=0) {
			System.out.println("no file names");
			return false;
		}
		for (String fileName : fileNameList) {
			
		}
		
		
		return true;
	}
	
	// ����Ŀ¼�Ĺ��߷���, �ж�Ŀ¼�Ƿ����
	public static void mkdir(File file) {
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	// �õ����е����������ļ�
	public static List<File> getAllPropertyFiles() {
		List<File> proFiles = new ArrayList<File>();
		File dataFolder = new File(FileUtil.DATA_FOLDER);
		File[] accountFolders = dataFolder.listFiles();
		for (int i = 0, length = accountFolders.length; i < length; i++) {
			File account = accountFolders[i];
			if (account.isDirectory()) {
//				proFiles.add(filterFiles(account, ".properties").get(0));
			}
		}
		return proFiles;
	}
	
	// �õ��γ̹鵵�ĸ�Ŀ¼�������ǰ����� / ��
	public static String getCourseRoot(Course course) {
		String accountRoot = DATA_FOLDER + getCourseInfo(course)
				+ File.separator;
		return accountRoot;
	}

	public static String getCourseInfo(Course course) {
		
		return null;
	}
	
	public static boolean copyFile(Path from,Path to) {
		
		try {  
            Files.copy(from, to);  
        } catch (IOException e) {  
            System.err.println(e); 
            return false;
        }  
		return true;
		
	}

	public static List<Student> getStudentList(String excelFilePath,int sheetNum){
		
		List<Student> stuList = new ArrayList<>();
		HSSFSheet sheet = getSheet(excelFilePath, sheetNum);
		
		for(int i = sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++)
		{
			HSSFRow  row = sheet.getRow(i);
			for(int j = row.getFirstCellNum(); j < row.getLastCellNum() ; j++ )
			{ 
				Pattern pattern = Pattern.compile("\\d{9}");
				String id = row.getCell(j).toString().trim();
				Matcher matcher = pattern.matcher(id);
				if (!matcher.matches()) {
					continue;
				}
				String name = row.getCell(j+1).toString().trim();
				System.out.println(id+","+name);
				stuList.add(new Student(id, name));
			}
		}
		
		return stuList;
	}

	/**
	 * @param excelFilePath
	 * @param sheetNum
	 * @param workbook
	 * @return
	 */
	public static HSSFSheet getSheet(String excelFilePath, int sheetNum) {
		
		HSSFWorkbook workbook = null;
		BufferedInputStream bufferedInputStream;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(excelFilePath));
			workbook = new HSSFWorkbook(bufferedInputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//��ȡxlsx�ļ���������workbook����
		HSSFSheet sheet = workbook.getSheetAt(sheetNum);
		return sheet;
	}
	public static String getCourseInfo(String excelFilePath,int sheetNum) {
		String cInfo = null;
		
		//�κ�:031J38A00 ����:��������������Ŀ�ۺϿγ� ��ʦ:��˫�� �༶:14�����A-JAVA
		HSSFSheet sheet = getSheet(excelFilePath, sheetNum);
		
		for(int i = sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++)
		{
			HSSFRow  row = sheet.getRow(i);
			for(int j = row.getFirstCellNum(); j < row.getLastCellNum() ; j++ )
			{ 
				cInfo = row.getCell(j).toString().trim();
				
				if (cInfo.contains(COURSE_ID)) {
					return cInfo;
				}
			}
		}
		return null;
		
	}
	
	public static String getKlassName(String cInfo) {
		return cInfo.substring(cInfo.indexOf(COURSE_KLASS)+COURSE_KLASS.length()).trim();
	}

	public static String getTeachers(String cInfo) {
		return cInfo.substring(cInfo.indexOf(COURSE_TEACHER)+COURSE_TEACHER.length(),cInfo.indexOf(COURSE_KLASS)).trim();
	}

	public static String getCourseName(String cInfo) {
		return cInfo.substring(cInfo.indexOf(COURSE_NAME)+COURSE_NAME.length(), cInfo.indexOf(COURSE_TEACHER)).trim();
	}

	public static String getCourseID(String cInfo) {
		Pattern p_cid = Pattern.compile("\\w{9}");
		Matcher m = p_cid.matcher(cInfo);
		if(m.find())
			return m.group(0);
		return "";
	}

	public static void mkDirs(Path newDir) {
		if (Files.notExists(newDir, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createDirectories(newDir);
				System.out.println("Create Dires successful!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean rename(Path fPath, String newName) {
		try {
			Files.move(fPath, fPath.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String join(ArrayList<String> fileTags,char sep ){
		return StringUtils.join(fileTags,sep);
	}
	
	public static void newFile(Path srcDir,String fileName){
		Path newFile = srcDir.resolve("newFile.docx");
		try {
			Files.createFile(newFile);
			System.out.println("Create file successful!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean deleteDirs(Path newDir) {
		if (Files.exists(newDir, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.delete(newDir);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	 /**     * ����Word�ļ�
     * @param destFile Ŀ���ļ�·��
     * @param fileContent Ҫ�������ļ�����
     */
	public static int exportDoc(String destFile,String fileContent){
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					fileContent.getBytes("UTF-8"));
			POIFSFileSystem fileSystem = new POIFSFileSystem();
			DirectoryEntry directory = fileSystem.getRoot();
			directory.createDocument("WordDocument", byteArrayInputStream);
			FileOutputStream fileOutputStream = new FileOutputStream(destFile);
			fileSystem.writeFilesystem(fileOutputStream);
			byteArrayInputStream.close();
			fileOutputStream.close();
			return 1;
		} catch (IOException e) {
			return 0;
		}
	}
     
     /**     * ��ȡwordģ�岢�滻����
      * @param templatePath ģ��·��
      * @param contentMap Ҫ�滻������
      * @return word��Document
      */
     public static HWPFDocument replaceDoc(String templatePath, Map<String, String> contentMap) {
         try {
             // ��ȡģ��
			 FileInputStream tempFileInputStream = new FileInputStream(new File(templatePath));
			 HWPFDocument document = new HWPFDocument(tempFileInputStream);
             // ��ȡ�ı�����
             Range bodyRange = document.getRange();
             // �滻����
             for (Map.Entry<String, String> entry : contentMap.entrySet()) {
                 bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
             }
             return document;
         } catch (Exception e) {
             return null;
         }
     }

}

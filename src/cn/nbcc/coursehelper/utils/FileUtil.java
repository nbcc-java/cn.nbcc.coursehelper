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
	
	private static final String COURSE_KLASS = "班级:";
	private static final String COURSE_TEACHER = "教师:";
	private static final String COURSE_NAME = "课名:";
	private static final String COURSE_ID = "课号:";
	// 存放所有用户数据的目录
	public static final String DATA_FOLDER = "D:\\datas" + File.separator;
	// 存放具体某个账户配置的properties文件
	public static final String CONFIG_FILE = "config.properties";
	public static final String ASSESSMENT_FILE = "期末考核";
	public static final String ATTANDENCE_FILE = "考勤表";
	public static final String PRACTICAL_FILE = "过程情况考核表";
	public static final String EXERCISE_FILE = "作业情况考核表";
	
	//文件格式常量
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
	
	// 创建目录的工具方法, 判断目录是否存在
	public static void mkdir(File file) {
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	// 得到所有的属性配置文件
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
	
	// 得到课程归档的根目录，这里是包含了 / 的
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
		
		//获取xlsx文件流，创建workbook对象
		HSSFSheet sheet = workbook.getSheetAt(sheetNum);
		return sheet;
	}
	public static String getCourseInfo(String excelFilePath,int sheetNum) {
		String cInfo = null;
		
		//课号:031J38A00 课名:基于面向对象的项目综合课程 教师:郭双宙 班级:14计算机A-JAVA
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
	
	 /**     * 导出Word文件
     * @param destFile 目标文件路径
     * @param fileContent 要导出的文件内容
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
     
     /**     * 读取word模板并替换变量
      * @param templatePath 模板路径
      * @param contentMap 要替换的内容
      * @return word的Document
      */
     public static HWPFDocument replaceDoc(String templatePath, Map<String, String> contentMap) {
         try {
             // 读取模板
			 FileInputStream tempFileInputStream = new FileInputStream(new File(templatePath));
			 HWPFDocument document = new HWPFDocument(tempFileInputStream);
             // 读取文本内容
             Range bodyRange = document.getRange();
             // 替换内容
             for (Map.Entry<String, String> entry : contentMap.entrySet()) {
                 bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
             }
             return document;
         } catch (Exception e) {
             return null;
         }
     }

}

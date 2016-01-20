package cn.nbcc.coursehelper.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static int getCurYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		return Integer.valueOf(sdf.format(new Date()));
	}

	public static int getCurMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return Integer.valueOf(sdf.format(new Date()));
	}

}

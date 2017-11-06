package com.example.util;

import java.util.Calendar;
import java.util.TimeZone;

public class Tools {

	public static String[] allColleges = {
			"建筑学院","机械工程学院","能源与环境学院","信息科学与工程学院"
			,"土木工程学院","电子科学与工程学院","数学系","自动化学院","计算机科学与工程学院"
			,"物理系","生物科学与医学工程学院","材料科学与工程学院","人文学院"
			,"经济管理学院","电气工程学院","外国语学院","体育系"
			,"化学化工学院","交通学院","仪器科学与工程学院","艺术学院"
			,"法学院","医学院","公共卫生学院","生命科学研究院"
			,"海外教育学院","软件学院","集成电路学院","马克思主义学院"
	};

	public static String myIp = "118.89.154.245";

	public static String getNowTime() {

		// 指定东八区，即北京时间
		Calendar cc = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		int year = cc.get(Calendar.YEAR);

		// 月份是从0开始计数的，所以此处进行加1
		int month = cc.get(Calendar.MONTH) + 1;
		int day = cc.get(Calendar.DAY_OF_MONTH);

		// int hour_8 = cc.get(Calendar.HOUR);//8小时制
		int hour_24 = cc.get(Calendar.HOUR_OF_DAY);// 24小时制

		int minute = cc.get(Calendar.MINUTE);
		int second = cc.get(Calendar.SECOND);
		String hStr = hour_24 + "";
		String mStr = minute + "";
		String sStr = second + "";
		if (hour_24 < 10) {
			hStr = "0" + hStr;
		}
		if (minute < 10) {
			mStr = "0" + mStr;
		}
		if (second < 10) {
			sStr = "0" + sStr;
		}

		String s = year + "-" + month + "-" + day + " " + hStr + ":" + mStr
				+ ":" + sStr;
		// System.out.println(s);

		return s;

	}


}

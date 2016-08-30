package com.visionet.letsdesk.app.common.pingying;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author 李晓健
 * @date 2013年10月29日 下午3:31:12
 */
public class CnToSpell {
    private static Log log = LogFactory.getLog(CnToSpell.class);
    
	// 将汉字转换为全拼
	public static String getPingYin(String src) {

		char[] t1 = src.toCharArray();
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuffer t4 = new StringBuffer("");
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
                    String[] t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4.append(t2[0]);
				} else
					t4.append(Character.toString(t1[i]));
			}
			// System.out.println(t4);
			return t4.toString();
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			log.error(e.toString(),e);
		}
		return t4.toString();
	}

	// 返回中文的首字母
	public static String getPinYinHeadChar(String str) {

		StringBuffer convert = new StringBuffer("");
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert.append(pinyinArray[0].charAt(0));
			} else {
				convert.append(word);
			}
		}
		return convert.toString();
	}

	// 将字符串转移为ASCII码
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i]&0xff));
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	

}

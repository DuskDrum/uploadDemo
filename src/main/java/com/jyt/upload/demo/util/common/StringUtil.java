package com.jyt.upload.demo.util.common;

import java.io.File;
import java.io.Serializable;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * String工具类
 * 
 * 
 */
public class StringUtil implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2265983598669708115L;

	/**
     * 格式化字符串
     * 
     * 例：formateString("xxx{0}bbb",1) = xxx1bbb
     * 
     * @param str
     * @param params
     * @return
     */
    public static String formateString(String str, String... params) {
        for (int i = 0; i < params.length; i++) {
            str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
        }
        return str;
    }

    /**
    * 判断参数是否为空，为空则返回""，否则返回其值
    * @param sSource 源字符串
    * @return 字符串
    */
    public String getString(String sSource) {
        String sReturn = "";
        if (sSource != null) {
            sReturn = sSource;
        }
        return sReturn;
    }

    /**
     * 判断参数是否为0，为0则返回""，否则返回其值
     * @param iSource 源字符串
     * @return 字符串
     */
    public static String getString(int iSource) {
        if (iSource == 0) {
            return "";
        } else {
            return "" + iSource;
        }
    }

    /**
     * 转码：GBK ----> iso-8859-1
     * @param s 转码字段
     * @return 转码后的字段
     */
    public static String GBKtoISO(String s) {
        try {
            s = new String(s.getBytes("GBK"), "iso-8859-1");
        } catch (Exception e) {
        }
        return s;
    }

    /**
     * 转码：iso-8859-1 ----> utf-8
     * @param s 转码字段
     * @return 转码后的字段
     */
    public static String ISOtoUtf(String s) {
        try {
            s = new String(s.getBytes("ISO-8859-1"), "utf-8");
        } catch (Exception e) {
        }
        return s;
    }

    /**
     * 转码：iso-8859-1 ----> GBK
     * @param s 转码字段
     * @return 转码后的字段
     */
    public static String ISOtoGBK(String s) {
        try {
            s = new String(s.getBytes("iso-8859-1"), "GBK");
        } catch (Exception e) {
        }
        return s;
    }

    /**
     * 判断参数是否为空，为空则返回一个长度为0的字符串数组，否则返回其值
     * @param aSource 源字符串数组
     * @return 字符串
     */
    public String[] getArray(String[] aSource) {
        String aReturn[] = new String[0];
        if (aSource != null) {
            aReturn = aSource;
        }
        return aReturn;
    }

    /**
     * 判断参数是否为空，为空则返回false,不为空则返回true
     * @param obj  源字符串
     * @return 整型数
     */
    public static boolean getObj(Object obj) {
        if (obj != null && !obj.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 判断参数是否为空，为空则返回0,不为空则返回其整型值
     * @param sSource  源字符串
     * @return 整型数
     */
    public static int getInt(String sSource) {
        int iReturn = 0;
        if (sSource != null && !sSource.equals("")) {
            iReturn = Integer.parseInt(sSource);
        }
        return iReturn;
    }

    /**
     * 判断参数是否为空，为空则返回一个长度为0的整形数组，否则返回其值
     * @param aSource 源字符串数组
     * @return 整形数组
     */
    public static int[] getIntArray(String[] aSource) {
        int iReturn[] = new int[0];
        if (aSource != null) {
            iReturn = new int[aSource.length];
            for (int i = 0; i < aSource.length; i++) {
                iReturn[i] = Integer.parseInt(aSource[i]);
            }
        }
        return iReturn;
    }

    /**
     * 判断参数是否为空，为空则返回0,不为空则返回其整型值 
     * @param sSource 源字符串
     * @return Double数
     */
    public static double getDouble(String sSource) {
        double dReturn = 0.00;
        if (sSource != null && !sSource.equals("")) {
            dReturn = (new Double(sSource)).doubleValue();
        }
        return dReturn;
    }

    /**
     * 查找以逗号分隔的源字符串是否包含给定字符串
     * @param sSource :源字符串
     * @param sItem :子串
     * @return 是否包含
     */
    public static boolean isContain(String sSource, String sItem) {
        boolean isReturn = false;
        StringTokenizer st = null;
        st = new StringTokenizer(sSource, ",");
        while (st.hasMoreTokens()) {
            if (sItem.equals(st.nextToken())) {
                isReturn = true;
                break;
            }
        }
        return isReturn;
    }

    /**
     * 查找源字符串数组中是否包含给定字符串
     * @param aSource :源字符串数组
     * @param sItem :子串
     * @return 是否包含
     */
    public static boolean isContain(String[] aSource, String sItem) {
        boolean isReturn = false;
        for (int i = 0; i < aSource.length; i++) {
            if (sItem.equals(aSource[i])) {
                isReturn = true;
                break;
            }
        }
        return isReturn;
    }

    /**
     * 将指定字符串从源字符串中删除掉，并返回替换后的结果字符串
     * @param source 源字符串
     * @param subString 要删除的字符
     * @return 替换后的字符串
     */
    public static String delete(String source, String subString) {
        StringBuffer output = new StringBuffer();
        //源字符串长度
        int lengthOfSource = source.length();
        //开始搜索位置
        int posStart = 0;
        //搜索到老字符串的位置
        int pos;
        while ((pos = source.indexOf(subString, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));
            posStart = pos + 1;
        }
        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }
        return output.toString();
    }

    /**
     * 此函数有三个输入参数，源字符串(将被操作的字符串),原字符串中被替换的字符串(旧字符串)
     * 替换的字符串(新字符串)，函数接收源字符串、旧字符串、新字符串三个值后，
     * 用新字符串代替源字符串中的旧字符串并返回结果
     * @param source 源字符串
     * @param oldString 旧字符串
     * @param newString 新字符串
     * @return 替换后的字符串
     */
    public static String replace(String source, String oldString, String newString) {
        StringBuffer output = new StringBuffer();
        int lengthOfSource = source.length(); // 源字符串长度
        int lengthOfOld = oldString.length(); // 老字符串长度
        int posStart = 0; // 开始搜索位置
        int pos; // 搜索到老字符串的位置
        while ((pos = source.indexOf(oldString, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));
            output.append(newString);
            posStart = pos + lengthOfOld;
        }
        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }
        return output.toString();
    }

    /**
     * 将给定的源字符串加1 例如：“0001” 经本函数转换后返回为“0002”
     * @param sSource :源字符串
     * @return 返回字符串
     */
    public static String increaseOne(String sSource) {
        String sReturn = null;
        int iSize = 0;
        iSize = sSource.length();
        long l = (new Long(sSource)).longValue();
        l++;
        sReturn = String.valueOf(l);
        for (int i = sReturn.length(); i < iSize; i++) {
            sReturn = "0" + sReturn;
        }
        return sReturn;
    }

    /**
     * 将给定的整数转化成字符串，结果字符串的长度为给定长度,不足位数的左端补"0"
     * 例如val=10，len=5，那么生成的字符串为"00010"
     * @param val 将被转化成字符串的整数
     * @param len 转化后的长度
     * @return String 返回值
     */
    public static String intToStr(int val, int len) {
        String sReturn = new String();
        sReturn = String.valueOf(val);
        if (sReturn.length() < len) {
            for (int i = len - sReturn.length(); i > 0; i--) {
                sReturn = "0" + sReturn;
            }
        }
        return sReturn;
    }

    /**
     * 将数组中的每个元素两端加上给定的符号
     * @param aSource 源数组
     * @param sChar 符号
     * @return 处理后的字符串数组
     */
    public static String[] arrayAddSign(String[] aSource, String sChar) {
        String aReturn[] = new String[aSource.length];
        for (int i = 0; i < aSource.length; i++) {
            aReturn[i] = sChar + aSource[i] + sChar;
        }
        return aReturn;
    }

    /**
     * 将数组中的元素连成一个以逗号分隔的字符串
     * @param aSource 源数组
     * @return 字符串
     */
    public static String arrayToString(String[] aSource) {
        String sReturn = "";
        for (int i = 0; i < aSource.length; i++) {
            if (i > 0) {
                sReturn += ",";
            }
            sReturn += aSource[i];
        }
        return sReturn;
    }

    /**
     * 将数组中的元素连成一个以逗号分隔的字符串
     * @param aSource 源数组
     * @return 字符串
     */
    public static String arrayToString(int[] aSource) {
        String sReturn = "";
        for (int i = 0; i < aSource.length; i++) {
            if (i > 0) {
                sReturn += ",";
            }
            sReturn += aSource[i];
        }
        return sReturn;
    }

    /**
     * 将数组中的元素连成一个以给定字符分隔的字符串
     * @param aSource 源数组
     * @param sChar 分隔符
     * @return 字符串
     */
    public static String arrayToString(String[] aSource, String sChar) {
        String sReturn = "";
        for (int i = 0; i < aSource.length; i++) {
            if (i > 0) {
                sReturn += sChar;
            }
            sReturn += aSource[i];
        }
        return sReturn;
    }

    /**
     * 将两个字符串的所有元素连结为一个字符串数组
     * @param array1 源字符串数组1
     * @param array2 源字符串数组2
     * @return String[]
     */
    public static String[] arrayAppend(String[] array1, String[] array2) {
        int iLen = 0;
        String aReturn[] = null;
        if (array1 == null) {
            array1 = new String[0];
        }
        if (array2 == null) {
            array2 = new String[0];
        }
        iLen = array1.length;
        aReturn = new String[iLen + array2.length];
        /**
         * 将第一个字符串数组的元素加到结果数组中
         */
        for (int i = 0; i < iLen; i++) {
            aReturn[i] = array1[i];
        }
        /**
         * 将第二个字符串数组的元素加到结果数组中
         */
        for (int i = 0; i < array2.length; i++) {
            aReturn[iLen + i] = array2[i];
        }
        return aReturn;
    }

    /**
     * 将两个对象数组中的所有元素连结为一个对象数组
     * @param array1 源字符串数组1
     * @param array2 源字符串数组2
     * @return Object[]
     */
    public static Object[] arrayAppend(Object[] array1, Object[] array2) {
        int iLen = 0;
        Object aReturn[] = null;
        if (array1 == null) {
            array1 = new Object[0];
        }
        if (array2 == null) {
            array2 = new Object[0];
        }
        iLen = array1.length;
        aReturn = new Object[iLen + array2.length];
        /**
         * 将第一个对象数组的元素加到结果数组中
         */
        for (int i = 0; i < iLen; i++) {
            aReturn[i] = array1[i];
        }
        /**
         * 将第二个对象数组的元素加到结果数组中
         */
        for (int i = 0; i < array2.length; i++) {
            aReturn[iLen + i] = array2[i];
        }
        return aReturn;
    }

    /**
     * 拆分以逗号分隔的字符串,并存入String数组中
     * @param sSource 源字符串
     * @return String[]
     */
    public static String[] strToArray(String sSource) {
        String aReturn[] = null;
        StringTokenizer st = null;
        st = new StringTokenizer(sSource, ",");
        aReturn = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            aReturn[i] = st.nextToken();
            i++;
        }
        return aReturn;
    }

    /**
     * 拆分以给定分隔符分隔的字符串,并存入字符串数组中
     * @param sSource  源字符串
     * @param sChar 分隔符
     * @return String[]
     */
    public static String[] strToArray(String sSource, String sChar) {
        String aReturn[] = null;
        StringTokenizer st = null;
        st = new StringTokenizer(sSource, sChar);
        int i = 0;
        aReturn = new String[st.countTokens()];
        while (st.hasMoreTokens()) {
            aReturn[i] = st.nextToken();
            i++;
        }
        return aReturn;
    }

    /**
     * 拆分以给定分隔符分隔的字符串,并存入整型数组中
     * @param sSource 源字符串
     * @param sChar 分隔符
     * @return int[]
     */
    public static int[] strToArray(String sSource, char sChar) {
        int aReturn[] = null;
        StringTokenizer st = null;
        st = new StringTokenizer(sSource, String.valueOf(sChar));
        int i = 0;
        aReturn = new int[st.countTokens()];
        while (st.hasMoreTokens()) {
            aReturn[i] = Integer.parseInt(st.nextToken());
            i++;
        }
        return aReturn;
    }

    /**
     * 将以逗号分隔的字符串的每个元素加上单引号 如： 1000,1001,1002 --> '1000','1001','1002'
     * @param sSource 源串
     * @return String
     */
    public static String addMark(String sSource) {
        String sReturn = "";
        StringTokenizer st = null;
        st = new StringTokenizer(sSource, ",");
        if (st.hasMoreTokens()) {
            sReturn += "'" + st.nextToken() + "'";
        }
        while (st.hasMoreTokens()) {
            sReturn += "," + "'" + st.nextToken() + "'";
        }
        return sReturn;
    }

    /**
     * 删除磁盘上的文件
     * @param fileName 文件全路径
     * @return boolean
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        return file.delete();
    }

    /**
     * 判断字符串是否可转换成数字
     * @param strInput 源串
     * @return boolean
     */
    public static boolean isNumber(String strInput) {
        boolean bRs = false;
        @SuppressWarnings("unused")
		int nRs = 0;
        try {
            nRs = Integer.parseInt(strInput);
            bRs = true;
        } catch (Exception e) {
            bRs = false;
        }
        return bRs;
    }
    /**
     * 随机数
     * @param length
     * @return
     */
    public static String genRandomString(int length){
		Random r = new Random(); 
		String ssource = "0123456789";
	    char[] src = ssource.toCharArray();
        char[] buf = new char[length];
        int rnd;
        for(int i=0;i<length;i++)
        {
                rnd = Math.abs(r.nextInt()) % src.length;

                buf[i] = src[rnd];
        }
        return new String(buf);
	}
    
	/**
	 * Convert byte[] to hex string.这里我们可以将byte转换成int，
	 * 然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	
	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	public static boolean isBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0))
            return true;
        for (int i = 0; i < length; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
	public static boolean isNotBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0))
            return false;
        for (int i = 0; i < length; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return true;
            }
        }
        return false;
    }
	public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }
    
    
    /**
     * 卡号屏蔽 保留前六位后四位，中间四个*
     * @param account
     * @return
     */
	public static String maskCardNo(String account) {
		if (isBlank(account))
			return account;

		int length = account.length();
		if (account == null || account.length() < 10) {
			return account;
		}

		StringBuffer result = new StringBuffer();
		result.append(account.substring(0, 6));
		result.append("****");
		result.append(account.substring(length - 4, length));

		return result.toString();
	}

	/**
	 * 证件号屏蔽 保留前六位后四位，中间四个*
	 * @param certNo
	 * @return
	 */
	public static String maskCertNo(String certNo) {
		if (isBlank(certNo)) {
			return certNo;
		}
		int length = certNo.length();
		if (certNo == null || certNo.length() < 10) {
			return certNo;
		}

		StringBuffer result = new StringBuffer();
		result.append(certNo.substring(0, 6));
		result.append("****");
		result.append(certNo.substring(length - 4, length));

		return result.toString();
	}

	/**
	 * 手机号屏蔽 保留前三位 后四位 ，中间四个*
	 * @param mobile
	 * @return
	 */
	public static String maskPhoneNo(String mobile) {
		if (isEmpty(mobile)) {
			return mobile;
		}

		int length = mobile.length();
		if (mobile == null || mobile.length() < 11) {
			return mobile;
		}

		StringBuffer result = new StringBuffer();
		result.append(mobile.substring(0, 3));
		result.append("****");
		result.append(mobile.substring(length - 4, length));

		return result.toString();
	}

	/**
	 * 姓名屏蔽
	 * @param name
	 * @return
	 */
	public static String maskName(String name) {
		if (isEmpty(name)) {
			return name;
		}

		StringBuffer result = new StringBuffer();
		if (!name.matches("^([\u4e00-\u9fa5]+|([a-zA-Z]+\\s?)+)$")) {
			return name;
		}

		String regCN = "[\u4e00-\u9fa5]";
		String word[] = null;
		if (name.matches(".*" + regCN + ".*")) {
			word = new String[name.length()];
			for (int i = 0; i < name.length(); i++) {
				word[i] = name.substring(i, i + 1);
			}
		} else {
			word = name.split("\\s?");
		}

		result.append(word[0]);
		int size = word.length;
		if (size == 1 || size == 2) {
			result.append("*");
		} else {
			for (int i = 1; i < size - 1; i++) {
				result.append("*");
			}
			result.append(word[size - 1]);
		}

		return result.toString();
	}
    
}

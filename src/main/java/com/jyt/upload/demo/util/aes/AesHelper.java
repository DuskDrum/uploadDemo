package com.jyt.upload.demo.util.aes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * AES 加密算法  JRE默认只能使用16个字节（128）位密钥
 * @author wangheng
 *
 */
public class AesHelper {
	
	//使用指定转换的Cipher
	public static final String CIPHER_ALGORITHM_AES = "AES"; 
	
	public static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
	
	public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	
	//【AES/CBC/NoPadding】模式下，待加密内容的长度必须是16的倍数，否则： javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
	public static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";
	
    private static final String ALGORITHM_MD5 = "md5";
    
    private static final String CHARSET = "UTF-8";
    
    
    /**
     * 加密
     * @param msg 需要加密的数据
     * @param key 密钥
     * @param transformation 加密类型（AES/ECB/CBC/）
     * @return
     * @throws Exception
     */
    public static String encryMsg(String msg, byte[] key, String transformation) throws Exception {
    	
    	byte[] source = msg.getBytes(CHARSET);
    	
    	Cipher cipher = Cipher.getInstance(transformation);
    	
    	Key secreKey = new SecretKeySpec(key, CIPHER_ALGORITHM_AES);
    	
    	if(transformation.equals(CIPHER_ALGORITHM_CBC) || transformation.equals(CIPHER_ALGORITHM_CBC_NoPadding)) {
    		cipher.init(Cipher.ENCRYPT_MODE, secreKey, new IvParameterSpec(getIV()));
    		//通过Base64编码为ASCII字符后传输
    		return Base64.getEncoder().encodeToString(cipher.doFinal(source));
    	}else {
    		cipher.init(Cipher.ENCRYPT_MODE, secreKey);
    		//通过Base64编码为ASCII字符后传输
    		return Base64.getEncoder().encodeToString(cipher.doFinal(source));
    	}
    }
    
    /**
     * 解密
     * @param msg 需要解密的数据
     * @param key 密钥
     * @param transformation 加密类型（AES/ECB/CBC/）
     * @return
     * @throws Exception
     */
    public static String decryptMsg(String msg, byte[] key, String transformation) throws Exception {
    	//收到后先用Base64解码
    	byte[] source = Base64.getDecoder().decode(msg.getBytes(CHARSET));
    	
    	Cipher cipher = Cipher.getInstance(transformation);
    	
    	Key secreKey = new SecretKeySpec(key, CIPHER_ALGORITHM_AES);
    	
    	if(transformation.equals(CIPHER_ALGORITHM_CBC) || transformation.equals(CIPHER_ALGORITHM_CBC_NoPadding)) {
    		cipher.init(Cipher.DECRYPT_MODE, secreKey, new IvParameterSpec(getIV()));
    		return new String(cipher.doFinal(source));
    	}else {
    		cipher.init(Cipher.DECRYPT_MODE, secreKey);
    		return new String(cipher.doFinal(source));
    	}
    }
    
    /**
     * 根据字符串生成AES的密钥字节数组</br>
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] getAESKeyBytes(String sKey) throws Exception{
    	//获得指定摘要算法的MessageDigest对象
    	MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
    	//使用指定的字节更摘要
    	md.update(sKey.getBytes(CHARSET));
    	return md.digest();
    }
    
    /**
     * 指定一个初始化向量（Initialization vector, IV） IV 必须是16位
     * @return
     * @throws Exception
     */
    private static final byte[] getIV() throws Exception{
    	return "1234567812345678".getBytes(CHARSET);
    }
    
    /**
     * AES密钥生成
     * @return
     */
    public static String generatorAESKey() {
    	try {
    		KeyGenerator kg = KeyGenerator.getInstance(CIPHER_ALGORITHM_AES);
    		kg.init(128);
    		SecretKey key = kg.generateKey();
    		byte [] b = key.getEncoded();
    		return byteToHexString(b);
    	}catch(Exception e) {
			System.err.println("AES密钥生成异常"+e);
    		return "";
    	}
    }
    

	/**
	 * byte数组转化为16进制字符串
	 * @param bytes
	 * @return
	 */
	public static String byteToHexString(byte[] bytes){     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < bytes.length; i++) {     
	         String strHex=Integer.toHexString(bytes[i]); 
	         if(strHex.length() > 3){     
	                sb.append(strHex.substring(6));     
	         } else {  
	              if(strHex.length() < 2){  
	                 sb.append("0" + strHex);  
	              } else {  
	                 sb.append(strHex);     
	              }     
	         }  
	    }  
	   return  sb.toString();     
	}
}

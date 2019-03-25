package com.jyt.upload.demo.util;

import com.jyt.upload.demo.util.aes.AesHelper;
import com.jyt.upload.demo.util.common.StringUtil;
import com.jyt.upload.demo.util.rsa.RsaHelper;

import java.io.UnsupportedEncodingException;

public class MockClient {

	String CLIENT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpo7gReyHajmjj"+
			"VBCzBdPuUmUzahCDM2n8lTiiWQhkrJbHRRRr67q+SgRR8X1FMRu5Q16Oz5xQjBlt"+
			"fDN9+BO0o9MNx/lyMJnOxsq2VUShEUeM+O487dlmC/DbixL/1tVV4pZna0y+9iS0"+
			"dcpBE2CHnTu84LWoXlgD+tIO5kO8rIQ7j8qa58ULxA3cZ8fYQ1S6UT6shUznycl7"+
			"yomxnI3qhz03BQVC9XtOFReNeMpO7aPzVlZ2cdmEBtm/pFh/jhstrsI5DT0d/PNx"+
			"8tR7dFZWytlB8L4elg1D4cdnN7mORs7FK4Kkp75jcN6xFmcdmXCdGYP5SECfkko3"+
			"9B3iYSXXAgMBAAECggEAO+JGlS0xazTAhBdl+byB7ux5OygjAh3BOV/36R+i3d8r"+
			"LtcM+XfyqT/5vG+SOD24urK0i5dwZ+K9CzBAAST019zJIWFJhmzvrtXwM2NwYad2"+
			"6aXqe+m+x85JTUcQAu50yue9Dm8LXZIIrQezyTss5VK1Bt0QY4pCac0+H/JbURvG"+
			"P0u04I9YaqNtUVSw9Mg0ob2yI260jaJ8vffwyXuzOWntFjau/s9fa5qky8IxhQxu"+
			"jBKzPvTOunENcDXAC11S0Vv6xjeyFaZqmaXGCUJLfJMxrZ79QdNXWRB+c7851X5x"+
			"gj2Jm4g5Nj4lH/WcFPdsgw/xI+Xe26BozI1oitIw2QKBgQDT/DNYEHhlB+u3LlFj"+
			"xWQp1JZabzdeR5GWW479MXrCyMbp1AX380P21JaDKKgkrnylj52m3V2+A4g+bTh1"+
			"sXKbAEMtACCElr/Pe+u6wGbKUrYnxaoFYrKoVZ6kSEeNY3KWU9EFfLPsdjc3EJrO"+
			"nJqQsqSZN5sw0WLdSyhZ6Ow5RQKBgQDM3LDR4MbNwGnqpn8W4Cr7001pNkS8o6kT"+
			"FhbNlUApEMObTLwuU0N1Ji6ENiyTcYY18ZmwHzEO6BpmJj71D9fpIxZa5N22cbMp"+
			"g+TpYp0oWb4Gjxdc2SOHJAUnWF8heywivzLaoB1JBPIXGXQgRWFXVEVa9lvSV5rE"+
			"XWg4KNS+awKBgHrjcUBUC/dFqDgA+d3EyFQXkTHL12UoSBXJ7hKJZIkWq3lSGEOt"+
			"GnE0vCw8nXzmSgwQPWxGV9mXYpwN5PUVcUK53DdyI2e5nGhDhlw0vWH8oOxdgjqt"+
			"BLThLZ4+6Hvmcr8Cx+FOcV9bCJc7YQL1dgT8Cr9npOef+pgewb2dcZOhAoGBALvx"+
			"2DKkch8/YKyLFVuKS0T/RSy6QfOCflHpWQBA5j0GiT5H9vjVILUwU0quXNTkOMny"+
			"nhoLc7mGTPncKrQvVdkofJDlGVcRFtqGi5NmCmKDeqePzVI8T7Di5euD0rrnwsUZ"+
			"/nC+pj9gTy3MB1K39CbnRD3AeNsPzghK3ZtwMkWDAoGALq7+cRkRqnjNkMq1+0WJ"+
			"ATqZrdad9P4Uy4Ao4VWGFrsnPrmwQu51Pgf31SkVBk6MJxfD5SITTEQLKJMl72BU"+
			"sTfH6ho16CmShpCeTsExu+LPJpqJU8SFoHQ+TyuWR7ONdb28MtwbQoYguL6B8Sjk"+
			"L069HrnFpRZb7kBi8St7Dw4=";
	
	String SERVER_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2r1Mn1cAkrCbBpo2zpmW"+
			"moehyYwa/Ap05Qwj7ilK/xjunn6WFBrQn/RY1UR1nnAO+GYWE2C9rBhrU9eiQfbl"+
			"g9wQaj1sOEGnZSPNHlqNB+jJpyb1s3IfOGrA7WUDOy+oA3MqFfTIIyGYsFymln4K"+
			"MZ/oDvYWL+GSl78mqs6/vWsPnhiL0hUnOWMtR6E7C5dIuGYMNTgj5xS0h3j3MVfA"+
			"2XqqOAG5Yi7PF/TjPEfxFBq1Z/Ie7HGNP8xi6hwGyPRLv9/NNXlrUej9sRwhkRA9"+
			"3vJzrfu1jECxIGajt/Fr/0KRlKjgelivNC8JaY98sk8e2/GW38KMDw4wCa2TFzuP"+
			"LwIDAQAB";
	
	
	
	public RsaHelper rsaHelper = new RsaHelper();
	{
		try {
			rsaHelper.initKey(CLIENT_PRIVATE_KEY, SERVER_PUBLIC_KEY, 2048);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String signMsg( String json ){
		String hexSign = null ;
		
		try {
			byte[] sign = rsaHelper.signRSA(json.getBytes("UTF-8"), false, "UTF-8") ;
			
			hexSign = StringUtil.bytesToHexString(sign) ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hexSign;
	}
	
	/**
	 * 报文加密
	 * @param json 报文明文
	 * @param key   des密钥
	 * @return
	 * @throws Exception 
	 */
	public String encryptJson( String json, byte[] key) throws Exception{
		String enc_xml = AesHelper.encryMsg(json, key, AesHelper.CIPHER_ALGORITHM_CBC);
		return enc_xml;
	}
	
	
	public String encryptKey( byte[] key){
		
		byte[] enc_key = null ;
		try {
			enc_key = rsaHelper.encryptRSA(key, false, "UTF-8") ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return StringUtil.bytesToHexString(enc_key) ;
	}
	
	/**
	 * des密钥解密
	 * @param hexkey 密钥密文
	 * @return
	 */
	public byte[] decryptKey(String hexkey){
		byte[] key = null ;
		byte[] enc_key = StringUtil.hexStringToBytes(hexkey) ;
		
		try {
			key = rsaHelper.decryptRSA(enc_key, false, "UTF-8") ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return key ;
	}
	
	
	/**
	 * 验签
	 * @param xml 返回明文
	 * @param sign 返回签名
	 * @return
	 */
	public boolean verifyMsgSign(String xml, String sign)
	{
		byte[] bsign = StringUtil.hexStringToBytes(sign) ;
		
		boolean ret = false ;
		try {
			ret = rsaHelper.verifyRSA(xml.getBytes("UTF-8"), bsign, false, "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
}

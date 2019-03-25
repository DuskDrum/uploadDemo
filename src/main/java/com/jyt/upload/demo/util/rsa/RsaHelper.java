package com.jyt.upload.demo.util.rsa;

import com.jyt.upload.demo.util.common.Base64Util;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RsaHelper {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
	public static final int KEYBIT = 2048;
	public static final int RESERVEBYTES = 11;

	private PrivateKey localPrivKey;
	private PublicKey peerPubKey;

	public RsaHelper() {
	}

	/**
	 * 初始化自己的私钥,对方的公钥以及密钥长度.
	 * 
	 * @param localPrivKeyBase64Str
	 *            Base64编码的私钥,PKCS#8编码. (去掉pem文件中的头尾标识)
	 * @param peerPubKeyBase64Str
	 *            Base64编码的公钥. (去掉pem文件中的头尾标识)
	 * @param keysize
	 *            密钥长度, 一般2048
	 */
	public void initKey(String localPrivKeyBase64Str,
			String peerPubKeyBase64Str, int keysize) throws Exception {
		try {
			localPrivKey = getPrivateKey(localPrivKeyBase64Str);
			peerPubKey = getPublicKey(peerPubKeyBase64Str);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化自己的私钥,对方的公钥以及密钥长度.
	 * <p> Create Date: 2015年3月26日 </p>
	 * @param localPrivKey
	 * @param peerPubKey
	 */
	public void initKey( PrivateKey localPrivKey, PublicKey peerPubKey){
		this.localPrivKey = localPrivKey ;
		this.peerPubKey = peerPubKey ;
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public RSAPublicKey getPublicKey(InputStream in) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			return getPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				throw new Exception("关闭输入缓存流出错");
			}

			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				throw new Exception("关闭输入流出错");
			}
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 公钥数据字符串为：pem文件中去掉第一行的“-----BEGIN *** KEY-----”和最后一行的“-----END *** KEY-----的内容
	 * 
	 * @param publicKeyStr 
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public RSAPublicKey getPublicKey(String publicKeyStr) throws Exception {
		try {
			byte[] buffer =  Base64Util.decode(publicKeyStr.getBytes());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory
					.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		}  catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @param in
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public RSAPrivateKey getPrivateKey(InputStream in) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			return getPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				throw new Exception("关闭输入缓存流出错");
			}

			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				throw new Exception("关闭输入流出错");
			}
		}
	}

	/**
	 * 从字符串中加载私钥
	 * 私钥数据字符串：pem文件中去掉第一行的“-----BEGIN *** KEY-----”和最后一行的“-----END *** KEY-----的内容
	 * 
	 * @param privateKeyStr
	 *            私钥数据字符串
	 * @throws Exception
	 *             加载私钥时产生的异常
	 */
	public RSAPrivateKey getPrivateKey(String privateKeyStr) throws Exception {
		try {
			byte[] buffer = Base64Util.decode(privateKeyStr.getBytes());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory
					.generatePrivate(keySpec);
			return privateKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * RAS加密
	 *
	 *            公钥
	 *            待加密信息
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] encryptRSA(byte[] plainBytes, boolean useBase64Code, String charset)
			throws Exception {
		String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
		int KEYBIT = 2048;
		int RESERVEBYTES = 11;
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		int decryptBlock = KEYBIT / 8; // 256 bytes
		int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
		// 计算分段加密的block数 (向上取整)
		int nBlock = (plainBytes.length / encryptBlock);
		if ((plainBytes.length % encryptBlock) != 0) { // 余数非0，block数再加1
			nBlock += 1;
		}
		// 输出buffer, 大小为nBlock个decryptBlock
		ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock
				* decryptBlock);
		cipher.init(Cipher.ENCRYPT_MODE, peerPubKey);
		// cryptedBase64Str =
		// Base64.encodeBase64String(cipher.doFinal(plaintext.getBytes()));
		// 分段加密
		for (int offset = 0; offset < plainBytes.length; offset += encryptBlock) {
			// block大小: encryptBlock 或 剩余字节数
			int inputLen = (plainBytes.length - offset);
			if (inputLen > encryptBlock) {
				inputLen = encryptBlock;
			}
			// 得到分段加密结果
			byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
			// 追加结果到输出buffer中
			outbuf.write(encryptedBlock);
		}
		// 如果是Base64编码，则返回Base64编码后的数组
		if (useBase64Code) {
			return Base64.encodeBase64String(outbuf.toByteArray()).getBytes(
					charset);
		} else {
			return outbuf.toByteArray(); // ciphertext
		}
	}

	/**
	 * RSA解密
	 * 
	 *            私钥
	 * @param cryptedBytes
	 *            待解密信息
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] decryptRSA(byte[] cryptedBytes, boolean useBase64Code,
			String charset) throws Exception {
		String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
		byte[] data = null;

		// 如果是Base64编码的话，则要Base64解码
		if (useBase64Code) {
			data = Base64.decodeBase64(new String(cryptedBytes, charset));
		} else {
			data = cryptedBytes;
		}

		int KEYBIT = 2048;
		int RESERVEBYTES = 11;
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		int decryptBlock = KEYBIT / 8; // 256 bytes
		int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
		// 计算分段解密的block数 (理论上应该能整除)
		int nBlock = (data.length / decryptBlock);
		// 输出buffer, , 大小为nBlock个encryptBlock
		ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock
				* encryptBlock);
		cipher.init(Cipher.DECRYPT_MODE, localPrivKey);
		// plaintext = new
		// String(cipher.doFinal(Base64.decodeBase64(cryptedBase64Str)));
		// 分段解密
		for (int offset = 0; offset < data.length; offset += decryptBlock) {
			// block大小: decryptBlock 或 剩余字节数
			int inputLen = (data.length - offset);
			if (inputLen > decryptBlock) {
				inputLen = decryptBlock;
			}

			// 得到分段解密结果
			byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
			// 追加结果到输出buffer中
			outbuf.write(decryptedBlock);
		}
		outbuf.flush();
		outbuf.close();
		return outbuf.toByteArray();
	}

	/**
	 * RSA签名
	 * 
	 *            私钥
	 *            需要签名的信息
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] signRSA(byte[] plainBytes, boolean useBase64Code,
			String charset) throws Exception {
		String SIGNATURE_ALGORITHM = "SHA1withRSA";
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(localPrivKey);
		signature.update(plainBytes);

		// 如果是Base64编码的话，需要对签名后的数组以Base64编码
		if (useBase64Code) {
			return Base64.encodeBase64String(signature.sign())
					.getBytes(charset);
		} else {
			return signature.sign();
		}
	}

	/**
	 * 验签操作
	 * 
	 *            公钥
	 * @param plainBytes
	 *            需要验签的信息
	 * @param signBytes
	 *            签名信息
	 * @return boolean
	 */
	public boolean verifyRSA(byte[] plainBytes, byte[] signBytes,
			boolean useBase64Code, String charset) throws Exception {
		boolean isValid = false;
		String SIGNATURE_ALGORITHM = "SHA1withRSA";
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(peerPubKey);
		signature.update(plainBytes);

		// 如果是Base64编码的话，需要对验签的数组以Base64解码
		if (useBase64Code) {
			isValid = signature.verify(Base64.decodeBase64(new String(
					signBytes, charset)));
		} else {
			isValid = signature.verify(signBytes);
		}
		return isValid;
	}
}

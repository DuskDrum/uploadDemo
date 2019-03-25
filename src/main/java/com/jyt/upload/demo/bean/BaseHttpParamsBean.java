package com.jyt.upload.demo.bean;

import org.hibernate.validator.constraints.NotBlank;

public class BaseHttpParamsBean {

	/**
	 * token
	 */
	private String token;
	
	/**
	 * 签名
	 */
	@NotBlank(message = "签名不能为空！")
	private String sign;
	
	/**
	 * 密钥密文
	 */
	@NotBlank(message = "密钥密文不能为空！")
	private String keyEnc;
	
	/**
	 * 报文密文
	 */
	@NotBlank(message = "密文信息不能为空！")
	private String enc;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getKeyEnc() {
		return keyEnc;
	}

	public void setKeyEnc(String keyEnc) {
		this.keyEnc = keyEnc;
	}

	public String getEnc() {
		return enc;
	}

	public void setEnc(String enc) {
		this.enc = enc;
	}
}

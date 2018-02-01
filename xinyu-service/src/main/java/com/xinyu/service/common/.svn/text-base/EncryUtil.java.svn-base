package com.xinyu.service.common;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class EncryUtil {
	
	private final static Logger logger = Logger.getLogger(EncryUtil.class);

	/**
	 * 用DES方法加密输入的字节 bytKey需为8字节长，是加密的密码 DES/CBC/PKCS5Padding
	 */
	public static byte[] encryptByDES(byte[] bytP, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		IvParameterSpec zeroIv = new IvParameterSpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cip.init(Cipher.ENCRYPT_MODE, sk, zeroIv);
		return cip.doFinal(bytP);
	}

	public static  String decrypt(String content, String requestCharSet,
	        String encryKey) {

	    if (StringUtils.isNotBlank(encryKey)) {
	        // 解密
	        try {
	            byte[] key = Base64.decodeBase64(encryKey
	                    .getBytes(requestCharSet));
	            // content Base64解码
	            byte[] contentBytes = Base64.decodeBase64(content
	                    .getBytes(requestCharSet));
	         //   byte[] outputData = AESCoder.decrypt(contentBytes, key);
	       //     content = new String(outputData, requestCharSet);
	        } catch (Exception e) {
	        	logger.debug("异常:"+e);
	        }
	    }
	    return content;
	}
	

}

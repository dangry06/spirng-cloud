/**============================================================
 * 文件：com.aspire.migu.cms.job.DES.java
 * 所含类: DES.java
 * 修改记录：
 * 日期                           作者                            内容
 * =============================================================
 * 2017年4月26日       duanyong       创建文件，实现基本功能
 * ============================================================*/

package com.ladon.miguapp.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * <p>DES</p>
 *
 * <p>类用途详细说明</p>
 * 
 * @ClassName DES
 * @author duanyong
 * @version 1.0
 * 
 */

public class DES {
	public static final String S_DES_KEY = new String(Base64.decodeBase64("amJYR0hUeXRCQXhGcWxlU2JK".getBytes()));;
	private static final String S_DEFAULT_KEY = "redcloud";
	private static final String S_DES = "DES";
	private static final String S_MODE = "DES/ECB/PKCS5Padding";
	private static final String S_HEX ="0123456789abcdef";
	
	public static String encode(String key, String input) throws Exception {
	    byte[] data = encrypt(key, input);
	    return toHex(data);
	}
	
	public static byte[] encrypt(String key, String input) throws Exception {
	    return doFinal(key, Cipher.ENCRYPT_MODE, input.getBytes());
	}
	
	private static byte[] doFinal(String key, int opmode, byte[] input) throws Exception {
	    key = key != null ? key : S_DEFAULT_KEY;
	    // DES算法要求有一个可信任的随机数源
	    SecureRandom sr = new SecureRandom();
	    // 从原始密匙数据创建一个DESKeySpec对象
	    DESKeySpec dks = new DESKeySpec(key.getBytes());
	    // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成 一个SecretKey对象
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(S_DES);
	    SecretKey secureKey = keyFactory.generateSecret(dks);
	    // Cipher对象实际完成解密操作
	    Cipher cipher = Cipher.getInstance(S_MODE);
	    // 用密匙初始化Cipher对象
	    // IvParameterSpec param = new IvParameterSpec(IV);
	    // cipher.init(Cipher.DECRYPT_MODE, securekey, param, sr);
	    cipher.init(opmode, secureKey, sr);
	    // 执行加密解密操作
	    return cipher.doFinal(input);
	}
	
	public static String toHex(byte[] buf) {
	    if (buf == null) {
	        return "";
	    }
	    
	    StringBuffer result = new StringBuffer(2 * buf.length);
	    for (int i = 0; i < buf.length; i++) {
	        appendHex(result, buf[i]);
	    }
	    return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b) {
	    sb.append(S_HEX.charAt((b >> 4) & 0x0f)).append(S_HEX.charAt(b & 0x0f));
	}

	public static void main(String[] args) throws Exception {
		//13f3f8ed8b48e731a2017462a41f6c2a
		String imei = "866050027896990";
		System.out.println(S_DES_KEY);
		System.out.println(encode(S_DES_KEY, imei));
	}
	
}

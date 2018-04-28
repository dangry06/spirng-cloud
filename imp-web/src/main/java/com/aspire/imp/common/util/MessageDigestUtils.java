package com.aspire.imp.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {
    
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
    /** 
     * 将摘要信息转换成MD5编码 
     * @param message 摘要信息 
     * @return MD5编码之后的字符串 
     */  
    public static String md5Digest(String message){  
        return digest("MD5",message);  
    }
    
    public static byte[] md5DigestBytes(byte[] bytes){  
    	return digestBytes("MD5", bytes);  
    }
    
    /** 
     * 将摘要信息转换成SHA编码 
     * @param message 摘要信息 
     * @return SHA编码之后的字符串 
     */  
    public static String shaDigest(String message){  
        return digest("SHA",message);  
    }
    
    public static byte[] shaDigestBytes(byte[] bytes){  
    	return digestBytes("SHA", bytes);  
    }
    
    /** 
     * 将摘要信息转换成SHA-256编码 
     * @param message 摘要信息 
     * @return SHA-256编码之后的字符串 
     */  
    public static String sha256Digest(String message){  
        return digest("SHA-256",message);  
    }
    
    public static byte[] sha256DigestBytes(byte[] bytes){  
    	return digestBytes("SHA-256", bytes);  
    }
    
    /** 
     * 将摘要信息转换成SHA-512编码 
     * @param message 摘要信息 
     * @return SHA-512编码之后的字符串 
     */  
    public static String sha512Digest(String message){  
        return digest("SHA-512",message);  
    }
    
    public static byte[] sha512DigestBytes(byte[] bytes){  
    	return digestBytes("SHA-512", bytes);  
    } 
    
    
	/** 
	 * 将摘要信息转换为相应的编码 
	 * @param algorithm 编码类型 
	 * @param message 摘要信息 
	 * @return 相应的编码字符串 
	 */  
	private static String digest(String algorithm, String message){  
	    String result = null;  
	    try {  
	    	MessageDigest messageDigest = MessageDigest.getInstance(algorithm);  
	        result = byte2hexString(messageDigest.digest(message.getBytes("UTF-8")));  
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
	    return result;  
	}
	
	/**
	 * <p>将字节数组进行转码</p>
	 * @param algorithm
	 * @param bytes
	 * @return
	 * @return byte[]
	 */
	private static byte[] digestBytes(String algorithm, byte[] bytes){
		byte[] result = null;  
	    try {  
	    	MessageDigest messageDigest = MessageDigest.getInstance(algorithm);  
	        result = messageDigest.digest(bytes);  
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    }  
	    return result;  
	}
	
    /**
     * <p>将字节数组转换成十六进制字符串</p>
     * @param bytes
     * @return
     * @return String
     */
    private static String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++ ) {
            if (((int)bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int)bytes[i] & 0xff, 16));
        }
        return buf.toString();
        
    }
    
	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param byteArray 要转换的byte[]
	 * @return 十六进制字符串表示形式
	 */
	private static String byteToHexString(byte[] byteArray) {
		String s;
		// 用字节表示就是 16 个字节
		char[] str = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for(int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = byteArray[i]; // 取第 i 个字节
			str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = HEX_DIGITS[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}
	
    public static void main(String[] args) {
		String msg = "admin";
		String enmsg = MessageDigestUtils.md5Digest(msg);
		System.out.println(enmsg);
    }
    
}

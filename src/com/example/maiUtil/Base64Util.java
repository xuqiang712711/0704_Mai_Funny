package com.example.maiUtil;

import java.io.ByteArrayOutputStream;
/**
 * Base64是一种基于64个可打印字符来表示二进制数据的表示方法，Base64常用于在通常处理文本数据的场合，表示、传输、存储一些二进制数据。包括MIME的email，email via MIME,在XML中存储复杂数据.
 * @author beryl
 *
 */
public class Base64Util { 
	/**编码字符*/
	private static final char[] base64EncodeChars = new char[] { 
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
		'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 
		'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 
		'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', 
		'4', '5', '6', '7', '8', '9', '+', '/' }; 
	
	/**解码字符*/
	private static byte[] base64DecodeChars = new byte[] {    
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 
		58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 
		9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, 
		-1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
		39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 }; 
	
	private Base64Util() {} 
	
	/**
	 * ���ַ����Ϊbase64�ַ�
	 * @param str
	 * @return
	 */
	public static String encodeStringToString(String str){
		return encode(str.getBytes());
	}
	
	/**
	 * ��base64�ַ����Ϊ�ַ�
	 * @param str
	 * @return
	 */
	public static String decodeStringToString(String str){
		return new String(decode(str));
	}
	
	/** 
	* ���ֽ��������Ϊ�ַ� 解码byte数组成字符串
	* @param data 
	*/ 
	public static String encode(byte[] data) { 
		StringBuffer sb = new StringBuffer(); 
		int len = data.length; 
		int i 	= 0; 
		int b1, b2, b3; 
		
		while (i < len) { 
			b1 = data[i++] & 0xff; 
			if (i == len) { 
				sb.append(base64EncodeChars[b1 >>> 2]); 
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]); 
				sb.append("=="); 
				break; 
			} 
			b2 = data[i++] & 0xff; 
			if (i == len) { 
				sb.append(base64EncodeChars[b1 >>> 2]); 
				sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]); 
				sb.append("="); 
				break; 
			} 
			b3 = data[i++] & 0xff; 
			sb.append(base64EncodeChars[b1 >>> 2]); 
			sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]); 
			sb.append(base64EncodeChars[b3 & 0x3f]); 
		} 
		return sb.toString(); 
	} 
	
	/** 
	* ��base64�ַ����Ϊ�ֽ����� 解码字符串成byte数组
	* @param str 
	*/ 
	public static byte[] decode(String str) { 
		byte[] data = str.getBytes(); 
		int len 	= data.length; 
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len); 
		int i 		= 0; 
		int b1, b2, b3, b4; 
		
		while (i < len) { 
			/* b1 */ 
			do { 
				b1 = base64DecodeChars[data[i++]]; 
			} while (i < len && b1 == -1); 
			if (b1 == -1) { 
				break; 
			} 
		
			/* b2 */ 
			do { 
				b2 = base64DecodeChars[data[i++]]; 
			} while (i < len && b2 == -1); 
			if (b2 == -1) { 
				break; 
			} 
			buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4))); 
		
			/* b3 */ 
			do { 
				b3 = data[i++]; 
				if (b3 == 61) { 
					return buf.toByteArray(); 
				} 
				b3 = base64DecodeChars[b3]; 
			} while (i < len && b3 == -1); 
			if (b3 == -1) { 
				break; 
			} 
			buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2))); 
		
			/* b4 */ 
			do { 
				b4 = data[i++]; 
				if (b4 == 61) { 
					return buf.toByteArray(); 
				} 
				b4 = base64DecodeChars[b4]; 
			} while (i < len && b4 == -1); 
			if (b4 == -1) { 
				break; 
			} 
			buf.write((int) (((b3 & 0x03) << 6) | b4)); 
		} 
		return buf.toByteArray(); 
	} 
}

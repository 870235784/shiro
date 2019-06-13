package com.tca.chapter5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.junit.Test;

public class BASE64Test {
	
	private static final String DEFAULT_CHARSET = "UTF-8";

	@Test
	public void jdk8_base64() throws IOException {
		// 读取文件
		File file = new File("C:\\Users\\DELL\\Desktop\\password.txt");
		FileInputStream is = new FileInputStream(file);
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[1024];   
        int len = 0;   
        while((len = is.read(b)) != -1){//当没有读取完时，继续读取  
        	sb.append(new String(b, 0, len));
        }
        String content = sb.toString();
        is.close();
        // 将图片使用base64编码
        String base64Result = base64Encode(content);
        
        // 使用base64编码并输出
        byte[] copyByte = base64Decode(base64Result);
        
        System.out.println(content.equals(new String(copyByte)));
        // 输出文件
        FileOutputStream os = new FileOutputStream("C:\\Users\\DELL\\Desktop\\password_copy.txt");
        os.write(copyByte);
        os.close();
        System.out.println("完成!");
	}
	
	@Test
	public void jdk8_base64_test() {
		String content = "hello world";
		String encodeResult = base64Encode(content);
		String decodeResult = new String(base64Decode(encodeResult));
		System.out.println(content.equals(decodeResult));
	}
	
	/**
	 * 使用BASE64编码
	 * @param content
	 * @return
	 */
	private String base64Encode(String content) {
		return base64Encode(content, DEFAULT_CHARSET);
	}
	
	/**
	 * 使用BASE64编码
	 * @param content
	 * @return
	 */
	private String base64Encode(String content, String charsetName) {
		try {
			return Base64.getEncoder().encodeToString(content.getBytes(charsetName));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 使用BASE64解码
	 * @param content
	 * @return
	 */
	private byte[] base64Decode(String content) {
		return Base64.getDecoder().decode(content);
	}
}

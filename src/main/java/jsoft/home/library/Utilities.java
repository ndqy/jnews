package jsoft.home.library;

import javax.servlet.ServletRequest;
import net.htmlparser.jericho.*;


public class Utilities {
	public static byte getByteParam(ServletRequest request, String name) {
		byte value = 0;
		
		String str_value = request.getParameter(name);
		
		if(str_value != null && !str_value.equalsIgnoreCase("")) {
			value = Byte.parseByte(str_value);
		}
		return value;
	}
	public static Short getShortParam(ServletRequest request, String name) {
		Short value = 0;
		
		String str_value = request.getParameter(name);
		
		if(str_value != null && !str_value.equalsIgnoreCase("")) {
			value = Short.parseShort(str_value);
		}
		return value;
	}
	/**
	 * Phương thức lấy số trang, ngầm định là 1
	 * @param request
	 * @param name
	 * @return
	 */
	public static Short getShortPage(ServletRequest request, String name) {
		Short value = 1;
		
		String str_value = request.getParameter(name);
		
		if(str_value != null && !str_value.equalsIgnoreCase("")) {
			value = Short.parseShort(str_value);
		}
		return value;
	}
	public static int getIntParam(ServletRequest request, String name) {
		int value = 0;
		
		String str_value = request.getParameter(name);
		
		if(str_value != null && !str_value.equalsIgnoreCase("")) {
			value = Integer.parseInt(str_value);
		}
		return value;
	}
	public static String encode(String str_unicode) {
		return CharacterReference.encode(str_unicode);
	}
	public static String decode(String str_html) {
		return CharacterReference.decode(str_html);
	}
}

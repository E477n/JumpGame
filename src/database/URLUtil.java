package database;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class URLUtil {//������ɾ��properties�����õ�

	private static File getClassFile(Class<?> clazz) {
		URL path = clazz.getResource(clazz.getName().substring(
				clazz.getName().lastIndexOf(".") + 1)
				+ ".class");
		if (path == null) {
			String name = clazz.getName().replaceAll("[.]", "/");
			path = clazz.getResource("/" + name + ".class");
		}
		return new File(path.getFile());
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassFilePath(Class<?> clazz) {
		try {
			return java.net.URLDecoder.decode(getClassFile(clazz)
					.getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static File getClassPathFile(Class<?> clazz) {
		File file = getClassFile(clazz);
		for (int i = 0, count = clazz.getName().split("[.]").length; i < count; i++)
			file = file.getParentFile();
		if (file.getName().toUpperCase().endsWith(".JAR!")) {
			file = file.getParentFile();
		}
		return file;

	}

	/**
	 * 获得classes的根目录
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassesPath(Class<?> clazz) {
		try {
			return java.net.URLDecoder.decode(getClassPathFile(clazz)
					.getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {
		System.out.println(getClassFilePath(URLUtil.class));
		System.out.println(getClassesPath(URLUtil.class));
	}
}

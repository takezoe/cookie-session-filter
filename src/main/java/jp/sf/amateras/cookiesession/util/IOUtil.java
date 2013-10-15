package jp.sf.amateras.cookiesession.util;

import java.io.Closeable;

public class IOUtil {

	public static void closeQuietly(Closeable closeable){
		if(closeable != null){
			try {
				closeable.close();
			} catch(Exception ex){
				;
			}
		}
	}

}

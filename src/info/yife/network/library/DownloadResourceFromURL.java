package info.yife.network.library;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;


/**
 * @author yife.greenspa
 *
 */
public class DownloadResourceFromURL {
	final String DEBUG_TAG = "DownloadResourceFromURL.java in info.yife.network.library";
	
	URL url;
	Integer maxLength;
	InputStream inputStream;
	
	final Integer READ_TIMEOUT_MILLSEC = 10000;
	final Integer CONNECTION_TIMEOUT_MILLSEC = 15000;
	final String REQUEST_METHOD = "GET";

	
	/**
	 * コンストラクタ。取得したいURLを指定する。
	 * 
	 * @param urlStr
	 * @throws MalformedURLException
	 */
	public DownloadResourceFromURL(String urlStr) throws MalformedURLException{
		this.url = new URL(urlStr);
		this.inputStream = null;
	}
	
	/**
	 * this.urlに格納されているURLにアクセスし、その内容を指定されたエンコードで解釈した文字列を返すラッパーメソッド。
	 * 
	 * @param encode
	 * @return URLのリソースを指定されたエンコードで文字列として解釈したもの
	 * @throws IOException
	 */
	public String fetchAsString(String encode) throws IOException{
		return readAsString(fetchData(), encode);
	}
	
	/**
	 * this.urlに格納されているURLにアクセスし、その内容をバイナリとして返すメソッド。　
	 * 
	 * @return URL先のリソースをバイナリとして解釈したもの
	 * @throws IOException
	 */
	private ByteArrayBuffer fetchData() throws IOException{
		
		try{
			HttpURLConnection httpConnection = (HttpURLConnection) this.url.openConnection();
			httpConnection.setReadTimeout(this.READ_TIMEOUT_MILLSEC);
			httpConnection.setConnectTimeout(this.CONNECTION_TIMEOUT_MILLSEC);
			httpConnection.setRequestMethod(this.REQUEST_METHOD);
			httpConnection.setDoInput(true);
			
			httpConnection.connect();
			int responseCode = httpConnection.getResponseCode();
			Log.d(this.DEBUG_TAG, "The response is " + responseCode);
			inputStream = new BufferedInputStream(httpConnection.getInputStream());
			
			ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(500);
			int current = 0;
			while( (current = inputStream.read()) != -1 ){
				byteArrayBuffer.append( (byte)current );
			}
			
			return byteArrayBuffer;
		}finally{
			if(inputStream != null){
				inputStream.close();
			}
		}
	}
	
	/**
	 * バイナリ（ByteArrayBuffer）を受け取り、指定されたエンコードで解釈して返すメソッド。
	 * 
	 * @param byteArrayBuffer
	 * @param encode
	 * @return 受け取ったバイナリを、指定されたエンコードで解釈した文字列
	 * @throws IOException
	 */
	private String readAsString(ByteArrayBuffer byteArrayBuffer, String encode) throws IOException{
		Log.d(this.DEBUG_TAG, "readAsString. Encoding is " + encode);
		String tmpString = new String(byteArrayBuffer.toByteArray(), encode);
		return tmpString;
	}
}

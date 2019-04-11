package lycee.httpclient;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TinyHttpClientTest {

	@Test
	public void test_client_get() {
		final TinyHttpClient client = new TinyHttpGet();

		client.setUrl("http://httpbin.org/get");

		final Map<String, String> params = new HashMap<>();
		params.put("abcde", "123");
		params.put("x", "123");
		params.put("y", "abc");
		client.setParameters(params);
		try {
			final HttpResponse response = client.call();

			System.out.println(response.getBody());

		} catch (final IOException e) {
			fail("ERROR!!");
		}
	}



	@Test
	public void test_client_post() {
		final TinyHttpClient client = new TinyHttpPost();

		client.setUrl("http://httpbin.org/post");

		final Map<String, String> params = new HashMap<>();
		params.put("abcde", "123");
		params.put("x", "123");
		params.put("y", "abc");
		client.setParameters(params);
		try {
			final HttpResponse response = client.call();

			System.out.println(response.getBody());

		} catch (final IOException e) {
			fail("ERROR!!");
		}
	}

	@Test
	public void test_raw_get() throws Exception {
		final HttpURLConnection conn ;
		final URL url = new URL("http://httpbin.org/get?abcde=123&x=123&y=abc");
		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setDoOutput(true);

		try {
			conn.connect();

			final int status = conn.getResponseCode();

			if(status==HttpURLConnection.HTTP_OK) {
				String encode = conn.getContentEncoding();
				if(encode==null) {
					encode = "UTF-8";
				}

				final Map<String, List<String>> headers = conn.getHeaderFields();

				System.out.println("HEADER ============================");
				headers.forEach((k,v)-> {
					System.out.println(k);
					v.forEach(value->{
						System.out.println("\t" + value);
					});
				});

				try(InputStream is = conn.getInputStream();
						InputStreamReader isr = new InputStreamReader(is, encode);
						BufferedReader reader = new BufferedReader(isr);
						) {
					String line;

					System.out.println("CONTENTS ============================");
					while( (line=reader.readLine())!=null ) {
						System.out.println(line);
					}
				}
			}else {
				fail("ERROR!!!["+status+"]");
			}
		}finally {
			if(conn!=null) {
				conn.disconnect();
			}
		}
	}

	@Test
	public void test_raw_post() throws Exception {
		final HttpURLConnection conn ;
		final URL url = new URL("http://httpbin.org/post");
		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		try {
			// POST
			try (OutputStream os = conn.getOutputStream()) {
				os.write("abcde=123&x=123&y=abc".getBytes("UTF-8"));
				os.flush();
			}
			conn.connect();

			final int status = conn.getResponseCode();

			if(status==HttpURLConnection.HTTP_OK) {
				String encode = conn.getContentEncoding();
				if(encode==null) {
					encode = "UTF-8";
				}

				final Map<String, List<String>> headers = conn.getHeaderFields();

				System.out.println("HEADER ============================");
				headers.forEach((k,v)-> {
					System.out.println(k);
					v.forEach(value->{
						System.out.println("\t" + value);
					});
				});

				try(InputStream is = conn.getInputStream();
						InputStreamReader isr = new InputStreamReader(is, encode);
						BufferedReader reader = new BufferedReader(isr);
						) {
					String line;

					System.out.println("CONTENTS ============================");
					while( (line=reader.readLine())!=null ) {
						System.out.println(line);
					}
				}
			}else {
				fail("ERROR!!!["+status+"]");
			}
		}finally {
			if(conn!=null) {
				conn.disconnect();
			}
		}
	}

}

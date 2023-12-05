/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package org.ormma.controller;

import aje.android.sdk.EmptyResponseException;
import aje.android.sdk.LogLevel;
import aje.android.sdk.Logger;
import android.content.Context;
import android.os.StatFs;
import android.util.Log;
import android.webkit.URLUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ormma.view.OrmmaView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The Class OrmmaAssetController. This class handles asset management for orrma
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class OrmmaAssetController extends OrmmaController {
	private static final String LOG_TAG = "OrmmaAccessConreoller";
	
	public static final String INJECTION_MARKER = "<!-- InjECtiOn PosItIon !&@^#%$ -->";

	/**
	 * Instantiates a new ormma asset controller.
	 *
	 * @param adView the ad view
	 * @param c      the c
	 */
	public OrmmaAssetController(OrmmaView adView, Context c) {
		super(adView, c);
	}

	/**
	 * Copy text file from jar into asset directory.
	 *
	 * @param alias  the alias to store it in
	 * @param source the source
	 * @return the path to the copied asset
	 * @throws OrmmaException operation failed
	 */
	public String copyTextFromJarIntoAssetDir(String alias, String source) throws OrmmaException {
		InputStream in = null;
		try {
			URL url = OrmmaAssetController.class.getClassLoader().getResource(
					source);
			String file = url.getFile();
			if (file.startsWith("file:")) {
				file = file.substring(5);
			}
			int pos = file.indexOf("!");
			if (pos > 0) {
				file = file.substring(0, pos);
			}
			JarFile jf = new JarFile(file);
			JarEntry entry = jf.getJarEntry(source);
			in = jf.getInputStream(entry);
			String name = writeToDisk(in, alias, false);
			return name;
		} catch (Exception e) {
			throw new OrmmaException("Couldn't access to resource", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					//Do nothing
				}
			}
		}
	}

	/**
	 * Adds an asset.
	 *
	 * @param alias the alias
	 * @param url   the url
	 * @throws OrmmaException operation failed
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void addAsset(String alias, String url) throws OrmmaException {
		HttpEntity entity = getHttpEntity(url);
		InputStream in = null;
		try {
			in = entity.getContent();
			writeToDisk(in, alias, false);
			String str = "OrmmaAdController.addedAsset('" + alias + "' )";
			ormmaView.injectJavaScript(str);
		} catch (Exception e) {
			throw new OrmmaException("Cannot add asset", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					//Do nothing
				}
			}
		}
		try {
			entity.consumeContent();
		} catch (Exception e) {
			//Do nothing
		}
	}

	/**
	 * Store picture from given URL to the local storage
	 *
	 * @param url the url
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void storePicture(String url) {
		try {
			HttpEntity entity = getHttpEntity(url);
			InputStream in = entity.getContent();

			File dir = new File("/sdcard/ORMMAGallery/");
			dir.mkdirs();

			Header contentTypeHeader = entity.getContentType();
			String contentType = null;
			if (contentTypeHeader != null) {
				contentType = contentTypeHeader.getValue();
			}
			String fileName = URLUtil.guessFileName(url, null, contentType);
			writeToDisk(in, fileName, false);

			try {
				entity.consumeContent();
			} catch (Exception e) {
				//Do nothing
			}
		} catch (Exception e) {
			ormmaView.injectJavaScript("Ormma.fireError(\"storePicture\",\"File is not saved in the devicelocal storage\")");
		}
	}

	/**
	 * pulls a resource from the web
	 *
	 * @param url the url
	 * @return the http entity
	 * @throws OrmmaException operation failed
	 */
	private static HttpEntity getHttpEntity(String url) throws OrmmaException {
		HttpEntity entity;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);
			entity = response.getEntity();
		} catch (Exception e) {
			throw new OrmmaException("Couldn't get URL", e);
		}
		return entity;
	}

	/**
	 * Cache remaining.
	 *
	 * @return the cache remaining
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public int cacheRemaining() {
		File filesDir = context.getFilesDir();
		StatFs stats = new StatFs(filesDir.getPath());
		int free = stats.getFreeBlocks() * stats.getBlockSize();
		return free;
	}

	/**
	 * Write a stream to disk.
	 *
	 * @param in                     the input stream
	 * @param file                   the file to store it in
	 * @param storeInHashedDirectory use a hashed directory name
	 * @return the path where it was stired
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException           Signals that an I/O exception has occurred.
	 */
	public String writeToDisk(InputStream in, String file, boolean storeInHashedDirectory) throws IllegalStateException, IOException {
		byte buff[] = new byte[1024];

		MessageDigest digest = null;
		if (storeInHashedDirectory) {
			try {
				digest = java.security.MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				//Do nothing
			}
		}
		FileOutputStream out = null;
		try {
			out = getAssetOutputString(file);
			do {
				int numread = in.read(buff);
				if (numread <= 0) {
					break;
				}

				if (storeInHashedDirectory && digest != null) {
					digest.update(buff);
				}
				out.write(buff, 0, numread);
			} while (true);
			out.flush();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					//Do nothing
				}
			}
		}
		String filesDir = getFilesDir();

		if (storeInHashedDirectory && digest != null) {
			filesDir = moveToAdDirectory(file, filesDir, asHex(digest));
		}
		return filesDir + file;

	}

	/**
	 * Write an input stream to a file wrapping it with ormma stuff
	 *
	 * @param in                     the input stream
	 * @param file                   the file to store it in
	 * @param storeInHashedDirectory use a hashed directory name
	 * @param injection injection javascript
	 * @param bridgePath path to bridge file
	 * @param ormmaPath path to ormma.js
	 * @param mraidPath path to mraid.js
	 * @return the path where it was stored
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException           Signals that an I/O exception has occurred.
	 * @throws aje.android.sdk.EmptyResponseException Ad Server returned empty response
	 */
	public String writeToDiskWrap(InputStream in, String file, boolean storeInHashedDirectory, String injection,
	                              String bridgePath, String ormmaPath, String mraidPath) throws IllegalStateException, IOException, EmptyResponseException {
		byte buff[] = new byte[1024];

		MessageDigest digest = null;
		if (storeInHashedDirectory) {
			try {
				digest = java.security.MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				//Do nothing
			}
		}

		// check for html tag in the input
		ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
		FileOutputStream out = null;
		try {
			do {
				int numread = in.read(buff);

				if (numread <= 0) {
					break;
				}

				if (storeInHashedDirectory && digest != null) {
					digest.update(buff);
				}

				fromFile.write(buff, 0, numread);

			} while (true);

			String wholeHTML = fromFile.toString();
			if (LogLevel.isLoggingEnabled(LogLevel.DEBUG)) {
				Logger.d(LOG_TAG, "Received ad server answer: "+wholeHTML);
			}
			
			if (wholeHTML == null || "".equals(wholeHTML)) {
				throw new EmptyResponseException();
			}

			boolean hasHTMLWrap = wholeHTML.contains("<html");

			out = getAssetOutputString(file);

			if (hasHTMLWrap) {
				wholeHTML = updateWholeHTML(wholeHTML, bridgePath, ormmaPath, mraidPath);
				out.write(wholeHTML.getBytes());
			} else {
				out.write("<html>".getBytes());
				out.write("<head>".getBytes());
				out.write("<meta name='viewport' content='user-scalable=no initial-scale=1.0' />"
						.getBytes());
				out.write("<title>Advertisement</title> ".getBytes());

				out.write(("<script src=\"file:/" + bridgePath + "\" type=\"text/javascript\"></script>")
						.getBytes());
				out.write(("<script src=\"file:/" + ormmaPath + "\" type=\"text/javascript\"></script>").getBytes());
				out.write(("<script src=\"file:/" + mraidPath + "\" type=\"text/javascript\"></script>").getBytes());

				if (injection != null) {
					out.write("<script type=\"text/javascript\">".getBytes());
					out.write(injection.getBytes());
					out.write("</script>".getBytes());
				}
				out.write("</head>".getBytes());
				out.write("<body style=\"margin:0; padding:0; overflow:hidden; background-color:transparent;\">"
						.getBytes());
				out.write("<div align=\"center\"> ".getBytes());
				out.write(fromFile.toByteArray());
				out.write("</div> ".getBytes());
				out.write("</body> ".getBytes());
				out.write("</html> ".getBytes());
			}

			out.flush();
		} finally {
			if (fromFile != null) {
				try {
					fromFile.close();
				} catch (Exception e) {
					//Do nothing
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					//Do nothing
				}
			}
		}
		String filesDir = getFilesDir();

		if (storeInHashedDirectory && digest != null) {
			filesDir = moveToAdDirectory(file, filesDir, asHex(digest));
		}
		return filesDir;
	}
	
	private String updateWholeHTML(String html, String bridgePath, String ormmaPath, String mraidPath) {
		html = updateScriptReference(html, "ormma_bridge.js", "file:/" + bridgePath);
		html = updateScriptReference(html, "ormma.js", "file:/" + ormmaPath);
		html = updateScriptReference(html, "mraid.js", "file:/" + mraidPath);
		return html;
	}
	
	private String updateScriptReference(String html, String pattren, String value) {
		if (html.contains(pattren)) {
			return html.replace(pattren, value);
		} else {
			if (!html.contains(INJECTION_MARKER)) {
				html = html.replace("<head>", "<head>" + INJECTION_MARKER);
			}
			return html.replace(INJECTION_MARKER, "<script src=\"" + value + "\"></script>" + INJECTION_MARKER);
		}
	}

	/**
	 * Move a file to ad directory.
	 *
	 * @param fn       the filename
	 * @param filesDir the files directory
	 * @param subDir   the sub directory
	 * @return the path where it was stored
	 */
	private String moveToAdDirectory(String fn, String filesDir, String subDir) {
		File file = new File(filesDir + java.io.File.separator + fn);
		File adDir = new File(filesDir + java.io.File.separator + "ad");
		adDir.mkdir();
		File dir = new File(filesDir + java.io.File.separator + "ad"
				+ java.io.File.separator + subDir);
		dir.mkdir();
		file.renameTo(new File(dir, file.getName()));
		return dir.getPath() + java.io.File.separator;
	}

	/**
	 * The Constant HEX_CHARS.
	 */
	private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',};

	/**
	 * Builds a hex string
	 *
	 * @param digest the digest
	 * @return the string
	 */
	private String asHex(MessageDigest digest) {
		byte[] hash = digest.digest();
		char buf[] = new char[hash.length * 2];
		for (int i = 0, x = 0; i < hash.length; i++) {
			buf[x++] = HEX_CHARS[(hash[i] >>> 4) & 0xf];
			buf[x++] = HEX_CHARS[hash[i] & 0xf];
		}
		return new String(buf);
	}

	/**
	 * Gets the files dir for the activity.
	 *
	 * @return the files dir
	 */
	private String getFilesDir() {
		return context.getFilesDir().getPath();
	}

	/**
	 * Gets the asset output string.
	 *
	 * @param asset the asset
	 * @return the asset output string
	 * @throws FileNotFoundException the file not found exception
	 */
	public FileOutputStream getAssetOutputString(String asset)
			throws FileNotFoundException {
		File dir = getAssetDir(getAssetPath(asset));
		dir.mkdirs();
		File file = new File(dir, getAssetName(asset));
		return new FileOutputStream(file);
	}

	/**
	 * Removes the asset.
	 *
	 * @param asset the asset
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void removeAsset(String asset) {
		File dir = getAssetDir(getAssetPath(asset));
		dir.mkdirs();
		File file = new File(dir, getAssetName(asset));
		file.delete();

		String str = "OrmmaAdController.assetRemoved('" + asset + "' )";
		ormmaView.injectJavaScript(str);
	}

	/**
	 * Gets the asset dir.
	 *
	 * @param path the path
	 * @return the asset dir
	 */
	private File getAssetDir(String path) {
		File filesDir = context.getFilesDir();
		File newDir = new File(filesDir.getPath() + java.io.File.separator + path);
		return newDir;
	}

	/**
	 * Gets the asset path.
	 *
	 * @param asset the asset
	 * @return the asset path
	 */
	private String getAssetPath(String asset) {
		int lastSep = asset.lastIndexOf(java.io.File.separatorChar);
		String path = "/";

		if (lastSep >= 0) {
			path = asset.substring(0,
					asset.lastIndexOf(java.io.File.separatorChar));
		}
		return path;
	}

	/**
	 * Gets the asset name.
	 *
	 * @param asset the asset
	 * @return the asset name
	 */
	private String getAssetName(String asset) {
		int lastSep = asset.lastIndexOf(java.io.File.separatorChar);
		String name = asset;

		if (lastSep >= 0) {
			name = asset.substring(asset
					.lastIndexOf(java.io.File.separatorChar) + 1);
		}
		return name;
	}

	/**
	 * Gets the asset path.
	 *
	 * @return the asset path
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public String getAssetPath() {
		return "file://" + context.getFilesDir() + "/";
	}

	/**
	 * Delete directory.
	 *
	 * @param path the path
	 * @return true, if successful
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	static public boolean deleteDirectory(String path) {
		return path != null && deleteDirectory(new File(path));
	}

	/**
	 * Delete directory.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	static public boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					deleteDirectory(file);
				} else {
					file.delete();
				}
			}
		}
		return (path.delete());
	}

	/**
	 * Delete old ads.
	 */
	public void deleteOldAds() {
		String filesDir = getFilesDir();
		File adDir = new File(filesDir + java.io.File.separator + "ad");
		deleteDirectory(adDir);
	}

	/** {@inheritDoc} */
	@Override
	public void stopAllListeners() {
	}

	public static String getHttpContent(String url) throws Exception {
		HttpEntity entity = getHttpEntity(url);
		InputStream in = entity.getContent();

		byte buff[] = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		do {
			int numread = in.read(buff);
			if (numread <= 0)
				break;
			out.write(buff, 0, numread);
		} while (true);

		return out.toString();
	}
}

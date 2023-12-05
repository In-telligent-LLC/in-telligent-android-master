/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */


package org.ormma.controller.util;

import android.os.Bundle;

public class OrmmaUtils {

	private static final String CHAR_SET = "ISO-8859-1";

	static public String byteToHex(byte b) {
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		char[] array = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
		return new String(array);
	}

	/**
	 * Does String encoding
	 *
	 * @param str - String to be encoded
	 * @return hex string
	 */
	public static String convert(String str) {
		try {
			final byte[] array = str.getBytes();

			StringBuilder sb = new StringBuilder();
			for (byte anArray : array) {
				if ((anArray & 0x80) > 0) {
					sb.append("%").append(byteToHex(anArray));
				} else {
					sb.append((char) (anArray));
				}
			}
			return new String(sb.toString().getBytes(), CHAR_SET);
		} catch (Exception ex) {
			//Do nothing?
		}
		return null;
	}

	/**
	 * Get data from bundle
	 *
	 * @param key  - key to fetch data
	 * @param key  - key to fetch data
	 * @param data - Bundle containing data
	 * @return data
	 */
	public static String getData(String key, Bundle data) {
		return data.getString(key);
	}
}

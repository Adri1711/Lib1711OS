package com.adri1711.util;

import java.util.Base64;

public class Util {
	public static String dec(String s) {
		return new String(Base64.getDecoder().decode(s));
	}
}

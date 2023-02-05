package com.longdict.service.vocabulary.external;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public final class FileFetchingService {
	public static byte[] get(String path) {
		try (BufferedInputStream in = new BufferedInputStream(new URL(path).openStream());
				ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(1024)) {
			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				byteOutputStream.write(dataBuffer, 0, bytesRead);
			}
			return byteOutputStream.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
}

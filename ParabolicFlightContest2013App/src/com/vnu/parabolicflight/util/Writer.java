package com.vnu.parabolicflight.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.annotation.SuppressLint;
import android.os.Environment;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Writer {

	private String fileName;
	private File dataFile;

	public void setNewFileName() {
		this.fileName = "parabolic-flight-"
				+ String.valueOf(System.currentTimeMillis()) + ".txt";
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void creatNewFile() {

		setNewFileName();

		try {
			dataFile = new File(Environment.getExternalStorageDirectory()
					.toString(), fileName);

			dataFile.createNewFile();
		} catch (Exception e) {
		}
	}

	@SuppressLint("SimpleDateFormat")
	public void appendFile(String fileName, int type, float X, float Y, float Z) {
		dataFile = new File(Environment.getExternalStorageDirectory()
				.toString(), fileName);

		try {
			FileOutputStream fOut = new FileOutputStream(dataFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

			if (type == 0) {
				long currentTime = System.currentTimeMillis();
				DateFormat df = new SimpleDateFormat("HH:mm:ss");

				myOutWriter.append(df.format(currentTime) + " " + currentTime + "\n");
			}

			myOutWriter.append(X + " " + Y + " " + Z + "\n");
			myOutWriter.close();

			fOut.close();
		} catch (Exception e) {

		}
	}
}
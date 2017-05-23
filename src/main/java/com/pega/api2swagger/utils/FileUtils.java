package com.pega.api2swagger.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.google.common.io.Files;
import io.swagger.models.Swagger;
import io.swagger.util.Json;

public class FileUtils {
	
	public static void writeSwaggerToFile(String absoluteFilePath, Swagger finalSwagger) {
		File file = new File(absoluteFilePath);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			Files.write(Json.pretty(finalSwagger), file, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

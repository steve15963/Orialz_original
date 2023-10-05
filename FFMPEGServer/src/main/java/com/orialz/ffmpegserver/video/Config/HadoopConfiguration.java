package com.orialz.ffmpegserver.video.Config;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HadoopConfiguration {

	static final Configuration CONFIGURATION = new Configuration();
	static Path path;

	static String root ="hdfs://cluster1:9000/";

	static FileSystem fileSystem;

	static {
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		path = new Path(root);
		try {
			fileSystem = path.getFileSystem(CONFIGURATION);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public Configuration getConfiguration(){
		return CONFIGURATION;
	}

	@Bean
	public Path getPath(){
		return path;
	}

	@Bean
	public FileSystem getFileSystem(){
		return fileSystem;
	}


}

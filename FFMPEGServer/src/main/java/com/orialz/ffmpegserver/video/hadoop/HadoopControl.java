package com.orialz.ffmpegserver.video.hadoop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.orialz.ffmpegserver.video.Config.HadoopConfiguration;
import com.orialz.ffmpegserver.video.Vo.Entity.Job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HadoopControl {

	HadoopConfiguration hadoopConfiguration;

	static String root = "hdfs://cluster1:9000/";


	public String HDFSUploadInputTxt(Job job) throws IOException {
		FileSystem fileSystem = hadoopConfiguration.getFileSystem();
		String local = job.getLocalInputPath();
		String remote = job.getRemoteInputPath();
		fileSystem.copyFromLocalFile(new Path(local),new Path(root+remote));
		return root+remote;
	}

	public boolean HDFSDownloadOutputTxt(Job job) throws IOException {
		FileSystem fileSystem = hadoopConfiguration.getFileSystem();
		String local = job.getRemoteOutputPath();
		String remote = job.getLocalOutputPath();
		fileSystem.copyToLocalFile(new Path(root+remote),new Path(local));
		return true;
	}

	public void MapreduceRunJob(Job nowJob) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(
			"hadoop",
			"jar",
			"/home/hadoop/jenkins/workspace/hadoop/build/libs/Hadoop-Gradle-1.0-SNAPSHOT.jar",
			"com.orialz.ImgToJson",
			"input.txt",
			"output"
		);
		Process process = processBuilder.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			log.info("data : {}",line);
		}
		int exitCode = process.waitFor();
	}

	public void RemoveLocalInput(){
	}

	public void RemoveLocalOutput(){

	}
}

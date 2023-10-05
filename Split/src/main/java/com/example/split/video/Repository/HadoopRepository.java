package com.example.split.video.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;

import com.example.split.config.HadoopConfiguration;
import com.example.split.video.domain.Entity.Job;
import com.example.split.video.domain.Entity.Video;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class HadoopRepository {

	HadoopConfiguration hadoopConfiguration;

	static String remoteRoot = "hdfs://cluster1:9000";

	public String HDFSUpload(Job job) throws IOException {
		String localRootPath = job.getRoot();
		String member = job.getMember();
		String hash = job.getHash();

		FileSystem fileSystem = hadoopConfiguration.getFileSystem();

		Path remoteMemberPath = new Path("/user/hadoop/"+member);
		if(fileSystem.exists(remoteMemberPath) && fileSystem.isDirectory(remoteMemberPath)){
			fileSystem.create(remoteMemberPath);
		}
		Path remoteHashPath = new Path("/user/hadoop/" + member + "/" + hash);
		if(fileSystem.exists(remoteHashPath)&& fileSystem.isDirectory(remoteHashPath)){
			fileSystem.create(remoteHashPath);
		}
		File localImagePath = new File(localRootPath + "/" + member + "/" + hash + "/output");
		File[] files = localImagePath.listFiles();

		//input
		fileSystem.copyFromLocalFile(
			new Path(localRootPath + "/" + member + "/" + hash + "/frame_timeStamp.txt"),
			new Path(remoteRoot + "/" + member + "/" + hash + "/frame_timeStamp.txt")
		);
		File input = new File(localRootPath + "/" + member + "/" + hash + "/frame_timeStamp.txt");
		input.delete();
		//img
		for(int i = 0; i < files.length; i++){
			fileSystem.copyFromLocalFile(
				new Path(localRootPath + "/" + member + "/" + hash + "/output/" + files[i].getName()),
				new Path(remoteRoot + "/" + member + "/" + hash + "/output/" + files[i].getName())
			);
			files[i].delete();
		}
		return remoteRoot + "/" + member + "/" + hash;
	}

	public boolean HDFSDownload(Job job) throws IOException {
		String localRootPath = job.getRoot();
		String member = job.getMember();
		String hash = job.getHash();
		FileSystem fileSystem = hadoopConfiguration.getFileSystem();
		Path remoteJson = new Path("/user/hadoop/" + member + "/" + hash + "/json/part-r-00000");
		Path localJson = new Path(localRootPath + "/" + member +"/"+hash+"/json/json.txt");
		fileSystem.copyToLocalFile(remoteJson,localJson);
		fileSystem.removeAcl(new Path("/user/hadoop/" + member + "/" + hash));
		return true;
	}

	public boolean MapreduceRunJob(Job job) throws IOException, InterruptedException {
		String localRootPath = job.getRoot();
		String member = job.getMember();
		String hash = job.getHash();
		Video video = job.getVideo();
		ProcessBuilder processBuilder = new ProcessBuilder("hadoop",
			"jar",
			"/home/hadoop/jenkins/workspace/hadoop/build/libs/Hadoop-Gradle-1.0-SNAPSHOT.jar",
			"com.orialz.imgToJson",
			member+"/"+hash+"/frame_timeStamp.txt",
			member+"/"+hash+"/json"
		);
		Process start = processBuilder.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(start.getInputStream()));
		BufferedReader er = new BufferedReader(new InputStreamReader(start.getErrorStream()));


		String line;
		while((line = br.readLine()) != null){
			// System.out.println(line);
			log.info("Hadoop out{}", line);
		}

		while((line = er.readLine()) != null){
			// System.out.println(line);
			log.info("Hadoop Err{}", line);
		}
		if(start.waitFor() == 0)
			return false;
		return true;
	}
}

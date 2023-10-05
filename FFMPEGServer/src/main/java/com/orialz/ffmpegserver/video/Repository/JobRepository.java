package com.orialz.ffmpegserver.video.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orialz.ffmpegserver.video.Vo.Entity.Job;

public interface JobRepository extends JpaRepository<Job,Long> {
	public Optional<Job> findFirstByOrderByIdAsc();
}

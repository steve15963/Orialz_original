package com.example.split.video.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.split.video.domain.Entity.Job;

public interface JobRepository extends JpaRepository<Job,Long> {
	public Optional<Job> findFirstByOrderByIdAsc();
}

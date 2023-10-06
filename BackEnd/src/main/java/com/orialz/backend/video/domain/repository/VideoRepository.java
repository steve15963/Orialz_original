package com.orialz.backend.video.domain.repository;

import com.orialz.backend.video.domain.entity.Video;
import com.orialz.backend.video.dto.response.VideoListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {


    @Query(nativeQuery = true , value = "(SELECT * FROM (SELECT * FROM video WHERE title LIKE %:keyword% ORDER BY view DESC limit 100) AS subquery1)" +
            "UNION " +
            "(SELECT * FROM (SELECT * FROM video WHERE category = :keyword ORDER BY view DESC limit 100) AS subquery2)" )
    List<Video> findSearchedVideo(@Param("keyword") String keyword);

    List<Video> findByMember_Id(Long memberId);
}

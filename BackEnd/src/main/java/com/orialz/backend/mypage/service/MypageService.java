package com.orialz.backend.mypage.service;

import com.orialz.backend.comment.domain.entity.Comment;
import com.orialz.backend.comment.domain.repository.CommentRepository;
import com.orialz.backend.mypage.dto.response.MypageCommentListResponseDto;
import com.orialz.backend.mypage.dto.response.MypageVideoListResponseDto;
import com.orialz.backend.video.domain.entity.Video;
import com.orialz.backend.video.domain.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;

    public List<MypageVideoListResponseDto> getMyVideo(Long memberId){
        List<Video> nowVideoList = videoRepository.findByMember_Id(memberId);
        List<MypageVideoListResponseDto> myVideoList = new ArrayList<>();
        for(Video video : nowVideoList){
            MypageVideoListResponseDto myVideo = MypageVideoListResponseDto.builder()
                    .videoId(video.getVideoId())
                    .thumbnail(video.getThumbnail())
                    .title(video.getTitle())
                    .view(video.getView())
                    .build();
            myVideoList.add(myVideo);
        }
        return myVideoList;
    }

    public List<MypageCommentListResponseDto> getMyComment(Long memberId){
//        log.info("memberId: "+memberId);
        List<Comment> nowCommentList = commentRepository.findByMember_Id(memberId);
        List<MypageCommentListResponseDto> myCommentList = new ArrayList<>();
        for(Comment comment : nowCommentList){
            MypageCommentListResponseDto myComment = MypageCommentListResponseDto.builder()
                    .commentId(comment.getCommentId())
                    .content(comment.getContent())
                    .videoId(comment.getVideo().getVideoId())
                    .thumbnail(comment.getVideo().getThumbnail())
                    .createAt(comment.getVideo().getCreatedAt())
                    .build();

            myCommentList.add(myComment);
        }
        return myCommentList;
    }
}

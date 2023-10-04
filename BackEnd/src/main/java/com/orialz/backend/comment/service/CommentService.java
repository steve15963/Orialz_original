package com.orialz.backend.comment.service;

import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.Member.domain.repository.MemberRepository;
import com.orialz.backend.comment.domain.entity.Comment;
import com.orialz.backend.comment.domain.repository.CommentRepository;
import com.orialz.backend.comment.dto.request.CommentPostRequestDto;
import com.orialz.backend.comment.dto.response.CommentListResponseDto;
import com.orialz.backend.comment.dto.response.CommentPostResponseDto;
import com.orialz.backend.video.domain.entity.Video;
import com.orialz.backend.video.domain.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;

    // 특정 비디오의 댓글 전체 조회
    //나중에 유저 정보 가지고 와야할 수 있으니 entity에서 받기
    public List<CommentListResponseDto> getCommentList(Long videoId){
        List<Comment> nowComments = commentRepository.findByVideo_VideoId(videoId);
        List<CommentListResponseDto> result = new ArrayList<>();
        for(Comment comment : nowComments){
            CommentListResponseDto res = CommentListResponseDto.builder()
                    .commentId(comment.getCommentId())
                    .content(comment.getContent())
                    .memberId(comment.getMember().getId())
                    .memberProfile(comment.getMember().getPicture())
                    .memberNickname(comment.getMember().getNickname())
                    .videoId(comment.getVideo().getVideoId())
                    .build();
            result.add(res);
        }
        return result;
    }


    @Transactional
    //댓글 작성
    public CommentPostResponseDto postComment(Long videoId, Long memberId, CommentPostRequestDto commentPostRequestDto ){

        try{
            // 해당 유저 조회
            Member nowMember = memberRepository.findById(memberId).orElse(null);
            // 해당 비디오 조회
            Video nowVideo = videoRepository.findById(videoId).orElse(null);
            Comment comment = Comment.builder()
                    .video(nowVideo)
                    .member(nowMember)
                    .content(commentPostRequestDto.getContent())
                    .build();
            commentRepository.save(comment);
            CommentPostResponseDto response = CommentPostResponseDto.builder()
                    .success(true)
                    .build();
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        CommentPostResponseDto response = CommentPostResponseDto.builder()
                .success(false)
                .build();
        return response;
    }
}

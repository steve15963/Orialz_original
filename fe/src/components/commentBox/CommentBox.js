import "./CommentBox.css"

export default function CommentBox({comment}){
    return(
        <div className="video-detail-comment">
            <div>코멘트 아디: {comment.commentId}</div>
            <div>비디오 아디: {comment.videoId}</div>
            <div>멤버 아디: {comment.memberId}</div>
            <div>내용: {comment.content}</div>
        </div>
    )
}
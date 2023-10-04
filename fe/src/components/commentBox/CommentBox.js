import "./CommentBox.css";

export default function CommentBox({ comment }) {
    const date = new Date(comment.createAt);
    const formattedDate = date.toLocaleString("ko-KR", {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
        hour12: true,
    });

    return (
        <div className="video-detail-comment">
            <div className="comment-detail">
                <img
                    className="comment-writer-profile"
                    src={comment.memberProfile}
                    alt="profile"
                />
                <div>
                    <div className="comment-writer-detail">
                        <span className="comment-writer-nickname">
                            {comment.memberNickname}
                        </span>
                        <span className="comment-write-date">
                            {formattedDate}
                        </span>
                    </div>
                    <p>{comment.content}</p>
                </div>
            </div>
        </div>
    );
}

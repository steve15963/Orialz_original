import "./ProfileCommentBox.css";

export default function ProfileCommentBox({ comment }) {
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
        <div className="profile-comment">
            <div className="profile-comment-detail">
                <img
                    className="profile-comment-thumbnail"
                    src={comment.thumbnail}
                    alt="thumbnail"
                />
                <div>
                    <div className="profile-comment-titlendate">
                        <span className="profile-comment-title">
                            {comment.title}
                        </span>
                        <span className="profile-comment-date">
                            {formattedDate}
                        </span>
                    </div>
                    <p>{comment.content}</p>
                </div>
            </div>
        </div>
    );
}

import CommentBox from "../commentBox/CommentBox"
import "./CommentsContainer.css"

export default function CommentsContainer({comments}){
    console.log(comments);
    return(
        <div className="video-detail-comment-box">
            {
                comments.map((comment, idx)=>{
                    return(
                        <CommentBox comment={comment} key={idx}/>
                    )
                })
            }
		</div>
    )
}
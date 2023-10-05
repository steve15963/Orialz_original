import CommentBox from '../commentBox/CommentBox'

export default function ProfileCommentsContainer({commentList}){

    return(
        <div className='profile-comments-container'>
            {commentList.length > 0
            ? commentList.map((comment, idx) => {
                return <CommentBox comment={comment} key={idx} />;
            })
            : <div>작성한 댓글이 없습니다.</div>
            }
        </div>
    )
}
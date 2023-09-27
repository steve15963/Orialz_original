/* eslint-disable react-hooks/exhaustive-deps */
import CommentBox from "../commentBox/CommentBox"
import "./CommentsContainer.css"
import { useState, useEffect, useRef } from "react";
import axios from "axios";

export default function CommentsContainer({videoId}){

	const [comments, setComments] = useState([]);
	const commentInputRef = useRef(null);

	async function getComments() {
		try {
			//응답 성공
			const response = await axios.get(`https://test.orialz.com/api/comment/${videoId}`, {});
			console.log(response);
			setComments(response.data);
		} catch (error) {
			//응답 실패
			console.error(error);
		}
	}

	useEffect(()=>{
        getComments();
    },[])

    function handleSubmit(e){
        e.preventDefault();
        console.log(commentInputRef.current.value);
        // 여기서 axios 요청으로 댓글 작성
    }
    
    return(
        <div className="video-detail-comment-box">
            <form className="comment-form" onSubmit={handleSubmit}>
				<input type="text" ref={commentInputRef} placeholder="댓글 쓰기" className="comment-input"></input>
				<button type="submit">작성</button>
			</form>
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
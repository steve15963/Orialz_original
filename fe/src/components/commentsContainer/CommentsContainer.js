/* eslint-disable react-hooks/exhaustive-deps */
import CommentBox from "../commentBox/CommentBox";
import "./CommentsContainer.css";
import { useState, useEffect, useRef } from "react";
import axios from "axios";

export default function CommentsContainer({ videoId }) {
    const [comments, setComments] = useState([]);
    const commentInputRef = useRef(null);

    async function getComments() {
        try {
            //응답 성공
            const response = await axios.get(
                `${process.env.REACT_APP_API_PATH}/api/comment/${videoId}`,
                {}
            );
            console.log(response);
            setComments(response.data);
        } catch (error) {
            //응답 실패
            console.error(error);
        }
    }

    useEffect(() => {
        getComments();
    }, []);

    function handleSubmit(e) {
        e.preventDefault();
        console.log(commentInputRef.current.value);
        // 여기서 axios 요청으로 댓글 작성
        axios
            .post(
                `${process.env.REACT_APP_API_PATH}/api/comment/${videoId}`,
                {
                    content: commentInputRef.current.value,
                },
                {
                    headers: {
                        Authorization:
                            "Bearer " + localStorage.getItem("access_token"),
                        "Content-Type": "application/json",
                    },
                }
            )
            .then((result) => {
                console.log(result.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    return (
        <div className="video-detail-comment-box">
            <form className="comment-form" onSubmit={handleSubmit}>
                <input
                    type="text"
                    ref={commentInputRef}
                    placeholder="댓글 쓰기"
                    className="comment-input"
                ></input>
                <button type="submit">작성</button>
            </form>
            {comments.map((comment, idx) => {
                return <CommentBox comment={comment} key={idx} />;
            })}
        </div>
    );
}

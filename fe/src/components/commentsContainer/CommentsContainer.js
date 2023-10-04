/* eslint-disable react-hooks/exhaustive-deps */
import CommentBox from "../commentBox/CommentBox";
import "./CommentsContainer.css";
import { useState, useEffect, useRef } from "react";
import axios from "axios";

export default function CommentsContainer({ videoId }) {
    const [commentClick, setCommentClick] = useState(false);
    const [commentChange, setCommentChange] = useState(false);
    const [comments, setComments] = useState([]);
    const commentInputRef = useRef(null);

    const handleCommentInputFocus = () => {
        setCommentClick(true);
    };

    const handleCommentInputBlur = () => {
        setCommentClick(false);
    };

    async function getComments() {
        try {
            //응답 성공
            const response = await axios.get(
                `https://test.orialz.com/api/comment/${videoId}`,
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
        setCommentChange(false);
    }, [commentChange]);

    function handleSubmit(e) {
        e.preventDefault();
        console.log(commentInputRef.current.value);
        // 여기서 axios 요청으로 댓글 작성
        let content = commentInputRef.current.value;
        if (content === "" || content.replace(" ", "") === "") {
            return alert("내용을 입력해주세요.");
        }
        axios
            .post(
                `https://test.orialz.com/api/comment/${videoId}`,
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
            .then(() => {
                setCommentChange(true);
                commentInputRef.current.value = "";
                return;
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    return alert("로그인 후 댓글을 작성해주세요.");
                } else {
                    return alert(
                        "오류로 인해 댓글을 작성할 수 없습니다.\n다시 시도해주시길 바랍니다."
                    );
                }
            });
    }

    return (
        <div className="video-detail-comment-box">
            <form className="comment-form" onSubmit={handleSubmit}>
                <div
                    className={`comment-input-line ${
                        commentClick ? "active" : ""
                    }`}
                >
                    <input
                        type="text"
                        ref={commentInputRef}
                        placeholder="댓글 쓰기"
                        className="comment-input"
                        onFocus={handleCommentInputFocus}
                        onBlur={handleCommentInputBlur}
                    ></input>
                </div>
                <button type="submit" className="comment-write-btn">
                    작성
                </button>
            </form>
            {comments.map((comment, idx) => {
                return <CommentBox comment={comment} key={idx} />;
            })}
        </div>
    );
}

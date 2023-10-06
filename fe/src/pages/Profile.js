import { useEffect, useRef, useState } from "react";
import "./Profile.css";
import ProfileBanner from "../components/profileBanner/ProfileBanner";
import ProfileWorks from "../components/profileWorks/ProfileWorks";
import FilterSelect from "../components/filterSelect/FilterSelect";
import axios from "axios";

export default function Profile({ myData }) {
    const [modalOn, setModalOn] = useState(false);
    const [commentList, setCommentList] = useState([]);
    const [commentNum, setCommentNum] = useState(0);
    const [videoList, setVideoList] = useState([]);
    const [videoNum, setVideoNum] = useState(0);
    const userInfoRef = useRef(null);


    function getUserInfo(){
        const str = localStorage.getItem("user");
        if(str){
            const obj = JSON.parse(str);
            return obj;
        } else {
            return null;
        }
    }

    function handleModalOn() {
        setModalOn(true);
    }

    function handleModalOff() {
        setTimeout(() => {
            setModalOn(false);
        }, 100);
    }

    async function getMyData() {
        try {
            const response = await axios.get(
                `${process.env.REACT_APP_API_PATH}/api/mypage/mydata`,
                {
                    headers: {
                        Authorization:
                            "Bearer " + localStorage.getItem("access_token"),
                        "Content-Type": "application/json",
                    },
                }
            );
            console.log(response.data);
            setCommentList(response.data.commentList);
            setCommentNum(response.data.commentNum);
            setVideoList(response.data.videoList);
            setVideoNum(response.data.videoNum);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        userInfoRef.current = getUserInfo();
        getMyData();
    }, []);

    return (
        <div className="profile-page">
            {modalOn ? <FilterSelect handleModalOff={handleModalOff} /> : null}
            <ProfileBanner
                handleModalOn={handleModalOn}
                videoNum={videoNum}
                commentNum={commentNum}
            />
            <ProfileWorks videoList={videoList} commentList={commentList}/>
        </div>
    );
}

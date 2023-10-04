import { useEffect, useState } from "react";
import "./Profile.css";
import ProfileBanner from "../components/profileBanner/ProfileBanner";
import ProfileWorks from "../components/profileWorks/ProfileWorks";
import FilterSelect from "../components/filterSelect/FilterSelect";
import axios from "axios";

export default function Profile({ myData }) {
    const [modalOn, setModalOn] = useState(false);
    const [commentNum, setCommentNum] = useState(0);
    const [videoNum, setVideoNum] = useState(0);

    useState(() => {
        console.log(myData);
    }, [myData]);

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
            setVideoNum(response.data.videoNum);
            setCommentNum(response.data.commentNum);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
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
            <ProfileWorks />
        </div>
    );
}

import { useRef, useState } from "react";
import NoticeContainer from "../noticeContainer/NoticeContainer";
import ProfileContainer from "../profileContainer/ProfileContainer";
import "./ProfileBox.css";

export default function ProfileBox() {
    const [noticeOn, setNoticeOn] = useState(false);
    const [profileOn, setProfileOn] = useState(false);

    const profileRef = useRef();

    function falsifyNoticeOn() {
        setNoticeOn(false);
    }

    function falsifyProfileOn() {
        setProfileOn(false);
    }

    const profileImgClick = (e) => {
        e.stopPropagation(); // Prevent event capturing
        setProfileOn((prevState) => !prevState);
    };

    useState(() => {
        const clickOutside = (e) => {
            if (profileRef.current && !profileRef.current.contains(e.target)) {
                falsifyProfileOn();
            }
        };

        document.addEventListener("click", clickOutside);

        return () => {
            document.removeEventListener("click", clickOutside);
        };
    }, []);

    return (
        <div className="bell-profile-box" onClick={() => setProfileOn(false)}>
            {noticeOn ? (
                <NoticeContainer falsifyNoticeOn={falsifyNoticeOn} />
            ) : null}
            <img
                src="/notification.png"
                alt="bell"
                className="bell-img icon-wiggle"
                onClick={() => {
                    setNoticeOn(true);
                }}
            />
            {profileOn ? (
                <div ref={profileRef}>
                    <ProfileContainer falsifyProfileOn={falsifyProfileOn} />
                </div>
            ) : null}
            <img
                src="/profile.svg"
                alt="profile"
                className="profile-img icon-wiggle"
                onClick={profileImgClick}
            />
        </div>
    );
}

import "./ProfileWorks.css";
import { useState } from "react";
import ProfileVideoContainer from "../profileVideoContainer/ProfileVideoContainer";
import ProfileCommentsContainer from "../profileCommentsContainer/ProfileCommentsContainer";

export default function ProfileWorks() {
  const [mode, setMode] = useState(0);

  function setModeOne() {
    setMode(1);
  }
  function setModeZero() {
    setMode(0);
  }

  return (
    <div className="profile-works">
      <div className="profile-works-btns">
        <div
          className={`profile-video-btn ${mode === 0 ? "active" : ""}`}
          onClick={setModeZero}
        >
          영상
        </div>
        <div
          className={`profile-comments-btn ${mode === 1 ? "active" : ""}`}
          onClick={setModeOne}
        >
          댓글
        </div>
      </div>
      <div className="profile-works-containers">
        {mode === 0 ? <ProfileVideoContainer /> : <ProfileCommentsContainer />}
      </div>
    </div>
  );
}

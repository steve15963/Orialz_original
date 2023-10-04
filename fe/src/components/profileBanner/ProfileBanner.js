import "./ProfileBanner.css";

export default function ProfileBanner({ handleModalOn }) {
  return (
    <div className="profile-banner">
      <div className="profile-banner-profiles">
        <img className="profile-banner-img" src="/profile.svg" alt="profile" />
        <div className="profile-banner-data">
          <div className="profile-banner-name">김싸피</div>
          <div className="profile-banner-cnts">
            <div className="profile-banner-movie-cnt">영상 n개 &nbsp;</div>
            <div className="profile-banner-comment-cnt">댓글 n개</div>
          </div>
        </div>
      </div>
      <div className="category-select-btn" onClick={handleModalOn}>
        필터링 카테고리 설정
        <img
          className="category-select-right-icon"
          src="/right-icon.png"
          alt="right"
        />
      </div>
    </div>
  );
}

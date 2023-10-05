import "./ProfileBanner.css";

export default function ProfileBanner({ handleModalOn, videoNum, commentNum }) {
    let loginUser = {};
    const str = localStorage.getItem("user");
    if(str){
        loginUser = JSON.parse(str);
    }

    return (
        <div className="profile-banner">
            <div className="profile-banner-profiles">
                <img
                    className="profile-banner-img"
                    src={loginUser.picture}
                    alt="profile"
                />
                <div className="profile-banner-data">
                    <div className="profile-banner-name">
                        {loginUser.nickname}
                    </div>
                    <div className="profile-banner-cnts">
                        <div className="profile-banner-movie-cnt">
                            영상 {videoNum}개 &nbsp;
                        </div>
                        <div className="profile-banner-comment-cnt">
                            댓글 {commentNum}개
                        </div>
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

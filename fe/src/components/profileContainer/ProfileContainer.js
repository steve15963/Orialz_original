import { NavLink } from "react-router-dom";
import "./ProfileContainer.css";
import { useDispatch } from "react-redux";
import { discardUser } from "../../util/slice/userSlice";

export default function ProfileContainer({ falsifyProfileOn }) {
    const dispatch = useDispatch();

    function logoutGoogle() {
        window.location.href = `${process.env.REACT_APP_API_PATH}/logout`;
        localStorage.removeItem("access_token");
        localStorage.removeItem("user");
    }

    return (
        <div className="profile-container">
            <NavLink to={"/profile"} className={"user-info-click"}>
                <img
                    src="user-info-icon.png"
                    alt="userImage"
                    className="user-info-icon"
                />
                <span className="user-info-text">내 정보</span>
            </NavLink>

            <div
                className="logout-click"
                onClick={(e) => {
                    e.preventDefault();
                    dispatch(discardUser());
                    logoutGoogle();
                }}
            >
                <img
                    src="logout-icon.png"
                    alt="logoutImage"
                    className="logout-icon"
                />
                <span>로그아웃</span>
            </div>
        </div>
    );
}

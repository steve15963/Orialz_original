
import { NavLink } from "react-router-dom";
import "./LoginBtn.css";

export default function LoginBtn(){
    return(
        <NavLink to={"login"}>
            <img src="btn_google_signin_dark_normal_web.png" alt="googleImage" className="google-img"/>
        </NavLink>
    )
}
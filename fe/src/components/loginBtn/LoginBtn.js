import "./LoginBtn.css";

export default function LoginBtn({googleLogin}){

    return(
        <div onClick={googleLogin}>
            <img src="btn_google_signin_dark_normal_web.png" alt="googleImage" className="google-img"/>
        </div>
    )
}
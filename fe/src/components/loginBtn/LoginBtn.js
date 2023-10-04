import "./LoginBtn.css";

export default function LoginBtn({ googleLogin }) {
  return (
    <div onClick={googleLogin} className="google-login-btn">
      <img src="google_icon.png" alt="googleImage" className="google-logo" />
      <div className="login-text">로그인</div>
    </div>
  );
}

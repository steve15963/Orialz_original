import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();

  const navigateToGoogleLogin = () => {
    navigate("/oauth2/authorization/google");
  };

  return (
    <div>
      <button onClick={navigateToGoogleLogin}>구글로 로그인하기</button>
    </div>
  );
}

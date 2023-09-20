export default function Login() {
  function navigateToGoogleLogin() {
    window.location.href = `${process.env.REACT_APP_API_PATH}/oauth2/authorization/google`;
    // const newTab = window.open(
    //   "http://localhost:8080/oauth2/authorization/google",
    //   "_blank"
    // );

    // if (newTab) {
    //   newTab.onload = function () {
    //     console.log("하이");
    //     const urlParams = new URLSearchParams(newTab.location.search);
    //     console.log(urlParams);
    //     const token = urlParams.get("token");

    //     localStorage.setItem("accessToken", token);

    //     newTab.close();

    //     window.location.href = "http://localhost:3000";
    //   };
    // }
  }

  return (
    <div>
      <button onClick={navigateToGoogleLogin}>구글로 로그인하기</button>
    </div>
  );
}

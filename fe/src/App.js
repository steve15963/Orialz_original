/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable array-callback-return */
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Login from "./pages/Login";
import Main from "./pages/Main";
import Test from "./pages/Test";
import Profile from "./pages/Profile";
import Upload from "./pages/Upload";
import "./App.css";
import FileCheck from "./components/fileUpload/fileUpload";
import { useEffect } from "react";

function App() {
  function logoutGoogle() {
    window.location.href = `${process.env.REACT_APP_API_PATH}/logout`;
    localStorage.removeItem("access_token");
  }

  function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(";");
    cookie.some(function (item) {
      item = item.replace(" ", "");
      var dic = item.split("=");
      if (key === dic[0]) {
        result = dic[1];
        return true;
      }
    });
    return result;
  }

  function accessTokenReissue() {
    const refresh_token = getCookie("refresh_token");
    console.log(refresh_token);

    fetch(`${process.env.REACT_APP_API_PATH}/api/token`, {
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("access_token"),
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        refreshToken: getCookie("refresh_token"),
      }),
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        }
      })
      .then((result) => {
        console.log(result.accessToken);
        localStorage.setItem("access_token", result.accessToken);
      })
      .catch((error) => console.log(error));
  }

  function getMemberInfo() {
    fetch(`${process.env.REACT_APP_API_PATH}/api/member/info`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("access_token"),
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => console.log(data))
      .catch((error) => console.log(error));
  }

  useEffect(() => {
    const refresh_token = getCookie("refresh_token");
    if (refresh_token) {
      accessTokenReissue();
    }
    if (localStorage.getItem("access_token")) {
      getMemberInfo();
    }
  }, []);

  return (
    <BrowserRouter>
      <div className="App">
        <Link to="/login">Login</Link>
        <button onClick={logoutGoogle}>로그아웃</button>
        <Link to="/">Main</Link>
        <Link to="/test">테스트페이지</Link>
        <Link to="/profile">프로필</Link>
        <Link to="/upload">업로드</Link>

        <Routes>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/" element={<Main />}></Route>
          <Route path="/test" element={<Test />}></Route>
          <Route path="/profile" element={<Profile />}></Route>
          <Route path="/upload" element={<Upload />}></Route>
          <Route path="/find" element={<FileCheck />}></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;

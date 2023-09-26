/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable array-callback-return */
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Login from "./pages/Login";
import Main from "./pages/Main";
import VideoDetail from "./pages/VideoDetail";
import Profile from "./pages/Profile";
import Upload from "./pages/Upload";
import "./App.css";


function App() {




	return (
		<BrowserRouter>
		<div className="App">
			<Link to="/login">Login</Link>
			<button>로그아웃</button>
			<Link to="/">Main</Link>
			<Link to="/videoDetail">테스트페이지</Link>
			<Link to="/profile">프로필</Link>
			<Link to="/upload">업로드</Link>


			<Routes>
			<Route path="/login" element={<Login />}></Route>
			<Route path="/" element={<Main />}></Route>
			<Route path="/videoDetail" element={<VideoDetail />}></Route>
			<Route path="/profile" element={<Profile />}></Route>
			<Route path="/upload" element={<Upload />}></Route>
			</Routes>
		</div>
		</BrowserRouter>
	);
}

export default App;

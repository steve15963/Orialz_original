/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable array-callback-return */
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Login from "./pages/Login";
import Main from "./pages/Main";
import VideoDetail from "./pages/VideoDetail";
import Profile from "./pages/Profile";
import Upload from "./pages/Upload";
import "./App.css";
import { useState, useEffect } from "react";
import axios from "axios";

function App() {

	const [videos, setVideos] = useState([]);
	
	useEffect(()=>{getVideos()},[]);

	async function getVideos() {
		try {
			//응답 성공
			const response = await axios.get("https://test.orialz.com/api/video", {});
			console.log(response);
			setVideos(response.data);
		} catch (error) {
			//응답 실패
			console.error(error);
		}
	}


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
			<Route path="/login" element={<Login/>}></Route>
			<Route path="/" element={<Main videos={videos}/>}></Route>
			<Route path="/videoDetail" element={<VideoDetail videos={videos}/>}></Route>
			<Route path="/profile" element={<Profile />}></Route>
			<Route path="/upload" element={<Upload />}></Route>
			</Routes>
		</div>
		</BrowserRouter>
	);
}

export default App;

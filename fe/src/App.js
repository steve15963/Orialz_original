/* eslint-disable no-unused-vars */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable array-callback-return */
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Login from "./pages/Login";
import Main from "./pages/Main";
import VideoDetail from "./pages/VideoDetail";
import Profile from "./pages/Profile";
import Upload from "./pages/Upload";
import "./App.css";
import FileCheck from "./components/fileUpload/fileUpload";
import { useEffect } from "react";
import axios from "axios";
import { useState } from "react";

function App() {

	const [videos, setVideos] = useState([]);
	const [myData, setMyData] = useState([]);

	useEffect(()=>{getVideos();getMyData()},[]);

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

	// 로그인 되어있을때만 적용시켜야함
	async function getMyData() {
		try {
			//응답 성공
			const response = await axios.get("https://test.orialz.com/api/mypage/1", {});
			console.log(response);
			setMyData(response.data);
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
			<Route path="/profile" element={<Profile myData={myData}/>}></Route>
			<Route path="/upload" element={<Upload />}></Route>
			<Route path="/upload2" element={<FileCheck />}></Route>
			</Routes>
		</div>
		</BrowserRouter>
	);
}

export default App;

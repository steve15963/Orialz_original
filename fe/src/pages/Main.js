import { useEffect, useState } from "react";
import VideoContainer from "../components/videoContainer/VideoContainer";
import "./Main.css";
import Header from "../components/header/Header";
import axios from "axios";

export default function Main() {

	useEffect(() => {
		const params = new URLSearchParams(window.location.search);
		const token = params.get("token");

		if (token) {
		localStorage.setItem("access_token", token);
		window.location.replace("/");
		}

		
		getData();
	}, []);

	const [videos, setVideos] = useState([]);

	async function getData() {
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
		<div className="main">
		<Header></Header>
		<VideoContainer videos={videos}></VideoContainer>
		</div>
	);
}

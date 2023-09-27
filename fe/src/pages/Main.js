import { useState, useEffect } from "react";
import VideoContainer from "../components/videoContainer/VideoContainer";
import "./Main.css";
import Header from "../components/header/Header";
import axios from "axios";

export default function Main({videos}) {

	const [searchedVideos, setSearchedVideos] = useState([]);

	useEffect(() => {
		const params = new URLSearchParams(window.location.search);
		const token = params.get("token");
		
		if (token) {
		localStorage.setItem("access_token", token);
		window.location.replace("/");
		}
		
		setSearchedVideos(videos)
		
	}, [videos]);


	async function searchVideos(keyword){
		try {
			//응답 성공
			const response = await axios.get(`https://test.orialz.com/api/video/search?keyword=${keyword}`, {});
			console.log(response);
			setSearchedVideos(response.data);
		} catch (error) {
			//응답 실패
			console.error(error);
		}
	}

	return (
		<div className="main">
			<Header searchVideos={searchVideos}></Header>
			<VideoContainer videos={searchedVideos}></VideoContainer>
		</div>
	);
}

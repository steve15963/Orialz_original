import { useEffect} from "react";
import VideoContainer from "../components/videoContainer/VideoContainer";
import "./Main.css";
import Header from "../components/header/Header";

export default function Main({videos}) {

	useEffect(() => {
		const params = new URLSearchParams(window.location.search);
		const token = params.get("token");

		if (token) {
			localStorage.setItem("access_token", token);
			window.location.replace("/");
		}

		
	}, []);

	

	return (
		<div className="main">
			<Header></Header>
			<VideoContainer videos={videos}></VideoContainer>
		</div>
	);
}


import React from 'react';
import VideoJS from '../components/VideoJS';
import videojs from 'video.js';
import './VideoDetail.css';
import data3 from './data3.json';
import axios from 'axios';

const VideoDetail = () => {
	const playerRef = React.useRef(null);
	console.log(data3);
	console.log(1);
	async function getData() {
		console.log(2);
		try {
			//응답 성공
			console.log(3);
			const response = await axios.get("https://test.orialz.com/hls/streaming/3/output.m3u8",{
			});
			console.log(4);
			console.log(response);
		} catch (error) {
			console.log(5);
			//응답 실패
			console.error(error);
			console.log(6);
		}
	}
	console.log(7);
	getData();
	console.log(8);
    const videoJsOptions = {
		autoplay: true,
		controls: true,
		// responsive: true,
		// fluid: true,
		userActions: {
			click: false,
			doubleClick: false,
		},
		controlBar:{
			pictureInPictureToggle: false,
			fullscreenToggle: false,
		},
		sources: [{
			src: 'https://test.orialz.com/hls/streaming/3/output.m3u8',
			type: 'application/x-mpegURL'
		}]
    };
	
	const handlePlayerReady = (player) => {
		console.log(9);
		playerRef.current = player;
		console.log(10);
		// You can handle player events here, for example:
		player.on('waiting', () => {
		  videojs.log('player is waiting');
		});
		console.log(11);
		player.on('dispose', () => {
		  videojs.log('player will dispose');
		});
		console.log(12);
	};
	console.log(13);
	
	return (
		<div className="video-detail-container">
			<div className="videojs-container">
		  		<VideoJS options={videoJsOptions} onReady={handlePlayerReady} blurData={data3}/>	
			</div>
		</div>
	);
};

export default VideoDetail;
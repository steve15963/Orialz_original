/* eslint-disable react-hooks/exhaustive-deps */
import axios from 'axios';
import React, { useRef, useEffect, useState } from 'react';
import VideoJS from '../components/VideoJS';
import videojs from 'video.js';
import './VideoDetail.css';
import CommentsContainer from '../components/commentsContainer/CommentsContainer';
import VideoSubs from '../components/videoSubs/VideoSubs';
// import data3 from './data3.json';
// import tempVideo from './tempVideo.mp4';
require('videojs-contrib-hls.js');

const VideoDetail = ({videos}) => {

	const [curVideo, setCurVideo] = useState({});
	const urlParams = new URL(window.location.href).searchParams;
	const videoId = useRef(urlParams.get('id'));
	
	const blurUrlRef = useRef(`https://test.orialz.com/api/blur/list/7/11`);
	const userInfoRef = useRef("ImUser");

	// const data4 = useRef(null);

	async function viewIncrease() {
		try {
			//응답 성공
			await axios.get(`https://test.orialz.com/api/video/${videoId.current}/view`, {});
		} catch (error) {
			//응답 실패
			console.error(error);
		}
	}
	
	

	useEffect(()=>{
		viewIncrease();
		videos.forEach((e)=>{
			if(e.id === Number(videoId.current)){
				setCurVideo(e);
				return;
			}
		});


	},[]);

	const playerRef = useRef(null);

	const videoJsOptionsRef = useRef(null);
	videoJsOptionsRef.current = {
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
			src: `https://test.orialz.com/hls/streaming/${videoId.current}/output.m3u8`,
			type: 'application/x-mpegURL'
			// src: tempVideo,
			// type: 'video/mp4'
		}]
	};
	

	
	const handlePlayerReady = (player) => {
		playerRef.current = player;
		
		// You can handle player events here, for example:
		player.on('waiting', () => {
		  videojs.log('player is waiting');
		});
	
		player.on('dispose', () => {
		  videojs.log('player will dispose');
		});
	};

	
	
	return (
		<div className="video-detail-page">
			<div className="video-detail-container">
				<div className="videojs-container">
					<VideoJS options={videoJsOptionsRef.current} onReady={handlePlayerReady} blurUrl={blurUrlRef.current} userInfo={userInfoRef.current}/>
				</div>
				<VideoDisc curVideo={curVideo}/>
				
				<CommentsContainer videoId={videoId.current}/>
				
			</div>
			<VideoSubs videos={videos}/>
			
		</div>
	);
};

function VideoDisc({curVideo}){
	return(
		<>
			<div className="video-detail-title">
				{curVideo.title}
			</div>
			<div className="video-detail-description">
				<div>업로드 일: {curVideo.date}</div>
				<div>올린이: {curVideo.uploader}</div>
				<div>조회수: {curVideo.view}</div>
				<div>설명:</div>
			</div>
		</>
	)
}

export default VideoDetail;
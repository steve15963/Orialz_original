/* eslint-disable react-hooks/exhaustive-deps */

import React, { useEffect, useRef, useState } from 'react';
import VideoJS from '../components/VideoJS';
import videojs from 'video.js';
import './VideoDetail.css';
import CommentsContainer from '../components/commentsContainer/CommentsContainer';
import VideoSubs from '../components/videoSubs/VideoSubs';
import data3 from './data3.json';
import axios from 'axios';
// import tempVideo from './tempVideo.mp4';
require('videojs-contrib-hls.js');

const VideoDetail = ({videos}) => {
	const playerRef = React.useRef(null);
	console.log(data3);

	const urlParams = new URL(window.location.href).searchParams;
	const videoId = urlParams.get('id');

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
			src: `https://test.orialz.com/hls/streaming/${videoId}/output.m3u8`,
			type: 'application/x-mpegURL'
			// src: tempVideo,
			// type: 'video/mp4'
		}]
	};

	const [comments, setComments] = useState([]);
	
	async function getData() {
		try {
			//응답 성공
			const response = await axios.get(`https://test.orialz.com/api/comment/${videoId}`, {});
			console.log(response);
			setComments(response.data);
		} catch (error) {
			//응답 실패
			console.error(error);
		}
	}
	useEffect(()=>{
		getData();
	},[])

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
					<VideoJS options={videoJsOptionsRef.current} onReady={handlePlayerReady} blurData={data3}/>	
				</div>
				<div className="video-detail-title">
					제목제목제목제목제목
				</div>
				<div className="video-detail-description">
					<div>조회수</div>
					<div>연도</div>
					<div>설명</div>
				</div>
				<form className="comment-form">
					<input placeholder="댓글 쓰기" className="comment-input"></input>
				</form>
				<CommentsContainer comments={comments}/>
				
			</div>
			<VideoSubs videos={videos}/>
			
		</div>
	);
};

export default VideoDetail;
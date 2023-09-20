/* eslint-disable no-unused-vars */
import React from 'react';
import VideoJS from '../components/VideoJS';
import videojs from 'video.js';
import './VideoDetail.css';
import data3 from './data3.json';

const VideoDetail = () => {
	const playerRef = React.useRef(null);
	const index = React.useRef(0);
	const imgRef = React.useRef(null);
	const canvasRef = React.useRef(null);
	const videoCanvasRef = React.useRef(null);

    const videoJsOptions = {
		autoplay: true,
		controls: true,
		// responsive: true,
		fluid: true,
		userActions: {
			click: false,
			doubleClick: false,
		},
		controlBar:{
			pictureInPictureToggle: false,
			fullscreenToggle: false,
		},
		sources: [{
			src: 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
			type: 'video/mp4'
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
		<div className="video-detail-container">
			<div className="videojs-container">
		  		<VideoJS options={videoJsOptions} onReady={handlePlayerReady} blurData={data3}/>	
			</div>
		</div>
	);
};

export default VideoDetail;
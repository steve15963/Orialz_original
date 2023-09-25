
import React from 'react';
import videojs from 'video.js';
import 'video.js/dist/video-js.css';

export const VideoJS = (props) => {
	const videoRef = React.useRef(null);
	const playerRef = React.useRef(null);
	const blurIdx = React.useRef(0);
	const realVideoRef = React.useRef(null);

	const blursRef = React.useRef([]);
	const blurAnimationRef = React.useRef();
	const timeBefore = React.useRef(-1);
	const {options, onReady, blurData} = props;

	console.log(1);
	React.useEffect(() => {
		console.log(2);
		// Make sure Video.js player is only initialized once
		if (!playerRef.current) {
			// The Video.js player needs to be _inside_ the component el for React 18 Strict Mode. 
			const videoElement = document.createElement("video-js");
			console.log(3);
			videoElement.classList.add('vjs-big-play-centered','vjs-16-9');
			videoRef.current.appendChild(videoElement);
			realVideoRef.current = videoElement;
			console.log(4);
			const player = playerRef.current = videojs(videoElement, options, () => {
				videojs.log('player is ready');
				onReady && onReady(player);
			});
			console.log(5);
		// You could update an existing player in the `else` block here
		// on prop change, for example:
		} else {
			console.log(6);
			const player = playerRef.current;

			player.autoplay(options.autoplay);
			player.src(options.sources);
			console.log(7);
		}
		console.log(8);
	}, [onReady, options, videoRef]);

	// Dispose the Video.js player when the functional component unmounts
	React.useEffect(() => {
		const player = playerRef.current;
		console.log(9);
		return () => {
			if (player && !player.isDisposed()) {
				player.dispose();
				playerRef.current = null;
			}
		};
	}, [playerRef]);

	function testDraw(){
		console.log(10);
		blurAnimationRef.current = window.requestAnimationFrame(drawBlur);
	}
	
	function fullscreen(){
		console.log(11);
		videoRef.current.requestFullscreen();
	}
	function createBlurElement(){
		console.log(12);
		blursRef.current.forEach((e)=>{e.remove()});
				
				blurData.data[blurIdx.current].objects.forEach((e)=>{
					const rect2 = realVideoRef.current.getBoundingClientRect();
					const realZone = document.querySelector(".vjs-text-track-display");
					const rect = realZone.getBoundingClientRect();
					const blurSquare = document.createElement("div");
					blurSquare.classList.add("blur-square");
					blurSquare.style.position = "absolute";
					blurSquare.style.top =  rect.height * e.y + "px";
					blurSquare.style.left = rect.width * e.x +  (rect2.width - rect.width)/2 + "px";
					blurSquare.style.height = e.h  + "%";
					blurSquare.style.width = e.w * (rect.width/rect2.width) +  "%";	
					blurSquare.style.backgroundColor = "rgba(0,0,0,0.1)";
					blurSquare.style.zIndex = 5;
					blurSquare.style.backdropFilter = "blur(20px)";
					blursRef.current.push(blurSquare);
					realVideoRef.current.appendChild(blurSquare);
				})
				console.log(13);
	}
	function drawBlur(){
		console.log(14);
		let curTime = playerRef.current.currentTime();
		// 영상의 이전 부분으로 돌아갈 때
		let mode = 0;
		// 영상이 멈춰있을 때
		console.log(15);
		if(timeBefore.current === curTime){
			// console.log("영상멈춤");
			mode = 0;
		} else if(timeBefore.current > curTime){
			mode = -1;
			blurIdx.current = Math.floor(curTime);
				// console.log("영상뒤로감");
		} else {
			mode = 1;
			blurIdx.current = Math.floor(curTime);
			// console.log("영상앞으로감");
			
		}
		console.log(16);
		if(mode === 0){
		} else if(mode===1){
			if(curTime > blurData.data[blurIdx.current].time - 0.5){
				createBlurElement();
				blurIdx.current++;
			}
			
		} else if(mode === -1){
			if(curTime < blurData.data[blurIdx.current].time + 0.5){
				createBlurElement();
				blurIdx.current--;
			}
		}
		console.log(17);
		// console.log(curTime, blurIdx.current);
		
		
		timeBefore.current = curTime;
		console.log(18);
		blurAnimationRef.current = window.requestAnimationFrame(drawBlur);
		console.log(19);
	}
	console.log(20);

  	return (
		<div data-vjs-player id='videoZone'>
 			<div ref={videoRef} onClick={testDraw} onDoubleClick={fullscreen}>
				
			</div>
		</div>
  	);
}

export default VideoJS;
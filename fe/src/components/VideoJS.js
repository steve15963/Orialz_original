/* eslint-disable react-hooks/exhaustive-deps */
import React from 'react';
import videojs from 'video.js';
import 'video.js/dist/video-js.css';

export const VideoJS = (props) => {
	const videoRef = React.useRef(null);
	const playerRef = React.useRef(null);
	const canvasRef = React.useRef(null);
	const ctxRef = React.useRef(null);
	const blurIdx = React.useRef(0);

	const {options, onReady, blurData} = props;


	React.useEffect(() => {

		// Make sure Video.js player is only initialized once
		if (!playerRef.current) {
			// The Video.js player needs to be _inside_ the component el for React 18 Strict Mode. 
			const videoElement = document.createElement("video-js");

			videoElement.classList.add('vjs-big-play-centered');
			videoRef.current.appendChild(videoElement);

			const player = playerRef.current = videojs(videoElement, options, () => {
				videojs.log('player is ready');
				onReady && onReady(player);
			});
		// You could update an existing player in the `else` block here
		// on prop change, for example:
		} else {
			const player = playerRef.current;

			player.autoplay(options.autoplay);
			player.src(options.sources);
		}
	}, [options, videoRef]);

	// Dispose the Video.js player when the functional component unmounts
	React.useEffect(() => {
		const player = playerRef.current;

		return () => {
			if (player && !player.isDisposed()) {
				player.dispose();
				playerRef.current = null;
			}
		};
	}, [playerRef]);

	function testDraw(){
		canvasRef.current.width = videoRef.current.clientWidth;
		canvasRef.current.height = videoRef.current.clientHeight;
		ctxRef.current = canvasRef.current.getContext("2d");

		// ctx.fillStyle = "green";
		
		// console.log(videoRef.current.clientWidth);
		// // canvas.style.

		// ctx.fillRect(0,0,canvas.width,canvas.height);
		// setTimeout(()=>{
		// 	ctx.clearRect(0,0,canvas.width,canvas.height);
		// },1000);

		window.requestAnimationFrame(drawBlur);
	}
	
	function fullscreen(){

		

		videoRef.current.requestFullscreen();
	}

	function drawBlur(){
		// ctxRef.current.fillStyle = "green";
		ctxRef.current.filter = "blur(4px)";
		ctxRef.current.font = "48px serif";
		let curTime = playerRef.current.currentTime();
		
		
		if(curTime > blurData.data[blurIdx.current].time + 0.5){
			
			console.log("hi");
			ctxRef.current.clearRect(0,0,canvasRef.current.width, canvasRef.current.height);

			blurData.data[blurIdx.current].objects.forEach((e)=>{
				console.log(e.x, e.y);
				ctxRef.current.fillRect(canvasRef.current.width * e.x, canvasRef.current.height * e.y, canvasRef.current.width * e.w * 0.01, canvasRef.current.height * e.h * 0.01);
			})

			blurIdx.current++;
			if(blurIdx.current === blurData.data.length){
				return;
			}

		}
		window.requestAnimationFrame(drawBlur);
	}
	

  	return (
		<div data-vjs-player id='videoZone'>
 			<div ref={videoRef} onClick={testDraw} onDoubleClick={fullscreen}>
				<canvas ref={canvasRef} style={{
					position: "absolute",
					zIndex:"5",
					pointerEvents:"none"
				}}
				/>
			</div>
		</div>
  	);
}

export default VideoJS;
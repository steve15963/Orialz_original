/* eslint-disable react-hooks/exhaustive-deps */
import React, { useEffect, useRef } from 'react';
import videojs from 'video.js';
import 'video.js/dist/video-js.css';
import axios from 'axios';

export const VideoJS = (props) => {
	const {options, onReady, blurUrl, userInfo} = props;
	const playerRef = useRef(null);
	const videoRef = useRef(null);
	const realVideoRef = useRef(null);
	const blurDataRef = useRef([]);
	const blursRef = useRef([]);
	const blurIdx = useRef(0);
	const blurAnimationRef = useRef();
	const timeBefore = useRef(-1);
	const myWordsRef = useRef([]);

	useEffect(() => {
		// Make sure Video.js player is only initialized once
		if (!playerRef.current) {
			// The Video.js player needs to be _inside_ the component el for React 18 Strict Mode. 
			const videoElement = document.createElement("video-js");
			videoElement.classList.add('vjs-big-play-centered','vjs-16-9');
			videoRef.current.appendChild(videoElement);
			realVideoRef.current = videoElement;
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
	}, [onReady, options, videoRef]);

	// Dispose the Video.js player when the functional component unmounts
	useEffect(() => {
		const player = playerRef.current;
		return () => {
			if (player && !player.isDisposed()) {
				player.dispose();
				playerRef.current = null;
				cancelAnimationFrame(blurAnimationRef.current);
				myWordsRef.current = [];
			}
		};
	}, [playerRef]);

	
	useEffect(()=>{
		if(userInfo){
			getMyWords();
			getBlurData();
		}
		const controlBar = document.querySelector(".vjs-control-bar");
		controlBar.style.zIndex = 2;
		console.log(controlBar);
	},[])

	async function getBlurData() {
		try {
			//응답 성공
			const response = await axios.get(blurUrl, {});
			console.log(response);
			blurDataRef.current = response.data;
			blurAnimationRef.current = window.requestAnimationFrame(drawBlur);
		} catch (error) {
			//응답 실패
			console.error(error);
		}
	}
	
	async function getMyWords() {
		try {
            const mywords = await axios.get(`https://test.orialz.com/api/onkeyword/list/${1}`, {});
			mywords.data.forEach((e)=>{
				myWordsRef.current.push(e.keyword);
			})
		} catch (error) {
			console.error(error);
		}
	}

	function fullScreen(){
		videoRef.current.requestFullscreen();
	}

	function createBlurElement(){
		blursRef.current.forEach((e)=>{e.remove()});
		blurDataRef.current.data[blurIdx.current].objects.forEach((e)=>{

			// 내 필터 키워드에 있을 때만 블러처리
			if(myWordsRef.current.includes(e.label)){

				// 비디오 전체 크기
				const rect2 = realVideoRef.current.getBoundingClientRect();
				const realZone = document.querySelector(".vjs-text-track-display");
				// 비디오 실제 플레이부분 크기
				const rect = realZone.getBoundingClientRect();
				const blurSquare = document.createElement("div");
				blurSquare.classList.add("blur-square");
				blurSquare.style.position = "absolute";

				blurSquare.style.top =  rect.height * e.y + "px";
				blurSquare.style.left = rect.width * e.x +  (rect2.width - rect.width)/2 + "px";
				blurSquare.style.height = e.h * 100  + "%";
				blurSquare.style.width = e.w * (rect.width/rect2.width) * 100+  "%";	

				blurSquare.style.backgroundColor = "rgba(0,0,0,0.1)";
				blurSquare.style.zIndex = 1;
				blurSquare.style.backdropFilter = "blur(20px)";
				blursRef.current.push(blurSquare);
				realVideoRef.current.appendChild(blurSquare);

				// setTimeout(()=>{blurSquare.remove()},100)
			}

		})

	}

	function drawBlur(){

		let curTime = playerRef.current.currentTime();
		// 영상의 이전 부분으로 돌아갈 때
		let mode = 0;
		// 영상이 멈춰있을 때
		if(timeBefore.current === curTime){
			mode = 0;
		} else if(timeBefore.current > curTime){
			mode = -1;
			blurDataRef.current.data.some((e,i)=>{
				if(e.time>curTime){
					console.log(e.time, curTime, i);
					blurIdx.current = i; 
					return true;
				}
				return false;
			})
		} else if(timeBefore.current + 0.05 < curTime) {
			mode = 1;
			// blurIdx.current = Math.floor(curTime/20);
			blurDataRef.current.data.some((e,i)=>{
				if(e.time>curTime){
					console.log(e.time, curTime);
					blurIdx.current = i; 
					return true;
				}
				return false;
			})
		} else {
			mode = 1;
		}

		if(mode === 0){
		} else if(mode===1){
			if(curTime > blurDataRef.current.data[blurIdx.current].time - 0.003){
				createBlurElement();
				blurIdx.current++;
			}
			
		} else if(mode === -1){
			if(curTime < blurDataRef.current.data[blurIdx.current].time + 0.003){
				createBlurElement();
				blurIdx.current--;
			}
		}
		
		
		timeBefore.current = curTime;
		blurAnimationRef.current = window.requestAnimationFrame(drawBlur);
	}

  	return (
		<div data-vjs-player id='videoZone'>
 			<div ref={videoRef} onDoubleClick={fullScreen}>
			</div>
		</div>
  	);
}

export default VideoJS;
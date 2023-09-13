import React, { useEffect } from 'react';
import VideoJS from '../components/VideoJS'
import videojs from 'video.js';
import overlay from 'videojs-overlay';
import './Test.css'
import data from './data.json';
import data2 from './data2.json';
videojs.registerPlugin('overlay', overlay);

const Test = () => {
	const playerRef = React.useRef(null);
	const index = React.useRef(0);
	const imgRef = React.useRef(null);
	const canvasRef = React.useRef(null);
	const videoCanvasRef = React.useRef(null);

    const videoJsOptions = {
		autoplay: true,
		controls: true,
		// responsive: true,
		// fluid: true,
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
		const overlay = playerRef.current.overlay({
			content: 'Default overlay content',
			debug: true,
			overlays: [{
			  content: 'The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!The video is playing!v',
			  start: 'play',
			  end: 'pause'
			}],
		  });
		  overlay.add({content: "this is a new one", start: "play", end: "pause"});
	};


	function makeBlur(){

		setInterval(()=>{
			if(index.current>=data.scenes.length){
				clearInterval(this);
				return
			}
			if(data.scenes[index.current].time - 0.5 < playerRef.current.currentTime()){
				const block = document.createElement('div');
				block.classList.add('blur-box');
				block.style.top = data.scenes[index.current].y + 'px';
				block.style.left = data.scenes[index.current].x + 'px';
				block.style.width = '100px';
				block.style.height = '100px';
				document.body.appendChild(block);
				
				index.current++;
			}

    	},1000)
	}

	function testDraw(){
		const canvas = videoCanvasRef.current;
		const ctx = videoCanvasRef.current.getContext('2d');
		// ctx.fillRect(25,25,100,100);
		// setTimeout(()=>{ctx.clearRect(0,0,canvas.width,canvas.height)},1000)

		// playerRef.current.requestFullscreen();
		// canvas.requestFullscreen();
		// imgRef.current.requestFullscreen();
		// console.log(playerRef.current.overlay);
	}

	function createBlur(el, data, filter){

		const rect = el.getBoundingClientRect();
		console.log(rect);
		data.localizedObjectAnnotations.forEach((e)=>{
			
			const block = document.createElement('div');
			const normList = e.boundingPoly.normalizedVertices
			const blurWidth = (normList[1].x - normList[0].x)*el.clientWidth;
			const blurHeight = (normList[3].y - normList[0].y)*el.clientHeight;
			console.log(blurHeight, blurWidth);

			block.classList.add('blur-box');
			block.style.top = normList[0].y*el.clientHeight + rect.y + window.scrollY + 'px';
			block.style.left = normList[0].x*el.clientWidth + rect.x + window.scrollX + 'px';
			block.style.width = blurWidth + 'px';
			block.style.height = blurHeight + 'px';
			console.log(normList[0].y + rect.y + 'px');
			document.body.appendChild(block);
			setTimeout(()=>{block.remove()},1000)
		})


	};

	function doSomething(){
		const ctx = canvasRef.current.getContext("2d");
		let a = 0;
		let up = true;
		setInterval(()=>{
			let b = performance.now();
			
			ctx.clearRect(0,0,canvasRef.current.width,canvasRef.current.height);
			
			
			let xList = data2.regions[0].shape_attributes.all_points_x;
			let yList = data2.regions[0].shape_attributes.all_points_y;
			
			ctx.beginPath();
			// region.moveTo(30, 90);
			// region.lineTo(110, 20);
			// region.lineTo(240, 170);
			// region.lineTo(60, 130);
			// region.lineTo(190, 20);
			// region.lineTo(270, 90);
			// region.closePath();
			
			// console.log(xList);
			xList.forEach((e,i)=>{
				if(i===0){
					// console.log(xList[i]/10,yList[i]/10);
					ctx.moveTo(xList[i]/10,(yList[i] - a)/10)
				}
				else if(i<xList.length-1) {
					// console.log(xList[i]/10,yList[i]/10);
					ctx.lineTo(xList[i]/10,(yList[i] - a)/10);
				}
			})
			if(up){
				a-=10;
			} else {
				a+=10;
			}

			if(a>10){
				up=true;
			} else if(a<-10){
				up=false;
			}
			ctx.closePath();
			ctx.fillStyle = "green";
			ctx.fill();
			
			// setTimeout(() => {
				// }, 80);
				let c = performance.now();
				console.log(c-b);
			},10)

	}
	
	function manageCreateBlur(){
		createBlur(imgRef.current, data, "");
		// imgRef.current.requestFullscreen();
	}
	
	return (
		<>
		  	<div>Rest of app here</div>
		  	<VideoJS options={videoJsOptions} onReady={handlePlayerReady} />
			<img src={require('./room.jpg')} alt='room img' ref={imgRef} onClick={manageCreateBlur}/>
			<canvas className='blur-canvas' ref={canvasRef} onClick={doSomething}></canvas>
			<canvas className='video-canvas' ref={videoCanvasRef} onClick={testDraw}></canvas>
		  	<div>Rest of app here</div>
		</>
	);
};

export default Test;
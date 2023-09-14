
import { NavLink } from "react-router-dom";
import VideoBox from "../videoBox/VideoBox";
import "./VideoContainer.css";
import { useEffect } from "react";

export default function VideoContainer({videos}){
    useEffect(()=>{
        let targetClassName = 'flex-wrap-anim';
        let defaultDuration = '0.5s';
          
        let dummyList = [];
        function addDummy(item, duration) {
			let top = item.offsetTop;
			let left = item.offsetLeft;
				setTimeout(function() {
					item.style.position = 'absolute';
					item.style.top = top + 'px';
					item.style.left = left + 'px';
			
					let dummyDiv = document.createElement('div');
					
					dummyDiv.classList.add(targetClassName + '-dummy');
					let rect = item.getBoundingClientRect();
					dummyDiv.style.width = rect.width +10+ 'px';
					dummyDiv.style.height = rect.height +10+ 'px';
					dummyDiv.style.visibility = 'hidden';
					dummyDiv['__' + targetClassName + '_pair'] = item;
					dummyDiv['__' + targetClassName + '_duration'] = duration;
					item.parentNode.appendChild(dummyDiv);
					dummyList.push(dummyDiv);
				}, 0);
            }
          
        let conts = document.getElementsByClassName(targetClassName);
        for (let i = 0, max = conts.length; i < max; i++) {
			let cont = conts[i];
			cont.style.position = 'relative';
			let duration = cont.getAttribute('data-duration') || defaultDuration;
			//   let items = cont.getElementsByTagName('div');
			let items = cont.getElementsByClassName("video-box");
			for (let j = 0, max2 = items.length; j < max2; j++) {
				addDummy(items[j], duration);
			}
        }
          
        window.addEventListener('resize', function(event) {
			dummyList.forEach(function(dummyDiv) {
				let item = dummyDiv['__' + targetClassName + '_pair'];
				let duration = dummyDiv['__' + targetClassName + '_duration'];
				if (item.offsetTop !== dummyDiv.offsetTop) {
					item.style.transition = 'all ' + duration;
					item.style.top = dummyDiv.offsetTop + 'px';
					item.style.left = dummyDiv.offsetLeft + 'px';
				} else {
					item.style.transition = '';
					item.style.left = dummyDiv.offsetLeft + 'px';
				}
			});
        });
    },[])
	
    return(
        <div className="video-container flex-cont flex-wrap-anim">
            {
                videos.map((video, index) => {
                    return (
                        <NavLink to={"/test"} key={video.id} style={{ textDecoration: "none" }}>
                            <VideoBox
                                thumbnail={video.thumbnail}
                                title={video.title}
                                uploader={video.uploader}
                                view={video.view}
                                date={video.date}
                            ></VideoBox>
                        </NavLink>
                    )
                })
            }
        </div>
    )
}
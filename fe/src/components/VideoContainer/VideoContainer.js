
import { NavLink } from "react-router-dom";
import VideoBox from "../videoBox/VideoBox";
import "./VideoContainer.css";
import { useEffect } from "react";

export default function VideoContainer({videos}){
    useEffect(()=>{
        // window.addEventListener('load', function(event) {
            var targetClassName = 'flex-wrap-anim';
            var defaultDuration = '1s';
          
            var dummyList = [];
            function addDummy(item, duration) {
              var top = item.offsetTop;
              var left = item.offsetLeft;
              setTimeout(function() {
                item.style.position = 'absolute';
                item.style.top = top + 'px';
                item.style.left = left + 'px';
          
                var dummyDiv = document.createElement('div');
                
                dummyDiv.classList.add(targetClassName + '-dummy');
                var rect = item.getBoundingClientRect();
                dummyDiv.style.width = rect.width +10+ 'px';
                dummyDiv.style.height = rect.height +10+ 'px';
                dummyDiv.style.visibility = 'hidden';
                dummyDiv['__' + targetClassName + '_pair'] = item;
                dummyDiv['__' + targetClassName + '_duration'] = duration;
                item.parentNode.appendChild(dummyDiv);
                dummyList.push(dummyDiv);
              }, 0);
            }
          
            var conts = document.getElementsByClassName(targetClassName);
            for (var i = 0, max = conts.length; i < max; i++) {
              var cont = conts[i];
              cont.style.position = 'relative';
              var duration = cont.getAttribute('data-duration')
                || defaultDuration;
            //   var items = cont.getElementsByTagName('div');
            var items = cont.getElementsByClassName("video-box");
              for (var i = 0, max = items.length; i < max; i++) {
                addDummy(items[i], duration);
              }
            }
          
            window.addEventListener('resize', function(event) {
              dummyList.forEach(function(dummyDiv) {
                var item = dummyDiv['__' + targetClassName + '_pair'];
                var duration = dummyDiv['__' + targetClassName + '_duration'];
                if (item.offsetTop != dummyDiv.offsetTop) {
                  item.style.transition = 'all ' + duration;
                  item.style.top = dummyDiv.offsetTop + 'px';
                  item.style.left = dummyDiv.offsetLeft + 'px';
                } else {
                  item.style.transition = '';
                  item.style.left = dummyDiv.offsetLeft + 'px';
                }
              });
            });
            console.log(dummyList);
        //   });
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
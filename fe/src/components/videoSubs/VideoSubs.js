import VideoBox from "../videoBox/VideoBox"
import { NavLink } from "react-router-dom"
import "./VideoSubs.css"

export default function VideoSubs({videos}){
    return(
        <div className="video-detail-video-box">
            {
                videos.map((video, idx)=>{
                    return(
                        <NavLink to={`/videoDetail?id=${video.id}`} className="video-detail-video" key={video.id} style={{ textDecoration: "none" }}>
                            <VideoBox
                                style={{ textDecoration: "none" }}
                                thumbnail={video.thumbnail}
                                title={video.title}
                                uploader={video.uploader}
                                view={video.view}
                                date={video.date}
                            />
                        </NavLink>
                    )
                        
                })
            }
		</div>
    )
}
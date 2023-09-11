import VideoBox from "../videoBox/VideoBox"
import "./VideoSection.css"
import { NavLink } from "react-router-dom"
import VideoContainer from "../VideoContainer/VideoContainer"

export default function VideoSection({videos}){

    return (
        <div className="video-section">
            <NavLink to={'/upload'} style={{ textDecoration: "none" }}>
                <div className="video-upload-btn">영상업로드</div>
            </NavLink>
            <VideoContainer videos={videos}/>

        </div>
    )
}

import "./VideoSection.css"
import { NavLink } from "react-router-dom"
import VideoContainer from "../videoContainer/VideoContainer"

export default function VideoSection({videos}){

    return (
        <div className="video-section">
            <VideoContainer videos={videos}/>
            <NavLink to={'/upload'} style={{ textDecoration: "none" }}>
                <div className="video-upload-btn">영상 업로드</div>
            </NavLink>

        </div>
    )
}
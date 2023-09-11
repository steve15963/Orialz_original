
import { NavLink } from "react-router-dom";
import VideoBox from "../videoBox/VideoBox";
import "./VideoContainer.css";

export default function VideoContainer({videos}){
    return(
        <div className="video-container">
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
import './VideoBox.css';
export default function VideoBox({thumbnail, title, uploader, view, date}){

    return (
        <div className="video-box">
            <div>{thumbnail}</div>
            <div>{title}</div>
            <div>{uploader}</div>
            <div>{view}</div>
            <div>{date}</div>
        </div>
    )
}
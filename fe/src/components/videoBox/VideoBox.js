import './VideoBox.css';
export default function VideoBox({thumbnail, title, uploader, view, date}){

    const today = new Date();
    const todayTime = today.getTime();

    function calcPastDate(oldD){
        const date2 = new Date(oldD);
        const pastDays = (todayTime - date2.getTime())/(1000*60*60*24);
        return Math.floor(pastDays);
    }

    return (
        <div >
            <div className="video-box">
                <div className="video-box-thumbnail">
                    <img src={thumbnail} alt='videoImage' className="video-box-thumbnail-image"/>    
                </div>
                <div className="video-box-data">
                    <div className="video-box-title">{title}</div>
                    <div className="video-box-uploader">{uploader}</div>
                    <div className="video-box-viewdate">
                        <div className="video-box-view">조회수:{view}&nbsp;|&nbsp;</div>
                        <div className="video-box-date">{calcPastDate(date)}일 전</div>
                    </div>
                </div>

            </div>
        </div>
    )
}
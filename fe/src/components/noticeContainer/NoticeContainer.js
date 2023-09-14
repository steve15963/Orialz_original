import NoticeBox from '../noticeBox/NoticeBox'
import './NoticeContainer.css'

export default function NoticeContainer({falsifyNoticeOn}){
    return(
        <div className='notice-container'>
            <button className='close-notice-btn' onClick={falsifyNoticeOn}>X</button>
            <NoticeBox/>
            <NoticeBox/>
            <NoticeBox/>
            <NoticeBox/>
        </div>
    )
}
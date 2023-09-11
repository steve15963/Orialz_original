
import { useState } from "react";
import { NavLink } from "react-router-dom";
import NoticeContainer from "../noticeContainer/NoticeContainer";
import "./ProfileBox.css"

export default function ProfileBox(){

    const [noticeOn, setNoticeOn] = useState(false);

    function falsifyNoticeOn(){
        setNoticeOn(false);
    }

    return(
        <div className='bell-profile-box'>
            {noticeOn ? <NoticeContainer falsifyNoticeOn={falsifyNoticeOn}/> : null}
            <img src="/bell.svg" alt="bell" className='bell-img icon-wiggle' onClick={()=>{
                setNoticeOn(true);
            }}/>
            
            <NavLink to={"/profile"}>
                <img src="/profile.svg" alt="profile" className='profile-img icon-wiggle' />
            </NavLink>
        </div>
    )

}
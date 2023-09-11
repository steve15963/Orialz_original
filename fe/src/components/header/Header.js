import { useSelector, useDispatch } from 'react-redux';
import { useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';
import {
  selectUser,
  uploadUser,
  discardUser,
} from '../../util/slice/userSlice';
import './Header.css';
import NoticeContainer from '../noticeContainer/NoticeContainer';

export default function Header({search}){
    
    const user = useSelector(selectUser);
    const dispatch = useDispatch();
    console.log(user);
    const userInfo = {
        email: "hihihiihi",
        nickname: "john"
    }

    

    return (
        <div className='header'>
            <img src="/duck.svg" alt="logo" className='logo-img'/>            
            <form className='search-form'>
                <input placeholder="검색어를 입력하세요" className='search-form-input'></input>
                <button 
                    onClick={search}
                    className='search-form-btn'
                    >검색</button>
            </form>
            <div>
                <button onClick={(e)=>{
                    e.preventDefault();
                    dispatch(uploadUser(userInfo));
                }}>인</button>
                <button onClick={(e)=>{
                    e.preventDefault();
                    dispatch(discardUser());
                }}>아웃</button>
            </div>
            {user.email ? <ProfileBox/> : <LoginBtn/>}

            {/* <button onClick={(e)=>{
                e.preventDefault();
                dispatch(uploadUser(userInfo));
            }}>테스트로그인</button>

            <button onClick={(e)=>{
                e.preventDefault();
                dispatch(discardUser());
            }}>테스트로그아웃</button>

            <button onClick={(e)=>{
                e.preventDefault();
                console.log(user);
            }}>리덕스확인</button> */}
        </div>
    )
}

export function LoginBtn(){
    return(
        <NavLink to={"login"}>
            <div>로그인 하기</div>
        </NavLink>
    )
}

export  function ProfileBox(){

    const [noticeOn, setNoticeOn] = useState(false);

    function falsifyNoticeOn(){
        setNoticeOn(false);
    }

    return(
        <div className='bell-profile-box'>
            {noticeOn ? <NoticeContainer falsifyNoticeOn={falsifyNoticeOn}/> : null}
            <img src="/bell.svg" alt="bell" className='bell-img' onClick={()=>{
                setNoticeOn(true);
            }}/>
            
            <NavLink to={"/profile"}>
                <img src="/profile.svg" alt="profile" className='profile-img' />
            </NavLink>
        </div>
    )

}
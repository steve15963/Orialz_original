
import {useState} from 'react';
import './Profile.css';
import ProfileBanner from '../components/profileBanner/ProfileBanner';
import ProfileWorks from '../components/profileWorks/ProfileWorks';
import FilterSelect from '../components/filterSelect/FilterSelect';


export default function Profile(){

    const [modalOn, setModalOn] = useState(false);

    function handleModalOn(){
        setModalOn(true);
    }

    function handleModalOff(){
        setTimeout(()=>{
            setModalOn(false);
        },100)
    }

    return (
        <div className='profile-page'>
            {modalOn ? <FilterSelect handleModalOff={handleModalOff}/> : null}
            <ProfileBanner handleModalOn={handleModalOn}/>
            <ProfileWorks/>
        </div>
    )
}
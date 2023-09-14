import VideoSection from '../videoSection/VideoSection';
import { useState } from 'react';

export default function ProfileVideoContainer(){
    const [videos] = useState([
        {
            id: 1,
            thumbnail: 'pepe.jpg',
            title:'first video',
            uploader:'Jack',
            view:1,
            date:'2023-08-01',
        },
        {
            id: 2,
            thumbnail: 'pepe.jpg',
            title:'second video',
            uploader:'Mike',
            view:123,
            date:'2021-02-11',
        },
        {
            id: 3,
            thumbnail: 'pepe.jpg',
            title:'third video',
            uploader:'Sam',
            view:4,
            date:'2023-07-23',
        },
        {
            id: 4,
            thumbnail: 'pepe.jpg',
            title:'fourth video',
            uploader:'Lee',
            view:75,
            date:'2022-06-12',
        },
        {
            id: 5,
            thumbnail: 'pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 6,
            thumbnail: 'pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 7,
            thumbnail: 'pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 8,
            thumbnail: 'pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 9,
            thumbnail: 'pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 10,
            thumbnail: 'pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
    ])
    return(
        <div className='profile-video-container'>
            <VideoSection videos={videos}/>
        </div>
    )
}
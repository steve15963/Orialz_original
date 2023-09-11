import {useState} from "react";
import VideoContainer from "../components/videoContainer/VideoContainer";
import Header from "../components/header/Header";
import './Main.css';

export default function Main(){

    const [videos, setVideos] = useState([
        {
            id: 1,
            thumbnail: './pepe.jpg',
            title:'first video',
            uploader:'Jack',
            view:1,
            date:'2023-08-01',
        },
        {
            id: 2,
            thumbnail: './pepe.jpg',
            title:'second video',
            uploader:'Mike',
            view:123,
            date:'2021-02-11',
        },
        {
            id: 3,
            thumbnail: './pepe.jpg',
            title:'third video',
            uploader:'Sam',
            view:4,
            date:'2023-07-23',
        },
        {
            id: 4,
            thumbnail: './pepe.jpg',
            title:'fourth video',
            uploader:'Lee',
            view:75,
            date:'2022-06-12',
        },
        {
            id: 5,
            thumbnail: './pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 6,
            thumbnail: './pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 7,
            thumbnail: './pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 8,
            thumbnail: './pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 9,
            thumbnail: './pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
        {
            id: 10,
            thumbnail: './pepe.jpg',
            title:'fifth video',
            uploader:'Queen',
            view:32,
            date:'2023-04-19',
        },
    ])

    function search(e){
        e.preventDefault();
        //검색하는 알고리즘
    }

    return (
        <div>
            {/* <Header search={search}></Header> */}
            <Header search={search}></Header>
            <div className="main">
                <VideoContainer videos={videos}></VideoContainer>
            </div>
        </div>
    )
}
import { useEffect, useState } from "react";
import VideoContainer from "../components/videoContainer/VideoContainer";
import Header from "../components/header/Header";
import "./Main.css";

export default function Main() {
  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");

    if (token) {
      localStorage.setItem("access_token", token);
      window.location.replace("/");
    }
  }, []);

  const [videos] = useState([
    {
      id: 1,
      thumbnail: "./pepe.jpg",
      title: "first video",
      uploader: "Jack",
      view: 1,
      date: "2023-08-01",
    },
    {
      id: 2,
      thumbnail: "./pepe.jpg",
      title: "second video",
      uploader: "Mike",
      view: 123,
      date: "2021-02-11",
    },
    {
      id: 3,
      thumbnail: "./pepe.jpg",
      title: "third video",
      uploader: "Sam",
      view: 4,
      date: "2023-07-23",
    },
    {
      id: 4,
      thumbnail: "./pepe.jpg",
      title: "fourth video",
      uploader: "Lee",
      view: 75,
      date: "2022-06-12",
    },
    {
      id: 5,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 6,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 7,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 8,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 9,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 10,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 11,
      thumbnail: "./pepe.jpg",
      title: "first video",
      uploader: "Jack",
      view: 1,
      date: "2023-08-01",
    },
    {
      id: 12,
      thumbnail: "./pepe.jpg",
      title: "second video",
      uploader: "Mike",
      view: 123,
      date: "2021-02-11",
    },
    {
      id: 13,
      thumbnail: "./pepe.jpg",
      title: "third video",
      uploader: "Sam",
      view: 4,
      date: "2023-07-23",
    },
    {
      id: 14,
      thumbnail: "./pepe.jpg",
      title: "fourth video",
      uploader: "Lee",
      view: 75,
      date: "2022-06-12",
    },
    {
      id: 15,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 16,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 17,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 18,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 19,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 20,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 21,
      thumbnail: "./pepe.jpg",
      title: "first video",
      uploader: "Jack",
      view: 1,
      date: "2023-08-01",
    },
    {
      id: 22,
      thumbnail: "./pepe.jpg",
      title: "second video",
      uploader: "Mike",
      view: 123,
      date: "2021-02-11",
    },
    {
      id: 23,
      thumbnail: "./pepe.jpg",
      title: "third video",
      uploader: "Sam",
      view: 4,
      date: "2023-07-23",
    },
    {
      id: 24,
      thumbnail: "./pepe.jpg",
      title: "fourth video",
      uploader: "Lee",
      view: 75,
      date: "2022-06-12",
    },
    {
      id: 25,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 26,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 27,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 28,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 29,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
    {
      id: 30,
      thumbnail: "./pepe.jpg",
      title: "fifth video",
      uploader: "Queen",
      view: 32,
      date: "2023-04-19",
    },
  ]);

  function search(e) {
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
  );
}

/* eslint-disable no-unused-vars */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable array-callback-return */
import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Upload.css";
import { useRef } from "react";
import { BsFill1CircleFill, BsFill2CircleFill,BsFill3CircleFill,BsFillFileEarmarkPlayFill } from "react-icons/bs";
import { useNavigate } from "react-router-dom";
function Upload() {
  const [file, setFile] = useState(null);
  const [url, setUrl] = useState({});
  // const [formData, setFormData] = useState(new FormData());
  const [content, setContent] = useState("");
  const [title, setTitle] = useState("");
  const [cssActive, setActive] = useState(false);
  const myTextarea = useRef(null);
  const myTextarea1 = useRef(null);
  const contentRef = useRef(null);
  const progressRef = useRef("0");
  const [isMyTextarea,setIsMyTextarea] = useState(false);
  const [isMyTextarea1,setIsMyTextarea1] = useState(false);
  const [progressActive,setProgressActive] = useState(false);
  const [iconColor,setIconColor] = useState("black");
  const [isChecked,setIsChecked] = useState(true)
  const [category,setCategory] = useState("MUSIC");
  const [progressMax,setProgressMax] = useState(100);
  const navigate = useNavigate();


  const handleDragStart = () => {
    setActive(true);
    setIconColor("orange")
  }

  const handleDragEnd = () => {
    setActive(false);
    setIconColor("black")
  }

  // 파일을 첨부하면 state에 file 저장
  const handleFile = (event) => { // input으로 바로 넣기
    event.preventDefault();

    filefunc(event.target.files[0]);

  };

  const handleDragOver = (event) =>{
    event.preventDefault();


  }

  const handleDrop = (event) =>{ //drag 업로드
    event.preventDefault();
    console.log(event.dataTransfer.files[0]);
    filefunc(event.dataTransfer.files[0]);
  }

  const filefunc = (file) =>{ 
    setFile(file);
    let videoType = "";
    if(file !== undefined){
      videoType =file.type.includes("video");
      setUrl({
        url: URL.createObjectURL(file),
        video: videoType,
      });
    }
    else{
      setUrl({
        url: "",
        video: "",
      });
    }

    setActive(false);
  }

  const handleTextStyle = () =>{
    setIsMyTextarea(true);
    if(isMyTextarea){
      myTextarea.current.style.border = " 1px solid orange";
      myTextarea1.current.style.border = "";
    }else{
      myTextarea.current.style.border = "";
    }

    
  }

  const handleTextStyle1 = () =>{
    setIsMyTextarea1(true);
    if(isMyTextarea1){
      myTextarea1.current.style.border = " 1px solid orange";
      myTextarea.current.style.border = "";
      contentRef.current.focus();
     
    }else{
      myTextarea1.current.style.border = "";
    }
    
    
  }
  const handleMouseEnter = () => {
    setIconColor("orange")
  };

  const handleMouseLeave = () => {
    setIconColor("black")
  };

  const handleContent = (event) => {
    event.target.style.height = '1px';
    event.target.style.height = (12 + event.target.scrollHeight) + 'px' ;
    // myTextarea1.current.style.height = (12 + event.target.scrollHeight) + 'px' ;
    setContent(event.target.value);
  };

  const handleTitle = (event) => {
    event.target.style.height = '1px';
    event.target.style.height = (12 + event.target.scrollHeight) + 'px' ;
    setTitle(event.target.value);
  };

  const upload = async () => {
    if (!file) {
      alert("파일을 선택해주세요.");
      return;
    }
   
   
    const chunkSize = 1024 * 1024 * 10; //10MB
    const totalChunkNum = Math.ceil(file.size / chunkSize);
    setProgressMax(totalChunkNum);

    setProgressActive(true);
    console.log(totalChunkNum);
    // const reader = new FileReader();

    let time = 0;
    for (let chunkNum = 0; chunkNum < totalChunkNum; chunkNum++) {
      const _startTime = performance.now(); // 시작시간
      const start = chunkNum * chunkSize;
      const end = Math.min(start + chunkSize, file.size);
      const chunk = file.slice(start, end);
      const formData = new FormData();
      formData.append("totalChunkNum", totalChunkNum);
      formData.append("chunk", chunk);
      formData.append("fileName", file.name);
      formData.append("chunkNum", chunkNum);
      if (chunkNum === totalChunkNum - 1) {
        formData.append("content", content);
        formData.append("title", title);
        formData.append("category", category);
      }

    
      const response = await axios.post("/upload/chunk", formData, {
        headers: {
            // Authorization:
            //     "Bearer " + localStorage.getItem("access_token"),
          "Content-Type": `multipart/form-data`,
          // "Origin" : 'http://localhost:3000',
        },
        // baseURL: "http://localhost:8080/hls",
        baseURL: "https://test.orialz.com/hls",
      });

      const formData2 = new FormData();
      formData2.append("totalChunkNum", totalChunkNum);
      formData2.append("chunk", chunk);
      formData2.append("fileName", file.name);
      formData2.append("chunkNum", chunkNum);
      formData2.append("videoId",response.data.videoId);
      formData2.append("createAt",response.data.createAt);
      formData2.append("hash",response.data.hash);
      if (chunkNum === totalChunkNum - 1) {
        // formData2.append("content", content);
        // formData2.append("title", title);
        // formData2.append("category", category);
      }


      const response2 = await axios.post("/upload/chunk", formData2, {
        headers: {
          "Content-Type": `multipart/form-data`,
          // "Origin" : 'http://localhost:3000',
        },
        // baseURL: "http://localhost:8081/split",
        baseURL: "https://test.orialz.com/split",
      });
      const _endTime = performance.now(); // 시작시간
      console.log(response);
      if(response.status === 200){
        // console.log("value: "+progressRef.current.value)
        // console.log("total chunkNum" + totalChunkNum);
        // console.log("chunkNum: " + chunkNum);
        // progressRef.current.value = Math.ceil(100 / totalChunkNum * 2) * (chunkNum+1);
        progressRef.current.value = chunkNum + 1;
        if(chunkNum === totalChunkNum -1){
          progressRef.current.value = chunkNum + 1;
          alert("업로드 완료");  
          navigate("/");
        }
      }
      time += _endTime - _startTime;
    }
    console.log("total: " + time + "ms");

  };


  useEffect(()=>{
      if(title.length >= 5 && content.length >= 5 && file != null){
        // console.log(title.length)
        // console.log(content.length)
        // console.log(file)
        // console.log("check!!")
        setIsChecked(false);
      }else{
        setIsChecked(true);
      }

  },[title.length,content.length,file])

  useEffect (()=> {
   
    const handleClickOutside = (event) => {
      if(myTextarea.current && myTextarea.current.contains(event.target)){
        // console.log("asdf")

        return;
      }
      if(myTextarea1.current && myTextarea1.current.contains(event.target)){
        // console.log("asdf")
        return;
      }
      handleTextStyle();
      handleTextStyle1();
    }
    document.addEventListener('click',handleClickOutside);
    
    return () =>{
      document.removeEventListener('click',handleClickOutside);
    }
  },[]);


  return (
<div className="upload-container">
  <div className="content">
    <div className="textContent">
      <h3>세부내용</h3>
          <div  ref = {myTextarea} className="titleContainer" onClick={handleTextStyle}>
            <label htmlFor="article">제목</label>
            <textarea maxLength="200" value={title} onChange={handleTitle}></textarea>
            <h5>{title.length}/200</h5>
          </div>
          <div ref = {myTextarea1} className="contentContainer" onClick={handleTextStyle1}>
            <label htmlFor="article">글 작성</label>
            <textarea  ref = {contentRef} maxLength="1999" value={content} onChange={handleContent}></textarea>
            <h5>{content.length}/2000</h5>
          </div>
    </div>
    <div className="fileUpload" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}> 
    <label className={`preview${cssActive ? ' active': ''}`}
    onDragLeave={handleDragEnd}
    onDragEnter={handleDragStart}
    onDragOver={handleDragOver}
    onDrop={handleDrop}
    >
      <input type="file" accept = "video/*" className="file" onChange={handleFile}/>
      {url.video && <video src={url.url} controls width="100%" height="250px"/>}
      {file == null ? (<div className="beforeUpload">
        <BsFillFileEarmarkPlayFill size="80" color={iconColor}></BsFillFileEarmarkPlayFill>
        <p className="preview_msg">클릭 혹은 파일을 이곳에 드롭하세요.</p>
       {/* <p className='preview_desc'>파일당 최대 3MB</p> */}
      </div>
      ) : ""}
    </label>
  
    <div className="submitCheck">
    <div>
    <BsFill1CircleFill size="50" color={title.length > 5 ? "orange":"black"}></BsFill1CircleFill>
    <p className="checkText">제목</p>
    </div>
    <div>
   <BsFill2CircleFill size="50"  color={content.length > 5 ? "orange":"black"}></BsFill2CircleFill>
    <p className="checkText">설명</p>
    </div>
    <div>
   <BsFill3CircleFill size="50"  color={file != null ? "orange":"black"}></BsFill3CircleFill>
    <p className="checkText">동영상</p>  
    </div>
    </div>
    <div className="progressContainer">
    {progressActive ? ( <label><progress ref = {progressRef} value="0" max = {progressMax}></progress></label>) : ""}
    </div>
    </div>
  </div>
   <div className="btnContainer">
    <button disabled={isChecked} className = "uploadBtn" onClick={upload}>제출</button>
   </div>
</div>
  );
}

export default Upload;

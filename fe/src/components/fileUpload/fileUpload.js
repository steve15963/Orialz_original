
import VideoJS from './videoJs';
import React, { useEffect,useState } from 'react';
import axios from 'axios'; 
function FileUpload() {

  const [file,setFile] = useState(null);
  const [formData,setFormData] = useState(new FormData());
  const [content,setContent] = useState("");
  const [title,setTitle] = useState("");
  const [category,setCategory] = useState('MUSIC');

  // 파일을 첨부하면 state에 file 저장
  const handleFile = (event) =>{
    console.log(event.target.files[0]);
    setFile(event.target.files[0]);
  } 
  

  const handleContent = (event) =>{
    // console.log(event.target.value);
    setContent(event.target.value);
  }
  const handleTitle = (event) =>{
    console.log(event.target.value);
    setTitle(event.target.value);
  }

  const upload = async () => {
    if (!file) {
      alert('파일을 선택해주세요.');
      return;
    }
    
    const chunkSize = 1024 * 1024 * 0.5; //1MB
    const totalChunkNum = Math.ceil(file.size / chunkSize);

    console.log(totalChunkNum);
    // const reader = new FileReader();
  
  
    let time = 0;
    for (let chunkNum = 0; chunkNum <  totalChunkNum; chunkNum++) {
      const _startTime = performance.now(); // 시작시간
      const start = chunkNum * chunkSize;
      const end = Math.min(start + chunkSize, file.size);
      const chunk = file.slice(start, end);
      const formData = new FormData();
      formData.append('totalChunkNum', totalChunkNum);
      formData.append('chunk', chunk);
      formData.append('fileName', file.name);
      formData.append('chunkNum', chunkNum);
      if(chunkNum == totalChunkNum -1){
        formData.append('content',content);
        formData.append('title',title);
        formData.append('category',category);
      }

      const response = await axios.post('/upload/chunk',formData,{
        headers:{
          "Content-Type": `multipart/form-data`,
          // "Origin" : 'http://localhost:3000',
        },
        // baseURL:"http://localhost:8081/hls"
        baseURL:"https://test.orialz.com/hls"
      });
      const _endTime = performance.now(); // 시작시간
      console.log(response);
      // console.log("time: "+(_endTime - _startTime)+"ms");
      time += (_endTime - _startTime);
    }
    console.log("total: "+time+"ms");

  }

  return (
    <div>
      <div>FileUpload</div>
      <input type="file" accept='video/*' onChange={handleFile}></input>
      <div>

      <div>
      <label htmlFor="article">제목 :</label>
      <input type="text" value={title} onChange={handleTitle}></input>
      </div>
      <div>
      <label htmlFor="article">글 작성:</label>
        <textarea value={content} onChange={handleContent}></textarea>
        </div>
      </div>
      <button onClick={upload}>제출</button>
    </div>
  );
}

export default FileUpload;


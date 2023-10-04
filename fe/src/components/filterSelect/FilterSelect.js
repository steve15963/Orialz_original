import {useEffect, useRef, useState} from 'react';
import './FilterSelect.css';
import axios from 'axios';

export default function FilterSelect({handleModalOff}){

    const wordsRef = useRef(null);
    const fcRef = useRef(null);
    const mode = useRef(0);
    const modeBtnRef1 = useRef(null);
    const modeBtnRef2 = useRef(null);
    const modeBtnRef3 = useRef(null);

    // const [keywords, setKeywords] = useState(null);
    const [myWords, setMyWords] = useState(null);

    
    function seeAllWords(){
        for(const child of wordsRef.current.children){
            child.style.display = ''
        }
        mode.current=0;
        modeBtnRef1.current.style.backgroundColor = "aliceblue";
        modeBtnRef2.current.style.backgroundColor = "rgb(255, 186, 101)";
        modeBtnRef3.current.style.backgroundColor = "rgb(255, 186, 101)";
    }

    function seeFilteredWords(){
        for(const child of wordsRef.current.children){
            child.style.display = ''
            if(child.classList.contains('unfiltered')){
                child.style.display = 'none'
            }
        }
        mode.current=1;
        modeBtnRef1.current.style.backgroundColor = "rgb(255, 186, 101)";
        modeBtnRef2.current.style.backgroundColor = "aliceblue";
        modeBtnRef3.current.style.backgroundColor = "rgb(255, 186, 101)";
    }

    function seeUnfilteredWords(){
        for(const child of wordsRef.current.children){
            child.style.display = ''
            if(child.classList.contains('filtered')){
                child.style.display = 'none'
            }
        }
        mode.current=2;
        modeBtnRef1.current.style.backgroundColor = "rgb(255, 186, 101)";
        modeBtnRef2.current.style.backgroundColor = "rgb(255, 186, 101)";
        modeBtnRef3.current.style.backgroundColor = "aliceblue";
    }

    useEffect(()=>{
        console.log("hi");
        initKeywords();
    },[])

    async function initKeywords() {
		try {
            const keywords = await axios.get(`https://test.orialz.com/api/keyword/list`, {});

            const mywords = await axios.get(`https://test.orialz.com/api/onkeyword/list/${1}`, {});
            
            const tempFilter = [];

            keywords.data.forEach((word, idx) => {
                const obj = {
                    id: word.id,
                    keyword: word.keyword,
                    filter: false,
                };
                tempFilter.push(obj);
            });

            mywords.data.forEach((e)=>{
                tempFilter[e.id].filter=true;
            })

            setMyWords(tempFilter);

		} catch (error) {
			//응답 실패
			console.error(error);
		}
	}

    async function toggleKeyword(kid) {
		try {
			//응답 성공
			await axios.get(`https://test.orialz.com/api/onkeyword/change/${1}/${kid}`, {});
		} catch (error) {
			//응답 실패
			console.error(error);
		}
	}

    return(
        <div className='filter-container' ref={fcRef}>
            <div className='filter-options'>
                <div className='filter-modes'>
                    <div className='filter-mode-btn' ref={modeBtnRef1} onClick={seeAllWords} style={{backgroundColor:'aliceblue'}}>전체 보기</div>
                    <div className='filter-mode-btn' ref={modeBtnRef2} onClick={seeFilteredWords}>적용된 키워드</div>
                    <div className='filter-mode-btn' ref={modeBtnRef3} onClick={seeUnfilteredWords}>미적용 키워드</div>
                </div>
                <div className='filter-exit-btn' onClick={(e)=>{fcRef.current.style.animation="filter-container-close 100ms forwards"; handleModalOff();}}>X</div>
            </div>
            <div className='filter-word-container' ref={wordsRef}>
                {!myWords ? null : myWords.map((word,idx)=>{
                    const name = word.keyword;
                    return (<div
                                className={myWords[idx].filter ? 'filter-word filtered':'filter-word unfiltered'}
                                key={word.id}
                                onClick={(e)=>{
                                    // const newFilter = [];
                                    // for(let i=0; i<idx; i++){
                                    //     newFilter.push(user.filter[i]);
                                    // }
                                    // newFilter.push({name:user.filter[idx].name, filter: !user.filter[idx].filter});
                                    // for(let i=idx+1; i<user.filter.length; i++){
                                    //     newFilter.push(user.filter[i]);
                                    // }

                                    if(e.target.classList.contains("unfiltered")){
                                        e.target.classList.add('filtered');
                                        e.target.classList.remove('unfiltered');
                                        if(mode.current===2){e.target.style.display='none'}
                                    } else {
                                        e.target.classList.add('unfiltered');
                                        e.target.classList.remove('filtered');
                                        if(mode.current===1){e.target.style.display='none'}
                                    }


                                    toggleKeyword(word.id);
                                }}
                            >{name}</div>)
                    
                })}
            </div>
        </div>
    )
}

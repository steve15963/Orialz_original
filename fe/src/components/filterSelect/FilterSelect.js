import { useSelector, useDispatch } from 'react-redux';
import {selectUser, uploadFilter} from '../../util/slice/userSlice'
import {useRef} from 'react';
import './FilterSelect.css';

export default function FilterSelect({handleModalOff}){

    
    const user = useSelector(selectUser);
    const dispatch = useDispatch();
    const wordsRef = useRef(null);
    const fcRef = useRef(null);
    const mode = useRef(0);
    const modeBtnRef1 = useRef(null);
    const modeBtnRef2 = useRef(null);
    const modeBtnRef3 = useRef(null);


    
    console.log(mode.current);
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
                {user.filter.map((e,idx)=>{
                    const name = e.name;
                    return (<div
                                className={user.filter[idx].filter ? 'filter-word filtered':'filter-word unfiltered'}
                                key={idx}
                                onClick={(e)=>{
                                    const newFilter = [];
                                    for(let i=0; i<idx; i++){
                                        newFilter.push(user.filter[i]);
                                    }
                                    newFilter.push({name:user.filter[idx].name, filter: !user.filter[idx].filter});
                                    for(let i=idx+1; i<user.filter.length; i++){
                                        newFilter.push(user.filter[i]);
                                    }

                                    if(newFilter[idx].filter){
                                        e.target.classList.add('filtered');
                                        e.target.classList.remove('unfiltered');
                                        if(mode.current===2){e.target.style.display='none'}
                                    } else {
                                        e.target.classList.add('unfiltered');
                                        e.target.classList.remove('filtered');
                                        if(mode.current===1){e.target.style.display='none'}
                                    }
                                    dispatch(uploadFilter(newFilter))
                                }}
                            >{name}</div>)
                    
                })}
            </div>
        </div>
    )
}

export function filterWord(){
    return(
        <div>name</div>
    )
}
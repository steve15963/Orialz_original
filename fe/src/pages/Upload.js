import './Upload.css'

export default function Upload(){
    return (
        <div className="upload-container">
            <form className='upload-form'>
                <div>영상불러오기</div>
                <input placeholder='영상 제목을 입력해주세요.'/>
                <input placeholder='영상 설명을 입력해주세요.'/>
                <div>게시하기</div>
            </form>
        </div>
    )
}
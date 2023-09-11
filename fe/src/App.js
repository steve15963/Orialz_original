import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Login from './pages/Login';
import Main from './pages/Main';
import Test from './pages/Test';
import Profile from './pages/Profile';
import Upload from './pages/Upload';
import './App.css';

function App() {
	return (
		<BrowserRouter>
			<div className="App">
				<Link to="/login">Login</Link>
				<Link to="/">Main</Link>
				<Link to="/test">테스트페이지</Link>
				<Link to="/profile">프로필</Link>
				<Link to="/upload">업로드</Link>

				<Routes>
					<Route path="/login" element={<Login/>}></Route>
					<Route path="/" element={<Main/>}></Route>
					<Route path="/test" element={<Test/>}></Route>
					<Route path="/profile" element={<Profile/>}></Route>
					<Route path="/upload" element={<Upload/>}></Route>
				</Routes>
			</div>
		</BrowserRouter>
	);
}

export default App;

import { NavLink, Outlet } from "react-router-dom";

export default function Navbar()
{
    const success =localStorage.getItem("login");
    let navItem;
    if(success == "success")
    {
      navItem = <li className="nav-item mx-3">
                  <NavLink to="/logout" className={({isActive}) => {return isActive ? "nav-link active" : "nav-link" }}>로그아웃</NavLink>
                </li>
    }
    else
    {
      navItem = <li className="nav-item mx-3">
                  <NavLink to="/login" className={({isActive}) => {return isActive ? "nav-link active" : "nav-link" }}>로그인</NavLink>
                </li>
    }
    return(
      <>
        <nav className="navbar navbar-expand-md">
          <div className="container-fluid">
            <NavLink to="/" className="navbar-brand" style={{color: '#001F58', fontSize: '36px'}} end>
              <i className="bi bi-table me-3"></i>AutoTimeTable
            </NavLink>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
              <ul className="navbar-nav ms-auto nav-tabs me-5">
                <li className="nav-item mx-3">
                  <NavLink to="/selectSubject" className={({isActive}) => {return isActive ? "nav-link active" : "nav-link" }}>과목 선택</NavLink>
                </li>
                <li className="nav-item mx-3">
                  <NavLink to="/conditionSelect" className={({isActive}) => {return isActive ? "nav-link active" : "nav-link" }}>조건 선택</NavLink>
                </li>
                <li className="nav-item mx-3">
                  <NavLink to="/generateTimeTalbe" className={({isActive}) => {return isActive ? "nav-link active" : "nav-link" }} aria-current="page">시간표</NavLink>
                </li>
                {navItem}
              </ul>
            </div>
          </div>
        </nav>
        <Outlet/>
      </>
    )
}
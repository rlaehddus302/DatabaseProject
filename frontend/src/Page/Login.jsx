import { useRef, useState } from "react"
import { Link, useNavigate } from "react-router-dom";

export default function Login()
{
    const [loginSuccess, setLoginSucess] = useState(true);
    const navigate = useNavigate();
    const id = useRef(null);
    const passowrd = useRef(null);
    async function move()
    {
        try
        {
            let loginId = id.current.value;
            let loginPwd = passowrd.current.value;
            const baToken = 'Basic ' + window.btoa(loginId + ":" + loginPwd)
            const response = await fetch('http://localhost:8080/basicOauth', {
                method: 'POST',
                credentials: 'include',
                headers: { 'Authorization': baToken }
            });
            console.log(response)
            if(response.status != 200)
                {
                    throw new Error("로그인 오류");
                } 
            localStorage.setItem("login","success");
            navigate("/")
        }
        catch(e)
        {
            console.log(e)
            setLoginSucess(false)
        }
    }
    return(
        <>
            <section className="vh-100 d-flex justify-content-center align-items-center bg-body-secondary">
                <div className = "p-3 bg-white rounded shadow-sm" style={{width: "480px"}}>
                    <p className="mb-0 fw-bold fs-2">로그인</p>
                    <p className="fs-6 text-secondary">
                        시간표 생성을 위해 로그인 해 주세요
                    </p>
                    <div class="mb-2">
                        <label htmlFor="id" className="form-label">학번</label>
                        <input ref={id} type="text" className="form-control" id="id"/>
                    </div>
                    <div class="mb-2">
                        <label htmlFor="password" className="form-label">비밀번호</label>
                        <input ref={passowrd} type="password" className="form-control" id="password"/>
                    </div>
                    <button onClick={move} type="button" className="mt-3 btn btn-dark w-100 text-center">로그인</button>
                    {!loginSuccess && <p className="fs-6 text-danger"> 아이디 또는 비밀번호가 잘못 되었습니다. 
                        아이디와 비밀번호를 정확히 입력해 주세요.</p>}
                    <p className="text-center mt-4 mb-4">계정이 없으신가요? <Link to={"/signUp"} className="text-decoration-none">회원가입</Link></p>
                </div>
            </section>
        </>
    )
}
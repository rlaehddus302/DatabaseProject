import { useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function SignUp()
{
    const [checkMessage, setCheckMessage] = useState(true);
    const [duplicate,setDuplicate] = useState(false);
    const navigate = useNavigate();
    const name = useRef(null);
    const studentNumber = useRef(null);
    const id = useRef(null);
    const password = useRef(null);
    const passwordConfig = useRef(null);
    function check()
    {
        if(passwordConfig.current.value == "")
        {
            setCheckMessage(true);
        }
        else if(password.current.value != passwordConfig.current.value)
        {
            setCheckMessage(false);
            setDuplicate(false);
        }
        else
        {
            setCheckMessage(true);
            setDuplicate(true);
        }
    }
    async function move(event)
    {
        event.preventDefault();
        if(duplicate)
        {
            let body = {
                "studentNumber" :  studentNumber.current.value,
                "password" : password.current.value,
              	"name" : name.current.value,
                "id" : id.current.value
            }
            let data = JSON.stringify(body);
            console.log(data)
            try
            {
                const response = await fetch('http://localhost:8080/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json', // JSON 형식임을 명시
                    },
                    body: data,
                });
                console.log(response)
                if(response.status != 201)
                    {
                        throw new Error("생성 중 오류 발생");
                    } 
                navigate("/")
            }
            catch(e)
            {
                console.log(e)
            }
        }
    }
    return(
        <>
            <section className="vh-100 d-flex justify-content-center align-items-center bg-body-secondary">
                <div className = "p-3 bg-white rounded shadow-sm" style={{width: "480px"}}>
                    <p className="mb-0 fw-bold fs-2">회원가입</p>
                    <p className="fs-6 text-secondary">새 계정을 만들어 시간표 생성 서비스를 이용해보세요.</p>
                    <form onSubmit={(event) => move(event)}>
                        <div class="mb-2">
                            <label htmlFor="name" className="form-label">이름</label>
                            <input required ref={name} type="text" className="form-control" id="name"/>
                        </div>
                        <div class="mb-2">
                            <label htmlFor="studentNumber" className="form-label">학번</label>
                            <input required ref={studentNumber} type="text" className="form-control" id="studentNumber"/>
                        </div>
                        <div class="mb-2">
                            <label htmlFor="id" className="form-label">아이디</label>
                            <input required ref={id} type="text" className="form-control" id="id"/>
                        </div>
                        <div class="mb-2">
                            <label htmlFor="password" className="form-label">비밀번호</label>
                            <input required onChange={check} ref={password} type="password" className="form-control" id="password"/>
                        </div>
                        <div class="mb-2">
                            <label htmlFor="passwordConfig" className="form-label">비밀번호 확인</label>
                            <input required onChange={check} ref={passwordConfig} type="password" className="form-control" id="passwordConfig"/>
                        </div>
                        {!checkMessage && <p className="fs-6 text-danger">비밀번호가 일치하지 않습니다.</p>}
                        <p></p>
                        <button className="mt-3 btn btn-dark w-100 text-center">로그인</button>
                    </form>
                    <p className="text-center mt-4 mb-4">계정이 있으신가요? <Link to={"/login"} className="text-decoration-none">회원가입</Link></p>
                </div>
            </section>
        </>
    )
}
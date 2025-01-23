import { useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function SignUp()
{
    const [checkMessage, setCheckMessage] = useState(false);
    const [valid,setValid] = useState(false);
    const [exist,setExist] = useState(null);
    const navigate = useNavigate();
    const name = useRef(null);
    const studentNumber = useRef(null);
    const id = useRef(null);
    const password = useRef(null);
    const passwordConfig = useRef(null);
    let content=""
    if(exist == null )
    {
        content="";
    }
    else if(exist)
    {
        content = <p className="fs-6 text-danger">이미 존재하는 아이디입니다.</p>
    }
    else
    {
        content = <p className="fs-6 text-success">사용 가능한 아이디입니다.</p>;
    }
    function check()
    {
        if(passwordConfig.current.value == "")
        {
            setCheckMessage(false);
        }
        else if(password.current.value != passwordConfig.current.value)
        {
            setCheckMessage(true);
            setValid(false);
        }
        else
        {
            setCheckMessage(false);
            setValid(true);
        }
    }
    async function idDuplicate()
    {
        let data = id.current.value;
        try
            {
                const response = await fetch('http://localhost:8080/idDuplicate', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'text/plain', 
                      },
                    body: data,
                });
                console.log(response)
                if(!response.ok)
                {
                    throw new Error("생성 중 오류 발생");
                } 
                setExist(false);
            }
            catch(e)
            {
                console.log(e)
                setExist(true);
            }
    }
    async function move(event)
    {
        event.preventDefault();
        if(valid)
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
                        'Content-Type': 'application/json', 
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
                            <div className="row w-100">
                                <div className="col-9">
                                    <input required ref={id} type="text" className="form-control" id="id"/>
                                </div>
                                <button onClick={idDuplicate} type="button" className="btn btn-primary col">중복확인</button>
                            </div>
                        </div>
                        {content}
                        <div class="mb-2">
                            <label htmlFor="password" className="form-label">비밀번호</label>
                            <input required onChange={check} ref={password} type="password" className="form-control" id="password"/>
                        </div>
                        <div class="mb-2">
                            <label htmlFor="passwordConfig" className="form-label">비밀번호 확인</label>
                            <input required onChange={check} ref={passwordConfig} type="password" className="form-control" id="passwordConfig"/>
                        </div>
                        {checkMessage && <p className="fs-6 text-danger">비밀번호가 일치하지 않습니다.</p>}
                        <p></p>
                        <button className="mt-3 btn btn-dark w-100 text-center">등록</button>
                    </form>
                    <p className="text-center mt-4 mb-4">계정이 있으신가요? <Link to={"/login"} className="text-decoration-none">로그인</Link></p>
                </div>
            </section>
        </>
    )
}
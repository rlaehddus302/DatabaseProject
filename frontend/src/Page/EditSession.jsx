import { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function EditSession()
{
    const param = useParams();
    const [sessionList,setSessionList] = useState([]);
    const [error, setError] = useState(null);
    const [subjectName,setSubjectName] = useState(null);
    const [session,setSession] = useState(null)
    const navigate = useNavigate();
    const modalRef = useRef(null);
    useEffect(() => {
                async function fetchCourse() {
                    let response
                    try
                    {
                        response = await fetch(`http://localhost:8080/adminCourse/${param.id}` ,{
                            credentials: "include",
                            headers: {
                                'Authorization': localStorage.getItem("jwt")
                            }
                        })
                        const resData = await response.json()
                        if(response.ok)
                        {
                            console.log(resData.session)
                            setSessionList(resData.session)
                            setSubjectName(resData.name)
                        }
                        else
                        {
                            const err = new Error();
                            err.status = response.status;
                            err.body = resData;
                            throw err;
                        }
                    }
                    catch(e)
                    {
                        setError(e);
                    }
                }
                const success =localStorage.getItem("login");
                if(success != "success")
                    {
                        navigate("/login")
                    }
                fetchCourse()
    },[])
    function openModal(index)
    {
        const modal = new window.bootstrap.Modal(modalRef.current);
        modal.show();
        setSession(()=>{
            const deepCopy = JSON.parse(JSON.stringify(sessionList[index]));
            return deepCopy;
        })
    }
    function updateSession(name, value, index)
    {
        let deepCopy = JSON.parse(JSON.stringify(session));
        if(index < 0)
        {
           deepCopy[name] = value;
           setSession(deepCopy);
        }
        else
        {
            deepCopy["classTimeAndLocation"][index][name]=value;
            setSession(deepCopy)
        }
    }
    async function sendUpdated()
    {
        console.log(session)
        let data = JSON.stringify(session);
        let response = await fetch('http://localhost:8080/updateSession',{
            credentials: "include",
            method : "PUT",
            body : data,
            headers: {
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("jwt")
            },
        })
        if(response.ok)
        {
            const modal = window.bootstrap.Modal.getInstance(modalRef.current);            
            if (modal) 
                modal.hide();
            setCourseList((prevStat) => {
                let copy = prevStat.map((course,index)=>{
                    if(index === sendCourse.id - 1)
                    {
                        return {...course,...sendCourse};
                    }
                    else
                    {
                        return course
                    }
                })
                console.log(copy);
                return copy;
            })
        }
        else
        {
            let message = response.json();
        }
    }
    async function deleteSession(index) {
        let response = await fetch('http://localhost:8080/deleteSession',{
            credentials: "include",
            method : "DELETE",
            body : index,
            headers: {
                'Content-Type': 'text/plain', 
                'Authorization': localStorage.getItem("jwt")
            },
        })
        if(response.ok)
        {
            setCourseList((prevStat)=>{
                return prevStat.filter((element)=>{
                    return element.id != index
                })
            })
        }
    }
    if(error)
    {
        throw error
    }
    return(
        <section className="container">
            <p className="fs-3 fw-bold m-0">과목 수정</p>
            <p className="text-body-tertiary">과목 수정 페이지</p>
            <hr />
            <div className="d-flex justify-content-center">
                <div className="w-75 border border-dark-subtle rounded mb-3">
                    <div className="mx-3">
                        <p className="mt-3 fs-4 fw-bold">과목 목록</p>
                        <table className="table">
                            <thead>
                                <tr>
                                    <th scope="col">과목명</th>
                                    <th scope="col">학수번호</th>
                                    <th scope="col">담당교원</th>
                                    <th scope="col">액션</th>
                                </tr>
                            </thead>
                            <tbody>
                                {sessionList.map((data, index)=>{
                                    return(
                                        <>
                                            <tr>
                                                <th className="align-middle" scope="row">{subjectName}</th>
                                                <td className="align-middle">{data.sessionCODE}</td>
                                                <td className="align-middle">{data.professor}</td>
                                                <td className="align-middle">
                                                    <button type="button" className="btn btn-light me-2" onClick={()=>openModal(index)}>수정</button>
                                                    <button type="button" className="btn btn-danger me-2" onClick={()=>deleteSession(data.id)}>삭제</button>
                                                </td>
                                            </tr>
                                        </>
                                    )                                    
                                })}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div className="modal fade" ref={modalRef} tabindex="-1" aria-labelledby="modify" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content">
                        <div className="modal-header border-0">
                            <h1 className="ms-auto modal-title fs-5 fw-bold" id="modify">Modal title</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <div className="row mb-3">
                                <div className="col-3">
                                    <div className="h-100 d-flex align-items-center">
                                        <label className="ms-auto mb-0 form-label">학수번호</label>
                                    </div>
                                </div>
                                <div className="col-9">
                                    <div className="d-flex">
                                        <input type="text" readonly class="form-control-plaintext w-50" value={session? session.sessionCODE.split("-")[0]+"-" : ""}/>
                                        <input type="text" className="form-control" value={session? session.sessionCODE.split("-")[1] : ""} onChange={(e)=>{updateSession("sessionCODE",session.sessionCODE.split("-")[0]+"-"+e.target.value,-1)}}/>
                                    </div>
                                </div>
                            </div>
                            <div className="row mb-3">
                                <div className="col-3">
                                    <div className="h-100 d-flex align-items-center">
                                        <label className="ms-auto mb-0 form-label">담당교원</label>
                                    </div>
                                </div>
                                <div className="col-9">
                                    <input type="text" className="form-control" value={session? session.professor : ""} onChange={(e)=>{updateSession("professor",e.target.value,-1)}}/>
                                </div>
                            </div>
                            <div className="row mb-3">
                                <div className="col-3">
                                    <div className="h-100 d-flex align-items-center">
                                        <label className="ms-auto mb-0 form-label">비고</label>
                                    </div>
                                </div>
                                <div className="col-9">
                                    <textarea className="form-control" value={session? session.remarks : ""} onChange={(e)=>{updateSession("remarks",e.target.value,-1)}}/>
                                </div>
                            </div>
                            {session && session.classTimeAndLocation.map((value,index)=>{
                                return(
                                    <>
                                        <div className="row mb-3">
                                            <div className="col-3">
                                                <div className="h-100 d-flex align-items-center">
                                                    <label className="ms-auto mb-0 form-label">강의{index+1} 요일</label>
                                                </div>
                                            </div>
                                            <div className="col-9">
                                                <input type="text" className="form-control" value={value.week} onChange={(e)=>{updateSession("week",e.target.value,index)}}/>
                                            </div>
                                        </div>
                                        <div className="row mb-3">
                                            <div className="col-3">
                                                <div className="h-100 d-flex align-items-center">
                                                    <label className="ms-auto mb-0 form-label">강의{index+1} 시간</label>
                                                </div>
                                            </div>
                                            <div className="col-9">
                                                <div className="d-flex">
                                                    <input type="text" className="form-control" value={value.startTime} onChange={(e)=>{updateSession("startTime",e.target.value,index)}}/>
                                                    <input type="text" className="form-control" value={value.endTime} onChange={(e)=>{updateSession("endTime",e.target.value,index)}}/>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="row mb-3">
                                            <div className="col-3">
                                                <div className="h-100 d-flex align-items-center">
                                                    <label className="ms-auto mb-0 form-label">강의{index+1} 장소</label>
                                                </div>
                                            </div>
                                            <div className="col-9">
                                                <input type="text" className="form-control" value={value.location} onChange={(e)=>{updateSession("location",e.target.value,index)}}/>
                                            </div>
                                        </div>
                                    </>
                                );
                            })}
                        </div>
                        <div class="modal-footer border-0">
                            <button type="button" className="btn btn-dark w-100" onClick={sendUpdated}>저장</button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}
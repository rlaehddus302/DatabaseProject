import { useEffect, useRef, useState } from "react"
import { useNavigate } from "react-router-dom";
export default function EditSubject()
{
    const [courseList, setCourseList] = useState([])
    const [course,setCourse] = useState({
        "name" : null,
        "credit" : null,
        "curriculum" : null,
        "area" : null,
        "code" : null,
        "id" : null,
    })
    const navigate = useNavigate();
    const modalRef = useRef(null);
    useEffect(() => {
            async function fetchCourse() {
                let response
                try
                {
                    response = await fetch('http://localhost:8080/course',{
                        credentials: "include",
                    })
                    const resData = await response.json()
                    setCourseList(resData)
                    console.log(resData)
                }
                catch(e)
                {
                    console.error("실패")
                    console.error(e)
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
        setCourse((prevData)=>{
            let copy = {...prevData}
            copy.name = courseList[index].name
            copy.credit = courseList[index].credit
            copy.code = courseList[index].code
            copy.area = courseList[index].area
            copy.curriculum = courseList[index].curriculum
            copy.id = courseList[index].id
            return copy;
        })
    }
    function updateCourse(name, value)
    {
        setCourse(prevData => {
            return {
                ...prevData,
                [name] : value,
            }
        })
    }
    async function sendUpdated()
    {
        let data = JSON.stringify(course);
        let sendCourse = course
        let response = await fetch('http://localhost:8080/updateCourse',{
            credentials: "include",
            method : "PUT",
            body : data,
            headers: {
                'Content-Type': 'application/json', 
            },
        })
        if(response.ok)
        {
            const modal = window.bootstrap.Modal.getInstance(modalRef.current);            
            if (modal) 
                modal.hide();
            console.log(sendCourse.id)
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
    async function deleteCourse(index) {
        let response = await fetch('http://localhost:8080/deleteCourse',{
            credentials: "include",
            method : "DELETE",
            body : index,
            headers: {
                'Content-Type': 'text/plain', 
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
                                    <th scope="col">과목코드</th>
                                    <th scope="col">학점</th>
                                    <th scope="col">액션</th>
                                </tr>
                            </thead>
                            <tbody>
                                {courseList.map((data, index)=>{
                                    return(
                                        <>
                                            <tr>
                                                <th className="align-middle" scope="row">{data.name}</th>
                                                <td className="align-middle">{data.code}</td>
                                                <td className="align-middle">{data.credit}</td>
                                                <td className="align-middle">
                                                    <button type="button" className="btn btn-light me-2" onClick={()=>openModal(index)}>수정</button>
                                                    <button type="button" className="btn btn-danger me-2" onClick={()=>deleteCourse(data.id)}>삭제</button>
                                                    <button type="button" className="btn btn-secondary" onClick={()=>{navigate(`${data.id}`)}}>분반 관리</button>
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
                                        <label className="ms-auto mb-0 form-label">과목명</label>
                                    </div>
                                </div>
                                <div className="col-9">
                                    <input type="text" className="form-control" value={course.name} onChange={(event) => {updateCourse("name",event.target.value)}}/>
                                </div>
                            </div>
                            <div className="row mb-3">
                                <div className="col-3">
                                    <div className="h-100 d-flex align-items-center">
                                        <label className="ms-auto mb-0 form-label">과목코드</label>
                                    </div>
                                </div>
                                <div className="col-9">
                                    <input type="text" className="form-control" value={course.code} onChange={(e)=>{updateCourse("code",e.target.value)}}/>
                                </div>
                            </div>
                            <div className="row mb-3">
                                <div className="col-3">
                                    <div className="h-100 d-flex align-items-center">
                                        <label className="ms-auto mb-0 form-label">학점</label>
                                    </div>
                                </div>
                                <div className="col-9">
                                    <input type="text" className="form-control" value={course.credit} onChange={(e)=>{updateCourse("credit",e.target.value)}}/>
                                </div>
                            </div>
                            <div className="row mb-3">
                                <div className="col-3">
                                    <div className="h-100 d-flex align-items-center">
                                        <label className="ms-auto mb-0 form-label">교과과정</label>
                                    </div>
                                </div>
                                <div className="col-9">
                                    <input type="text" className="form-control" value={course.curriculum} onChange={(e)=>{updateCourse("curriculum",e.target.value)}}/>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-3">
                                    <div className="h-100 d-flex align-items-center">
                                        <label className="ms-auto mb-0 form-label">영역</label>
                                    </div>
                                </div>
                                <div className="col-9">
                                    <input type="text" className="form-control" value={course.area} onChange={(e) => {updateCourse("area",e.target.value)}}/>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer border-0">
                            <button type="button" className="btn btn-dark w-100" onClick={sendUpdated}>저장</button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
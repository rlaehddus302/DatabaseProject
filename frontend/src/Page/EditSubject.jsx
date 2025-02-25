import { useEffect, useRef, useState } from "react"
import { useNavigate } from "react-router-dom";
export default function EditSubject()
{
    const [courseList, setCourseList] = useState([])
    const [error, setError] = useState(null);
    const [academicTerm, setAcademicTerm] = useState({
        "academic_year" : new Date().getFullYear(),
        "semester" : 1 
    })
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
    const csvRef = useRef(null);
    if(error)
    {
        throw error
    }
    async function fetchCourse(value) {
        let response
        let data = JSON.stringify(value)
        try
        {
            response = await fetch('http://localhost:8080/adminCourse',{
                method : "POST",
                credentials: "include",
                body : data,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem("jwt") 
                },
            })
            const resData = await response.json()
            if(response.ok)
            {
                setCourseList(resData)
                console.log(resData)
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
    useEffect(() => {
            const success =localStorage.getItem("login");
            if(success != "success")
                {
                    navigate("/login")
                }
            fetchCourse(academicTerm)
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
                'Authorization': localStorage.getItem("jwt") 
            },
        })
        if(response.ok)
        {
            const modal = window.bootstrap.Modal.getInstance(modalRef.current);            
            if (modal) 
                modal.hide();
            console.log(sendCourse.id)
            setCourseList((prevStat) => {
                let copy = prevStat.map((course)=>{
                    if(course.id === sendCourse.id)
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
    async function uploadCSV(csv) {
        const formData = new FormData();
        formData.append("csv", csv);
        formData.append("academic_year", academicTerm.academic_year)
        formData.append("semester",academicTerm.semester)
        try {
            const response = await fetch("http://localhost:8080/csv", {
                credentials: "include",
                method: "POST",
                body: formData,
                headers:{
                    'Authorization': localStorage.getItem("jwt"),
                }
            });
            alert("생성이 완료되었습니다.");
            navigate(0);
            console.log("Upload Success:", data);
        } catch (error) {
            console.error("Upload Error:", error);
        }
    }

    function handleAcademicTerm(name, value)
    {
        const copy = {...academicTerm,
            [name] : value,
        }
        setAcademicTerm(copy)
        fetchCourse(copy)
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
                        <div className="d-flex">
                            <input type="number" value={academicTerm.academic_year} min={2000} max={2100} onChange={(e)=>{handleAcademicTerm("academic_year",e.target.value)}} className="form-control me-2 w-25"/>
                            <select value={academicTerm.semester} className="form-select w-25" onChange={(e)=>{handleAcademicTerm("semester",e.target.value)}} aria-label="Default select example">
                                <option value="1">1학기</option>
                                <option value="2">2학기</option>
                            </select>
                        </div>
                        <div className="d-flex">
                            <button type="button" className="btn btn-dark h-50" style={{marginTop: "2.0em"}}>과목 추가</button>
                            <div class="mb-3 ms-auto">
                                <label htmlFor="formFile" className="form-label fw-bold">csv파일 업로드</label>
                                <input accept=".csv" ref={csvRef} className="form-control" type="file" id="formFile"/>
                            </div>
                            <button type="button" className="btn btn-dark h-50 ms-1" onClick={()=>{uploadCSV(csvRef.current.files[0])}} style={{marginTop: "2.0em"}}>확인</button>
                        </div>
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
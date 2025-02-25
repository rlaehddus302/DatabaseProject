import { useEffect, useState } from "react"
import Course from '../Components/Course'
import Select from '../Components/Select'
import { useNavigate } from "react-router-dom"
import Search from "../Components/Search"
export default function SelectSubject()
{
    const [courseList, setCourseList] = useState([])
    const [selectList, setSelectList] = useState([])
    const [academicTerm, setAcademicTerm] = useState({
        "academic_year" : new Date().getFullYear(),
        "semester" : 1 
    })
    const navigate = useNavigate();
    async function move()
    {
        if(selectList.reduce((accumulator, currentValue) => accumulator + currentValue.credit,0 )>24)
        {
            window.alert("최대 24학점까지 선택할 수 있습니다.");
        }
        else
        {
            const courses = selectList.map((value) => value.name);
            console.log(JSON.stringify(courses))
            const response = await fetch('http://localhost:8080/caculate', {
                method: 'POST',
                credentials: "include",
                body: JSON.stringify(courses),
                headers: { 'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem("jwt"),
                 }
            });
            navigate("/generateTimeTalbe")
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
    async function fetchCourse(value) {
        let response
        const currentYear = new Date().getFullYear();
        try
        {
            response = await fetch(`http://localhost:8080/course?year=${value.academic_year}&semester=${value.semester}`,
            {
                credentials: "include",
                headers:{ 
                    'Authorization': localStorage.getItem("jwt"),
                }
            })
            const resData = await response.json()
            setCourseList(resData)
        }
        catch(e)
        {
            console.error("실패")
            console.error(e)
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
    let name = [...selectList]
    return (
        <section className="container">
            <p className="fs-3 fw-bold m-0">과목 선택</p>
            <p className="text-body-tertiary">원하는 과목을 선택하세요</p>
            <hr />
            <div className="d-flex justify-content-center">
                <div className="w-75 border border-dark-subtle rounded mb-3">
                    <div className="mx-3">
                        <button className="text-start bg-white w-100 my-3 border border-black rounded p-2" data-bs-toggle="modal" data-bs-target="#exampleModal">
                            <i className="bi bi-search m-2"></i>
                            <span className="text-black-50">과목명으로 검색</span>
                        </button>
                        <Search handleSelect={setSelectList} academicTerm={academicTerm}/>
                        <p className="fs-4 fw-bold">과목 목록</p>
                        <div className="d-flex mb-3">
                            <input type="number" value={academicTerm.academic_year} min={2000} max={2100} onChange={(e)=>{handleAcademicTerm("academic_year",e.target.value)}} className="form-control me-2 w-25"/>
                            <select value={academicTerm.semester} className="form-select w-25" onChange={(e)=>{handleAcademicTerm("semester",e.target.value)}} aria-label="Default select example">
                                <option value="1">1학기</option>
                                <option value="2">2학기</option>
                            </select>
                        </div>
                        <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-2">
                            {courseList.map((value)=>{
                                return(
                                <Course selectList={name} handleSelect={setSelectList} name={value.name} courseCode={value.code} credit={value.credit}/>
                            )})}
                        </div>
                        <p className="fs-4 fw-bold mt-3">선택한 과목</p>
                        {
                            selectList.map((value) => {
                                return(
                                    <Select handleSelect={setSelectList} name={value.name} courseCode={value.code} credit={value.credit}></Select>
                                )
                            })
                        }
                        <span className="fs-5 fw-bold my-3">
                            총 학점:{selectList.reduce((accumulator, currentValue) => accumulator + currentValue.credit,0 )}
                        </span>
                        <div className="d-flex">
                            <button type='button' className="btn btn-primary btn-dark mb-3 ms-auto">
                                <span className="text-white fs-5"onClick={move}>시작하기</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
import { useEffect, useRef, useState } from "react";
import Table from "../Components/Table"
import MyImage from '../assets/react.svg';
import { useNavigate } from "react-router-dom";

export default function TimeTalbe()
{
    const [table, setTable] = useState([])
    const [loading,setLoading] = useState(false)
    const navigate = useNavigate();
    async function storeTable(index)
    {
        event.preventDefault();
        const formData = new FormData(event.target);
        const inputValue = formData.get('tableName');
        const fetchData = {
            "body" : table[index],
            "tableName" : inputValue
        }
        console.log(inputValue)
        const response = await fetch('http://localhost:8080/storeMyTimeTable', {
            method: 'POST',
            credentials: "include",
            body: JSON.stringify(fetchData),
            headers: { 'Content-Type': 'application/json' }
        });
        console.log(response)
        if(response.ok === false)
        {
            alert("이미 존재하는 이름입니다.");
        }
        else
        {
            alert("시간표가 저장되었습니다.");
        }
    }
    useEffect(() => {
        async function fetchTable() {
            let response
            try
            {
                response = await fetch('http://localhost:8080/table',{
                    credentials: "include",
                })
                const resData = await response.json()
                console.log(response)
                console.log(resData)
                const length = resData.length;
                setTable(resData)
                setTimeout(() => {
                    setLoading(true)
                }, length*25);
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
        fetchTable()
    },[])
    return(
        <>
            {loading ? 
                    <div className="h-75">
                        <div id="carouselExample" className="carousel slide" data-bs-theme="dark">
                            <div class="carousel-indicators">
                                {table.map((value,index) => { return(
                                    <button type="button" data-bs-target="#carouselExample" data-bs-slide-to={`${index}`} className={index==0 ? "active" : null} aria-current={index==0 ? "true" : null} aria-label={`Slide ${index+1}`} ></button>
                                )})}
                            </div>
                            <div className="carousel-inner">
                                {table.map((value,index) => {return(
                                    <div className={index==0 ? "carousel-item active" : "carousel-item"}>
                                        <div className="d-flex justify-content-center ">
                                            <Table prop={value}></Table>
                                        </div>
                                        <div className='d-flex justify-content-center mb-5 mt-2'>
                                            <div style={{width: "60%"}}>
                                                <form className='d-flex' onSubmit={() => {storeTable(index)}}>
                                                    <input name="tableName" required type="text" placeholder="저장할 이름" className="form-control bg-white text-dark"/>
                                                    <button style={{width: "5em"}} className="ms-2 btn btn-dark">저장</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                )})}
                            </div>
                            <button className="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
                                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span className="visually-hidden">Previous</span>
                            </button>
                            <button className="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
                                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                                <span className="visually-hidden">Next</span>
                            </button>
                        </div>
                    </div> :
                    <div style={{height: "89vh"}} className="d-flex justify-content-center align-items-center">
                        <div style={{width: "3rem", height: "3rem"}} class="spinner-border" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>
            }
        </>
    )
}
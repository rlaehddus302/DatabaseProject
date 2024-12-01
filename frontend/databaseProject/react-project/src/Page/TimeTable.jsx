import { useEffect, useState } from "react";
import Table from "../Components/Table"
import MyImage from '../assets/react.svg';

export default function TimeTalbe()
{
    const [table, setTable] = useState([])

    useEffect(() => {
        async function fetchTable() {
            let response
            try
            {
                response = await fetch('http://localhost:8080/table')
                const resData = await response.json()
                console.log(response)
                console.log(resData)
                setTable(resData)
            }
            catch(e)
            {
                console.error("실패")
                console.error(e)
            }
        }
        fetchTable()
    },[])
    return(
        <div className="h-75">
            <div id="carouselExample" className="carousel slide" data-bs-theme="dark">
                <div class="carousel-indicators">
                    {table.map((value,index) => { return(
                        <button type="button" data-bs-target="#carouselExample" data-bs-slide-to={`${index}`} className={index==0 ? "active" : null} aria-current={index==0 ? "true" : null} aria-label={`Slide ${index+1}`} ></button>
                    )})}
                </div>
                <div className="carousel-inner">
                    {table.map((value,index) => { return(
                        <div className={index==0 ? "carousel-item active" : "carousel-item"}>
                            <Table prop={value}></Table>
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
        </div>
    )
}
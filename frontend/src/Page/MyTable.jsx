import { useEffect, useRef, useState } from "react"
import Table from "../Components/Table";

export default function MyTable()
{
    const [choice, setChoice] = useState(null);
    const [myTable, setMyTable] = useState(null);
    const [isEditingName, setIsEditingName] = useState(false);
    const name = useRef(null);
    useEffect(() => {
        async function getData()
        {
            let response = await fetch('http://localhost:8080/myTable',{
                credentials: "include",
                headers: {
                    'Authorization': localStorage.getItem("jwt"),
                }
            })
            const resData = await response.json()
            console.log(response)
            console.log(resData)
            if(response.ok)
            {
                setMyTable(resData)
                setChoice(resData[0])
            }
        }
        getData();
    },[])
    function change(index)
    {
        console.log(index)
        setChoice(myTable[index])
    }
    function edit(boolean)
    {
        setIsEditingName(boolean)
    }
    async function sendUpdatedName()
    {
        let data = {
            "oldName" : choice.name,
            "newName" : name.current.value,
        }
        let sendData = JSON.stringify(data);
        let response = await fetch('http://localhost:8080/updateName',{
            credentials: "include",
            method: "PATCH",
            headers: {
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("jwt"),
            },
            body: sendData,
        })
        console.log(response)
        if(response.ok)
        {
            setChoice((prevData) => {
                let newChoice = {...prevData}
                newChoice.name = name.current.value
                return newChoice;
            })
            setIsEditingName(false);
            setMyTable((prevData)=>{
                prevData.map((data) => data.name === choice.name ? data.name = name.current.value : data)
                let copy = [...prevData]
                return copy;
            })
        }
        else
        {

        }
    }
    async function deleteTable()
    {
        let response = await fetch('http://localhost:8080/deleteTable',{
            credentials: "include",
            method: "DELETE",
            headers: {
                'Content-Type': 'text/plain',
                'Authorization': localStorage.getItem("jwt"), 
            },
            body: choice.name,
        })
        if(response.ok)
        {
            let filter;
            setMyTable((prevData) => {
                
                filter = prevData.filter((data) => data.name != choice.name)
                return filter
            })
            if( filter.length > 0)
            {
                setChoice(filter[0])
            }
        }
    }
    return (
        <>
            <section className="container">
                <p className="fs-3 fw-bold m-0">내 시간표</p>
                <hr />
            </section>
                {
                    myTable != null && myTable.length > 0 && 
                <center>
                    <div className="row mb-3" style={{width:"60%"}}>
                        <div className="col gx-0">
                            <select className="form-select" aria-label="Name select" onChange={(e) => change(e.target.selectedIndex)}>
                                {
                                    myTable.map((value,index) => { return (
                                        <option selected={index === 0}>{value.name}</option>
                                    )})
                                }
                            </select>
                        </div>
                        <div className="col p-0">
                            <div className="d-flex">
                                {isEditingName ? 
                                <div className="ms-auto">
                                    <button onClick={() => edit(false)} type="button" className="btn border border-secondary-subtle me-3">취소</button>
                                    <button onClick={sendUpdatedName} type="button" className="btn border border-secondary-subtle">저장</button>
                                </div> :
                                <button onClick={() => {edit(true)}} type="button" className="btn border border-secondary-subtle ms-auto">이름 수정</button>
                                }
                            </div>
                        </div>
                    </div>
                    <div style={{width:"60%"}}>
                        {isEditingName ? 
                        <input ref={name} className="form-control mb-3" type="text" defaultValue={choice.name} /> :
                        <p className="text-start fs-3 fw-bold">{choice.name}</p>
                        }
                    </div>
                    <Table prop={choice.body}></Table>
                    <div className="d-flex" style={{width:"60%"}}>
                        <button onClick={deleteTable} type="button" className="btn btn-danger ms-auto my-2">시간표 삭제</button>
                    </div>
                </center>
                }
        </>
    )
}
import { useEffect, useState } from "react";
import styles from './SelectCondition.module.css'
import { useNavigate } from "react-router-dom";

export default function SelectCondition()
{
    const [value, setValue]=useState(Array.from({ length: 5 }, () => Array(28).fill(0)))
    const [consecutiveClass, setConsecutiveClass] = useState(false)
    const [consecutiveClassTime, setConsecutiveClassTime] = useState(10)
    const [interval, setInterval] = useState(false)
    const [intervalTime, setIntervalTime] = useState(10)
    const [ratingOption, setRatingOption] = useState(false)
    const [rating, setRating] = useState(5)
    const navigate = useNavigate();
    let time = ["0800","0830","0900","0930","1000","1030","1100","1130","1200","1230","1300","1330","1400","1430","1500","1530",
        "1600","1630","1700","1730","1800","1830","1900","1930","2000","2030","2100","2130"]
    const success =localStorage.getItem("login");
    useEffect(() => {
        const success = localStorage.getItem("login");
        if (success !== "success") {
            navigate("/login");
        }
    }, []);
    function changeValue(rowIndex,colIndex)
    {
        let copy = [...value.map(col => [...col])]
        if(copy[colIndex][rowIndex]==1)
        {
            copy[colIndex][rowIndex]=0
        }
        else
        {
            copy[colIndex][rowIndex]=1
        }
        setValue(copy)
    }
    function consecutiveClassOption()
    {
        setConsecutiveClass((prevState) => {return !prevState})
    }
    function changeConsecutiveClassTime(event)
    {
        setConsecutiveClassTime(event.target.value)
    }
    async function move()
    {
        try
        {
            console.log(JSON.stringify(value))
            let fetchConsecutive = consecutiveClassTime;
            let fetchInterval = intervalTime
            if(!interval)
            {
                fetchInterval = null
            }
            if(!consecutiveClass)
            {
                fetchConsecutive = null
            }
            let fetchData = 
            {
                "freePeriod" : value,
                "consecutiveClassTime" :  fetchConsecutive,
                "intervalTime" : fetchInterval
            }
            console.log(JSON.stringify(fetchData))
            const response = await fetch('http://localhost:8080/SetCondition', {
                method: 'POST',
                credentials: "include",
                body: JSON.stringify(fetchData),
                headers: { 'Content-Type': 'application/json' }
            });
            console.log("완료")
            navigate("/selectSubject")
        }
        catch(e)
        {
            console.log(e)
        }
    }
    let table=Array.from({ length: 28 }, () => Array(5).fill(null));
    return(
        <section className="container">
            <p className="fs-3 fw-bold m-0">조건 선택</p>
            <p className="text-body-tertiary">원하는 조건건을 선택하세요</p>
            <hr />
            <div className="d-flex justify-content-center">
                <div className="w-75 mb-5">
                    <p className="fs-5">공강 시간</p>
                    <table className={`table text-center table-bordered`}>
                        <thead>
                            <tr>
                                <th className={styles.th}></th>
                                <th className={styles.th}>월</th>
                                <th className={styles.th}>화</th>
                                <th className={styles.th}>수</th>
                                <th className={styles.th}>목</th>
                                <th className={styles.th}>금</th>
                            </tr>
                        </thead>
                        <tbody>
                            {table.map((row, rowIndex)=>{
                                    return(
                                        <tr>
                                            <th className={styles.th} scope="row">{time[rowIndex]}</th>
                                            {row.map((col,colIndex) => {
                                                return (<td className={value[colIndex][rowIndex] === 0 ? styles.unselect : styles.select} 
                                                    onClick={() => {return changeValue(rowIndex,colIndex)} }></td>)
                                            })}
                                        </tr>
                                    )
                                }
                            )}
                        </tbody>
                    </table>
                    <div className="my-3 fs-5">
                        <div className="form-check form-switch">
                            <input className="form-check-input" type="checkbox" role="switch" onClick={consecutiveClassOption}/>
                            <label className="form-check-label">강의 시간 설정</label>
                        </div>
                        {consecutiveClass && 
                        <>
                            <label htmlFor="consecutiveClassOption" className="form-label mt-3">휴식 없이 들을 수 있는 최대 강의 시간</label>
                            <input type="range" className="form-range" min="1" max="10" step="0.5" id="consecutiveClassOption" onChange={changeConsecutiveClassTime}/>
                            <p>{consecutiveClassTime}시간</p>
                        </>
                        }
                    </div>
                    <div className="my-3 fs-5">
                        <div className="form-check form-switch ">
                            <input className="form-check-input" type="checkbox" role="switch" onClick={() => {setInterval((prevState)=> !prevState)}}/>
                            <label className="form-check-label">강의 간격 설정</label>
                        </div>
                        {interval && 
                        <>
                            <label htmlFor="customRange2" className="form-label mt-3">강의 간 최대 시간 간격</label>
                            <input type="range" className="form-range" min="0.5" max="10" step="0.5" id="customRange2" onChange={(event)=>{setIntervalTime(event.target.value)}}/>
                            <p>{intervalTime}시간</p>
                        </>
                        }
                    </div>
                    <div className="d-flex">
                        <button onClick={move} type='button' className="btn btn-primary btn-dark ms-auto">
                            <span className="text-white fs-5">과목 선택하기</span>
                        </button>
                    </div>
                </div>
            </div>
        </section>
    )
}
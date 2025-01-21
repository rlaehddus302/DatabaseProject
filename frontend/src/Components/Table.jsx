import { useEffect } from 'react';
import styles from './Table.module.css'

export default function Table(prop)
{
    let time = ["0800","0900","1000","1100","1200","1300","1400","1500","1600","1700","1800","1900","2000","2100"]
    let session = [styles.session1,styles.session2,styles.session3,styles.session4,styles.session5,styles.session6,styles.session7,
        styles.session8,styles.session9,styles.session10,styles.session11,styles.session12,styles.session13,styles.session14,styles.session15,
        styles.session16,
    ]
    let table=Array.from({ length: 5 }, () => Array(14).fill(null));
    const week = ["월","화","수","목","금"]
    useEffect(()=>{
        const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');
        const popoverList = [...popoverTriggerList].map(popoverTriggerEl => 
            new bootstrap.Popover(popoverTriggerEl)
        );
        return () => {
            popoverList.forEach(popover => popover.dispose());
        };
    },[])
    return(
        <div className="d-flex justify-content-center">
            <table className={`mb-5 ${styles.table}`} data-bs-theme="light" >
                <thead>
                    <tr>
                        <th className={`p-0 ${styles.th}`}></th>
                        <th style={{borderLeft:'1px solid black'}} className={`p-0 ${styles.th}`}>월</th>
                        <th style={{borderLeft:'1px solid black'}} className={`p-0 ${styles.th}`}>화</th>
                        <th style={{borderLeft:'1px solid black'}} className={`p-0 ${styles.th}`}>수</th>
                        <th style={{borderLeft:'1px solid black'}} className={`p-0 ${styles.th}`}>목</th>
                        <th style={{borderLeft:'1px solid black'}} className={`p-0 ${styles.th}`}>금</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th className='p-0'>
                            <div>
                                {time.map((value) => {
                                    return(
                                        <div className={styles.cell}>
                                            {value}
                                        </div>
                                    )
                                })}
                            </div>
                        </th>
                        {table.map((value, rowIndex)=>{
                            return(
                                <td style={{borderLeft:'1px solid black', position: 'relative'}} className='p-0'>
                                    {value.map(()=>{
                                        return(
                                            <div className={styles.cell}>

                                            </div>
                                        )
                                    })}
                                    {
                                        prop.prop.map((prevStat, index)=>{
                                            return prevStat.session.classTimeAndLocation
                                            .map(value => {
                                                let startInt = Math.floor(value.start/100)
                                                let endInt = Math.floor(value.end/100)
                                                let startFloat = value.start/100-startInt
                                                let endFloat = value.end/100 - endInt
                                                let int = endInt- startInt
                                                let float = (endFloat - startFloat)*10/6
                                                let height = (int+float)*50;
                                                let top = ((startInt-8)+startFloat*10/6)*50
                                                let locations = value.location;
                                                let location = locations.split("(")
                                                if(week[rowIndex] === value.week)
                                                {
                                                    return(
                                                        <button type='button' className={`${session[index+1]} w-100 text-center overflow-hidden`} style={{position:'absolute',top: `${top+1}px`, height:`${height-1}px`}} data-bs-html="true" data-bs-toggle="popover" 
                                                            data-bs-title={`<div class="text-center">${prevStat.name}</div>`} 
                                                            data-bs-content={
                                                                `<div>
                                                                 ${prevStat.curriculum} ${prevStat.area}<br>
                                                                 ${prevStat.session.professor}<br>
                                                                 ${prevStat.session.courseID}<br>
                                                                 ${locations}<br>
                                                                 ${prevStat.session.remarks}
                                                                 </div>`
                                                            }>
                                                            {prevStat.name}<br/>
                                                            {prevStat.session.professor}<br/>
                                                            {location[0]}
                                                        </button>
                                                            )
                                                }
                                                    }
                                                )
                                            }
                                        )
                                    }
                                </td>
                            )
                        })}
                    </tr>
                </tbody>
            </table>
        </div> 
    )
}
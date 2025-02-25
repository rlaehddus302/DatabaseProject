import { useMemo, useState } from "react"

export default function Search({handleSelect, academicTerm})
{
    const [resData,setResData] = useState([])

    const debouncedSearch = useMemo(() => {
        let timeoutId;
        return (query) => {
            clearTimeout(timeoutId); 
            timeoutId = setTimeout(async () => {
                console.log(query);
                const response = await fetch(`http://localhost:8080/courseSearch?year=${academicTerm.academic_year}&semester=${academicTerm.semester}`, {
                    method: 'POST',
                    credentials: "include",
                    body: JSON.stringify(query),
                    headers: { 'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem("jwt"),
                     }
                });
                const temp = await response.json();
                console.log(temp);
                console.log("보냄");
                setResData(temp);
            }, 100); 
        };
    }, [academicTerm]);

    function handleInputChange(event) {
        debouncedSearch(event.target.value); 
    }

    function handleClick(value)
    {
        handleSelect((prevState) => {
                const copy = [...prevState]
                copy.push({"name" : value.name, "courseCode" : {"code" : value.code}, "credit" : value.credit})
                console.log(copy)
                console.log("완료")
                return copy
            }
        )
        const modal = document.getElementById('exampleModal');
        if (modal) {
            const bootstrapModal = bootstrap.Modal.getInstance(modal);
            bootstrapModal.hide();
        }
    }
    return(
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body">
                        <div className="p-3">
                            <div className="pb-5">
                                <label className="fs-5 fw-bold mb-3" htmlFor="exampleFormControlInput1">과목검색</label>
                                <input type="text" style={{height:'3em'}} onChange={handleInputChange} className="form-control" id="exampleFormControlInput1" placeholder="검색하기"/>
                            </div>
                            <div>
                                {resData.map((value) => {
                                    return(
                                        <button type="button" className="my-1 btn btn-light w-100" style={{backgroundColor:'white',}}  onClick={() => handleClick(value)}>
                                            <div className="p-2 d-flex align-items-center">
                                                <p className="m-0 fs-6">{value.name}({value.code})</p>
                                                <p className="m-0 ms-auto fs-6">{value.credit}학점</p>
                                            </div>
                                        </button>
                                )})}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
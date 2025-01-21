import { useEffect, useState } from "react"

export default function Course({selectList, name, courseCode, credit, handleSelect})
{
    const [click,setClick] = useState(true)
    const buttonStyle = "border border-dark-subtle text-center btn w-100 mt-3"
    function handleClick()
    {
        if(click)
        {
            handleSelect((prevState) => {
                const copy = [...prevState]
                copy.push({"name" : name, "courseCode" : {"code" : courseCode}, "credit" : credit})
                return copy
            })
        }
        else
        {
            handleSelect((prevState) => {
                const filter = prevState.filter((object) => {
                    return (object.name != name)
                })
                return filter
            })
        }
        setClick((prevState) => !prevState)
    }
    useEffect(() => {
        if(selectList.some((value) => value.name == name ))
            {
                setClick(false)
            }
            else{
                setClick(true)
            }
    },[selectList])
    return(
        <div className="col">
            <div className="border border-dark-subtle p-2">
                <p className="fs-6 m-0">{name}</p>
                <span className="text-secondary" style={{fontSize:'0.8em'}}>({courseCode} {credit}학점)</span>
                <button onClick={handleClick} className={click ? buttonStyle : buttonStyle+" bg-dark-subtle"}>{click ? "선택" : "선택취소"}</button>
            </div>
        </div>
    )
}
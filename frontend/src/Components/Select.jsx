export default function Select({handleSelect, name, courseCode, credit})
{
    function handleClick()
    {
        handleSelect((prevState) => {
        const filter = prevState.filter((object) => {
                return (object.name != name)
            })
            return filter
        })   
    }
    return(
        <div className="border border-dark-subtle p-2">
            <div className="d-flex align-items-center">
                <span className="fs-6">{name}<span className="text-secondary" style={{fontSize:'0.8em'}}>({courseCode} {credit}학점)</span></span>
                <button onClick={handleClick} className="border border-dark-subtle text-center btn ms-auto">선택 취소</button>
            </div>
        </div>
    )
}
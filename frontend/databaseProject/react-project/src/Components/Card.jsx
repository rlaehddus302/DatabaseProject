export default function Card({className, icon, title, children})
{
    return(
        <div className="col">
            <div className={`bg-white ${className}`}>
                <i className={`fs-3 bi ${icon}`}></i>
                <p className=" fs-3 fw-bold">{title}</p>
                <p>{children}</p>            
            </div>
        </div>
    )
}
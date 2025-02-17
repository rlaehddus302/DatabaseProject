import { useRouteError } from "react-router-dom"

export default function ErrorPage()
{
    const error = useRouteError();
    console.log(error)
    return (
        <center>
            <h2>{error?.status && <p>HTTP 상태 코드: {error.status}</p>}</h2>
            <p>{error?.body?.message || "알 수 없는 오류가 발생했습니다."}</p>
        </center>
    )
}
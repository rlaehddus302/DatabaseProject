import { useNavigate } from "react-router-dom";

export default function Logout()
{
    const navigate = useNavigate();
    async function move()
    {
        try
        {
            const response = await fetch('http://localhost:8080/exit', {
                method: 'POST',
                credentials: 'include',
                headers : {
                    'Authorization': localStorage.getItem("jwt"),
                }
            });
            console.log(response)
            if(response.status != 200)
                {
                    throw new Error("로그아웃 오류");
                } 
            localStorage.clear();
            navigate("/")
        }
        catch(e)
        {
            console.log(e)
        }
    }
    move();
}
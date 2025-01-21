import 'bootstrap-icons/font/bootstrap-icons.css';
import { useNavigate } from 'react-router-dom';
import Card from '../Components/Card';

export default function RootPage()
{
    const navigate = useNavigate();
    function move()
    {
        navigate("/conditionSelect")
    }
    return(
        <>
            <section className={`container d-flex justify-content-center align-items-center py-5`}>
                <div className={`text-center`}>
                    <p className="fw-bold fs-1 my-0">자동 대학 시간표 생성기</p>
                    <p className="text-body-tertiary fs-5">원하는 과목과 공강 시간을 선택하면, 최적의 시간표를 자동으로 생성해드립니다.</p>
                    <button onClick={move} type='button' className="btn btn-primary btn-dark">
                        <span className="text-white fs-5">시작하기</span>
                    </button>
                </div>
            </section>
            <section className='py-5 text-center bg-body-secondary'>
                <p className="fw-bold fs-1">주요 기능</p>
                <div className='container'>
                    <div className="row g-3 row-cols-1 row-cols-md-2 row-cols-xl-3"> 
                        <Card className={"text-start rounded p-2"} icon={"bi-book"} title={"과목선택"}>
                            원하는 과목을 자유롭게 선택하세요. 듣길 원하는 과목명을 클릭하세요. 
                        </Card>
                        <Card className={" text-start rounded p-2"} icon={"bi-clock"} title={"공강 시간 설정"}>
                            원하는 공강 시간을 지정할 수 있습니다. 점심 시간이나 개인 활동 시간을 확보하세요. 
                        </Card>
                        <Card className={"text-start rounded p-2"} icon={"bi-check-square"} title={"강의 간격 및 시간 설정"}>
                            한 번에 최대로 가능한 수업 시간 및 강의 간 간격 시간을 설정할 수 있어요.  
                        </Card>
                        <Card className={"text-start rounded p-2"} icon={"bi-star"} title={"평점 설정"}>
                            최소 강의평가 점수를 설정하여 원하는 수준 이상의 강의를 쉽게 찾으세요.   
                        </Card>
                        <Card className={"text-start rounded p-2"} icon={"bi-calendar"} title={"자동 시간표 생성"}>
                            입력한 정보를 바탕으로 시간표를 자동으로 생성합니다. 시간표들 중 하나를 선택하세요. 
                        </Card>
                    </div>
                </div>
            </section>
        </>
    )
}
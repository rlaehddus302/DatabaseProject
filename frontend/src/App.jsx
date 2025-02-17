import { Children, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Navbar from './Components/Navbar'
import RootPage from './Page/RootPage'
import SelectCondition from './Page/SelectCondition'
import SelectSubject from './Page/SelectSubject'
import TimeTalbe from './Page/TimeTable'
import Login from './Page/Login'
import Logout from './Page/Logout'
import SignUp from './Page/SignUp'
import MyTable from './Page/MyTable'
import EditSubject from './Page/EditSubject'
import EditSession from './Page/EditSession'
import ErrorPage from './Page/ErrorPage'

const router = createBrowserRouter([
 {path:'/', element:<Navbar/>, errorElement:<ErrorPage/> ,children:[
    {path:'/', element:<RootPage/>},
    {path:'/conditionSelect', element:<SelectCondition/>},
    {path:'/selectSubject', element:<SelectSubject/>},
    {path:'/generateTimeTalbe', element:<TimeTalbe/>},
    {path:'/myTable', element:<MyTable/>},
    {path:'/editSubject', element:<EditSubject/>},
    {path:'/editSubject/:id', element:<EditSession/>},
  ]
 },
 {path:'/login', element:<Login/>},
 {path:'/logout', element:<Logout/>},
 {path:'/signUp', element:<SignUp/>},
])

function App() {
  return <RouterProvider router={router}></RouterProvider>
}

export default App

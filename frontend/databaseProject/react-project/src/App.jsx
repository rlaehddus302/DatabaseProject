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

const router = createBrowserRouter([
 {path:'/', element:<Navbar/>, children:[
    {path:'/', element:<RootPage/>},
    {path:'/conditionSelect', element:<SelectCondition/>},
    {path:'/selectSubject', element:<SelectSubject/>},
    {path:'/generateTimeTalbe', element:<TimeTalbe/>},
  ]
 },
])

function App() {
  return <RouterProvider router={router}></RouterProvider>
}

export default App

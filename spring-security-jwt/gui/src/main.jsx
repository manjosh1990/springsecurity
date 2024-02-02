import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from 'react-router-dom';
import Layout from './Layout.jsx';
import Home from './components/Home.jsx';
import Login from './components/Login.jsx';
import Register from './components/Register.jsx';

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path='/' element={<Layout />}>
      <Route path='' element={<Home/>}/>
      <Route path="login" element={<Login/>}/>
      <Route path='register' element={<Register/>}/>
      <Route path='*' element={<div>Not Found!</div>} />
    </Route>
  )
)
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)

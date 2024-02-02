import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react'
import {request} from '../axiosHelper';

const Home = () => {
    const[data,setData] = useState([]);
    
    useEffect(()=>{
        request("GET",
        "/messages",
        {})
        .then(res => {
            setData(res.data)
            console.log(data)
        })
    },[])

    const elments = data.map((key,item)=>(<h1 key={item}>{key}</h1>))
    
  return (
    <div>{elments}</div>
  )
}

export default Home
import  { useState } from 'react'
import { Link } from 'react-router-dom';

import { request } from '../axiosHelper';

const Login = () => {
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [error,setError] = useState("");

    const handleSubmit = (e) => {
        //prevent page from refreshing
        e.preventDefault()
        console.log("login in!")
        request("POST","/login",{userName :userName, password:password})
        .then((res)=>{
            console.log(res)
        }).catch((err)=>{
            console.log("error while logging in")
            console.log(err.response)
            setError(err.response.data)
        });

    }

    return (
        <div className='flex flex-wrap justify-center mt-9'>
            <div className='w-full max-w-xs'>
                <form className='bg-white shadow-lg *:rounded px-8 pt-6 pb-8 mb-4'>
                    <div className='mb-4'>
                        <label
                            htmlFor='username'
                            className='block text-gray-700 text-sm font-bold mb-2'
                        >Username</label>
                        <input
                            type='text'
                            placeholder="username"
                            value={userName}
                            id='username'
                            onChange={(e) => setUserName(e.target.value)}
                            className='shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline' />
                    </div>

                    <div className='mb-6'>
                        <label htmlFor='password'
                            className='block text-gray-700 text-sm font-bold mb-2'>Password</label>
                        <input
                            type='password'
                            placeholder="password"
                            value={password}
                            id='password'
                            onChange={(e) => setPassword(e.target.value)}
                            className='shadow appearance-none border  rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline' />
                    </div>
                    <div className="flex items-center justify-between">
                        <button onClick={handleSubmit}
                            className='bg-orange-700 hover:bg-orange-800 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline'>Sign in</button>
                        <Link to="/register" className='inline-block align-baseline font-bold text-sm text-orange-500 hover:text-orange-800'>Sign up?</Link>
                    </div>
                    {error &&  <div className='text-sm text-red-600 mt-4'>{error.message}</div>}
                </form>
                <p className="text-center text-gray-500 text-xs">
                    &copy;2020 Manjosh Corp. All rights reserved.
                </p>
            </div>
        </div>
    )
}

export default Login
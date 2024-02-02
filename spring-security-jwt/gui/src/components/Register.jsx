import React, { useState } from 'react'

const Register = () => {
    const[username,setUsername] = useState("");
    const[password,setPassword] = useState("");
    const[email,setEmail] = useState("");
    const[name,setName] = useState("");

    const handleSubmit =(e)=>{
        e.preventDefault()
        console.log(name)
        console.log(email)
        console.log(username)
        console.log(password)
    }
  return (
    <div className='flex flex-wrap justify-center'>
        <div className='w-full max-w-xs'>
        <form className='bg-white shadow-lg *:rounded px-8 pt-6 pb-8 mb-4'>
                    <div className='mb-6'>
                        <label htmlFor='name'
                            className='block text-gray-700 text-sm font-bold mb-2'>Name</label>
                        <input
                            type='text'
                            placeholder="name"
                            value={name}
                            id='name'
                            onChange={(e) => setName(e.target.value)}
                            className='shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline' />
                    </div>
                    <div className='mb-6'>
                        <label htmlFor='email'
                            className='block text-gray-700 text-sm font-bold mb-2'>Email</label>
                        <input
                            type='email'
                            placeholder="email"
                            value={email}
                            id='email'
                            onChange={(e) => setEmail(e.target.value)}
                            className='shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline' />
                    </div>
                    <div className='mb-4'>
                        <label
                            htmlFor='username'
                            className='block text-gray-700 text-sm font-bold mb-2'
                        >Username</label>
                        <input
                            type='text'
                            placeholder="username"
                            value={username}
                            id='username'
                            onChange={(e) => setUsername(e.target.value)}
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
                            className='shadow appearance-none border border-red-500 rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline' />
                    </div>
                    <div className="flex items-center justify-center">
                        <button onClick={handleSubmit}
                            className='bg-orange-700 hover:bg-orange-800 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline'>Register</button>
                    </div>
                </form>
        </div>
        </div>
  )
}

export default Register
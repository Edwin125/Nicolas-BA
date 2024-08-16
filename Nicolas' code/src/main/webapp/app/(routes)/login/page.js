'use client'
import {useState} from 'react'
import {useRouter} from 'next/navigation'
import {Button, TextField} from "@mui/material";

function LoginPage() {
    const [id, setId] = useState('')
    const router = useRouter()

    const handleLogin = async (e) => {
        e.preventDefault()
        localStorage.setItem("id", id)
        router.push('/chat')
    }

    return (
        <div className="fixed inset-0 overflow-auto">
            <div className="flex h-full">
                <div className="m-auto">
                    <form onSubmit={handleLogin}>
                        <div className="flex flex-col">
                            <div className="mb-6">
                                <TextField
                                    autoFocus
                                    id="outlined-basic"
                                    label="id"
                                    variant="outlined"
                                    onChange={(e) => setId(e.target.value)}
                                    value={id}
                                />
                            </div>

                            <Button
                                type="submit"
                                className="bg-blue-500 h-[56px]"
                                variant="contained"
                                disableRipple
                            >
                                Continue
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default LoginPage
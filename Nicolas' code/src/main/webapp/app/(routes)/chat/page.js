'use client'
import Chat from "@/app/(routes)/chat/Chat";
import TextInput from "@/app/(routes)/chat/TextInput";
import axios from "axios";
import {useEffect, useRef, useState} from "react";
import useKeystrokeSaver from "@/app/hooks/useKeystrokeSaver";
import {useRouter} from "next/navigation";

const scrollToBottom = (ref) => {
    ref.current?.scrollIntoView({behavior: "smooth", block: "end"})
}


export default function Page() {
    const [id, setId] = useState("");
    const [chats, setChats] = useState([])
    const [text, setText] = useState("")
    const [loading, setLoading] = useState(false)
    const router = useRouter();

    const chatRef = useRef(null)

    const [keystrokes, setKeystrokes] = useKeystrokeSaver();

    useEffect(() => {
        const localStorageId = typeof window !== 'undefined' ? localStorage.getItem("id") : ""
        if (!localStorageId) router.replace("login")
        setId(localStorageId)

        setLoading(true);

        axios.get("/ai/init/" + localStorageId)
            .then(res => {
                const initialChat = res.data
                    .map(chat => {
                        return {
                            text: chat.text,
                            time: new Date(chat.time),
                            user: chat.user
                        }
                    })
                setChats(initialChat)
            })
            .catch(err => {
                console.log(err)
            })
            .finally(() => {
                setLoading(false)
            })
    }, []);

    useEffect(() => {
        scrollToBottom(chatRef)
    }, [chats]);

    const onSubmit = (e) => {
        e.preventDefault()
        console.log(keystrokes)
        setKeystrokes([])
        setLoading(true)
        setChats(prevChats => [...prevChats,
            {
                text: text,
                time: new Date(),
                user: true
            },

        ])
        setText("")

        const request = {
            id: id,
            chatHistory: chats
                .map(c => {
                    return {
                        text: c.text,
                        user: c.user
                    }
                }),
            chat: {
                text: text,
                user: true
            },
            keystrokes: keystrokes
        }
        axios.post('/ai/send', request)
            .then(res => {
                setChats(prevChats => [...prevChats,
                    {
                        text: res.data.text,
                        time: new Date(res.data.time),
                        user: false,
                    }
                ])
            })
            .catch(err => {
                console.log(err)
            })
            .finally(() => {
                setLoading(false)
            })
    }

    return <div className="flex flex-col  text-2xl">
        <Chat
            ref={chatRef}
            chats={chats}
            loading={loading}
        />
        <TextInput
            onSubmit={onSubmit}
            text={text}
            setText={setText}
        />
    </div>;
}
import ChatBubble from "@/app/(routes)/chat/ChatBubble";
import LoadingChatBubble from "@/app/(routes)/chat/LoadingChatBubble";
import {forwardRef} from "react";

const Chat = forwardRef(function Chat({chats, loading}, messageEndRef) {
    const loadingChatBubble = loading && <LoadingChatBubble/>

    const chatBubbles = chats.map((chat, idx) => {
        return (
            <div key={idx} className="mb-auto">
                <ChatBubble {...chat}/>
            </div>
        )
    })

    return <div className="overflow-auto h-[95vh]">
        {chatBubbles}
        {loadingChatBubble}
        <div className="pt-12" ref={messageEndRef}/>
    </div>
})

export default Chat;
import React from 'react';

const formateDate = (date) => {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    let ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;
    return hours + ':' + minutes + ' ' + ampm;
}

const ChatBubble = ({text, time, user}) => {
    return (
        <div
            className={`my-3.5 mx-8 w-max flex flex-col rounded-md px-3 pt-2 py-6 relative break-words
             ${user ? "bg-blue-500 text-white ml-auto" : "bg-gray-200"}`}
        >
            <span
                className={`absolute w-0 h-0 top-[0px] bottom-auto -z-10
                border-x-transparent border-x-[20px] rounded-b-3xl
                ${user ? "-right-5 border-t-[20px] border-t-blue-500 " : "-left-5 border-t-[20px] border-t-gray-200"}`}
            />
            <div className="mr-auto mb-1.5 max-w-72">
                {text}
            </div>
            <div className="ml-auto text-xs self-end -mb-4 w-min whitespace-nowrap pl-4 opacity-50">
                {time && formateDate(time)}
            </div>
        </div>
    );
};

export default ChatBubble;
import React from 'react';

const LoadingChatBubble = () => {
    return (
        <div
            className={`my-3.5 mx-8 w-max flex flex-row rounded-md px-3 py-2 relative
             bg-gray-200`}
        >
            <span
                className={`absolute w-0 h-0 top-[0px] bottom-auto -z-10
                border-x-transparent border-x-[20px] rounded-b-3xl 
                -left-5 border-t-[20px] border-t-gray-200`}
            />
            <div className="mr-auto max-w-60">
                <div className="relative flex my-3 mr-0.5 w-10 items-center">
                    <span
                        className="absolute bg-gray-400 rounded-full w-2.5 h-2.5
                        animate-blink"/>
                    <span
                        className="absolute bg-gray-400 rounded-full w-2.5 h-2.5
                        animate-blink left-4 [animation-delay:_0.2s]"/>
                    <span
                        className="absolute bg-gray-400 rounded-full w-2.5 h-2.5
                        animate-blink left-8 [animation-delay:_0.4s]"/>
                </div>
            </div>
        </div>
    );
};

export default LoadingChatBubble;
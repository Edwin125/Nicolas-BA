'use client'
import React, {useRef} from 'react';
import RightArrowLogo from "@/app/assets/RightArrowLogo";
import useAutosizeTextArea from "@/app/hooks/useAutosizeTextArea";

const TextInput = ({onSubmit, text, setText}) => {
    const textAreaRef = useRef(null);

    useAutosizeTextArea(textAreaRef.current, text);

    const handleEnterPress = (e) => {
        if (e.keyCode === 13 && e.shiftKey === false) {
            e.preventDefault();
            e.target.form.requestSubmit()
        }
    }

    const handleChange = (evt) => {
        const val = evt.target?.value;

        setText(val);
    };

    return (
        <div>
            <form onSubmit={onSubmit}>
                <div className="absolute inset-x-0 bottom-0 border-2 rounded-[26px] flex flex-row
            mx-4 mb-4 pl-8 pr-6 py-2 bg-white">
                    <div className="w-full mr-3 h-min">
                    <textarea
                        className="w-full no-scrollbar"
                        name="text"
                        rows={1}
                        ref={textAreaRef}
                        placeholder="Write a message..."
                        value={text}
                        onChange={handleChange}
                        onKeyDown={handleEnterPress}
                    />
                    </div>

                    <div className="flex">
                        <div className="w-7 h-7 self-end mb-2">
                            <button>
                                <RightArrowLogo/>
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default TextInput;
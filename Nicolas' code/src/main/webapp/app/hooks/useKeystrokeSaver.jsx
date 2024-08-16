import {useEffect, useState} from 'react';

const SPECIAL_KEYS = {
    "Backspace": 8,
    "Tab": 9,
    "Enter": 13,
    "Shift": 16,
    "Control": 17,
    "Alt": 18,
    "Pause": 19,
    "CapsLock": 20
};

const useKeystrokeSaver = () => {
    const [keystrokes, setKeystrokes] = useState([]);
    const [lastKeystrokeTime, setLastKeystrokeTime] = useState(null);

    useEffect(() => {
        const handleKeydown = (event) => {
            const currentTime = new Date();
            const timeSinceLastKey = lastKeystrokeTime ? currentTime - lastKeystrokeTime : 0;

            let asciiValue;
            if (SPECIAL_KEYS[event.key] !== undefined) {
                asciiValue = SPECIAL_KEYS[event.key];
            } else {
                asciiValue = event.key;
            }

            const keystroke = {
                pressedKey: asciiValue,
                timestamp: currentTime.toISOString(),
                timeSinceLastKey: timeSinceLastKey,
            };

            setKeystrokes(prevKeystrokes => [...prevKeystrokes, keystroke]);
            setLastKeystrokeTime(currentTime);
        };

        window.addEventListener('keydown', handleKeydown);

        return () => {
            window.removeEventListener('keydown', handleKeydown);
        };
    }, [lastKeystrokeTime]);

    return [keystrokes, setKeystrokes];
};

export default useKeystrokeSaver;

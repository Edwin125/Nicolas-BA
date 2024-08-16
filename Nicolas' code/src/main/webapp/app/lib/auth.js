import {destroyCookie, parseCookies, setCookie} from 'nookies';

export const login = (token) => {
    setCookie(undefined, 'token', token, {
        maxAge: 7 * 24 * 60 * 60,
        path: '/',
    });
};

export const logout = () => {
    destroyCookie(undefined, 'token',
        {
            path: '/',
        });
};

export const getToken = (ctx) => {
    const cookies = parseCookies(ctx);
    return cookies.token;
};

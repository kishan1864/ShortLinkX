import { createContext, useContext, useState } from "react";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [token, setToken] = useState(
        () => localStorage.getItem("shortlinkx_token")
    );

    const [user, setUser] = useState(() => {
        try {
            return JSON.parse(
                localStorage.getItem("shortlinkx_user")
            ) || null;
        } catch {
            return null;
        }
    });

    const login = (data) => {
        const accessToken = data.accessToken;

        localStorage.setItem(
            "shortlinkx_token",
            accessToken
        );

        if (data.user) {
            localStorage.setItem(
                "shortlinkx_user",
                JSON.stringify(data.user)
            );
        }

        setToken(accessToken);
        setUser(data.user || null);
    };

    const logout = () => {
        localStorage.removeItem("shortlinkx_token");
        localStorage.removeItem("shortlinkx_user");

        setToken(null);
        setUser(null);
    };

    return (
        <AuthContext.Provider
            value={{
                token,
                user,
                login,
                logout,
                loading: false
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
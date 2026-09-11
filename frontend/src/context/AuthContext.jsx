import { createContext, useContext, useState } from "react";
import { authService } from "../services/authService";

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

    const login = async (data) => {

        const accessToken = data.accessToken;

        // Save JWT
        localStorage.setItem(
            "shortlinkx_token",
            accessToken
        );

        setToken(accessToken);

        try {
            // Get authenticated user's actual profile
            const currentUser =
                await authService.me();

            localStorage.setItem(
                "shortlinkx_user",
                JSON.stringify(currentUser)
            );

            setUser(currentUser);

        } catch (error) {

            console.error(
                "Failed to load current user:",
                error
            );

            localStorage.removeItem(
                "shortlinkx_user"
            );

            setUser(null);
        }
    };

    const logout = () => {

        localStorage.removeItem(
            "shortlinkx_token"
        );

        localStorage.removeItem(
            "shortlinkx_user"
        );

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
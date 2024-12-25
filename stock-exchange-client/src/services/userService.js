import {BASE_URL} from "@/configs/config.js";
import { reactive } from "vue";

const API = `${BASE_URL}/users`;

const fetchUserById = async (id) => {
    try {
        const data = await fetch(`${API}/${id}`);
        return await data.json();
    } catch (error) {
        console.error(error);
    }
};

const nameState = reactive ({
    username: "No Account"
});

const useNameState = () => nameState;

const isAuthenticated = () => {
    return (localStorage.getItem("user") !== "{}");
}

const getUserByName = async (firstName, lastName) => {
    const data = await fetch(API);
    const users = await data.json();

    var user = users.find(u => (u.firstName === firstName && u.lastName === lastName));

    return user;
}

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
}

const setCurrentUser = (user) => {
    localStorage.setItem("user", JSON.stringify(user));
}

const updateUserProfile = async (user) => {
    const options = {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'cors': 'no-cors',
        },
        body: JSON.stringify(user)
    };

    try {
        const response = await fetch(`${API}`, options);
        if (!response.ok) {
            console.error(response.statusText);
            throw new Error(response.statusText);
        }
        const data = await response.json();
        console.log('Update successful:', data);
        return data;
    } catch (error) {
        console.error('Error updating user profile:', error);
        throw new Error('Error updating user profile');
    }
};

export {
    fetchUserById,
    getCurrentUser,
    setCurrentUser,
    updateUserProfile,
    getUserByName,
    isAuthenticated,
    useNameState
};
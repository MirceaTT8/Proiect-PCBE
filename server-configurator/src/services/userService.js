import { BASE_URL } from "@/configs/config.js";

const API = `${BASE_URL}/users`;

const fetchUserById = async (id) => {
    try {
        const response = await fetch(`${API}/${id}`);
        if (!response.ok) {
            throw new Error(`Error fetching user with ID ${id}: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error(error);
        throw error;
    }
};

const createUser = async (user) => {
    try {
        const response = await fetch(API, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        });

        if (!response.ok) {
            throw new Error(`Error creating user: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error(error);
        throw error;
    }
};

const updateUser = async (data) => {

    console.log(data);

    try {
        const response = await fetch(API, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error(`Error updating user with ID ${id}: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error(error);
        throw error;
    }
};

const deleteUser = async (id) => {
    try {
        const response = await fetch(`${API}/${id}`, {
            method: "DELETE",
        });

        if (!response.ok) {
            throw new Error(`Error deleting user with ID ${id}: ${response.statusText}`);
        }
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export {
    fetchUserById,
    createUser,
    updateUser,
    deleteUser,
};
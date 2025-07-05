import axios from 'axios';

const BASE_URL = process.env.BASE_URL;

export const getKarts = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/api/karts/agregar`);
        return response.data;
    } catch (error) {
        console.error('Error fetching karts:', error);
        throw error;
    }
};
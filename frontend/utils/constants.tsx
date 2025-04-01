import Ionicons from '@expo/vector-icons/Ionicons';
import AntDesign from '@expo/vector-icons/AntDesign';

export const apiBaseUrl = "http://192.168.1.3:8080"

export const CategoryIcons = {
    Food: <Ionicons name="fast-food-outline" size={24} color="black" />,
    Transport: <Ionicons name="car-outline" size={24} color="black" />,
    Shopping: <AntDesign name="shoppingcart" size={24} color="black" />,
    Entertainment: <Ionicons name="film-outline" size={24} color="black" />,
    Utilities: <Ionicons name="bulb-outline" size={24} color="black" />,
    Health:<AntDesign name="medicinebox" size={24} color="black" />,
}
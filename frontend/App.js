import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import RegistrationScreen from './RegistrationScreen';
import LoginScreen from './LoginScreen';
import SuccessScreen from './SuccessScreen';
import { createDrawerNavigator } from '@react-navigation/drawer';
import HomeScreen from './HomeScreen'; // Ваш основной экран
import CustomDrawerContent from './CustomDrawerContent'; // Компонент для кастомного меню
import SendWishScreen from './SendWishScreen';
import MyFriends from './MyFriends';
import AddFriends from './AddFriendScreen';
import ChangePassword from './ChangePasswordScreen';
import SettingsScreen from './SettingsScreen';
const Stack = createNativeStackNavigator();
const Drawer = createDrawerNavigator();

const App = () => {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Registration">
                <Stack.Screen name="Registration" component={RegistrationScreen} options={{ headerShown: false }}/>
                <Stack.Screen name="Login" component={LoginScreen} options={{ headerShown: false }}/>
                <Stack.Screen name="Success" component={SuccessScreen} options={{ headerShown: false }}/>
                <Stack.Screen name="Wish" component={SendWishScreen} options={{ headerShown: false }}/>
                <Stack.Screen name="Friends" component={MyFriends} options={{ headerShown: false }}/>
                <Stack.Screen name="AddFriends" component={AddFriends} options={{ headerShown: false }}/>
                <Stack.Screen name="Settings" component={SettingsScreen} options={{ headerShown: false }} />

                <Stack.Screen name="ChangePassword" component={ChangePassword} options={{ headerShown: false }} />
                <Stack.Screen name="HomeDrawer" component={HomeDrawer} options={{ headerShown: false }} />
            </Stack.Navigator>
        </NavigationContainer>
    );
};

// Создаем отдельный компонент для Drawer Navigator
const HomeDrawer = () => {
    return (
        <Drawer.Navigator drawerContent={props => <CustomDrawerContent {...props} />}>
            <Drawer.Screen name="Home" component={HomeScreen} options={{ headerShown: false }}/>
        </Drawer.Navigator>
    );
};

export default App;
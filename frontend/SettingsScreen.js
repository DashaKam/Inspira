import React, { useState } from 'react';
import {
    SafeAreaView,
    ScrollView,
    View,
    Text,
    TouchableOpacity,
    Switch,
    StyleSheet,
} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome5';

const SettingsScreen = ({ navigation }) => {
    const [widgetEnabled, setWidgetEnabled] = useState(false);
    const [notificationsEnabled, setNotificationsEnabled] = useState(true);

    const handleEditNickname = () => {

        console.log('Переход на экран изменения никнейма');
    };

    const handleEditPassword = () => {
            navigation.navigate('ChangePassword');
    };

    return (
        <SafeAreaView style={styles.container}>
            <ScrollView contentContainerStyle={styles.scrollContainer}>
                {/* Header */}
                <View style={styles.header}>
                    <TouchableOpacity onPress={() => console.log('Back pressed')}>
                        <Icon name="chevron-left" size={24} color="#6A676B" />
                    </TouchableOpacity>
                    <Text style={styles.title}>Настройки</Text>
                </View>

                {/* Options */}
                <View style={styles.optionsContainer}>
                    {/* Change Nickname */}
                    <TouchableOpacity style={styles.optionItem} onPress={handleEditNickname}>
                        <Icon name="user" size={24} color="#6A676B" />
                        <Text style={styles.optionText}>Изменить никнейм</Text>
                    </TouchableOpacity>

                    {/* Change Password */}
                    <TouchableOpacity style={styles.optionItem} onPress={handleEditPassword}>
                        <Icon name="lock" size={24} color="#6A676B" />
                        <Text style={styles.optionText}>Изменить пароль</Text>
                    </TouchableOpacity>

                    {/* Message Type */}
                    <View style={styles.messageTypeContainer}>
                        <Icon name="envelope" size={24} color="#6A676B" />
                        <View style={styles.messageTypeTextContainer}>
                            <Text style={styles.optionText}>Тип сообщений:</Text>
                            <Text style={styles.subOptionText}>Пожелания</Text>
                        </View>
                    </View>

                    {/* Widget and Notifications */}
                    <View style={styles.toggleGroup}>
                        <View style={styles.switchContainer}>
                            <Text style={styles.switchLabel}>Виджет</Text>
                            <Switch
                                value={widgetEnabled}
                                onValueChange={(value) => setWidgetEnabled(value)}
                                trackColor={{ true: '#5856D6', false: '#CCCCCC' }}
                                thumbColor={widgetEnabled ? '#fff' : '#f4f3f4'}
                            />
                        </View>
                        <View style={styles.switchContainer}>
                            <Text style={styles.switchLabel}>Уведомления</Text>
                            <Switch
                                value={notificationsEnabled}
                                onValueChange={(value) => setNotificationsEnabled(value)}
                                trackColor={{ true: '#5856D6', false: '#CCCCCC' }}
                                thumbColor={notificationsEnabled ? '#fff' : '#f4f3f4'}
                            />
                        </View>
                    </View>

                    {/* Theme */}
                    <TouchableOpacity style={styles.optionItem}>
                        <Icon name="palette" size={24} color="#6A676B" />
                        <Text style={styles.optionText}>Тема: фиолетовая</Text>
                    </TouchableOpacity>
                </View>
            </ScrollView>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#E0CCEA',
    },
    scrollContainer: {
        flexGrow: 1,
        paddingVertical: 20,
        paddingHorizontal: 20,
    },
    header: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 20,
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#6A676B',
        marginLeft: 20,
    },
    optionsContainer: {
        flex: 1,
    },

    // Для пунктов меню
    optionItem: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 20,
    },
    optionText: {
        fontSize: 16,
        color: '#6A676B',
    },

    // Для Тип Сообщений
    messageTypeContainer: {
        flexDirection: 'row',
        marginBottom: 20,
    },
    messageTypeTextContainer: {
        marginLeft: 10,
    },
    subOptionText: {
        fontSize: 14,
        color: '#999',
        marginTop: 5,
        textAlign: 'center',
    },

    // Для Виджет и Уведомления
    toggleGroup: {
        marginBottom: 20,
    },
    switchContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 20,
    },
    switchLabel: {
        fontSize: 18,
        fontWeight: '500',
        color: '#6A676B',
        width: 120,
    },
});

export default SettingsScreen;
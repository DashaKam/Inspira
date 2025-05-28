import React, { useState } from 'react';
import {
    View,
    Text,
    TextInput,
    TouchableOpacity,
    StyleSheet,
    Alert,
    ScrollView,
} from 'react-native';

const ChangePasswordScreen = () => {
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSavePassword = async () => {
        // Проверка валидности
        if (!password || !confirmPassword) {
            Alert.alert('Ошибка', 'Заполните все поля');
            return;
        }

        if (password.length < 8) {
            Alert.alert('Ошибка', 'Пароль должен быть не менее 8 символов');
            return;
        }

        if (password !== confirmPassword) {
            Alert.alert('Ошибка', 'Пароли не совпадают');
            return;
        }

        setLoading(true);

        try {
            const response = await fetch('https:://185.157.214.169:8080/api/users/set-password ', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // если нужна авторизация — добавь заголовок Authorization
                },
                body: JSON.stringify({ password }),
            });

            const result = await response.json();

            if (response.ok) {
                Alert.alert('Успех', 'Пароль успешно изменен');
                setPassword('');
                setConfirmPassword('');
            } else {
                Alert.alert('Ошибка', result.message || 'Не удалось изменить пароль');
            }
        } catch (error) {
            console.error(error);
            Alert.alert('Ошибка', 'Произошла сетевая ошибка');
        } finally {
            setLoading(false);
        }
    };

    return (
        <ScrollView style={styles.container} contentContainerStyle={{ padding: 20 }}>
            <Text style={styles.label}>Новый пароль</Text>
            <TextInput
                style={styles.input}
                placeholder="Введите новый пароль"
                secureTextEntry
                value={password}
                onChangeText={setPassword}
                autoCapitalize="none"
            />

            <Text style={styles.label}>Подтвердите пароль</Text>
            <TextInput
                style={styles.input}
                placeholder="Повторите пароль"
                secureTextEntry
                value={confirmPassword}
                onChangeText={setConfirmPassword}
                autoCapitalize="none"
            />

            <TouchableOpacity
                style={[styles.button, loading && styles.buttonDisabled]}
                onPress={handleSavePassword}
                disabled={loading}
            >
                <Text style={styles.buttonText}>
                    {loading ? 'Сохранение...' : 'Сохранить пароль'}
                </Text>
            </TouchableOpacity>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#E0CCEA',
    },
    label: {
        fontSize: 16,
        color: '#6A676B',
        marginBottom: 5,
        marginTop: 15,
    },
    input: {
        height: 50,
        borderColor: '#ccc',
        borderWidth: 1,
        borderRadius: 8,
        paddingHorizontal: 15,
        backgroundColor: '#fff',
        fontSize: 16,
        color: '#333',
    },
    button: {
        marginTop: 25,
        backgroundColor: '#5856D6',
        paddingVertical: 15,
        borderRadius: 8,
        alignItems: 'center',
    },
    buttonDisabled: {
        backgroundColor: '#aaa',
    },
    buttonText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    },
});

export default ChangePasswordScreen;
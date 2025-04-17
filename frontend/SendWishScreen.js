import React, { useState } from 'react';
import {
    View,
    Text,
    TextInput,
    Button,
    StyleSheet,
    ImageBackground,
    Switch,
    Alert,
    ScrollView,
} from 'react-native';

export default function SendWishScreen() {
    const [nickname, setNickname] = useState('');
    const [message, setMessage] = useState('');
    const [anonymous, setAnonymous] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleSendWish = () => {
        if (!nickname.trim()) {
            Alert.alert('Ошибка', 'Пожалуйста введите никнейм получателя');
            return;
        }
        if (!message.trim()) {
            Alert.alert('Ошибка', 'Введите сообщение');
            return;
        }

        setIsSubmitting(true);

        fetch('http://185.157.214.169:8080/api/send-wish', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                reseivedNickname: nickname.trim(),
                message: message.trim(),
                anonymous: anonymous,
            }),
        })
            .then(res => res.json())
            .then(data => {
                setIsSubmitting(false);
                if (data.isWishAllowed) {
                    Alert.alert('Успех', 'Пожелание отправлено!');
                    setMessage('');
                    setAnonymous(false);
                    setNickname('');
                } else {
                    Alert.alert('Информация', 'Отправка пожелания не разрешена');
                }
            })
            .catch(err => {
                setIsSubmitting(false);
                console.error(err);
                Alert.alert('Ошибка', 'Не удалось отправить пожелание');
            });
    };

    return (
        <ImageBackground
            source={require('./assets/5418303167952711597.jpg')} // путь к картинке в проекте
            style={styles.background}
        >
            <ScrollView contentContainerStyle={styles.container}>
                <Text style={styles.label}>Никнейм получателя:</Text>

                <TextInput
                    style={styles.input}
                    value={nickname}
                    onChangeText={setNickname}
                    placeholder="Введите никнейм"
                    editable={!isSubmitting}
                    autoCapitalize="none"
                    autoCorrect={false}
                />

                <Text style={styles.label}>Сообщение:</Text>
                <TextInput
                    style={[styles.input, {height:100}]}
                    multiline
                    numberOfLines={4}
                    value={message}
                    onChangeText={setMessage}
                    placeholder="Введите ваше пожелание"
                    editable={!isSubmitting}
                />

                <View style={styles.switchRow}>
                    <Text style={styles.label}>Отправить анонимно</Text>
                    <Switch
                        value={anonymous}
                        onValueChange={setAnonymous}
                        disabled={isSubmitting}
                    />
                </View>

                <Button
                    title={isSubmitting ? "Отправка..." : "Отправить"}
                    onPress={handleSendWish}
                    disabled={isSubmitting}
                />
            </ScrollView>
        </ImageBackground>
    );
}

const styles = StyleSheet.create({
    background: {
        flex:1,
        resizeMode: 'cover',
    },
    container: {
        padding:20,
        flexGrow:1,
        justifyContent:'center',
        backgroundColor:'rgba(255,255,255,0.8)', // полупрозрачный фон для читаемости текста
        margin:20,
        borderRadius:10,
    },
    label: {
        fontSize:16,
        marginBottom:8,
        fontWeight:'bold',
    },
    input: {
        borderWidth:1,
        borderColor:'#ccc',
        borderRadius:5,
        padding:10,
        marginBottom:20,
        backgroundColor:'#fff',
        textAlignVertical:'top',
    },
    switchRow:{
        flexDirection:'row',
        alignItems:'center',
        justifyContent:'space-between',
        marginBottom:20,
    }
});
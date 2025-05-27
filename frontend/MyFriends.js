import React, { useState, useEffect } from 'react';
import {
    SafeAreaView,
    View,
    Text,
    FlatList,
    TouchableOpacity,
    StyleSheet,
    ImageBackground,
    Image,
    Modal,
    TouchableWithoutFeedback,
    TextInput,
    Switch,
    Button,
    Alert,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';

const MyFriends = () => {
    const navigation = useNavigation();
    const [friends, setFriends] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedFriend, setSelectedFriend] = useState(null);

    // Состояния для модального окна
    const [isModalVisible, setModalVisible] = useState(false);
    const [currentFriendNickname, setCurrentFriendNickname] = useState('');
    const [message, setMessage] = useState('');
    const [anonymous, setAnonymous] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);

    // Mock API call
    const fetchFriends = async () => {
        try {
            const data = [
                {
                    id: '1',
                    name: 'Настя',
                    username: '@sad_little_kitten',
                    avatar: 'https://placehold.co/60x60 ',
                },
                {
                    id: '2',
                    name: 'Соня',
                    username: '@good_little_penguin',
                    avatar: 'https://placehold.co/60x60 ',
                },
                {
                    id: '3',
                    name: 'Коля',
                    username: '@cheerful_fox',
                    avatar: 'https://placehold.co/60x60 ',
                },
                {
                    id: '4',
                    name: 'Никита',
                    username: '@thoughtful_panda',
                    avatar: 'https://placehold.co/60x60 ',
                },
                {
                    id: '5',
                    name: 'Дима',
                    username: '@melancholy_bunny',
                    avatar: 'https://placehold.co/60x60 ',
                },
            ];
            setFriends(data);
        } catch (error) {
            console.error('Error fetching friends:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchFriends();
    }, []);

    const handleFriendPress = (friend) => {
        setSelectedFriend(friend.id);
    };

    const handleClose = () => {
        setSelectedFriend(null);
    };

    const openWishModal = (friend) => {
        setCurrentFriendNickname(friend.username);
        setMessage('');
        setAnonymous(false);
        setModalVisible(true);
    };

    const handleSendWish = (nicknameToSend) => {
        if (!nicknameToSend.trim()) {
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
                reseivedNickname: nicknameToSend.trim(),
                message: message.trim(),
                anonymous: anonymous,
            }),
        })
            .then((res) => res.json())
            .then((data) => {
                setIsSubmitting(false);
                if (data.isWishAllowed) {
                    Alert.alert('Успех', 'Пожелание отправлено!');
                    setMessage('');
                    setAnonymous(false);
                } else {
                    Alert.alert('Информация', 'Отправка пожелания не разрешена');
                }
            })
            .catch((err) => {
                setIsSubmitting(false);
                console.error(err);
                Alert.alert('Ошибка', 'Не удалось отправить пожелание');
            });
    };

    const handleSubmitWish = (friendId) => {
        const friend = friends.find((f) => f.id === friendId);
        openWishModal(friend); // Открытие модального окна при нажатии
        handleClose(); // Закрытие выпадающего меню
    };

    const handleBackPress = () => {
        navigation.navigate('Home');
    };

    const handleAddFriendPress = () => {
        navigation.navigate('AddFriends');
    };

    const renderFriendItem = ({ item }) => {
        const isExpanded = selectedFriend === item.id;

        return (
            <View>
                {/* Friend Item */}
                <TouchableOpacity
                    style={styles.friendItem}
                    onPress={() => handleFriendPress(item)}
                >
                    <View style={styles.friendAvatarContainer}>
                        <Image source={{ uri: item.avatar }} style={styles.friendAvatar} />
                    </View>
                    <View style={styles.friendInfo}>
                        <Text style={styles.friendName}>{item.name}</Text>
                        <Text style={styles.friendUsername}>{item.username}</Text>
                    </View>
                </TouchableOpacity>

                {/* Expanded Actions */}
                {isExpanded && (
                    <View style={styles.expandedActions}>
                        <TouchableOpacity
                            style={[styles.actionButton, styles.writeWishButton]}
                            onPress={() => handleSubmitWish(item.id)}
                        >
                            <Text style={styles.actionButtonText}>Написать пожелание</Text>
                        </TouchableOpacity>
                        <TouchableOpacity
                            style={[styles.actionButton, styles.deleteButton]}
                            onPress={handleClose}
                        >
                            <Text style={styles.actionButtonText}>Удалить</Text>
                        </TouchableOpacity>
                    </View>
                )}
            </View>
        );
    };

    if (loading) {
        return (
            <SafeAreaView style={styles.loadingContainer}>
                <Text>Загрузка друзей...</Text>
            </SafeAreaView>
        );
    }

    return (
        <ImageBackground
            source={require('./assets/fon2.jpg')}
            style={styles.background}
            resizeMode="cover"
        >
            <SafeAreaView style={styles.container}>
                {/* Header */}
                <View style={styles.header}>
                    <TouchableOpacity style={styles.backButton} onPress={handleBackPress}>
                        <Text style={styles.backButtonText}>❮</Text>
                    </TouchableOpacity>
                    <Text style={styles.headerTitle}>Список друзей</Text>
                    <TouchableOpacity style={styles.searchButton}>
                        <Text style={styles.searchButtonText}>🔍</Text>
                    </TouchableOpacity>
                </View>

                {/* Табы */}
                <View style={styles.tabs}>
                    <TouchableOpacity style={[styles.tab, styles.activeTab]}>
                        <Text style={styles.tabText}>Мои друзья</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.tab} onPress={handleAddFriendPress}>
                        <Text style={styles.tabText}>Заявки в друзья</Text>
                    </TouchableOpacity>
                </View>

                {/* Список друзей */}
                <FlatList
                    data={friends}
                    keyExtractor={(item) => item.id}
                    renderItem={renderFriendItem}
                    contentContainerStyle={styles.listContent}
                />

                {/* Модальное окно */}
                <Modal
                    visible={isModalVisible}
                    transparent={true}
                    animationType="slide"
                    onRequestClose={() => setModalVisible(false)}
                >
                    <TouchableWithoutFeedback onPress={() => setModalVisible(false)}>
                        <View style={styles.modalOverlay} />
                    </TouchableWithoutFeedback>
                    <View style={styles.modalContainer}>
                        <Text style={styles.modalTitle}>Пожелание для {currentFriendNickname}</Text>
                        <TextInput
                            style={[styles.input, { height: 100 }]}
                            multiline
                            numberOfLines={4}
                            value={message}
                            onChangeText={setMessage}
                            placeholder="Введите ваше пожелание"
                        />
                        <View style={styles.switchRow}>
                            <Text style={styles.label}>Отправить анонимно</Text>
                            <Switch value={anonymous} onValueChange={setAnonymous} />
                        </View>
                        <View style={{ flexDirection: 'row', justifyContent: 'space-around' }}>
                            <Button
                                title="Отправить"
                                onPress={() => {
                                    handleSendWish(currentFriendNickname);
                                    setModalVisible(false);
                                }}
                                disabled={isSubmitting}
                            />
                            <Button title="Отмена" onPress={() => setModalVisible(false)} />
                        </View>
                    </View>
                </Modal>
            </SafeAreaView>
        </ImageBackground>
    );
};

// Стили — остаются такими же, как в первом файле

const styles = StyleSheet.create({
    background: {
        flex: 1,
        width: '100%',
        height: '100%',
        backgroundColor: '#F8EDEB',
    },
    container: {
        flex: 1,
        backgroundColor: 'rgba(248, 237, 235, 0.8)',
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    header: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        paddingVertical: 10,
        paddingHorizontal: 15,
        backgroundColor: 'rgba(248, 237, 235, 0.8)',
    },
    backButton: {
        width: 30,
        height: 30,
        justifyContent: 'center',
        alignItems: 'center',
    },
    backButtonText: {
        fontSize: 20,
        fontWeight: 'bold',
    },
    headerTitle: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    searchButton: {
        width: 30,
        height: 30,
        justifyContent: 'center',
    },
    searchButtonText: {
        fontSize: 20,
        fontWeight: 'bold',
    },
    tabs: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        paddingVertical: 10,
        backgroundColor: '#6A676B',
        borderRadius: 20,
    },
    tab: {
        flex: 1,
        alignItems: 'center',
        paddingVertical: 5,
        borderRadius: 20,
        marginHorizontal: 5,
        backgroundColor: '#E2C5FA',
    },
    activeTab: {
        backgroundColor: '#897C8E',
    },
    tabText: {
        fontSize: 14,
        fontWeight: 'bold',
        color: '#fff',
    },
    listContent: {
        flexGrow: 1,
        paddingBottom: 20,
    },
    friendItem: {
        flexDirection: 'row',
        alignItems: 'center',
        paddingVertical: 15,
        paddingHorizontal: 15,
        borderBottomWidth: 1,
        borderBottomColor: '#DDD',
    },
    friendAvatarContainer: { marginRight: 15 },
    friendAvatar: { width: 50, height: 50, borderRadius: 25 },
    friendInfo: { flex: 1 },
    friendName: { fontSize: 16, fontWeight: 'bold', color: '#333' },
    friendUsername: { fontSize: 14, color: '#666' },
    expandedActions: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        borderBottomWidth: 1,
        borderBottomColor: '#DDD',
        paddingHorizontal: 15,
        paddingVertical: 10,
    },
    actionButton: {
        flex: 1,
        paddingVertical: 10,
        borderRadius: 5,
        alignItems: 'center',
        justifyContent: 'center',
        marginHorizontal: -5,
    },
    writeWishButton: {
        backgroundColor: '#28a745',
    },
    deleteButton: {
        backgroundColor: '#dc3545',
    },
    actionButtonText: {
        color: '#fff',
        fontSize: 14,
    },
    // Стили для модального окна
    modalOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0,0,0,0.5)',
    },
    modalContainer: {
        position: 'absolute',
        top: '20%',
        left: '10%',
        right: '10%',
        backgroundColor: '#fff',
        padding: 20,
        borderRadius: 10,
    },
    modalTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 15,
    },
    input: {
        width: '100%',
        borderColor: '#ccc',
        borderWidth: 1,
        borderRadius: 4,
        paddingHorizontal: 10,
        marginBottom: 15,
    },
    switchRow: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        marginBottom: 15,
    },
    label: {
        fontSize: 16,
    },
});

export default MyFriends;
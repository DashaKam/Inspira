import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react-native';
import LoginScreen from '../LoginScreen';
import {Alert} from "react-native";
import axios from "axios";

jest.mock('axios');

const mockNavigate = jest.fn();
const navigation = { navigate: mockNavigate };

const TEXTS = {
  your_nickname: 'Твой никнейм',
  your_password: 'Парольчик',
  dont_have_account: 'Еще нет аккаунта?',
  registration_link: 'Скорее регистрируйся!',
  enter: 'Войти'
};

const TEST_CREDENTIALS = {
  nickname: 'testuser',
  password: 'password123'
};

describe('LoginScreen', () => {

  // TEST №1
  it('Должен отображать все элементы: инпуты, кнопку входа и ссылку на регистрацию', () => {
    const { getByPlaceholderText, getByText } = render(<LoginScreen navigation={navigation} />);

    expect(getByPlaceholderText(TEXTS.your_nickname)).toBeTruthy();
    expect(getByPlaceholderText(TEXTS.your_password)).toBeTruthy();
    expect(getByText(TEXTS.enter)).toBeTruthy();
    expect(getByText(TEXTS.dont_have_account)).toBeTruthy();
    expect(getByText(TEXTS.registration_link)).toBeTruthy();
  });

  // TEST №2
  it('Должен позволять вводить ник и пароль', () => {
    const { getByPlaceholderText, getByDisplayValue  } = render(<LoginScreen navigation={navigation} />);

    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);

    fireEvent.changeText(nicknameInput, TEST_CREDENTIALS.nickname);
    fireEvent.changeText(passwordInput, TEST_CREDENTIALS.password);

    expect(getByDisplayValue(TEST_CREDENTIALS.nickname)).toBeTruthy();
    expect(getByDisplayValue(TEST_CREDENTIALS.password)).toBeTruthy();
  });

  // TEST №3
  it('Должен показывать предупреждение, если поля не заполнены', () => {
    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByText } = render(<LoginScreen navigation={navigation} />);

    fireEvent.press(getByText(TEXTS.enter));

    expect(alertSpy).toHaveBeenCalledWith('Ошибка', 'Пожалуйста, заполните все поля.');
  });

  // TEST №4
  it('Должен выполнить вход и перейти на экран HomeDrawer при успешном ответе от сервера', async () => {
    // Мокаем axios.post, чтобы он сымитировал успешный ответ от сервера
    axios.post.mockResolvedValue({
      status: 200,
      data: { token: 'fake-token' }
    });

    const { getByPlaceholderText, getByText } = render(<LoginScreen navigation={navigation} />);

    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const enterButton = getByText(TEXTS.enter);

    fireEvent.changeText(nicknameInput, TEST_CREDENTIALS.nickname);
    fireEvent.changeText(passwordInput, TEST_CREDENTIALS.password);
    fireEvent.press(enterButton);

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith('HomeDrawer');
    });
  });

  // TEST №5
  it('Должен показывать предупреждение при вводе несуществующего пользователя', async () => {
    // Мокаем axios.post, чтобы он сымитировал ошибку "пользователь не найден"
    axios.post.mockRejectedValue({
      response: {
        data: {
          errorType: 'USER_NOT_FOUND',
          message: 'Пользователь с таким никнеймом не найден.'
        }
      }
    });

    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByPlaceholderText, getByText } = render(<LoginScreen navigation={navigation} />);

    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const enterButton = getByText(TEXTS.enter);

    fireEvent.changeText(nicknameInput, 'nonexistentuser');
    fireEvent.changeText(passwordInput, TEST_CREDENTIALS.password);
    fireEvent.press(enterButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith('Ошибка', 'Пользователь с таким никнеймом не найден.');
    });
  });

  // TEST №6
  it('Должен показывать предупреждение при вводе верного никнейма и неверного пароля', async () => {
    // Мокаем axios.post, чтобы он сымитировал ошибку "неверный пароль"
    axios.post.mockRejectedValue({
      response: {
        data: {
          errorType: 'INVALID_PASSWORD',
          message: 'Неверный пароль. Попробуйте еще раз.'
        }
      }
    });

    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByPlaceholderText, getByText } = render(<LoginScreen navigation={navigation} />);

    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const enterButton = getByText(TEXTS.enter);

    fireEvent.changeText(nicknameInput, TEST_CREDENTIALS.nickname);
    fireEvent.changeText(passwordInput, 'wrongpassword');
    fireEvent.press(enterButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith('Ошибка', 'Неверный пароль. Попробуйте еще раз.');
    });
  });

  // TEST №7
  it('Должен показывать предупреждение при ошибке валидации данных', async () => {
    // Мокаем axios.post, чтобы он сымитировал ошибку валидации
    axios.post.mockRejectedValue({
      response: {
        data: {
          details: [
            { path: 'nickname', message: 'Никнейм должен содержать только буквы и цифры' },
            { path: 'password', message: 'Пароль должен быть не менее 8 символов' }
          ]
        }
      }
    });

    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByPlaceholderText, getByText } = render(<LoginScreen navigation={navigation} />);

    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const enterButton = getByText(TEXTS.enter);

    fireEvent.changeText(nicknameInput, 'invalid@user');
    fireEvent.changeText(passwordInput, 'short');
    fireEvent.press(enterButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        'Ошибка валидации', 
        'nickname: Никнейм должен содержать только буквы и цифры\npassword: Пароль должен быть не менее 8 символов'
      );
    });
  });

  // TEST №8
  it('Должен показывать общее сообщение об ошибке при неизвестной ошибке сервера', async () => {
    // Мокаем axios.post, чтобы он сымитировал неизвестную ошибку
    axios.post.mockRejectedValue({
      response: {
        data: {
          message: 'Произошла неизвестная ошибка'
        }
      }
    });

    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByPlaceholderText, getByText } = render(<LoginScreen navigation={navigation} />);

    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const enterButton = getByText(TEXTS.enter);

    fireEvent.changeText(nicknameInput, TEST_CREDENTIALS.nickname);
    fireEvent.changeText(passwordInput, TEST_CREDENTIALS.password);
    fireEvent.press(enterButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        'Ошибка', 
        'Произошла неизвестная ошибка'
      );
    });
  });

  // TEST №9
  it('Должен показывать общее сообщение об ошибке при сбое сети', async () => {
    // Мокаем axios.post, чтобы он сымитировал сбой сети (без response)
    axios.post.mockRejectedValue(new Error('Network Error'));

    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByPlaceholderText, getByText } = render(<LoginScreen navigation={navigation} />);

    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const enterButton = getByText(TEXTS.enter);

    fireEvent.changeText(nicknameInput, TEST_CREDENTIALS.nickname);
    fireEvent.changeText(passwordInput, TEST_CREDENTIALS.password);
    fireEvent.press(enterButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        'Ошибка', 
        'Не удалось войти. Попробуйте еще раз.'
      );
    });
  });

  // TEST №10
  it('Должен переходить на экран регистрации при нажатии на ссылку регистрации', () => {
    const { getByText } = render(<LoginScreen navigation={navigation} />);

    // Находим ссылку для регистрации
    const registrationLink = getByText('Скорее регистрируйся!');

    // Нажимаем на ссылку
    fireEvent.press(registrationLink);

    // Проверяем, что была вызвана навигация на экран Registration
    expect(mockNavigate).toHaveBeenCalledWith('Registration');
  });
});

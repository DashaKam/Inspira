import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react-native';
import RegistrationScreen from '../RegistrationScreen';
import { Alert } from "react-native";
import axios from "axios";

jest.mock('axios');

const mockNavigate = jest.fn();
const navigation = { navigate: mockNavigate };

const TEXTS = {
  your_name: 'Как тебя зовут?',
  your_nickname: 'Придумай никнейм',
  your_password: 'Придумай пароль',
  register: 'Зарегистрироваться',
  already_have_account: 'Уже есть аккаунт?',
  login_link: 'Скорее заходи!'
};

const TEST_USER_DATA = {
  name: 'Test User',
  nickname: 'testuser',
  password: 'password123'
};

describe('RegistrationScreen', () => {

  // TEST №1
  it('Должен отображать все элементы: инпуты, кнопку регистрации и ссылку на логин', () => {
    const { getByPlaceholderText, getByText } = render(<RegistrationScreen navigation={navigation} />);

    expect(getByPlaceholderText(TEXTS.your_name)).toBeTruthy();
    expect(getByPlaceholderText(TEXTS.your_nickname)).toBeTruthy();
    expect(getByPlaceholderText(TEXTS.your_password)).toBeTruthy();
    expect(getByText(TEXTS.register)).toBeTruthy();
    expect(getByText(TEXTS.already_have_account)).toBeTruthy();
    expect(getByText(TEXTS.login_link)).toBeTruthy();
  });

  // TEST №2
  it('Должен позволять вводить имя, никнейм и пароль', () => {
    const { getByPlaceholderText, getByDisplayValue } = render(<RegistrationScreen navigation={navigation} />);

    const nameInput = getByPlaceholderText(TEXTS.your_name);
    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);

    fireEvent.changeText(nameInput, TEST_USER_DATA.name);
    fireEvent.changeText(nicknameInput, TEST_USER_DATA.nickname);
    fireEvent.changeText(passwordInput, TEST_USER_DATA.password);

    expect(getByDisplayValue(TEST_USER_DATA.name)).toBeTruthy();
    expect(getByDisplayValue(TEST_USER_DATA.nickname)).toBeTruthy();
    expect(getByDisplayValue(TEST_USER_DATA.password)).toBeTruthy();
  });

  // TEST №3
  it('Должен показывать предупреждение, если поля не заполнены', () => {
    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByText } = render(<RegistrationScreen navigation={navigation} />);

    fireEvent.press(getByText(TEXTS.register));

    expect(alertSpy).toHaveBeenCalledWith('Ошибка', 'Пожалуйста, заполните все поля.');
  });

  // TEST №4
  it('Должен выполнить регистрацию и перейти на экран HomeDrawer при успешном ответе от сервера', async () => {
    // Мокаем axios.post, чтобы он сымитировал успешный ответ от сервера
    axios.post.mockResolvedValue({
      status: 200,
      data: { token: 'fake-token' }
    });

    const { getByPlaceholderText, getByText } = render(<RegistrationScreen navigation={navigation} />);

    const nameInput = getByPlaceholderText(TEXTS.your_name);
    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const registerButton = getByText(TEXTS.register);

    fireEvent.changeText(nameInput, TEST_USER_DATA.name);
    fireEvent.changeText(nicknameInput, TEST_USER_DATA.nickname);
    fireEvent.changeText(passwordInput, TEST_USER_DATA.password);
    fireEvent.press(registerButton);

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith('HomeDrawer');
    });
  });

  // TEST №5
  it('Должен показывать предупреждение, если никнейм уже занят', async () => {
    // Мокаем axios.post, чтобы он сымитировал ошибку "никнейм занят"
    axios.post.mockRejectedValue({
      response: {
        data: {
          errorType: 'USER_NICKNAME_OCCUPIED',
          message: 'Этот никнейм уже занят. Пожалуйста, выберите другой.'
        }
      }
    });

    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByPlaceholderText, getByText } = render(<RegistrationScreen navigation={navigation} />);

    const nameInput = getByPlaceholderText(TEXTS.your_name);
    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const registerButton = getByText(TEXTS.register);

    fireEvent.changeText(nameInput, TEST_USER_DATA.name);
    fireEvent.changeText(nicknameInput, 'existinguser');
    fireEvent.changeText(passwordInput, TEST_USER_DATA.password);
    fireEvent.press(registerButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith('Ошибка', 'Этот никнейм уже занят. Пожалуйста, выберите другой.');
    });
  });

  // TEST №6
  it('Должен показывать предупреждение при ошибке валидации данных', async () => {
    // Мокаем axios.post, чтобы он сымитировал ошибку валидации
    axios.post.mockRejectedValue({
      response: {
        data: {
          details: [
            { path: 'nickname', message: 'Invalid nickname' },
            { path: 'password', message: 'Invalid password' }
          ]
        }
      }
    });

    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByPlaceholderText, getByText } = render(<RegistrationScreen navigation={navigation} />);

    const nameInput = getByPlaceholderText(TEXTS.your_name);
    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const registerButton = getByText(TEXTS.register);

    fireEvent.changeText(nameInput, TEST_USER_DATA.name);
    fireEvent.changeText(nicknameInput, 'inv');
    fireEvent.changeText(passwordInput, 'short');
    fireEvent.press(registerButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        'Ошибка валидации',
        expect.stringContaining('Пожалуйста, исправьте следующие ошибки:')
      );
    });
  });

  // TEST №7
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
    const { getByPlaceholderText, getByText } = render(<RegistrationScreen navigation={navigation} />);

    const nameInput = getByPlaceholderText(TEXTS.your_name);
    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const registerButton = getByText(TEXTS.register);

    fireEvent.changeText(nameInput, TEST_USER_DATA.name);
    fireEvent.changeText(nicknameInput, TEST_USER_DATA.nickname);
    fireEvent.changeText(passwordInput, TEST_USER_DATA.password);
    fireEvent.press(registerButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        'Ошибка',
        'Произошла неизвестная ошибка'
      );
    });
  });

  // TEST №8
  it('Должен показывать общее сообщение об ошибке при сбое сети', async () => {
    // Мокаем axios.post, чтобы он сымитировал сбой сети (без response)
    axios.post.mockRejectedValue(new Error('Network Error'));

    const alertSpy = jest.spyOn(Alert, 'alert');
    const { getByPlaceholderText, getByText } = render(<RegistrationScreen navigation={navigation} />);

    const nameInput = getByPlaceholderText(TEXTS.your_name);
    const nicknameInput = getByPlaceholderText(TEXTS.your_nickname);
    const passwordInput = getByPlaceholderText(TEXTS.your_password);
    const registerButton = getByText(TEXTS.register);

    fireEvent.changeText(nameInput, TEST_USER_DATA.name);
    fireEvent.changeText(nicknameInput, TEST_USER_DATA.nickname);
    fireEvent.changeText(passwordInput, TEST_USER_DATA.password);
    fireEvent.press(registerButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        'Ошибка',
        'Не удалось зарегистрироваться. Пожалуйста, проверьте ваше интернет-соединение и попробуйте снова.'
      );
    });
  });

  // TEST №9
  it('Должен перенаправлять на экран логина при нажатии на ссылку', () => {
    const { getByText } = render(<RegistrationScreen navigation={navigation} />);
    
    fireEvent.press(getByText(TEXTS.login_link));
    
    expect(mockNavigate).toHaveBeenCalledWith('Login');
  });
});
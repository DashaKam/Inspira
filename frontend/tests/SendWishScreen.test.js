import React from 'react';
import { render } from '@testing-library/react-native';
import SendWishScreen from '../SendWishScreen';

// Мокаем Alert, но пока не проверяем его функциональность
jest.mock('react-native/Libraries/Alert/Alert', () => ({
  alert: jest.fn(),
}));

describe('SendWishScreen', () => {
  it('Корректное отображение элементов', () => {
    const { getByPlaceholderText, getByText } = render(<SendWishScreen />);
    
    // Check if the main UI elements are present
    expect(getByText('Никнейм получателя:')).toBeTruthy();
    expect(getByPlaceholderText('Введите никнейм')).toBeTruthy();
    expect(getByText('Сообщение:')).toBeTruthy();
    expect(getByPlaceholderText('Введите ваше пожелание')).toBeTruthy();
    expect(getByText('Отправить анонимно')).toBeTruthy();
    expect(getByText('Отправить')).toBeTruthy();
  });
});
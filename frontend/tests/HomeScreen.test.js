import React from 'react';
import { render } from '@testing-library/react-native';
import HomeScreen from '../HomeScreen';

describe('HomeScreen', () => {
  it('Корректное отображение элементов', () => {
    const mockNavigation = { navigate: jest.fn() };
    const { getByText } = render(<HomeScreen navigation={mockNavigation} />);
    
    // Check if the quote text is present
    expect(getByText('Если не хочешь остаться ребенком навеки, нельзя ждать подсказок от других. Ты должен найти решение в себе самом.')).toBeTruthy();
  });
});
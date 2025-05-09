module.exports = {
    preset: 'react-native',
    setupFilesAfterEnv: ['@testing-library/jest-native/extend-expect'],
    transformIgnorePatterns: [
        'node_modules/(?!((jest-)?react-native|@react-native(-community)?)/)',
    ],
    testPathIgnorePatterns: ['/node_modules/', '/android/', '/ios/'],
    testMatch: ['**/__tests__/**/*.test.js'], // ищет тесты в папке __tests__
    moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
};

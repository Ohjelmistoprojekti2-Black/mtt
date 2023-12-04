import '@testing-library/jest-dom';

const mockGeolocation = {
    getCurrentPosition: jest.fn(),
    watchPosition: jest.fn(),
};

beforeAll(() => {
    global.navigator = {
        ...global.navigator,
        geolocation: mockGeolocation,
    };
});

afterAll(() => {
});

beforeEach(() => {
});

afterEach(() => {
});

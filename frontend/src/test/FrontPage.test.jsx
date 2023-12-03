/**
 * @jest-environment jsdom
 */

import React from 'react';
import { act, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';
import FrontPage from '../components/FrontPage';
import 'jest-fetch-mock';

jest.mock('../components/Weather', () => () => null);

beforeAll(() => {
    jest.spyOn(global, 'fetch').mockImplementation(() =>
        Promise.resolve({
            json: () => Promise.resolve([]),
        })
    );
});

test('renders FrontPage component', async () => {
    await act(async () => {
        render(<FrontPage />);
    });
    expect(screen.getByText('All events')).toBeInTheDocument();
});

test('searches events by name', async () => {
    render(<FrontPage />);

    const searchInput = screen.getByPlaceholderText(/Search by event name/i);

    userEvent.type(searchInput, 'Test event');
    console.log('Debug: Odotetaan tekstiä "Test event"...');

    await waitFor(
        () => {
            console.log('Debug: Tarkistetaan, onko teksti "Test event" näkyvissä...');
            if (screen.queryByText('Test event')) {
                console.log('Debug: Teksti on näkyvissä!');
                return true;
            }
            return false;
        },
        { timeout: 5000 }
    );
});

test('expands and collapses event details', async () => {
    render(<FrontPage />);

    await waitFor(() => {
        expect(screen.getByText('All events')).toBeInTheDocument();
    });

    await waitFor(() => {
        const viewDetailsButtons = screen.getAllByText(/view details/i);

        expect(viewDetailsButtons.length).toBeGreaterThan(0);
    });

    userEvent.click(screen.getByText('View Details'));

    await waitFor(() => {
        expect(screen.getByText('Price:')).toBeInTheDocument();
    });

    userEvent.click(screen.getByText('Close Details'));

    await waitFor(() => {
        expect(screen.queryByText('Price:')).toBeNull();
    });
});

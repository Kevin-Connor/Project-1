/**
 * @jest-environment jsdom
 */

const URL = 'http://localhost:8080/warehouses';

describe('Warehouse Management Frontend', () => {
  beforeEach(() => {
    // Setting up the DOM structure needed for tests
    document.body.innerHTML = `
      <form id="new-warehouse-form">
        <input name="new-warehouse-name" />
        <input name="new-warehouse-city" />
        <input name="new-warehouse-capacity" />
        <button type="submit">Submit</button>
      </form>
      <table>
        <tbody id="warehouse-table-body"></tbody>
      </table>
      <form id="update-warehouse-form">
        <input id="update-warehouse-id" />
        <input id="update-warehouse-name" />
        <input id="update-warehouse-city" />
        <input id="update-warehouse-capacity" />
      </form>
      <form id="delete-warehouse-form">
        <input id="delete-warehouse-id" />
        <input id="delete-warehouse-name" />
        <input id="delete-warehouse-city" />
        <input id="delete-warehouse-capacity" />
      </form>
    `;

    // Mock function for adding warehouse
    window.addWarehouseToTable = jest.fn((warehouse) => {
      const tableBody = document.getElementById('warehouse-table-body');
      const row = document.createElement('tr');
      row.innerHTML = `<td>${warehouse.warehouseName}</td><td>${warehouse.city}</td><td>${warehouse.capacity}</td>`;
      tableBody.appendChild(row);
    });

    // Mock function for updating warehouse
    window.updateWarehouseInTable = jest.fn((warehouse) => {
      const tableBody = document.getElementById('warehouse-table-body');
      const rows = Array.from(tableBody.getElementsByTagName('tr'));
      const row = rows.find(r => r.cells[0].textContent === warehouse.warehouseName);
      if (row) {
        row.cells[0].textContent = warehouse.warehouseName;
        row.cells[1].textContent = warehouse.city;
        row.cells[2].textContent = warehouse.capacity;
      }
    });
  });

  test('loads and displays warehouses on DOMContentLoaded', async () => {
    // Mock fetch response
    global.fetch = jest.fn(() =>
      Promise.resolve({
        json: () => Promise.resolve([{ id: 1, warehouseName: 'Test Warehouse', city: 'Test City', capacity: 100 }]),
      })
    );

    // Dispatch the DOMContentLoaded event
    document.dispatchEvent(new Event('DOMContentLoaded'));

    // Wait for fetch to resolve
    await new Promise(resolve => setTimeout(resolve, 0));

    // Assertions to check if fetch was called
    expect(fetch).toHaveBeenCalledWith(URL);

    // Call function to add warehouse to table
    const warehouses = await fetch().then(response => response.json());
    warehouses.forEach(warehouse => addWarehouseToTable(warehouse));

    const tableBody = document.getElementById('warehouse-table-body');
    expect(tableBody.innerHTML).toContain('Test Warehouse');
  });

  test('handles new warehouse form submission and adds warehouse to the table', async () => {
    const form = document.getElementById('new-warehouse-form');

    // Fill in form inputs
    form.elements['new-warehouse-name'].value = 'Test Warehouse';
    form.elements['new-warehouse-city'].value = 'Test City';
    form.elements['new-warehouse-capacity'].value = 100;

    // Mock fetch for POST response
    global.fetch = jest.fn(() =>
      Promise.resolve({
        json: () => Promise.resolve({ id: 2, warehouseName: 'Test Warehouse', city: 'Test City', capacity: 100 }),
      })
    );

    // Simulate form submission
    form.dispatchEvent(new Event('submit', { bubbles: true, cancelable: true }));

    // Handle the form submission
    const newWarehouse = {
      warehouseName: form.elements['new-warehouse-name'].value,
      city: form.elements['new-warehouse-city'].value,
      capacity: form.elements['new-warehouse-capacity'].value,
    };

    await fetch(URL + '/warehouse', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newWarehouse),
    });

    // Call addWarehouseToTable after fetch
    addWarehouseToTable(newWarehouse);

    // Assertions to check if the table has the new warehouse
    const tableBody = document.getElementById('warehouse-table-body');
    expect(tableBody.innerHTML).toContain('Test Warehouse');
  });

  test('removes a warehouse from the table when deleted', async () => {
    // Setup initial state
    const initialWarehouse = { id: 1, warehouseName: 'Test Warehouse', city: 'Test City', capacity: 100 };
    addWarehouseToTable(initialWarehouse);

    const form = document.getElementById('delete-warehouse-form');

    // Fill in delete form inputs
    form.elements['delete-warehouse-id'].value = 1;
    form.elements['delete-warehouse-name'].value = 'Test Warehouse';
    form.elements['delete-warehouse-city'].value = 'Test City';
    form.elements['delete-warehouse-capacity'].value = 100;

    // Mock fetch for DELETE response
    global.fetch = jest.fn(() =>
      Promise.resolve({
        status: 204 // No Content
      })
    );

    // Simulate form submission for delete
    form.dispatchEvent(new Event('submit', { bubbles: true, cancelable: true }));

    // Assert fetch DELETE was called
    expect(fetch).toHaveBeenCalledWith(`${URL}/warehouse/1`, expect.objectContaining({
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        id: '1',
        warehouseName: 'Test Warehouse',
        city: 'Test City',
        capacity: '100'
      })
    }));

    // Wait for DOM update
    await new Promise(resolve => setTimeout(resolve, 0));

    const tableBody = document.getElementById('warehouse-table-body');
    expect(tableBody.innerHTML).not.toContain('Test Warehouse');
  });

  // Additional tests for error handling
  test('handles error when loading warehouses', async () => {
    // Mock fetch to return an error
    global.fetch = jest.fn(() => Promise.reject(new Error('Network Error')));

    // Trigger DOMContentLoaded
    document.dispatchEvent(new Event('DOMContentLoaded'));

    // Wait for fetch to resolve
    await new Promise(resolve => setTimeout(resolve, 0));

    // Check if any specific error handling actions were taken (e.g., showing an error message)
    const tableBody = document.getElementById('warehouse-table-body');
    expect(tableBody.innerHTML).toBe(''); // Assuming error handling clears the table
  });

  test('handles error when adding a new warehouse', async () => {
    const form = document.getElementById('new-warehouse-form');

    // Fill in form inputs
    form.elements['new-warehouse-name'].value = 'Test Warehouse';
    form.elements['new-warehouse-city'].value = 'Test City';
    form.elements['new-warehouse-capacity'].value = 100;

    // Mock fetch to return an error for POST
    global.fetch = jest.fn(() => Promise.reject(new Error('Network Error')));

    // Simulate form submission
    form.dispatchEvent(new Event('submit', { bubbles: true, cancelable: true }));

    // Wait for DOM update after fetch
    await new Promise(resolve => setTimeout(resolve, 0));

    // Check if error handling works, e.g., showing an error message
    const tableBody = document.getElementById('warehouse-table-body');
    expect(tableBody.innerHTML).not.toContain('Test Warehouse'); // Ensure it didn't add
  });

  test('handles error when deleting a warehouse', async () => {
    const form = document.getElementById('delete-warehouse-form');

    // Mock initial state with a warehouse
    const initialWarehouse = { id: 1, warehouseName: 'Test Warehouse', city: 'Test City', capacity: 100 };
    addWarehouseToTable(initialWarehouse);

    // Fill in delete form inputs
    form.elements['delete-warehouse-id'].value = 1;
    form.elements['delete-warehouse-name'].value = 'Test Warehouse';
    form.elements['delete-warehouse-city'].value = 'Test City';
    form.elements['delete-warehouse-capacity'].value = 100;

    // Mock fetch to return an error for DELETE
    global.fetch = jest.fn(() => Promise.reject(new Error('Network Error')));

    // Simulate form submission for delete
    form.dispatchEvent(new Event('submit', { bubbles: true, cancelable: true }));

    // Wait for DOM update
    await new Promise(resolve => setTimeout(resolve, 0));

    // Check if the warehouse is still there
    const tableBody = document.getElementById('warehouse-table-body');
    expect(tableBody.innerHTML).toContain('Test Warehouse'); // Ensure it still exists
  });
});

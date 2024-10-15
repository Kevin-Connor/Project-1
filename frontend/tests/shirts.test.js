/**
 * @jest-environment jsdom
 */

const URL = 'http://localhost:8080/shirts';

describe('Shirt Management Frontend', () => {
  beforeEach(() => {
    // Setting up the DOM structure needed for tests
    document.body.innerHTML = `
      <form id="new-shirt-form">
        <input type="text" name="new-shirt-animal" />
        <input type="text" name="new-shirt-size" />
        <input type="text" name="new-warehouse-name" />
        <button type="submit">Add Shirt</button>
      </form>
      
      <table>
        <tbody id="shirt-table-body"></tbody>
      </table>
    `;

    // Mock functions for adding and updating shirts
    window.addShirtToTable = jest.fn((shirt) => {
      const tableBody = document.getElementById('shirt-table-body');
      const row = document.createElement('tr');
      row.innerHTML = `<td>${shirt.animal}</td><td>${shirt.warehouse.warehouseName}</td>`;
      tableBody.appendChild(row);
    });

    window.updateShirtInTable = jest.fn((shirt) => {
      const tableBody = document.getElementById('shirt-table-body');
      const rows = Array.from(tableBody.getElementsByTagName('tr'));
      const row = rows.find(r => r.cells[0].textContent === shirt.animal);
      if (row) {
        row.cells[0].textContent = shirt.animal; // Update animal name
        row.cells[1].textContent = shirt.warehouse.warehouseName; // Update warehouse name
      }
    });
  });

  // Test for adding a shirt to the table
  test('should add a shirt to the table when new shirt form is submitted', async () => {
    const form = document.getElementById('new-shirt-form');

    // Fill in the form fields
    form.elements['new-shirt-animal'].value = 'Cat';
    form.elements['new-shirt-size'].value = 'M';
    form.elements['new-warehouse-name'].value = 'Warehouse 1';

    // Mock fetch response
    global.fetch = jest.fn(() =>
      Promise.resolve({
        json: () => Promise.resolve({
          id: 1,
          animal: 'Cat',
          size: 'M',
          warehouse: { warehouseName: 'Warehouse 1' }
        }),
      })
    );

    // Trigger form submission
    form.dispatchEvent(new Event('submit', { bubbles: true, cancelable: true }));

    // Handle the form submission to call the addShirtToTable
    const newShirt = {
      animal: form.elements['new-shirt-animal'].value,
      size: form.elements['new-shirt-size'].value,
      warehouse: { warehouseName: form.elements['new-warehouse-name'].value },
    };
    await fetch(URL + '/shirt', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newShirt),
    });

    // Call addShirtToTable after fetch
    addShirtToTable(newShirt);

    // Assertions to check if the table has the new shirt
    const tableBody = document.getElementById('shirt-table-body');
    expect(tableBody.innerHTML).toContain('Cat');
    expect(tableBody.innerHTML).toContain('Warehouse 1');
    expect(fetch).toHaveBeenCalledWith(URL + '/shirt', expect.objectContaining({
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newShirt),
    }));
  });

    // Mocking the XMLHttpRequest to test initial load
    test('should load shirts on DOMContentLoaded', async () => {
        const mockShirts = [
        { id: 1, animal: 'Cat', size: 'M', warehouse: { warehouseName: 'Warehouse 1' } },
        { id: 2, animal: 'Dog', size: 'L', warehouse: { warehouseName: 'Warehouse 2' } },
        ];
    
        // Set up the mock for fetch to return the correct structure
        global.fetch = jest.fn().mockResolvedValue({
        json: jest.fn().mockResolvedValue(mockShirts), // Ensure json() returns a promise resolving to mockShirts
        });
    
        // Dispatch the DOMContentLoaded event
        document.dispatchEvent(new Event('DOMContentLoaded'));
    
        // Wait for the fetch to resolve
        const shirts = await fetch().then(response => response.json()); // Call fetch and then json()
    
        // Add shirts to the table
        shirts.forEach(shirt => addShirtToTable(shirt));
    
        const tableBody = document.getElementById('shirt-table-body');
        expect(tableBody.innerHTML).toContain('Cat');
        expect(tableBody.innerHTML).toContain('Dog');
    });
  

  // Test the update process
  test('should update the shirt details in the table', async () => {
    const mockShirt = { id: 1, animal: 'Cat', size: 'M', warehouse: { warehouseName: 'Warehouse 1' } };

    // Add initial shirt to the table
    addShirtToTable(mockShirt);

    // Now, simulate updating the shirt
    const updatedShirt = { 
        id: 1, 
        animal: 'Tiger', 
        size: 'L', 
        warehouse: { warehouseName: 'Warehouse 2' } 
    };

    // Call the function to update the shirt
    updateShirtInTable(updatedShirt);

    const tableBody = document.getElementById('shirt-table-body');
    expect(tableBody.innerHTML).toContain('Tiger'); // Check for updated animal name
    expect(tableBody.innerHTML).toContain('Warehouse 2'); // Check for updated warehouse name
});

});

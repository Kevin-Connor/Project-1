
const URL = 'http://localhost:8080/warehouses';

let allWarehouses = [];

document.addEventListener('DOMContentLoaded', () => {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = () => {

        if(xhr.readyState === 4) {
            let warehouses = JSON.parse(xhr.responseText);
         
            warehouses.forEach(newWarehouse => {
                addWarehouseToTable(newWarehouse);
            });
        }
    }

    xhr.open('GET', URL);

    xhr.send();

});

document.getElementById('new-warehouse-form').addEventListener('submit', (event) => {

        event.preventDefault();         

        let inputData = new FormData(document.getElementById('new-warehouse-form'));

        let newWarehouse = {
            warehouseName : inputData.get('new-warehouse-name'),
            city : inputData.get('new-warehouse-city'),
            capacity : inputData.get('new-warehouse-capacity')
        }

        doPostRequest(newWarehouse);

});

function addWarehouseToTable(newWarehouse) {

    console.log(newWarehouse);


    //creating DOM elements
    let tr = document.createElement('tr');      
    let id = document.createElement('td');           
    let warehouseName = document.createElement('td');     
    let city = document.createElement('td');      
    let capacity = document.createElement('td');    
    let editBtn = document.createElement('td');     
    let deleteBtn = document.createElement('td');   

    id.innerText = newWarehouse.id;
    warehouseName.innerText = newWarehouse.warehouseName;
    city.innerText = newWarehouse.city;
    capacity.innerText = newWarehouse.capacity;

    editBtn.innerHTML =
    `<button class="btn btn-primary" id="edit-button" onclick="activateEditForm(${newWarehouse.id})">Edit</button>`

    deleteBtn.innerHTML =
    `<button class="btn btn-danger" id="delete-button" onclick="activateDeleteForm(${newWarehouse.id})">Delete</button>`

    //adds the <td> tag as nested children to the <tr> tag in our html page
    tr.appendChild(id);
    tr.appendChild(warehouseName);
    tr.appendChild(city);
    tr.appendChild(capacity);
    tr.appendChild(editBtn);
    tr.appendChild(deleteBtn);

    //sets the id attribute for the <tr> tag
    tr.setAttribute('id', 'TR' + newWarehouse.id)

    //adds the <tr> tag to the <tbody> tag
    document.getElementById('warehouse-table-body').appendChild(tr);

    //adds new warehouse to the list
    allWarehouses.push(newWarehouse);

}

async function doPostRequest(newWarehouse) {

    console.log(newWarehouse);
    let returnedData = await fetch(URL + '/warehouse', {
        method : 'POST',
        headers : {
            'Content-Type' : 'application/json' 
        },
        body : JSON.stringify(newWarehouse)    
    });

    let warehouseJson = await returnedData.json();

    console.log('WAREHOUSE JSON' + warehouseJson);

    //add warehouse to table
    addWarehouseToTable(warehouseJson);

    //reset the form
    document.getElementById('new-warehouse-form').reset();
}

//UPDATE our warehouse table
document.getElementById('update-warehouse-form').addEventListener('submit', (event) => {
    event.preventDefault();  

    //retrieving data from the update form
    let inputData = new FormData(document.getElementById('update-warehouse-form'));

    let warehouse = {
        id : document.getElementById('update-warehouse-id').value, 
        warehouseName : inputData.get('update-warehouse-name'),
        city : inputData.get('update-warehouse-city'),
        capacity : inputData.get('update-warehouse-capacity')
    }

    fetch(URL + '/warehouse', {
        method : 'PUT',
        headers : {
            'Content-Type' : 'application/json',
        }, 
        body : JSON.stringify(warehouse)

    })
    .then((data) => {

        return data.json();
    })
    .then((warehouseJSON) => {    

        //add updated warehouse to our table
        updateWarehouseInTable(warehouseJSON);
    })
    .catch((error) => {


        console.error(error);
    })

});

document.getElementById('delete-warehouse-form').addEventListener('submit', (event) => {
    event.preventDefault(); 

    let warehouseId = document.getElementById('delete-warehouse-id').value;
    let nameOnForm = document.getElementById('delete-warehouse-name').value;
    let cityOnForm = document.getElementById('delete-warehouse-city').value;
    let capacityOnForm = document.getElementById('delete-warehouse-capacity').value;

    let warehouse = {
        id : warehouseId,
        warehouseName : nameOnForm,
        city : cityOnForm, 
        capacity : capacityOnForm
    }

    fetch(URL + `/warehouse/${warehouseId}`, {
        method : 'DELETE',
        headers : {
            'Content-Type' : 'application/json',
        },
        body : JSON.stringify(warehouse)
    })
    .then((data) => {

        if(data.status === 204) {

            //removes a warehouse from the table
            removeWarehouseFromTable(warehouse);

            // reseting all forms
            resetAllForms();
        }
    })
    .catch((error) => {
        console.error(error);
    })
});

function updateWarehouseInTable(warehouse) {
    document.getElementById('TR' + warehouse.id).innerHTML = `
    <td>${warehouse.id}</td>
    <td>${warehouse.warehouseName}</td>
    <td>${warehouse.city}</td>
    <td>${warehouse.capacity}</td>
    <td><button class="btn btn-primary" id="editButton" onclick="activateEditForm(${warehouse.id})">Edit</button></td>
    <td><button class="btn btn-danger" id="deleteButton" onclick="activateDeleteForm(${warehouse.id})">Delete</button></td>
    `;
}

function activateEditForm(warehouseId) {
    // find the warehouse that needs to be edited
    for(let w of allWarehouses) {
        if(w.id === warehouseId) {
            document.getElementById('update-warehouse-id').value = w.id;
            document.getElementById('update-warehouse-name').value = w.warehouseName;
            document.getElementById('update-warehouse-city').value = w.city;
            document.getElementById('update-warehouse-capacity').value = w.capacity;
        }
    }

    //SHOWING ONLY THE EDIT FORM
    document.getElementById('new-warehouse-form').style.display = 'none';
    document.getElementById('update-warehouse-form').style.display = 'block'; 
    document.getElementById('delete-warehouse-form').style.display = 'none'; 
}

function activateDeleteForm(warehouseId) {
    // find the warehouse that needs to be deleted
    for(let w of allWarehouses) {
        if(w.id === warehouseId) {
            document.getElementById('delete-warehouse-id').value = w.id;
            document.getElementById('delete-warehouse-name').value = w.warehouseName;
            document.getElementById('delete-warehouse-city').value = w.city;
            document.getElementById('delete-warehouse-capacity').value = w.capacity;
        }
    }

    //SHOWING ONLY THE DELETE FORM
    document.getElementById('new-warehouse-form').style.display = 'none';
    document.getElementById('update-warehouse-form').style.display = 'none';   
    document.getElementById('delete-warehouse-form').style.display = 'block'; 
}

document.getElementById('delete-cancel-button').addEventListener('click', (event) => {
    event.preventDefault();

    // reset the delete form
    document.getElementById('delete-warehouse-form').reset();

    // hide the delete form
    document.getElementById('delete-warehouse-form').style.display = 'none';

    // display the new-warehouse-form
    document.getElementById('new-warehouse-form').style.display = 'block';
});

function removeWarehouseFromTable(warehouse) {
    const element = document.getElementById('TR' + warehouse.id);
    element.remove();
}

function resetAllForms() {

    // clears data from all forms
    document.getElementById('new-warehouse-form').reset();
    document.getElementById('update-warehouse-form').reset();
    document.getElementById('delete-warehouse-form').reset();

    //displays only the new-warehouse-form
    document.getElementById('new-warehouse-form').style.display = 'block';
    document.getElementById('update-warehouse-form').style.display = 'none';
    document.getElementById('delete-warehouse-form').style.display = 'none';
}
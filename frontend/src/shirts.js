
const URL = 'http://localhost:8080/shirts';

let allShirts = [];

document.addEventListener('DOMContentLoaded', () => {

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = () => {

        if(xhr.readyState === 4) {
            var shirts = JSON.parse(xhr.responseText);
            
            shirts.forEach(newShirt => {
                addShirtToTable(newShirt);
            });
        }
    }

    xhr.open('GET', URL);

    xhr.send();

});

document.getElementById('new-shirt-form').addEventListener('submit', (event) => {

        event.preventDefault();         

        let inputData = new FormData(document.getElementById('new-shirt-form'));

        let newShirt = {
            animal : inputData.get('new-shirt-animal'),
            size : inputData.get('new-shirt-size'),
            warehouse : {
                warehouseName : inputData.get('new-warehouse-name')
            }
        }

        doPostRequest(newShirt);

});

function addShirtToTable(newShirt) {

    console.log(newShirt);


    //creating DOM elements
    let tr = document.createElement('tr');      
    let id = document.createElement('td');          
    let animal = document.createElement('td');      
    let size = document.createElement('td');    
    let warehouse = document.createElement('td');   
    let editBtn = document.createElement('td');     
    let deleteBtn = document.createElement('td');   

    id.innerText = newShirt.id;
    animal.innerText = newShirt.animal;
    size.innerText = newShirt.size;
    warehouse.innerText = newShirt.warehouse.warehouseName;

    editBtn.innerHTML =
    `<button class="btn btn-primary" id="edit-button" onclick="activateEditForm(${newShirt.id})">Edit</button>`

    deleteBtn.innerHTML =
    `<button class="btn btn-danger" id="delete-button" onclick="activateDeleteForm(${newShirt.id})">Delete</button>`

    //adds the <td> tag as nested children to the <tr> tag in our html page
    tr.appendChild(id);
    tr.appendChild(animal);
    tr.appendChild(size);
    tr.appendChild(warehouse);
    tr.appendChild(editBtn);
    tr.appendChild(deleteBtn);

    //sets the id attribute for the <tr> tag
    tr.setAttribute('id', 'TR' + newShirt.id)

    //adds the <tr> tag to the <tbody> tag
    document.getElementById('shirt-table-body').appendChild(tr);

    //adds new shirt to the list
    allShirts.push(newShirt);

}

async function doPostRequest(newShirt) {

    console.log(newShirt);
    let returnedData = await fetch(URL + '/shirt', {
        method : 'POST',
        headers : {
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(newShirt)   
    });

    let shirtJson = await returnedData.json();

    console.log('SHIRT JSON' + shirtJson);

    //add shirt to table
    addShirtToTable(shirtJson);

    //reset the form
    document.getElementById('new-shirt-form').reset();
}

//UPDATE our shirt table
document.getElementById('update-shirt-form').addEventListener('submit', (event) => {
    event.preventDefault();  

    //retrieving data from the update form
    let inputData = new FormData(document.getElementById('update-shirt-form'));

    let shirt = {
        id : document.getElementById('update-shirt-id').value,
        animal : inputData.get('update-shirt-animal'),
        size : inputData.get('update-shirt-size'),
        warehouse : {
            id : document.getElementById('update-warehouse-id').value,
            warehouseName : inputData.get('update-warehouse-name')
        }
    }

    fetch(URL + '/shirt', {
        method : 'PUT',
        headers : {
            'Content-Type' : 'application/json',
        }, 
        body : JSON.stringify(shirt)
    })
    .then((data) => {
       
        return data.json();
    })
    .then((shirtJSON) => {    

        //add updated shirt to our table
        updateShirtInTable(shirtJSON);
    })
    .catch((error) => {
        //this will handle all 400 and 500 status response codes

        console.error(error);
    })

});

document.getElementById('delete-shirt-form').addEventListener('submit', (event) => {
    event.preventDefault(); 

    let shirtId = document.getElementById('delete-shirt-id').value;
    let animalOnForm = document.getElementById('delete-shirt-animal').value;
    let sizeOnForm = document.getElementById('delete-shirt-size').value;
    let warehouseId = document.getElementById('delete-warehouse-id').value;
    let warehouseNameOnForm = document.getElementById('delete-warehouse-name').value;

    let shirt = {
        id : shirtId,
        animal : animalOnForm,
        size : sizeOnForm, 
        warehouse : {
            id : warehouseId,
            warehouseName : warehouseNameOnForm
        }
    }

    // Check if there are any shirts referencing the warehouse being deleted
    let shirtsReferencingWarehouse = allShirts.filter(shirt => shirt.warehouse.id === warehouseId);

    if (shirtsReferencingWarehouse.length > 0) {
        // If there are shirts referencing the warehouse, prompt the user to handle them
        if (confirm('There are shirts referencing this warehouse. Do you want to delete them?')) {
            // Delete the shirts referencing the warehouse
            deleteShirtsReferencingWarehouse(shirtsReferencingWarehouse);
        } else {
            // Cancel the deletion process
            return;
        }
    }

    fetch(URL + '/shirt', {
        method : 'DELETE',
        headers : {
            'Content-Type' : 'application/json',
        },
        body : JSON.stringify(shirt)
    })
    .then((data) => {

        if(data.status === 204) {

            //removes a shirt from the table
            removeShirtFromTable(shirt);

            // reseting all forms
            resetAllForms();
        }
    })
    .catch((error) => {
        console.error(error);
    })
});

function updateShirtInTable(shirt) {
    const shirtRow = document.getElementById('TR' + shirt.id);
    if (shirtRow) {
        shirtRow.innerHTML = `
            <td>${shirt.id}</td>
            <td>${shirt.animal}</td>
            <td>${shirt.size}</td>
            <td>${shirt.warehouse.warehouseName}</td>
            <td><button class="btn btn-primary" id="editButton" onclick="activateEditForm(${shirt.id})">Edit</button></td>
            <td><button class="btn btn-danger" id="deleteButton" onclick="activateDeleteForm(${shirt.id})">Delete</button></td>
        `;
    } else {
        console.error('Row not found for ID:', shirt.id);
    }
}


function activateEditForm(shirtId) {
    // find the shirt and that needs to be edited
    for(let s of allShirts) {
        if(s.id === shirtId) {
            document.getElementById('update-shirt-id').value = s.id;
            document.getElementById('update-shirt-animal').value = s.animal;
            document.getElementById('update-shirt-size').value = s.size;
            document.getElementById('update-warehouse-id').value = s.warehouse.id;
            document.getElementById('update-warehouse-name').value = s.warehouse.warehouseName;
        }
    }

    //SHOWING ONLY THE EDIT FORM
    document.getElementById('new-shirt-form').style.display = 'none';
    document.getElementById('update-shirt-form').style.display = 'block';  
    document.getElementById('delete-shirt-form').style.display = 'none'; 
}
document.getElementById('delete-cancel-button').addEventListener('click', (event) => {
    event.preventDefault();

    // reset the delete form
    document.getElementById('delete-shirt-form').reset();

    // hide the delete form
    document.getElementById('delete-shirt-form').style.display = 'none';

    // display the new-shirt-form
    document.getElementById('new-shirt-form').style.display = 'block';
});

function activateDeleteForm(shirtId) {
    // find the shirt that needs to be deleted
    for(let s of allShirts) {
        if(s.id === shirtId) {
            document.getElementById('delete-shirt-id').value = s.id;
            document.getElementById('delete-shirt-animal').value = s.animal;
            document.getElementById('delete-shirt-size').value = s.size;
            document.getElementById('delete-warehouse-id').value = s.warehouse.id;
            document.getElementById('delete-warehouse-name').value = s.warehouse.warehouseName;
        }
    }

    //SHOWING ONLY THE DELETE FORM
    document.getElementById('new-shirt-form').style.display = 'none';
    document.getElementById('update-shirt-form').style.display = 'none';   
    document.getElementById('delete-shirt-form').style.display = 'block'; 
}

function removeShirtFromTable(shirt) {
    //removing the <tr> tag from the table when a shirt gets deleted
    const element = document.getElementById('TR' + shirt.id);
    element.remove();
}

function resetAllForms() {

    // clears data from all forms
    document.getElementById('new-shirt-form').reset();
    document.getElementById('update-shirt-form').reset();
    document.getElementById('delete-shirt-form').reset();

    //displays only the new-shirt-form
    document.getElementById('new-shirt-form').style.display = 'block';
    document.getElementById('update-shirt-form').style.display = 'none';
    document.getElementById('delete-shirt-form').style.display = 'none';
}

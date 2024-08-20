console.log("Contacts")
const baseUrl = "http://localhost:8080"
const viewContactModal = document.getElementById('view-contact');

//option with default values
const option = {
    placement:'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
                'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: ()=> {
        console.log('modal is hidden');
    },
    onShow: ()=> {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

const instanceOptions = {
    id : 'view-contact',
    override: true
};

const contactModal = new Modal(viewContactModal, option, instanceOptions);

function openContactModal(){
    contactModal.show();
}
function closeContactModal(){
      contactModal.hide();
}
async function loadContactData(id){
    //function call to load data
    console.log(id);
    try{
        const data = await (await fetch(`${baseUrl}/api/contacts/${id}`)).json();
        console.log(data);
        console.log(data.name);
        document.querySelector('#contact_name').innerHTML=data.name;
        document.querySelector('#contact_email').innerHTML=data.email;
        document.querySelector('#contact_phone').innerHTML=data.phoneNumber;
        document.querySelector('#contact_address').innerHTML=data.address;
        document.querySelector('#contact_description').innerHTML=data.description;
        document.getElementById("contact_image").src=data.contactPicLink;
        if(data.favourite==true){
            document.querySelector('#contact_favourite').innerHTML='Yes';
        }else{
            document.querySelector('#contact_favourite').innerHTML='No';
        }

        
        openContactModal();

        
    }catch(error){
        console.log("Error: ",error);
    }
}
 
//delete contact

async function deleteContact(id){
  Swal.fire({
    title: "Do you want to delete the contact?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!"
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseUrl}/user/contacts/delete/`+id;
      window.location.replace(url);
    
    }
  });
}
function exportData(){
    TableToExcel.convert(document.getElementById("contact-table"), {
        name: "contacts.xlsx",
        sheet: {
          name: "Sheet 1"
        }
      });
}
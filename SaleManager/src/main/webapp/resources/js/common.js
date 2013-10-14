var jDlgContact;
var uText="createContactForm:pgContact";

onload = function() {    
    //alert('loaded');
    jDlgContact = new MyDialog(wDlgContact);
};

function MyDialog(iDialog){
    var dialog = iDialog;
    var isOpen = false;    
    var updateString = "createContactForm:pgContact";
    
    this.onHide=onHide;
    function onHide(){
        this.isOpen = false;
        //alert('onHide: ' +  this.isOpen);
    }
    
    this.show=show;
    function show(){        
        updateDialog();        
        dialog.show();
        isOpen = true;           
    }
    
    this.hide=hide;
    function hide(){        
        dialog.hide();
        isOpen = false;
        //alert('hide: ' +  this.isOpen);
    }   
    
    this.updateDialog=updateDialog;
    function updateDialog(){        
        //rcUpdateClient();
        alert('updateDialog: ' +  updateString + " isOpen" + isOpen);
        ercUpdateString(updateString);
    }   
    
}
